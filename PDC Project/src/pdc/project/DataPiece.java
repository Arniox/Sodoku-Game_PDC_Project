/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc.project;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used for holding multiple boolean values and a List variable as a single Object for function return uses
 * @author Nikkolas Diehl bjy5305
 */
public class DataPiece {
    private boolean filled;
    private boolean backTracker;
    private List usedTestValues;
    
    /**
     * Initiates the usedTestValues List variable with new ArrayList
     */
    public void initiateUsedTestValues()
    {
        this.usedTestValues = new ArrayList();
    }
    
    //Getters and Setters
    public boolean getFilled(){
        return this.filled;
    }
    public boolean getBackTracker(){
        return this.backTracker;
    }
    public List getUsedTestValues()
    {
        return this.usedTestValues;
    }
    public void setFilled(boolean truthFalse){
        this.filled = truthFalse;
    }
    public void setBackTracker(boolean truthFalse){
        this.backTracker = truthFalse;
    }
}
