/*
 * Nikkolas Diehl - bjy5305 16945724.
 * Project 1 - PDC Project
 * .
 */
package pdc.project;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class creates the CLI(CUI/Command Line Interface) for the game
 * @author Nikkolas Diehl bjy5305
 */
public class CLI {
    SodokuGenerationAlgorithm gameSeed; //Create gameSeed generation algorithm
    FileIO fileIO = new FileIO(); //Create fileIO
    Scanner scan = new Scanner(System.in); //Create scan
    GamePrinter gamePrinter = new GamePrinter(); //Create gamePrinter
    TextColours col = new TextColours(); //Create text colour object
    int score = 0;
    int gamesPlayed = 0;
    
    /**
     * Start the CLI(CUI) but first check for a saved game file
     */
    public void runWithSavedGameFileCheck()
    {
        char[][] seed = new char[9][9]; //Create Seed array
        ArrayList scoreSet = fileIO.getScore(); //Create a set from the getScore function. Holds an int value for score and int value for total games played
        this.score = (int)scoreSet.get(0); //At index 0 is score (line 0)
        this.gamesPlayed = (int)scoreSet.get(1); //At index 1 is total gamesPlayed (line 1)
        
        System.out.println("Welcome to "+this.rainBowMagicSodoku());
        System.out.println("You have won "+score+" games out of "+gamesPlayed);
        System.out.println("The rules are simple: ");
        System.out.println("1 - All 3x3 cells must only contain 1 - 9 with no duplicates\n"
                         + "2 - All rows of length: 9, must only contain 1 - 9 with no duplicates\n"
                         + "3 - All columns of length: 9, must only contain 1 - 9 with no duplicates");
        System.out.println("This game has a save function. If you quit the game (stop the runtime) your current game will be saved"
                + "\n"+col.getANSI_BLUE()+"Quitting the game will lock your answers in permanently. "
                + "Restarting a saved game will not allow you to change locked answers"+col.getANSI_RESET());
        
        if(fileIO.getSavedGame() == null){ //No game found. Run normal function
            System.out.println(col.getANSI_RED()+"No saved game found...\nNew game starting: "+col.getANSI_RESET());
            this.run(seed);
        }else{ //Saved game found, ask if you want to use it
            System.out.println(col.getANSI_RED()+"Saved game found...\n"+col.getANSI_RESET());
            System.out.println("Do you wish to continue with your saved game? Type 'yes' for yes, and anything else to start a new game: ");
            if(scan.nextLine().equalsIgnoreCase("yes")){ //If the user wants to continue the saved game
                ArrayList set = fileIO.getSavedGame(); //Create a set from getSavedGame function
                this.lostOrWinPrint(this.playGame((char[][])set.get(0), (char[][])set.get(1))); //Run game function as normal, but with saved game
                if(scan.nextLine().equalsIgnoreCase("yes")){
                    fileIO.deleteSavedFile();
                    this.run(seed);
                }else{
                    fileIO.deleteSavedFile();
                    System.out.println("Sorry to see you go. Come back and play some "+rainBowMagicSodoku()+" another time!!!");
                }
            }else{
                fileIO.deleteSavedFile();
                System.out.println(col.getANSI_RED()+"New game starting..."+col.getANSI_RESET());
                this.run(seed);
            }
        }
    }
    
