/*
 * Nikkolas Diehl - bjy5305 16945724.
 * Project 1 - PDC Project
 * .
 */
package pdc.project;

/**
 * This class holds a single char value and is the lowest child Object in the SodokuGenerationAlgorithm class
 * One tile is a single spot within the sodoku grid
 * @author Nikkolas Diehl bjy5305
 */
public class Tile {
    private char value;
    
    /**
     * isEmpty checks if itself is an empty tile
     * @return true or false
     */
    public boolean isEmpty()
    {
        if(this.value == ' ')
        {
            return true;
        }
        return false;
    }
    
    //Getters and Setters
    public char getValue()
    {
        return this.value;
    }
    public void setVaule(char value)
    {
        this.value = value;
    }
}
