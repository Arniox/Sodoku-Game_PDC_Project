/*
 * Nikkolas Diehl - bjy5305 16945724.
 * Project 1 - PDC Project
 * .
 */
package pdc.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * This class holds and generates a single Sodoku Seed.
 * @author Nikkolas Diehl bjy5305
 */
public class SodokuGenerationAlgorithm {
    private Random randomNum = new Random();
    private DataPiece set = new DataPiece();
    private TextColours col = new TextColours();
    private String consoleLog = "";
    
    private Grid sodokuGame = new Grid(); //set up the new Grid Object
    
    //Creates a list of available chars per individual 3x3 cell. This is emptied per cell
    private List<Character> availableChars = new ArrayList();
    //Creates a list of used row chars per the whole grid. This is filled as the loop goes on
    private ArrayList<Character>[] rows = new ArrayList[9];
    //Creates a list of used column chars per the whole grid. This is filled as the loop goes on
    private ArrayList<Character>[] columns = new ArrayList[9];
    //Creates text in which the entire game exists
    private char[][] sodokuSeed = new char[9][9];
    
    /**
     * 
     * @return Grid Object
     */
    public Grid getSodokuGame()
    {
        return this.sodokuGame;
    }
    
    /**
     * 
     * @return char 3D array holding the seed
     */
    public char[][] getSodokuSeed()
    {
        return this.sodokuSeed;
    }
    /**Main algorithm for filling the grid with a seed
     * @return errorCode
     * @exception 299 (Index out of bounds. This happens when NOTHING at all is found for swapping)
     * @exception 114 (No values could go in the first spot in a column)
     * @exception 409 (Resultent bug is an infite loop as it tries to switch in the value)
     */
    public ArrayList fillSodokuGame()
    {
        //System.out.println(col.getANSI_RED()+"Console Log: In Main Function"+col.getANSI_RESET());
        consoleLog+="Console Log: In Main Function\n";
        ArrayList errorCode = new ArrayList();
        
        this.resetAvailableChars(); //Calls function to fill the available char list to be pulled from
        this.initiateRows(); //Initiates the row list with new ArrayLists
        this.initiateColumns(); //Initiates the columns list with new ArrayLists
        this.initiateCompleteGrid(); // Initiates the entire grid
        this.initiateCharSeedArray(); //Fill the char seed array with empty spaces for initiation
        this.set.initiateUsedTestValues(); //Fill test values for later
        
        
        for(int i=0;i<9;i++) //For each row
        {
            for(int k=0;k<9;k++) //For each column, or single tile going along the rows
            {
                if(!(this.columns[i].isEmpty()) || !(this.rows[k].isEmpty()) ||
                   !(this.sodokuGame.getThreeByThreeGrid()[k/3][i/3].isEmpty()))
                {
                    boolean filled = false; //Set a condition for the while loop
                    int g = 0; //Set an increasing value for checking the availableChars
                    while(filled==false){
                        if(!(this.columns[i].contains(this.availableChars.get(g))) && !(this.rows[k].contains(this.availableChars.get(g)))
                           && !(this.sodokuGame.getThreeByThreeGrid()[k/3][i/3].checkForValue(this.availableChars.get(g))))
                        {   //Just fill a tile with a new value at index 0. Because of Collections.suffle, this should be a random value
                            //Creat a new char value to hold what was inside availableChars at index 0
                            char valueAt = this.availableChars.get(g); //Grab the next available char at 'g'
                            this.setValues(k, i, valueAt);
                            filled = true; //Finish the filled loop
                            this.availableChars.remove(g); //Remove the available char from the randomised ArrayList since it's been used
                        }else {
                            filled = false;
                            g++;//If the value in availableChars at index g was used in either the columns or rows,
                                //increase g by 1 to check the next value
                                //If we reach the end of all available chars and havn't found anything, activate backtracking algorithm
                            if(g>=this.availableChars.size()){
                                k = this.backTracking(i, k, filled);
                                if(k==299 || k==114 || k==409){
                                    //Error on line 299, 114 or 409. Fail entire sodoku game
                                    errorCode.add(k);
                                    errorCode.add(consoleLog);
                                    return errorCode;
                                }
                                filled = this.set.getFilled();
                            }
                        }
                    }
                    
                }else{ //Otherwise, if the columns and rows are empty, use the same filling algorithm as above
                    char valueAt = this.availableChars.get(0);
                    this.setValues(k, i, valueAt);
                    this.availableChars.remove(0);
                }
            }
            this.resetAvailableChars();
        }
        //System.out.println(col.getANSI_RED()+"Console Log: Escape main Function"+col.getANSI_RESET());
        consoleLog+="Console Log: Escape main Function\n";
        errorCode.add(0);
        errorCode.add(consoleLog);
        return errorCode; //Error code 0 - Nothing errored
    }
    