    public void run(char[][] seed)
    {
        do{
            System.out.println("\nAre you ready to play?\nType 'yes' for yes, and anything else for no");
            boolean startOrNo = scan.nextLine().equalsIgnoreCase("yes");
            if(startOrNo){
                System.out.println("This game runs on what's called game seeds. One seed is worth 29393280 (29 million) sodoku games");
                System.out.println("Would you like to use: \n"
                        + "     1 - A random, pre-generated sodoku seed, or \n"
                        + "     2 - Generate a chosen amount of sodoku seeds up to 1000?");
                switch(this.choices(1, 2)){
                    case 1:
                        seed = (char[][])fileIO.getRandomSeed(0).get(0); //Get a random seed from the SeedFile.txt
                        if(seed == null){ //If the seed is null, the file did not exist and was empty, thus fill it
                            System.out.println("SeedFile was not found, generating one seed for you...");
                            this.createNewSeeds(1); //Put one seed into the file
                            seed = (char[][])fileIO.getRandomSeed(1).get(0); //Get the specific seed you just generated from SeedFile.txt
                            System.out.println("Your seed has been captured for use in the game.");
                        }
                        break;
                    case 2:
                        System.out.print("number of seeds to generate: ");
                        int numOfSeeds = this.choices(1, 1000); //get number of seeds to generate
                        this.createNewSeeds(numOfSeeds); //Create numOfSeeds new seed(s)
                        seed = (char[][])fileIO.getRandomSeed(1).get(0); //Get the specific seed (if many generated, get last one) you just generated from SeedFile.txt
                        System.out.println("Since you generated new seeds; the last/latest seed generated has been captured for use in the game");
                        break;
                }
                char[][] game = gamePrinter.assigner(seed, true); //Run the gamePrinter object. This assigns each letter to a number. This is what means there is 9^81 different possible sodoku games
                                                            //Assuming a seed is already generated, there is an 9x9 square of tiles, each with a chance out of 9 to be a different number
                                                            //This results in 9!*81 different possible sodoku games per seed (29393280)
                
                System.out.println("\nThe way to play using MAGIC SODOKU (CLI version) is;\n"
                        + "1 - You will be asked for a position at (X,Y) and then a number.\n"
                        + "2 - The position is where you want your number to go to solve the sodoku game\n");
                System.out.println("Firstly, what difficulty game do you wish to play? Enter a 1 - 5 range of difficulty."
                        + "\nThe "+col.getANSI_BLUE()+"LOWER"+col.getANSI_RESET()+" your input, the harder the game.\n"
                        + "The "+col.getANSI_GREEN()+"HIGHER"+col.getANSI_RESET()+" your input, the easier the game.");
                int difficulty = this.choices(1, 5);
                char[][] printableGame = gamePrinter.invisableAssigner(game, difficulty); //Set some of the values in the game char array to invisable with a difficulty rating

                //this.printSodokuSeedGiven(game); //Print the completed game (Testing purposes)_UN-COMMENT THIS IF YOU WANT TO SEE THE COMPLETED GAME FOR EASE OF TESTING
                this.lostOrWinPrint(this.playGame(printableGame, game));
            }else{
                System.out.println("Sorry to see you go. Come back and play some "+rainBowMagicSodoku()+" another time!!!");
                return;
            }
        }while(scan.nextLine().equalsIgnoreCase("yes"));
        
        System.out.println("Sorry to see you go. Come back and play some "+rainBowMagicSodoku()+" another time!!!");
    }
    
    /**
     * Takes in printableGame and game and runs the play time for the user
     * @param printableGame
     * @param game
     * @return 
     */
    public boolean playGame(char[][] printableGame, char[][] game){
        char[][] emptyCharForGUI = new char[9][9];
        
        int x = 1; //X positon
        int y = 1; //Y positon
        int value = 0; //Value at positon x, y
        String[][] displayGame = new String[9][9]; //The String array for displaying the game as it changes in console
        char[][] comparibleGame = new char[9][9]; //ComparibleGame in char array for comparing the user version to the completed version
        this.copyRunningGame(printableGame, displayGame, comparibleGame); //Copies the printableGame to both the display game and comparibleGame. One as string, one as char
        
        //To keep track:
            //game is a char 3D array that holds a completed game transformed from letters to numbers. This is for comparison uses. It is not printed unless user failed
            //printableGame is a char 3D array that holds an uncompleted game transformed from a completed game. This is for algorithm uses. It is never printed
            //displayGame is a String 3D array that holds the user game with input, updated each time a user inputs and is the game that the user sees
            //comparibleGame is a char 3D array that holds the user game with input, updated each time a suer inputs but is not printed. Only for comparison uses
        
        boolean another = false; //Value for if the user wants to input another value or finish the game
        if(!(this.isFull(printableGame))){
            while(another == false){
                fileIO.saveGame(comparibleGame, game, emptyCharForGUI, emptyCharForGUI); //Save the game to a file everytime the game is updated so anytime the game is closed, it has it saved
                this.printSodokuStringArray(displayGame); //Print game per filled value
                System.out.println("X is equal to the column number (going from 1 - 9 "+col.getANSI_PURPLE()+"ACROSS"+col.getANSI_RESET()+" the board)\n"
                    + "Y is equal to the row number (going from 1 - 9 "+col.getANSI_CYAN()+"DOWN"+col.getANSI_RESET()+" the board");
                boolean accept = false;
                while(printableGame[y-1][x-1] != ' ' || accept == false || !(value>=1 && value<=9)){ //Checks the printableGame, NOT the playerGame. 
                    System.out.print("X = ");                                                        //This allows you to change your already input value
                    x = this.choices(1, 9);
                    System.out.print("Y = ");
                    y = this.choices(1, 9);
                    if((printableGame[y-1][x-1] != ' ')){
                        System.out.println("Sorry, you cannot change this value");
                    }
                    else{
                        System.out.print("Value = ");
                        value = this.choices(1, 9);

                        System.out.println("You are attempting to input "+value+" at position ("+x+", "+y+"). Are you happy with this? type 'yes' for yes, and anything else for no");
                        scan.nextLine();
                        if(scan.nextLine().equalsIgnoreCase("yes")){
                            displayGame[y-1][x-1] = col.getANSI_RED()+value+col.getANSI_RESET(); //Set into display game, the string value of user input with colour
                            comparibleGame[y-1][x-1] = (char)(value+48); //Set into the comparibleGame a char with 48 added to convert int input to char value
                            accept = true; //Set accept to true since user accpeted his choice
                            another = this.doContinue();
                        }
                        else{
                            accept = false;
                        }
                    }
                }
            }
        }else{
            System.out.println(col.getANSI_RED()+"\nYou have already completed this game. You cannot change any values now"+col.getANSI_RESET());
            this.printSodokuSeedGiven(printableGame);
        }
        
        fileIO.deleteSavedFile(); //Delete the savedGame file once you have finished it.
        if(gamePrinter.compareGames(game, comparibleGame)){
            return true; //You won
        }else{
            return false; //You lost
        }
    }
    
