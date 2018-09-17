/*
 * Nikkolas Diehl - bjy5305 16945724.
 * Project 1 - PDC Project
 * .
 */
package pdc.project;

/**
 * This class is a 3D char array holding a 3x3 grid of tiles as a cell. It is a child of the Grid class
 * @author Nikkolas Diehl bjy5305
 */
public class Cell {
    private Tile[][] threeByThree = new Tile[3][3];
    
    //Getter
    public Tile[][] getThreeByThree()
    {
        return this.threeByThree;
    }
    
    /**
     * initiateTiles fills all tiles spots within the Tile 3D Object array with a new Tile object and then a white space character
     */
    public void initiateTiles()
    {
        for(int i=0;i<3;i++)
        {
            for(int k=0;k<3;k++)
            {
                this.threeByThree[i][k] = new Tile();
                this.threeByThree[i][k].setVaule(' ');
            }
        }
    }
    
    /**
     * 
     * @param valueToCount
     * @return the frequency count of a specified char value
     */
    public int frequencyCount(char valueToCount)
    {
        int count = 0;
        for(int i=0;i>3;i++)
        {
            for(int k=0;k>3;k++)
            {
                if(this.threeByThree[i][k].getValue() == valueToCount)
                {
                    count++;
                }
            }
        }
        
        return count;
    }
    
    /**
     * checks if an entire cell is empty (each tile within a cell must be empty for true)
     * @return false or true
     */
    public boolean isEmpty()
    {
        for(int i=0;i<3;i++)
        {
            for(int k=0;k<3;k++)
            {
                if(!(this.threeByThree[i][k].isEmpty()))
                {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    /**
     * deletes a value at a specified position
     * @param x
     * @param y 
     */
    public void deleteValue(int x, int y)
    {
        this.threeByThree[x][y].setVaule(' ');
    }
    
    /**
     * checks for a specified value in the current cell
     * @param value
     * @return true or false
     */
    public boolean checkForValue(char value)
    {
        for(Tile[] height : this.threeByThree)
        {
            for(Tile width : height)
            {
                if(width.getValue() == value)
                {
                    return true;
                }
            }
        }
        return false;
    }
}
