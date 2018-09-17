/*
 * Nikkolas Diehl - bjy5305 16945724.
 * Project 1 - PDC Project
 */
package pdc.project;

import javax.swing.JFrame;

/**
 * @author Nikkolas Diehl - bjy5305 16945724.
 */
public class PDCProject {
    //GUI Start
    public static void main(String[] args) {
        JFrame frame = new GUIView();
        frame.setTitle("Magic Sodoku");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setAlwaysOnTop(false);
    }
    
    
    
    //CLI Start
    /**
     * @param args the command line arguments
     */
    /*public static void main(String[] args) {
        CLI cli = new CLI();
        cli.runWithSavedGameFileCheck();
    }*/
    
}
