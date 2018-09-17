/*
 * Nikkolas Diehl - bjy5305 16945724.
 * Project 1 - PDC Project
 * .
 */
package pdc.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class creates, saves and holds all information to be read and printed to files
 * @author Nikkolas Diehl bjy5305
 */
public class FileIO {
    TextColours col = new TextColours();
    
    /**
     * Takes in char 3D array and prints it to a file
     * @param seed 
     */
    public void seedWriter(char[][] seed){
        PrintWriter pr = null; //Set up a print writer
        try {
            pr = new PrintWriter(new FileWriter("SeedFile.txt", true)); //Create a file appending called SeedFile

            for(char[] seedRow : seed){ //For each row
                for(char seedColumn : seedRow){ //For each column
                    pr.print(Character.toLowerCase(seedColumn)); //print to the file
                }
            }
            pr.println(); //Close the print
            pr.close();
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a char 3D array containing a game seed
     * @return char[][]
     */
    public ArrayList getRandomSeed(int choice){
        Random ran = new Random();
        ArrayList set = new ArrayList();
        
        char[][] chosenSeed = new char[9][9]; //Create an empty 3D char array for return
        String seedsFound = "";
        
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("SeedFile.txt"));  //Set up BufferedReader
            String line; //Set a String line
            ArrayList<char[]> seedsArray = new ArrayList<char[]>(); //Set up an arrayList of char arrays for each line
            while((line = br.readLine())!=null) //While the line is not empty
            {
                char[] seedLine = new char[line.length()]; //Make a seedLine char array
                for(int i=0;i<line.length();i++){ //For the line.length() move the character in line at position i to the seedLine char array
                    seedLine[i] = line.charAt(i);
                }
                seedsArray.add(seedLine); //Once it scans in the entire line, add the line into the arrayList of char arrays
            }
            seedsFound+=seedsArray.size()+" Seeds found in SeedFile.txt. ";
            int count=0;
            char[] chosenLine;
            if(choice == 0){
                int chosenIndex = ran.nextInt(seedsArray.size());
                chosenLine = seedsArray.get(chosenIndex); //Choose a random char array from the seedsArray arrayList and set it to chosenLine
                seedsFound+="Seed "+chosenIndex+" out of "+seedsArray.size()+" chosen for use";
            }else{
                chosenLine = seedsArray.get(seedsArray.size()-1); //Choose a specifc seed at the end of the file (newest one generated)
                seedsFound+="Seed "+(seedsArray.size())+" out of "+seedsArray.size()+" chosen for use";
            }
            seedsArray = new ArrayList(); //Clean the seedsArray ArrayList to stop it from being a massive variable and taking up tones of memory
            for(int i=0;i<9;i++){ //For rows
                for(int k=0;k<9;k++){ //For columns
                    chosenSeed[i][k] = chosenLine[count]; //Set into the chosenSeed 3D array, a 2D array of chars
                    count++;
                }
            }
        br.close();
        }
        catch (FileNotFoundException ex) {
            return null;
        } catch (IOException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        set.add(chosenSeed);
        set.add(seedsFound);
        
        return set;
    }
    public int countSeeds(){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("SeedFile.txt"));  //Set up BufferedReader
            String line; //Set a String line
            ArrayList<char[]> seedsArray = new ArrayList<char[]>(); //Set up an arrayList of char arrays for each line
            while((line = br.readLine())!=null) //While the line is not empty
            {
                char[] seedLine = new char[line.length()]; //Make a seedLine char array
                for(int i=0;i<line.length();i++){ //For the line.length() move the character in line at position i to the seedLine char array
                    seedLine[i] = line.charAt(i);
                }
                seedsArray.add(seedLine); //Once it scans in the entire line, add the line into the arrayList of char arrays
            }
            return seedsArray.size();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    /**
     * Save a given game and score to a file called inProgressGame
     * @param score
     * @param game 
     */
    public void saveGame(char[][] comparibleGame, char[][] completedGame, char[][] playerValues, char[][] uncompletedGame){
        PrintWriter pr = null; //Set up a print writer
        try {
            pr = new PrintWriter(new FileWriter("inProgressGame.txt", false)); //Create a file refreshing any old ones called inProgressGame

            for(char[] gameRow : comparibleGame){ //Print the comparibleGame into the file
                for(char gameColumn : gameRow){
                    pr.print(Character.toLowerCase(gameColumn)); //print to the file
                }
            }
            pr.println(); //Print a new line
            for(char[] gameRow : completedGame){ //Print the completedGame into the file
                for(char gameColumn : gameRow){
                    pr.print(Character.toLowerCase(gameColumn));
                }
            }
            pr.println(); //Print a new line
            for(char[] gameRow : playerValues){ //Print the playerValues into the file
                for(char gameColumn : gameRow){
                    pr.print(Character.toLowerCase(gameColumn));
                }
            }
            pr.println();
            for(char[] gameRow : uncompletedGame){ //Print the uncompletedGame into the file
                for(char gameColumn : gameRow){
                    pr.print(Character.toLowerCase(gameColumn));
                }
            }
            pr.close();
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * get any saved game function finds
     * @return set (score and inProgressGame)
     */
    public ArrayList getSavedGame(){
        char[][] inProgressGame = new char[9][9]; //Create an empty char array for saved game to go into
        char[][] completedGame = new char[9][9];
        char[][] playerValues = new char[9][9];
        char[][] uncompletedGame = new char[9][9];
        ArrayList set = new ArrayList();
        
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("inProgressGame.txt"));  //Set up BufferedReader
            String line; //Set a String line
            int count = 0;
            while((line = br.readLine())!=null) //While the line is not empty
            {
                if(count == 0){ //On line 0, this is inProgressGame
                    char[] gameLine = new char[line.length()]; //Make a gameLine char array
                    for(int i=0;i<line.length();i++){ //For the line.length() move the character in line at position i to the gameLine char array
                        gameLine[i] = line.charAt(i);
                    }
                    int goingAlongLine = 0;
                    for(int i=0;i<9;i++){ //For rows
                        for(int k=0;k<9;k++){ //For columns
                            inProgressGame[i][k] = gameLine[goingAlongLine]; //Set into the inProgressGame 3D array, a 2D array of chars
                            goingAlongLine++;
                        }
                    }
                    set.add(inProgressGame);
                }else if(count == 1){ //On line 1, this is the completed game
                    char[] gameLine = new char[line.length()]; //Make a gameLine char array
                    for(int i=0;i<line.length();i++){
                        gameLine[i] = line.charAt(i);
                    }
                    int goingAlongLine = 0;
                    for(int i=0;i<9;i++){ //For rows
                        for(int k=0;k<9;k++){ //For columns
                            completedGame[i][k] = gameLine[goingAlongLine]; //Set into the completedGame 3D array, a 2D array of chars
                            goingAlongLine++;
                        }
                    }
                    set.add(completedGame);
                }else if(count == 2){ //On line 2, this is the player values
                    char[] gameLine = new char[line.length()];
                    for(int i=0;i<line.length();i++){
                        gameLine[i] = line.charAt(i);
                    }
                    int goingAlongLine = 0;
                    for(int i=0;i<9;i++){
                        for(int k=0;k<9;k++){
                            playerValues[i][k] = gameLine[goingAlongLine]; //Set into the playerValues 3D array, a 2D array of chars
                            goingAlongLine++;
                        }
                    }
                    set.add(playerValues);
                }else if(count == 3){
                    char[] gameLine = new char[line.length()];
                    for(int i=0;i<line.length();i++){
                        gameLine[i] = line.charAt(i);
                    }
                    int goingAlongLine = 0;
                    for(int i=0;i<9;i++){
                        for(int k=0;k<9;k++){
                            uncompletedGame[i][k] = gameLine[goingAlongLine]; //SEt into the uncompletedGame 3D array, a 2D array of chars
                            goingAlongLine++;
                        }
                    }
                    set.add(uncompletedGame);
                }
                count++;
            }
        br.close();
        }
        catch (FileNotFoundException ex) {
            return null;
        } catch (IOException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return set;
    }
    
    /**
     * deleteSaveFile deletes the inProgressGame file when a game is finished or a user does not want to use a saved game
     */
    public void deleteSavedFile()
    {
        File file = new File("inProgressGame.txt");
        file.delete();
    }
    
    /**
     * saveScore takes in a score and saves it to score
     * @param score 
     */
    public void saveScore(int score, int gamesPlayed){
        try {
            PrintWriter pr = null; //Set up a print writer
            pr = new PrintWriter(new FileWriter("score.txt", false)); //Create a file refreshing any old ones called inProgressGame
            pr.println(score);
            pr.print(gamesPlayed);
            pr.close();
        } catch (IOException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * getScore gets the score inside the score file
     * @return score
     */
    public ArrayList getScore(){
        ArrayList set =  new ArrayList();
        
        try {
            BufferedReader br = null;
            br = new BufferedReader(new FileReader("score.txt"));  //Set up BufferedReader
            String line;
            while((line = br.readLine())!=null) //While the line is not empty
            {
                set.add(Integer.valueOf(line));
            }
            br.close();
            return set;
            
        } catch (FileNotFoundException ex) {
            this.saveScore(0,0); //If score file not found. Create one with score 0
            set.add(0);
            set.add(0);
            return set;
        } catch (IOException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return set;
    }
}