    /**
     * Back tracking algorithm for when all values are checked to go in position i,k
     * @param i
     * @param k
     * @param truthFalse
     * @return k
     */
    private int backTracking(int i, int k, boolean truthFalse)
    {
        //System.out.println(col.getANSI_RED()+"Console Log: In backTracking function"+col.getANSI_RESET());
        consoleLog+="Console Log: In backTracking function\n";
        this.set.setFilled(truthFalse);
        
        for(int w=8;w>=0;w--) //For each tile in a row going backwards from the very edge
        {
            //Because we havn't filled the currently selected tile yet, 
            //we have to go back by one tile on the first loop
            while(this.sodokuSeed[w][i] == ' '){
                w-=1;
                if(w<0){
                    return 114; //Error on line 114 - No values could go in the first spot in a column.
                                //Previous times this has happened has resulted in an endless loop so just error and start again.
                }
            }
            //Save all values at the backtracking location to backup
            char valueInGame = this.sodokuGame.getThreeByThreeGrid()[w/3][i/3].getThreeByThree()[w%3][i%3].getValue();
            //Delete and remove all values at the backtracking location
            this.deleteValues(w, i);
            //Set a boolean backTrackingLogger for the while loop
            boolean backTrackLogger = false;
            int v = 0;
            //For each tile we go back to, check all the values still available in availableChars to see if any of them
            //would have worked further back
            while(backTrackLogger == false)
            {
                //If any values work; If any value in the availableChars would have worked in a backtracked location AND
                //if the old value that was in the backtracked location works in the new location at ixk
                //AND if a value in availableChars worked in the cell
                if((!(this.columns[i].contains(this.availableChars.get(v))) && !(this.rows[w].contains(this.availableChars.get(v))))
                    &&(!(this.columns[i].contains(valueInGame)) && !(this.rows[k].contains(valueInGame)))
                    &&(!(this.sodokuGame.getThreeByThreeGrid()[w/3][i/3].checkForValue(this.availableChars.get(v))))
                    &&(!(this.sodokuGame.getThreeByThreeGrid()[k/3][i/3].checkForValue(valueInGame))))
                {
                    //Set the back tracked location to the new usable value in the availableChars
                    char valueAt = this.availableChars.get(v);
                    this.setValues(w, i, valueAt);
                    //Put the old value back into available chars for use
                    this.availableChars.remove(v);
                    this.availableChars.add(valueInGame);

                    //End the loops. This has been solved
                    backTrackLogger = true;
                    this.set.setFilled(true);
                    w=-500;
                    k-=1;
                    //System.out.println(col.getANSI_RED()+"Console Log: Escape backTracking Function"+col.getANSI_RESET());
                    consoleLog+="Console Log: Escape backTracking Function\n";
                    return k;
                }
                //Once we run out of the availableChars, move on to the next back tracked tile
                else if((v+1)>=this.availableChars.size())
                {
                    //Re-insert the old back tracked value into the back tracked location
                    this.setValues(w, i, valueInGame);

                    if(w==0)//If completely run out of options. Go back a column and backtrack
                    {
                        w = this.columnBackTracking(i, k, w, backTrackLogger);
                        if(w==299 || w==409){
                            //Error on line 340 or 409. Fail entire sodoku game
                            return w;
                        }
                        backTrackLogger = this.set.getBackTracker();
                    }

                    //End the while loop, but go to the next tile backwards to keep looking
                    backTrackLogger = true;
                }
                //Otherwise, keep checking valuse in the availableChars
                else{
                    backTrackLogger = false;
                    v++;
                }
            }
        }
        return k;
    }
    
