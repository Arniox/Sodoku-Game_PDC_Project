/*
 * Nikkolas Diehl - bjy5305 16945724.
 * Project 1 - PDC Project
 * .
 */
package pdc.project;

/**
 * This class is a 3D array of Cell Objects. Each element in the 3D array holds a 3x3 array of Tile Objects.<br>
 * Grid class is a child of sodokuGenerationAlgorithm class
 * @author Nikkolas Diehl bjy5305
 */
public class Grid {
    private Cell[][] threeByThreeGrid = new Cell[3][3];
    
    //Getter
    public Cell[][] getThreeByThreeGrid()
    {
        return this.threeByThreeGrid;
    }
    
    /**
     * For each cell in the Grid 3D array, fill with new Cell Object
     */
    public void initiateCells()
    {
        for(int i=0;i<3;i++)
        {
            for(int k=0;k<3;k++)
            {
                threeByThreeGrid[i][k] = new Cell();
            }
        }
    }
    
}
