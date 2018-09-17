/*
 * Nikkolas Diehl - bjy5305 16945724.
 * Project 1 - PDC Project
 * .
 */
package pdc.project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nikkolas Diehl - bjy5305 16945724.
 */
public class GUIControl {
    
    //Set up GUIModel for MVC controling
    private GUIModel model;
    /**
     * Take in GUIModel mainModel and set this.model to mainModel
     * @param mainModel GUIModel
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public GUIControl(GUIModel mainModel){
        model = mainModel;
    }
    /**
     * Returns GUIModel
     * @return model GUIModel
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public GUIModel getMyView() {
        return this.model;
    }

    //Game Values
    private char[][] seed = null;
    private char[][] completedGame = null;
    private char[][] uncompletedGame = null;
    private char[][] playerValues = new char[9][9];
    private char[][] printingGame = new char[9][9];
    //Previous Games
    ArrayList previousGamesSet = new ArrayList();
    //Scores
    int score = 0; //At index 0 is score (line 0)
    int gamesPlayed = 0; //At index 1 is total gamesPlayed (line 1)
    
    //Important values
    private float averageCalcTime = -1;
    private int difficultyValue=0;
    
    //Set up SodokuGenerationAlgorithm, FileIO and GamePrinter ojects
    private SodokuGenerationAlgorithm gameSeed; //Create gameSeed generation algorithm
    private DataBaseIO dbIO = new DataBaseIO();
    private GamePrinter gamePrinter = new GamePrinter(); //Create gamePrinter
    
    //Set up basic fonts
    private Font fontForLabel = new Font("Lucida Sans", Font.BOLD, 26);
    private Font fontForTextArea = new Font("Lucida Sans", Font.BOLD, 20);
    
    //Set frame size and exact center of any monitor
    private Toolkit kit=Toolkit.getDefaultToolkit();
    private Dimension screenSize=kit.getScreenSize();
    private Point frameSize = new Point((screenSize.width/2)+700, (screenSize.height/2)+350);
    private Point centerPoint = new Point((screenSize.width/2)-frameSize.x/2, (screenSize.height/2)-frameSize.y/2);
    
    
    //BUTTON FUNCTIONS
    /**
     * newGameButtonFunction is the functionality for the new game button
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public void newGameButtonFunction(){
        model.setState(GUIModel.MENUSTATE.NEWGAMESTG1);
        dbIO.deleteSavedGame();
        previousGamesSet = new ArrayList();
        model.getMyView().getSavedGame().setText("");
        model.getMyView().getSavedGame().setEnabled(false);
        difficultyValue = 0;

        this.setDiffSliderTextColour(Color.YELLOW);
        model.getMyView().getDifficultyRating().setEnabled(true);
        model.getMyView().getDifficultyRating().setValue(3);

        model.getMyView().getSteps().setText("Select a Difficulty Rating - The Higher, the Harder");
        model.getMyView().getSteps().setBounds(frameSize.x/2-524/2, (int)((frameSize.y)*0.17), 524, 33);

        model.changeMenu();
    }
    
    /**
     * generateButtonFunction is the functionality for the generate button
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public void generateButtonFunction(){
        int numOfSeeds = model.getMyView().getAmountOfSeeds().getValue();
        String infoOutput = "Generating: "+numOfSeeds+" seeds...\n";

        model.getMyView().getData().append(infoOutput);

        this.createNewSeeds(numOfSeeds); //Create numOfSeeds new seed(s)
    }
    
    /**
     * setDifficultyButtonFunction is the functionality for the confirm difficulty button
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public void setDifficultyButtonFunction(){
        difficultyValue = (model.getMyView().getDifficultyRating().getValue());
           
        model.getMyView().getSteps().setForeground(Color.LIGHT_GRAY);
        model.getMyView().getSteps().setText("Difficulty Chosen: "+difficultyValue);
        model.getMyView().getSteps().setBounds(frameSize.x/2-205/2, (int)((frameSize.y)*0.17), 205, 33);

        this.flipDiffValue();

        //Set up char arrays with new game
        ArrayList set = dbIO.getRandomSeed(-5);
        if(set==null){ //If seed file does not exist, create 1 new seed and get it
            this.createNewSeeds(1);
            set = dbIO.getRandomSeed(-5);
        }

        seed = (char[][])set.get(0);
        completedGame = gamePrinter.assigner(seed, true);
        uncompletedGame = gamePrinter.invisableAssigner(completedGame, difficultyValue);

        model.getMyView().getDifficultyRating().setEnabled(false);
        model.getMyView().getSetDifficulty().setVisible(false);

        model.getMyView().getSeedsToUseLabel().setVisible(true);
        model.getMyView().getSeedsToUseLabel().setText((String)set.get(1));
        model.getMyView().getSeedsToUseLabel().setBounds(frameSize.x/2-1000/2, (int)((frameSize.y)*0.32), 1000, 33);

        model.getMyView().getSeedToUse().setVisible(true);
        model.getMyView().getSeedToUse().setText(this.convert3DArrayToString(seed));

        model.getMyView().getGameToUseLabel().setVisible(true);
        model.getMyView().getGameToUse().setVisible(true);
        model.getMyView().getGameToUse().setText(this.convert3DArrayToString(uncompletedGame));

        model.getMyView().getContinueButton().setVisible(true);
    }
    
    /**
     * continueButtonFunction is the functionality for the continue button
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public void continueButtonFunction(boolean newGame){
        model.setState(GUIModel.MENUSTATE.GAMEINPROGRESS);
        model.getMyView().inProgressGameActivate(frameSize);
        
        ArrayList scoreSet = dbIO.getScore(); //Create a set from the getScore function. Holds an int value for score and int value for total games played
        score = (int)scoreSet.get(0); //At index 0 is score (line 0)
        gamesPlayed = (int)scoreSet.get(1); //At index 1 is total gamesPlayed (line 1)
        gamesPlayed++;
        dbIO.saveScore(score, gamesPlayed);

        model.getMyView().getGamesPlayedLabel().setText("Total Games Played: "+gamesPlayed);
        model.getMyView().getTotalScoreLabel().setText("Total score: "+score+" games won");

        if(newGame){
            for(int i=0;i<9;i++){
                for(int j=0;j<9;j++){
                    if(!(uncompletedGame[i][j] == ' ')){
                        model.getMyView().getTextFieldGrid()[i][j].setText(""+uncompletedGame[i][j]);
                        model.getMyView().getTextFieldGrid()[i][j].setBackground(new Color(120,100,100,180));
                        model.getMyView().getTextFieldGrid()[i][j].setEnabled(false);
                    }else{
                        model.getMyView().getTextFieldGrid()[i][j].setText("");
                        model.getMyView().getTextFieldGrid()[i][j].setForeground(Color.ORANGE);
                        model.getMyView().getTextFieldGrid()[i][j].setEnabled(true);
                    }
                }
            }
        }else{
            for(int i=0;i<9;i++){
                for(int j=0;j<9;j++){
                    if(!(printingGame[i][j] == ' ') && !(playerValues[i][j] == ' ')){ //If the value found is in printingGame AND playerValues, the value needs to be able to be changed
                        model.getMyView().getTextFieldGrid()[i][j].setText(""+printingGame[i][j]);
                        model.getMyView().getTextFieldGrid()[i][j].setForeground(Color.ORANGE);
                        model.getMyView().getTextFieldGrid()[i][j].setEnabled(true);
                    }else if(!(printingGame[i][j] == ' ') && (playerValues[i][j] == ' ')){ //If the value found is in printingGame but NOT in playerValues, then the value is stuck
                        model.getMyView().getTextFieldGrid()[i][j].setText(""+printingGame[i][j]);
                        model.getMyView().getTextFieldGrid()[i][j].setBackground(new Color(120,100,100,180));
                        model.getMyView().getTextFieldGrid()[i][j].setEnabled(false);
                    }else if(printingGame[i][j] == ' ' && playerValues[i][j] == ' '){
                        model.getMyView().getTextFieldGrid()[i][j].setText("");
                        model.getMyView().getTextFieldGrid()[i][j].setForeground(Color.ORANGE);
                        model.getMyView().getTextFieldGrid()[i][j].setEnabled(true);
                    }
                }
            }
        }
        
        model.changeMenu();
    }
    
    /**
     * finishGameButtonFunction is the functionality for the finish game button
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public void finishGameButtonFunction(){
        this.getPrintingGameFromTextFieldGrid();
        boolean winLost = gamePrinter.compareGames(completedGame, printingGame);
        
        if(winLost){
            model.getMyView().getWinLostStatus().setText("WINNER!");
            model.getMyView().getWhatToDoNow().setText("If you want to play again, simply go back and click new game");
            dbIO.deleteSavedGame();
            score++;
            dbIO.saveScore(score, gamesPlayed);
        }else{
            model.getMyView().getWinLostStatus().setText("LOSER!");
            model.getMyView().getWhatToDoNow().setText("<html>"
                    +"If You Want To Retry The Same Puzzle, Click Back And Select Continue Game<br/>"
                    +"Otherwise, If you want to play again, simply go back and click new game"
                    +"<html>");
            this.saveAndExitButtonFunction(false);
        }
        
        
        model.setState(GUIModel.MENUSTATE.WINSTATUSSCREEN);
        model.changeMenu();
    }
    
    /**
     * saveAndExitButtonFunction is the functionality for the finish game button
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public void saveAndExitButtonFunction(boolean dispose){
        this.getPrintingGameFromTextFieldGrid();
        this.getPlayerValuesFromTextFieldGrid();
        
        dbIO.saveGame(printingGame, completedGame, playerValues, uncompletedGame);
        
        if(dispose){
            closeAll();
            model.getMyView().dispose();
        }
    }
    
    /**
     * continueGameButtonFunction is the functionality for the continue game button
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public void continueGameButtonFunction(){
        model.setState(GUIModel.MENUSTATE.CONTINUE);
        
        
        previousGamesSet = dbIO.getSavedGame();
        if(previousGamesSet==null){
            model.getMyView().getGameFoundStatus().setText("Previous Game Not Found");
            model.getMyView().getSavedGame().setEnabled(false);
        }else{
            model.getMyView().getGameFoundStatus().setText("Previous Game Found!");
            model.getMyView().getSavedGame().setEnabled(true);
            model.getMyView().getSavedGame().setText(convert3DArrayToStringBreaks((char[][])previousGamesSet.get(0)));
        }
        
        model.changeMenu();
    }
    
    /**
     * savedGameButtonFunction is the functionality for the saved game button
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public void savedGameButtonFunction(){
        
        printingGame = (char[][])previousGamesSet.get(0);
        completedGame = (char[][])previousGamesSet.get(1);
        playerValues = (char[][])previousGamesSet.get(2);
        uncompletedGame = (char[][])previousGamesSet.get(3);
        
        ArrayList scoreSet = dbIO.getScore();
        score = (int)scoreSet.get(0);
        gamesPlayed = (int)scoreSet.get(1);
        gamesPlayed--; //If you continue a game, you're not playing a new game which is what the continueButtonFunction is adding to
        dbIO.saveScore(score, gamesPlayed);
        
        continueButtonFunction(false);
        
    }
    
    
    //Important Funnctions
    /**
     * getPrintingGameFromTextFieldGrid retrieves all values from the TextField Grid
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public void getPrintingGameFromTextFieldGrid(){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(!(model.getMyView().getTextFieldGrid()[i][j].getText().isEmpty())){
                    printingGame[i][j] = model.getMyView().getTextFieldGrid()[i][j].getText().charAt(0);
                }else{
                    printingGame[i][j] = ' ';
                }
                
            }
        }
    }
    
    /**
     * getPlayerValuesFromTextFieldGrid retrieves only the player values entered into the TextField Grid
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public void getPlayerValuesFromTextFieldGrid(){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if((!(model.getMyView().getTextFieldGrid()[i][j].getText().isEmpty()))
                        &&(uncompletedGame[i][j] == ' ')){
                    playerValues[i][j] = model.getMyView().getTextFieldGrid()[i][j].getText().charAt(0);
                }else{
                    playerValues[i][j] = ' ';
                }
                
            }
        }
    }
    
    /**
     * Sets the back button to visible or not
     * @param e boolean
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public void setBackButton(boolean e){
        model.getMyView().getBack().setVisible(e);
    }
    
    /**
     * Sets color of slider font
     * @param col Color
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public void setDiffSliderTextColour(Color col){
        model.getMyView().getSteps().setForeground(col);
    }
    
    /**
     * Set the average time of seed generation
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public void setAverageTime(){
        if(averageCalcTime <= 0){
            model.getMyView().getTime().setText("Average calculation time not known");
        }else{
            model.getMyView().getTime().setFont(new Font("Lucida Sans", Font.BOLD, 17));
            model.getMyView().getTime().setText("This will take roughly "
                +(int)(model.getMyView().getAmountOfSeeds().getValue()/averageCalcTime)
                +" milliseconds");
            model.getMyView().getTime().setBounds(frameSize.x/2-380/2, (int)((frameSize.y)*0.17), 380, 20);
        }
        
    }
    
    /**
     * Convert char 3D array to string
     * @param seedChar char[][]
     * @return seed String
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public String convert3DArrayToString(char[][] seedChar){
        String seed = "";
        
        for(char[] row : seedChar){
            for(char cell : row){
                seed+=cell;
            }
        }
        
        return seed;
    }
    
    /**
     * Converts a given char[][] to a String
     * @param previousGame
     * @return st String
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public String convert3DArrayToStringBreaks(char[][] previousGame){
        //&nbsp; == one space
        //&ensp; == two spaces
        //&emsp; == four spaces
        
        String st = new String();
        st+="<html> ";
        
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                st += "[";
                if(previousGame[j][i] == ' '){
                    st+="&nbsp;&nbsp;]&emsp;&emsp;";
                }else{
                    st += previousGame[j][i]+"]&emsp;&emsp;";
                }
            }
            if(i!=8){
                st+="<br/><br/>";
            }
        }
        
        st+="</html>";
        return st;
    }
    
    /**
     * Flip Difficulty Value
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public void flipDiffValue(){
        switch(difficultyValue){
            case 1:
                difficultyValue = 5;
                break;
            case 2:
                difficultyValue = 4;
                break;
            case 3:
                difficultyValue = 3;
                break;
            case 4:
                difficultyValue = 2;
                break;
            case 5:
                difficultyValue = 1;
                break;
        }
    }
    
    
    /**
     * Takes in required seeds and generates that many seeds then sends them to the SeedFile
     * @param seedCount int
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public void createNewSeeds(int seedCount){
        ArrayList errorPiece = new ArrayList();
        ArrayList<char[][]> seedChunk = new ArrayList<char[][]>();
        int errorCode = 0;
        int errorCount = 0;
        model.getMyView().getConsoleLog().setText("");
        
        long start = System.currentTimeMillis(); //Count how long it takes to generate seeds
        
        for(int i=0;i<seedCount;i++)
        {
            do{
                gameSeed = new SodokuGenerationAlgorithm(); //Create new SodokuGenerationAlgorithm
                model.getMyView().getConsoleLog().append("\n\nStart\n");
                
                errorPiece = gameSeed.fillSodokuGame(); //Runs the fillSodokuGame function which generates a sodoku seed. This returns an arraylist
                errorCode = (int)errorPiece.get(0); //In the array list, at index 0, there is an error code. If it's 0, then no error, if else, then there's an error
                model.getMyView().getConsoleLog().append((String)errorPiece.get(1)); //In the array list, at index 1, there is the console log
                
                if(errorCode == 299 || errorCode == 114 || errorCode == 409){ //if the error code is anything but 0, then error handle
                    model.getMyView().getConsoleLog().append("\nError "+errorCode+" - Starting again\n-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-\n");
                    errorCount++; //Add to error count
                }
            }while(errorCode == 299 || errorCode == 114 || errorCode == 409); //If gameSeed errors, restart the sodoku game and start again.
           // fileIO.seedWriter(gameSeed.getSodokuSeed());
            seedChunk.add(gameSeed.getSodokuSeed());
            model.getMyView().getConsoleLog().append("\nFinished");
        }
        dbIO.seedWriter(seedChunk);
        
        // Get elapsed time in milliseconds
        long elapsedTimeMillis = System.currentTimeMillis()-start;
        // Get elapsed time in seconds
        float elapsedTimeSec = elapsedTimeMillis/1000F;        
        
        model.getMyView().getData().append("Added "+seedCount+" seeds to SodokuDB - Table SEEDS. \nNow a total of "+this.dbIO.countSeeds()+" seeds in database\n"
                + "- "+errorCount+"/"+seedCount+" failed and were re-generated\n");
        model.getMyView().getData().append("\nTotal time taken to generate seeds was: \n"
                +elapsedTimeSec+" seconds, or \n"
                +elapsedTimeMillis+" milliseconds\n\n\n");
        
        if(!(elapsedTimeMillis == 0)){
            this.setAverageCalcTime(((float)seedCount/(float)elapsedTimeMillis));
        }else{
            this.setAverageCalcTime(1);
        }       
        
        this.setAverageTime();
       
    }
    
    /**
     * Closes all db connections
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public void closeAll(){
        try {
            dbIO.conn.close();
            dbIO.statement.close();
            System.out.println(dbIO.url+ " closed connection...");
        } catch (SQLException ex) {
            Logger.getLogger(GUIControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    //Getters
    public GUIModel getModel() {
        return this.model;
    }
    public DataBaseIO getDbIO() {
        return this.dbIO;
    }
    public char[][] getSeed() {
        return this.seed;
    }
    public char[][] getCompletedGame() {
        return this.completedGame;
    }
    public char[][] getUncompletedGame() {
        return this.uncompletedGame;
    }
    public char[][] getPlayerValues() {
        return this.playerValues;
    }
    public char[][] getPrintingGame() {
        return this.printingGame;
    }
    public float getAverageCalcTime() {
        return this.averageCalcTime;
    }
    public int getDifficultyValue() {
        return this.difficultyValue;
    }
    public SodokuGenerationAlgorithm getGameSeed() {
        return this.gameSeed;
    }
    public GamePrinter getGamePrinter() {
        return this.gamePrinter;
    }
    public Font getFontForLabel() {
        return this.fontForLabel;
    }
    public Font getFontForTextArea() {
        return this.fontForTextArea;
    }
    public Point getFrameSize() {
        return this.frameSize;
    }
    public Point getCenterPoint() {
        return this.centerPoint;
    }
    
    
    
    //Setters
    public void setModel(GUIModel model) {
        this.model = model;
    }
    public void setSeed(char[][] seed) {
        this.seed = seed;
    }
    public void setCompletedGame(char[][] completedGame) {
        this.completedGame = completedGame;
    }
    public void setUncompletedGame(char[][] uncompletedGame) {
        this.uncompletedGame = uncompletedGame;
    }
    public void setPlayerValues(char[][] playerValues) {
        this.playerValues = playerValues;
    }
    public void setPrintingGame(char[][] printingGame) {
        this.printingGame = printingGame;
    }
    public void setAverageCalcTime(float averageCalcTime) {
        this.averageCalcTime = averageCalcTime;
    }
    public void setDifficultyValue(int difficultyValue) {
        this.difficultyValue = difficultyValue;
    }
    
    
}