    /**
     * Tests if a 3D char array is full
     * @param array
     * @return boolean
     */
    public boolean isFull(char[][] array){
        for(char[] rows : array){
            for(char columns : rows){
                if(columns == ' '){
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Function to ask the user if they want to input a new value
     * @return 
     */
    public boolean doContinue(){
        System.out.println("\nAre you finished or do you want to input a new value? "
                + "Type 'yes' to continue, or type 'finished' to finish.");
        boolean fail = false;
        do{
            String userInputValue = scan.nextLine();
            if(userInputValue.equalsIgnoreCase("finished")){
                return true;
            }else if(userInputValue.equalsIgnoreCase("yes")){
                return false;
            }else{
                fail = false;
                System.out.print("Invalid input. Please answer again: ");
            }
        }while(fail == false);
        
        return false;
    }
    
    /**
     * Takes in the boolean value for if you won or lost and prints out user answer
     * @param winOrLost 
     */
    public void lostOrWinPrint(boolean winOrLost){
        if(winOrLost){//Run winLose function to play
            score++;
            gamesPlayed++;
            System.out.print("\nYou WON. Congradulations on completing a game of "+this.rainBowMagicSodoku()+"!!! - ");
            System.out.println("You have won "+score+" games out of "+gamesPlayed+"\n");
            System.out.println("Would you like to play again? noType 'yes' for yes, and anything else for no");
            
            fileIO.saveScore(score, gamesPlayed);
        }else{
            gamesPlayed++;
            System.out.println("\nYou LOST. That's a shame, but ah well. Thank you for playing "+this.rainBowMagicSodoku()+"!!!");
            System.out.print("You have won "+score+" games out of "+gamesPlayed+"\n");
            System.out.println("Would you like to play again? Type 'yes' for yes, and anything else for no");
            fileIO.saveScore(score, gamesPlayed);
        }
    }
    
    /**
     * Creates Rainbow Magic Sodoku Text
     * @return String
     */
    public String rainBowMagicSodoku(){
        String RainbowMagicSodoku = "";
        
        RainbowMagicSodoku = col.getANSI_BLUE()+"M"+col.getANSI_RESET()
                +col.getANSI_RED()+"A"+col.getANSI_RESET()
                +col.getANSI_CYAN()+"G"+col.getANSI_RESET()
                +col.getANSI_PURPLE()+"I"+col.getANSI_RESET()
                +col.getANSI_GREEN()+"C"+col.getANSI_RESET()
                +" "
                +col.getANSI_RED()+"S"+col.getANSI_RESET()
                +col.getANSI_BLUE()+"O"+col.getANSI_RESET()
                +col.getANSI_RED()+"D"+col.getANSI_RESET()
                +col.getANSI_CYAN()+"O"+col.getANSI_RESET()
                +col.getANSI_PURPLE()+"K"+col.getANSI_RESET()
                +col.getANSI_GREEN()+"U"+col.getANSI_RESET();
        
        return RainbowMagicSodoku;
    }
    
    /**
     * Takes in printableGame, displayGame and comparibleGame and copies printableGame to both displayGame and comparibleGame
     * @param printableGame
     * @param displayGame
     * @param comparibleGame 
     */
    public void copyRunningGame(char[][] printableGame, String[][] displayGame, char[][] comparibleGame){
        for(int i=0;i<9;i++){
            for(int k=0;k<9;k++){
                displayGame[i][k] = printableGame[i][k]+"";
                comparibleGame[i][k] = printableGame[i][k];
            }
        }
    }
    
    /**
     * Takes in required seeds and generates that many seeds then sends them to the SeedFile
     * @param seedCount 
     */
    public void createNewSeeds(int seedCount){
        ArrayList error = new ArrayList();
        int errorCode = 0;
        int errorCount = 0;
        for(int i=0;i<seedCount;i++)
        {
            do{
                gameSeed = new SodokuGenerationAlgorithm(); //Create new SodokuGenerationAlgorithm
                System.out.println(col.getANSI_RED()+"\nStart"+col.getANSI_RESET());
                error = gameSeed.fillSodokuGame();
                errorCode = (int)error.get(0);
                if(errorCode == 299 || errorCode == 114 || errorCode == 409){
                    System.out.println(col.getANSI_RED()+"Error "+errorCode+" - Starting again\n-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-"+col.getANSI_RESET());
                    errorCount++;
                }
            }while(errorCode == 299 || errorCode == 114 || errorCode == 409); //If gameSeed errors, restart the sodoku game and start again.
            fileIO.seedWriter(gameSeed.getSodokuSeed());
            System.out.println(col.getANSI_RED()+"Finished"+col.getANSI_RESET());
            //gameSeed.printSodokuSeed();
        }
        System.out.print("\n"+col.getANSI_RED()+"Generated "+seedCount+" seeds to SeedFile.txt - "+errorCount+"/"+seedCount+" failed and were re-generated"+col.getANSI_RESET()+"\n");
    }
    
    /**
     * Takes in two limiters for user choices and only accepts user input in between the limits
     * @param boundryStart
     * @param boundryEnd
     * @return user answer
     */
    public int choices(int boundryStart, int boundryEnd)
    {
        int num = 0;
        boolean loop = true;

        while (loop) {
            try {
                num = scan.nextInt();
                if(!(num>= boundryStart && num<=boundryEnd)){
                    System.out.println("Invalid Value");
                }else{
                    loop = false;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid value");
                scan.next(); // this consumes the invalid token
            } 
        }
        return num;
    }
    
    /**
     * prints out the given seed char array
     * @param givenSeed 
     */
    public void printSodokuSeedGiven(char[][] givenSeed){
        for(int i=0;i<9;i++){
            if(i==0){
                System.out.print("----------------------\n");
            }
            for(int k=0;k<9;k++){
                if(k==0 || k==3 || k==6){
                    System.out.print("|");
                }
                System.out.print(givenSeed[i][k] + " ");
                if(k==8){
                    System.out.print("|");
                }
            }
            System.out.print("\n");
            if(i==2 || i==5 || i==8){
                System.out.print("----------------------\n");
            }
        }
    }
    
    /**
     * prints out the given seed String Array
     * @param givenSeed 
     */
    public void printSodokuStringArray(String[][] givenSeed){
        for(int i=0;i<9;i++){
            if(i==0){
                System.out.print("----------------------\n");
            }
            for(int k=0;k<9;k++){
                if(k==0 || k==3 || k==6){
                    System.out.print("|");
                }
                System.out.print(givenSeed[i][k] + " ");
                if(k==8){
                    System.out.print("|");
                }
            }
            System.out.print("\n");
            if(i==2 || i==5 || i==8){
                System.out.print("----------------------\n");
            }
        }
    }
    
}