    /**
     * Column back tracking algorithm for when all backtracked targets have been extinguished<br>
     * Runs back through all previous columns and flips values around from i, k to try remove clashes
     * @param i
     * @param k
     * @param w
     * @param truthFalse
     * @return w
     */
    private int columnBackTracking(int i, int k, int w, boolean truthFalse)
    {
        //System.out.println(col.getANSI_RED()+"Console Log: In columnBackTracking Function"+col.getANSI_RESET());
        consoleLog+="Console Log: In columnBackTracking Function\n";
        this.set.setBackTracker(truthFalse);
        //This is also column by row.
        //If the backtracking gets to this point, give up single column backtracking and insert the value into the tile anyways
        //Ignoring all checks. Then go back through previous columns and try switch values around until the previous columns suite the new column
        boolean parentFilled = false;
        int j = 0;
        while(parentFilled == false)
        {
            char valueAt = ' ';
            //If all values have been checked with all backtracking, then try switch the current valueAt with an older value whilst ignoring the switched value rules
            if(j>=this.availableChars.size()){
               j = this.ifTryAll(i, k, j);
               if(j==299){
                   //Error on line 340. Fail entire sodouku game
                   return 299;
               }
            }

            //If the value is already used inside the cell, then set the new value to the cell above and try backtracking
            if(this.sodokuGame.getThreeByThreeGrid()[k/3][i/3].checkForValue(this.availableChars.get(j)))
            {
                valueAt = this.ifInCell(i, k, j, valueAt);
                if(valueAt == 409){
                    return 409; //Error 409 - Total unability to place value
                }
            }else{
                //Set value, ignoring clash and wait for the previous column checkers to finish
                valueAt = this.availableChars.get(j);
                this.setValues(k, i, valueAt);
                this.availableChars.remove(j);
            }

            for(int l=i-1;l>=0;l--)
            {
                for(int b=8;b>=0;b--)
                {
                    char switchValueGame = ' ';
                    char previousColumnGame = ' ';
                    
                    previousColumnGame = this.sodokuGame.getThreeByThreeGrid()[b/3][l/3].getThreeByThree()[b%3][l%3].getValue();
                    this.deleteValues(b, l);
                    //Important if statement. This means that if the columnBackTracker is trying to swap the same value on the same spot.
                    //If the if statement wasn't here, the value at b, l would get deleted by the above deleteValues and the switchValueGame would become ' '
                    //Originally, this caused a bug due to the function being run actually removing values from the ArrayList which caused IndexOutOfBoundException throw
                    if(!(k==b)){
                        switchValueGame = this.sodokuGame.getThreeByThreeGrid()[k/3][l/3].getThreeByThree()[k%3][l%3].getValue();
                        this.deleteValues(k, l);
                    }
                    else{
                       switchValueGame = previousColumnGame;
                    }
                    
                    if((!(this.sodokuGame.getThreeByThreeGrid()[b/3][l/3].checkForValue(switchValueGame))
                            &&!(this.columns[l].contains(switchValueGame))
                            &&!(this.rows[b].contains(switchValueGame)))
                        &&(!(this.sodokuGame.getThreeByThreeGrid()[k/3][l/3].checkForValue(previousColumnGame))
                            &&!(this.columns[l].contains(previousColumnGame))
                            &&!(this.rows[k].contains(previousColumnGame)))
                        &&(!((Collections.frequency(this.rows[k], valueAt))>=2)))
                    {
                        //Switch the two values found that work together and work with the new value being inserted without checks
                        this.setValues(b, l, switchValueGame);
                        this.setValues(k, l, previousColumnGame);

                        b-=500;
                        l-=500;
                        parentFilled = true;
                        this.set.setBackTracker(true);
                        this.set.setFilled(true);
                        w-=500;
                        //System.out.println(col.getANSI_RED()+"Console Log: Escape columnBackTracking Function"+col.getANSI_RESET());
                        consoleLog+="Console Log: Escape columnBackTracking Function\n";
                        return w;
                    }
                    
                    //If the switch can't work, then switch back
                    this.setValues(b, l, previousColumnGame);
                    this.setValues(k, l, switchValueGame);
                }
            }

            //If the column values weren't switched, then pull the availableChars value out and add it to index 0.
            //The next value used from available chars is index 1
            if(parentFilled==false){
                valueAt = this.sodokuGame.getThreeByThreeGrid()[k/3][i/3].getThreeByThree()[k%3][i%3].getValue();
                this.deleteValues(k, i);
                this.availableChars.add(0, valueAt);
            }

            j++;
        }
        return -500;
    }

