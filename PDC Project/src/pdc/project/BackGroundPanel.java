/*
 * Nikkolas Diehl - bjy5305 16945724.
 * Project 1 - PDC Project
 * .
 */
package pdc.project;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;

/**
 *
 * @author Arniox
 */
public class BackGroundPanel extends JPanel{
    Image img;
    public BackGroundPanel()
    {
        // Loads the background image and stores in img object.
        this.img = Toolkit.getDefaultToolkit().createImage("resources/backgrounds/Surface.jpg");
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);      
        g.drawImage(this.img, 0, 0, null); //For cubes background, use -400 for y
        this.repaint();
    }
}
