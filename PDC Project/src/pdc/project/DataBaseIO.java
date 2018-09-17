/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc.project;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bjy5305
 */
public class DataBaseIO {
    String url = "jdbc:derby:SodokuDB; create=true";
    String password = "jk";
    String username = "bjy5305";
    Connection conn;
    Statement statement;
    Statement scrollStatement;

    public DataBaseIO() {
        try {
            this.conn = DriverManager.getConnection(url, username, password);
            System.out.println(url+" connected...");
            
            //Create normal statement
            statement = conn.createStatement();
            //Create scrollable statements
            scrollStatement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * seedWriter takes in a 3D char array seed and saves it to the data base
     * @param seedChunk ArrayList<char[][]>
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public void seedWriter(ArrayList<char[][]> seedChunk){
        
        try {
            if(!(checkTable("SEEDS"))){
                String sqlCreateSeedTable = "CREATE TABLE SEEDS"
                        +"(SEED_NO VARCHAR(81))";

                statement.executeUpdate(sqlCreateSeedTable);
                conn.commit();
                System.out.println("Table SEEDS created and commited");
            }
            
            
            for(char[][] seed : seedChunk){
                String seedString = convertArrayToString(seed);
                statement.addBatch("INSERT INTO SEEDS(SEED_NO)"
                        + "VALUES('"+seedString+"')");
                System.out.println("Seed: "+seedString+" inserted into SEEDS table, SEED_NO column");
            }
            statement.executeBatch();
            conn.commit();
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * Gets random seed from db
     * @return set ArrayList<br>CONTAINS:<br>
     * chosenSeedArray char[][]<br>
     * seedsFound String
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public ArrayList getRandomSeed(int specificSeed){
        try {
            Random ran = new Random();
            ArrayList set = new ArrayList();
            
            String chosenLine = "";
            String stringToReturn = "";
            char[][] chosenSeedArray = new char[9][9];
            
            if(!(checkTable("SEEDS"))){
                System.out.println("SEEDS table not found");
                return null;
            }
            ResultSet rs = statement.executeQuery("SELECT * FROM SEEDS");
            int seedCount = this.countSeeds();
            int chosenIndex = ran.nextInt(seedCount);
            
            int t;
            if(specificSeed!=-5){
                t = specificSeed;
            }else{
                t = chosenIndex;
            }
            
            while(t>=0){
                rs.next();
                t--;}
            
            chosenLine = (String)rs.getString("SEED_NO");
            stringToReturn+="Seed "+(chosenIndex+1)+" out of "+seedCount+" chosen for use";
            
            int count = 0;
            for(int i=0;i<9;i++){
                for(int j=0;j<9;j++){
                    chosenSeedArray[i][j] = chosenLine.charAt(count);
                    count++;
                }
            }
            
            rs.close();
            set.add(chosenSeedArray);
            set.add(stringToReturn);
            
            return set;
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Count seeds counts the number of seeds in the db
     * @return count int
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public int countSeeds(){
        try {
            ResultSet rs = scrollStatement.executeQuery("SELECT * FROM SEEDS");
            
            rs.afterLast();
            rs.previous();
            return rs.getRow();
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    /**
     * Take in 4 chars
     * @param comparibleGame <br>   This char array (char[][]) represents the game in progress. As the player adds values, this parameter is updated
     * @param completedGame <br>    This char array (char[][]) represents the completed game before a player knows it. This is the final and only answer
     * @param playerValues <br>     This char array (char[][]) represents the player values ONLY. If no player values are entered, this is an empty char array of ' ' characters
     * @param uncompletedGame <br>  This char array (char[][]) represents ONLY the uncompleted game. This is the comparibleGame but DOES NOT update with player values being added
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public void saveGame(char[][] comparibleGame, char[][] completedGame, char[][] playerValues, char[][] uncompletedGame){
        try {
            String sqlCreateSavedGameTable = "CREATE TABLE SAVEDGAME"
                        +"(COMPARIBLE_GAME VARCHAR(81),"
                        + "COMPLETED_GAME VARCHAR(81),"
                        + "PLAYER_VALUES VARCHAR(81),"
                        + "UNCOMPLETED_GAME VARCHAR(81))";
            
            if(!(checkTable("SAVEDGAME"))){
                statement.executeUpdate(sqlCreateSavedGameTable);
                System.out.println("Table SAVEDGAME created and commited");
                conn.commit();
            }else{
                statement.executeUpdate("DROP TABLE SAVEDGAME");
                statement.executeUpdate(sqlCreateSavedGameTable);
                System.out.println("Table SAVEDGAME dropped and recreated");
                conn.commit();
            }
            
            statement.execute("INSERT INTO SAVEDGAME(COMPARIBLE_GAME, COMPLETED_GAME, PLAYER_VALUES, UNCOMPLETED_GAME)"
                        + "VALUES('"
                        + convertArrayToString(comparibleGame)+"','"
                        + convertArrayToString(completedGame)+"','"
                        + convertArrayToString(playerValues)+"','"
                        + convertArrayToString(uncompletedGame)+"')");
            System.out.println("Game inserted into table SAVEDGAME");
            conn.commit();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Deletes the saved game table
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public void deleteSavedGame(){
        try {
            if(checkTable("SAVEDGAME")){
                statement.executeUpdate("DROP TABLE SAVEDGAME");
                System.out.println("Table SAVEDGAME dropped and commited");
                conn.commit();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Gets saved game if any from data base
     * @return set ArrayList<br><br>CONTAINS:<br> 
     * inProgressGame char[][]<br>
     * completedGame char[][]<br>
     * playerValues char[][]<br>
     * uncompletedGame char[][]
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public ArrayList getSavedGame(){
        ArrayList set = new ArrayList();
        char[][] inProgressGame = new char[9][9]; //Create empty char arrays for saved game to go into
        char[][] completedGame = new char[9][9];
        char[][] playerValues = new char[9][9];
        char[][] uncompletedGame = new char[9][9];
        
        String inProgGame = "";
        String compGame = "";
        String player = "";
        String uncompGame = "";
        
        if(!(checkTable("SAVEDGAME"))){
            System.out.println("SAVEDGAME table not found");
            return null;
        }
        System.out.println("SAVEDGAME table found");
        ResultSet rs;
        
        try {
            rs = statement.executeQuery("SELECT * FROM SAVEDGAME");
            
            if(rs.next()){
                inProgGame = rs.getString("COMPARIBLE_GAME");
                compGame = rs.getString("COMPLETED_GAME");
                player = rs.getString("PLAYER_VALUES");
                uncompGame = rs.getString("UNCOMPLETED_GAME");
            }else{
                statement.executeUpdate("DROP TABLE SAVEDGAME");
                System.out.println("Table SAVEDGAME dropped and commited");
                conn.commit();
                return null; //Found no values in an existing table
            }
                
            
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int count = 0;
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                inProgressGame[i][j] = inProgGame.charAt(count);
                completedGame[i][j] = compGame.charAt(count);
                playerValues[i][j] = player.charAt(count);
                uncompletedGame[i][j] = uncompGame.charAt(count);
                count++;
            }
        }
        
        set.add(inProgressGame);
        set.add(completedGame);
        set.add(playerValues);
        set.add(uncompletedGame);
        
        return set;
    }
    
    /**
     * Saves the score and games played values
     * @param score int
     * @param gamesPlayed int 
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public void saveScore(int score, int gamesPlayed){
        try {
            String sqlCreateSavedScoreTable = "CREATE TABLE SCORE"
                    +"(SCORE INT,"
                    + "GAMES_PLAYED INT)";
            
            if(!(checkTable("SCORE"))){
                statement.executeUpdate(sqlCreateSavedScoreTable);
                System.out.println("Table SCORE created and commited");
                conn.commit();
            }else{
                statement.executeUpdate("DROP TABLE SCORE");
                System.out.println("Table SCORE and recreated");
                statement.executeUpdate(sqlCreateSavedScoreTable);
                conn.commit();
            }
            
            statement.executeUpdate("INSERT INTO SCORE(SCORE, GAMES_PLAYED)"
                        + "VALUES("+score+","+gamesPlayed+")");
            System.out.println("Score values inserted:\nGames played = "+gamesPlayed+", Score = "+score);
            conn.commit();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList getScore(){
        ArrayList set = new ArrayList();
        
        if(!(checkTable("SCORE"))){
            this.saveScore(0, 0); //Nothing found, so create new score table with 0 games played and 0 score
            set.add(0);
            set.add(0);
            return set;
        }
        ResultSet rs;
        
        try {
            rs = statement.executeQuery("SELECT * FROM SCORE");
            
            if(rs.next()){
                set.add(rs.getInt("SCORE"));
                set.add(rs.getInt("GAMES_PLAYED"));
            }else{
                this.saveScore(0, 0); //File found, bu no values inside, so re-create the table with 0 games played and 0 score
                set.add(0);
                set.add(0);
            }
            rs.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return set;
    }
    
    /**
     * Converts an input char 3D array to a String
     * @param array char[][]
     * @return seed String
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public String convertArrayToString(char[][] array){
        String seed = new String();
        
        for(char[] column : array){
            for(char cell : column){
                seed+=cell+"";
            }
        }
        
        return seed;
    }
    
    /**
     * Checks tableName table exists
     * @param tableName String
     * @return boolean
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public boolean checkTable(String tableName){
        
        try {
            DatabaseMetaData dbmd = conn.getMetaData();
            
            ResultSet resultSet = dbmd.getTables(null, null, tableName, null);
            if(resultSet.next()){
                resultSet.close();
                return true;
            }else{
                resultSet.close();
                return false;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    
}
