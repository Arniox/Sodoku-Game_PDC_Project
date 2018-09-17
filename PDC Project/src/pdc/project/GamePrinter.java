/*
 * Nikkolas Diehl - bjy5305 16945724.
 * Project 1 - PDC Project
 * .
 */
package pdc.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * This class transforms a seed into a random game
 * @author Nikkolas Diehl bjy5305
 */
public class GamePrinter {
    ArrayList numbersToAsign;
    Random randomNum = new Random();
    
    /**
     * Assigner takes a seed char 3D array and assigns each letter to a random number to create a random game based on one seed
     * @param seed
     * @return a char 3D array completed sodoku game.
     */
    public char[][] assigner(char[][] seed, boolean random)
    {
        char[][] game = new char[9][9];
        this.numbersToAsign = new ArrayList();
        this.assignNumbers();
        if(random){
            this.shuffleNumbersToAsign();}
        
        for(int i=0;i<9;i++){
            for(int k=0;k<9;k++){
                if(seed[i][k]=='a'){
                    game[i][k]=(char)this.numbersToAsign.get(0);
                }
                else if(seed[i][k]=='b'){
                    game[i][k]=(char)this.numbersToAsign.get(1);
                }
                else if(seed[i][k]=='c'){
                    game[i][k]=(char)this.numbersToAsign.get(2);
                }
                else if(seed[i][k]=='d'){
                    game[i][k]=(char)this.numbersToAsign.get(3);
                }
                else if(seed[i][k]=='e'){
                    game[i][k]=(char)this.numbersToAsign.get(4);
                }
                else if(seed[i][k]=='f'){
                    game[i][k]=(char)this.numbersToAsign.get(5);
                }
                else if(seed[i][k]=='g'){
                    game[i][k]=(char)this.numbersToAsign.get(6);
                }
                else if(seed[i][k]=='h'){
                    game[i][k]=(char)this.numbersToAsign.get(7);
                }
                else if(seed[i][k]=='i'){
                    game[i][k]=(char)this.numbersToAsign.get(8);
                }
            }
        }
        return game;
    }
    
    /**
     * Function takes in two char 3D arrays and compares them.<br>
     * It checks the completed game to the player finished game to see if player won or lost the game
     * @param game
     * @param comparibleGame
     * @return true or false
     */
    public boolean compareGames(char[][] game, char[][] comparibleGame){
        for(int i=0;i<9;i++){
            for(int k=0;k<9;k++){
                if(!(comparibleGame[i][k] == game[i][k])){
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Randomly assigns hidden values to tiles in the sodoku game based on a difficulty rating
     * @param game
     * @param difficulty
     * @return a ready game with hidden values
     */
    public char[][] invisableAssigner(char[][] game, int difficulty){
        char[][] printableGame = new char[9][9];
        
        for(int i=0;i<9;i++){
            for(int k=0;k<9;k++){
                if(randomNum.nextInt(difficulty+1) == 0){ //A 1 out of difficulty chance of making invisable characters
                    printableGame[i][k] = ' ';
                }
                else{
                    printableGame[i][k] = game[i][k];
                }
            }
        }
        
        return printableGame;
    }
    
    /**
     * Assigns numbers to the numbersToAsign arrayList
     */
    private void assignNumbers(){
        this.numbersToAsign.add('1');
        this.numbersToAsign.add('2');
        this.numbersToAsign.add('3');
        this.numbersToAsign.add('4');
        this.numbersToAsign.add('5');
        this.numbersToAsign.add('6');
        this.numbersToAsign.add('7');
        this.numbersToAsign.add('8');
        this.numbersToAsign.add('9');
    }
    
    /**
     * Shuffles an ArrayList of numbers for random assignment to a seed
     */
    private void shuffleNumbersToAsign() //Function for refilling the available chars
    {
        int r=randomNum.nextInt(50);
        for(int i=0;i<r;i++) //Shuffle the collection a random amount of times, up to 20 times
        {
            Collections.shuffle(this.numbersToAsign);
        }
    }
}