    /**
     * If all values and all backtracked locations and all columns have been tested and extinguish, then try swap the value at i, k anyways
     * @param i
     * @param k
     * @param j
     * @param truthFalse
     * @return j
     */
    private int ifTryAll(int i, int k, int j)
    {
        //System.out.println(col.getANSI_RED()+"Console Log: In ifTryAll Function"+col.getANSI_RESET());
        consoleLog+="Console Log: In ifTryAll Function\n";
        boolean swapCheckTest = false;
        int c = 0;
        while(swapCheckTest == false){
            for(int n=8;n>=0;n--)
            {
                while(this.sodokuSeed[n][i] == ' '){
                    n-=1;
                }
                char lastCall = this.sodokuGame.getThreeByThreeGrid()[n/3][i/3].getThreeByThree()[n%3][i%3].getValue();
                //Delete and remove all values at the backtracking location
                this.deleteValues(n, i);
                //Skip check for switching value. Just check that the available char can fit in a slot above, but
                //don't check that the swapped value can come down
                if((!(this.columns[i].contains(this.availableChars.get(c))) && !(this.rows[n].contains(this.availableChars.get(c))))
                    &&(!(this.sodokuGame.getThreeByThreeGrid()[n/3][i/3].checkForValue(this.availableChars.get(c))))
                    &&(!(this.sodokuGame.getThreeByThreeGrid()[k/3][i/3].checkForValue(lastCall))))
                {
                    //If this check already happened and found a working value but all the other checks ran out of all
                    //possible values to use and swap, then the loop is eventually going to come back here
                    //and will infintately loop switching the same two values over and over.
                    //Thus, if this whole if statement has already been used once, skip the first value it finds that is usable
                    if(!(this.set.getUsedTestValues().contains(this.availableChars.get(c)))){
                        char valueAt = this.availableChars.get(c);
                        this.set.getUsedTestValues().add(valueAt);
                        this.setValues(n, i, valueAt);
                        this.availableChars.remove(c);

                        this.availableChars.add(lastCall);
                        swapCheckTest = true;
                        n-=500;
                        j=0;
                        //System.out.println(col.getANSI_RED()+"Console Log: Escape ifTryAll Function"+col.getANSI_RESET());
                        consoleLog+="Console Log: Escape ifTryAll Function\n";
                        return j;
                    }
                    else{
                        this.setValues(n, i, lastCall);
                        swapCheckTest = false; //Try the next value
                    }
                }
                else{
                    //If the check fails, try the next value
                    this.setValues(n, i, lastCall);
                    swapCheckTest = false;
                }
            }
            c++;
            if(c>=this.availableChars.size() && swapCheckTest == false){
                return 299; //Error on this line - Index out of bounds. This happens when NOTHING at all is found for swapping
                            //If this error occures, fail entire sodoku game and start again.
            }
        }
        return 0;
    }
    
    /**
     * If the value being inserted at i, k already exists then swap backwards anyways and work from the new clashing value
     * @param i
     * @param k
     * @param j
     * @param valueAt
     * @return valueAt
     */
    private char ifInCell(int i, int k, int j, char valueAt)
    {
        //System.out.println(col.getANSI_RED()+"Console Log: In ifInCell Function"+col.getANSI_RESET());
        consoleLog+="Console Log: In ifInCell Function\n";
        boolean ifInCellFilled = false;
        for(int h=8;h>=0;h--)
        {
            while(this.sodokuSeed[h][i] == ' '){
                h-=1;
            }
            char lastCall = this.sodokuGame.getThreeByThreeGrid()[h/3][i/3].getThreeByThree()[h%3][i%3].getValue();
            //Delete and remove all values at the backtracking location
            this.deleteValues(h, i);
            //Skip check for switching value. Just check that the available char can fit in a slot above, but
            //don't check that the swapped value can come down
            if((!(this.columns[i].contains(this.availableChars.get(j))) && !(this.rows[h].contains(this.availableChars.get(j))))
                &&(!(this.sodokuGame.getThreeByThreeGrid()[h/3][i/3].checkForValue(this.availableChars.get(j))))
                &&(!(this.sodokuGame.getThreeByThreeGrid()[k/3][i/3].checkForValue(lastCall))))
            {
                valueAt = this.availableChars.get(j);
                this.setValues(h, i, valueAt);
                this.availableChars.remove(j);

                //Set value, ignoring clash and wait for the previous column checkers to finish
                this.setValues(k, i, lastCall);
                //Important last step. This switches valueAt to the value we pulled out of availableChars.
                //The reason we did this is because we looked for a spot where our old valueAt could go (i, h),
                //without checkingwhere the new value can go. Essentially a one sided swap. 
                //This results in the swaped value at (i, k) clashing with something in row[k]
                //so we need to make valueAt the value we swaped out of (i,h) so that it can be used in later algorithms
                valueAt = lastCall;
                h-=500;
                ifInCellFilled = true;
                //System.out.println(col.getANSI_RED()+"Console Log: Escape ifInCell Function"+col.getANSI_RESET());
                consoleLog+="Console Log: Escape ifInCell Function\n";
                return valueAt;
            }
            else{
                //If the check fails, try the next value
                this.setValues(h, i, lastCall);
            }
        }
        if(ifInCellFilled == false){
            return 409; //Error code 409 - Tried putting the value somewhere else above whilst checking to make sure old value isn't in current cell and new value isn't going
                        //into cell. This error means it didn't find anything. This means that say a valueAt of 'd' has been checked along all slots and clashes with either the cell
                        //or the row. OR, that any old value being switched out already exists in the current cell which means the old value cannot be switch in either.
                        //The resultent bug is an infite loop as it tries to switch in the value.
        }
        return valueAt;
    }
    
    /**
     * sets the value given into the grid, column, row and char array seed at a given position<br>
     * x is equal to k/w/b/n/h<br>
     * y is equal to i/l<br>
     * @param x
     * @param y
     * @param value 
     */
    private void setValues(int x, int y, char value){
        this.sodokuGame.getThreeByThreeGrid()[x/3][y/3].getThreeByThree()[x%3][y%3].setVaule(value);
        this.columns[y].set(x, value);
        this.rows[x].set(y, value);
        this.sodokuSeed[x][y] = value;
    }
    
    /**
     * Deletes the value from the grid, column, row and char array seed at a given position<br>
     * Sets the deleted value as a space character instead of removing the index
     * x is equal to k/w/b/n/h<br>
     * y is equal to i/l<br>
     * @param x
     * @param y
     */
    private void deleteValues(int x, int y){
        this.sodokuGame.getThreeByThreeGrid()[x/3][y/3].deleteValue(x%3, y%3);
        this.columns[y].set(x, ' ');
        this.rows[x].set(y, ' ');
        this.sodokuSeed[x][y] = ' ';
    }
    
    /**
     * initiates the complete grid<br>
     * runs sodokuGame.initiateCells() which fills each cell with a 3 by 3 grid of tiles<br>
     * runs, in a 3D loop, sodokuGame.getThreeByThreeGrid()[i][k].initiateTiles() which fills each cell with new tiles
     */
    private void initiateCompleteGrid() //Function for initiating the entire grid
    {
        this.sodokuGame.initiateCells(); //For the sodokuGame (Grid type), initiate all Cells of 3x3 size
        for(int i=0;i<3;i++) //For the threeByThree inside the sodokuGame, inside each Cell of 3x3 size, initiate each tile
        {
            for(int k=0;k<3;k++)
            {
                this.sodokuGame.getThreeByThreeGrid()[i][k].initiateTiles(); //Calls the initiate tile function inside each Cell
            }
        }
    }
    
    /**
     * initiates the char array for the game seed<br>
     * runs, in a 3D loop, a filling command that puts a space character in every element of the seed char array
     */
    private void initiateCharSeedArray()
    {
        for(int i=0;i<9;i++)
        {
            for(int k=0;k<9;k++)
            {
                this.sodokuSeed[i][k] = ' ';
            }
        }
    }
    
    /**
     * resets all available chars for the available chars ArrayList that is drawn from per column<br>
     * adds the letters a-i into an ArrayList in order<br>
     * runs Collections.shuffle(this.availableChars) to shuffle the usable chars in the ArrayList for randomization
     */
    private void resetAvailableChars() //Function for refilling the available chars
    {
        this.availableChars.add('a');
        this.availableChars.add('b');
        this.availableChars.add('c');
        this.availableChars.add('d');
        this.availableChars.add('e');
        this.availableChars.add('f');
        this.availableChars.add('g');
        this.availableChars.add('h');
        this.availableChars.add('i');
        int r=randomNum.nextInt(50);
        for(int i=0;i<r;i++) //Shuffle the collection a random amount of times, up to 20 times
        {
            Collections.shuffle(this.availableChars);
        }
    }
    
    /**
     * initiates all of the rows in the ArrayList array
     */
    private void initiateRows() //Fill the row list array with new ArrayLists
    {
        for(int i=0;i<9;i++)
        {
            this.rows[i] = new ArrayList();
            for(int k=0;k<9;k++)
            {
                this.rows[i].add(' ');
            }
        }
    }
    
    /**
     * initiates all of the columns in the ArrayList array
     */
    private void initiateColumns() //Fill the columns list array with new ArrayLists
    {
        for(int i=0;i<9;i++)
        {
            this.columns[i] = new ArrayList();
            for(int k=0;k<9;k++)
            {
                this.columns[i].add(' ');
            }
        }
    }
    
    /**
     * prints out the seed char array in a game format
     */
    private void printSodokuSeed()
    {
        for(int i=0;i<9;i++){
            if(i==0){
                System.out.print("----------------------\n");
            }
            for(int k=0;k<9;k++){
                if(k==0 || k==3 || k==6){
                    System.out.print("|");
                }
                System.out.print(this.sodokuSeed[i][k] + " ");
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
