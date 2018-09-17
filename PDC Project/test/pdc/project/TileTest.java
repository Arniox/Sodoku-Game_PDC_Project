/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc.project;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Arniox
 */
public class TileTest {
    TextColours col = new TextColours(); //Create text colour object
    
    public TileTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of isEmpty method, of class Tile.
     */
    @Test
    public void testIsEmpty() {
        System.out.println(col.getANSI_BLUE()+"Test that the tile comes out as empty"+col.getANSI_RESET());
        Tile instance = new Tile();
        
        instance.setVaule(' '); //Counted as empty char in Tile class
        boolean expResult = true; //Should BE empty
        boolean result = instance.isEmpty();
        
        assertEquals(expResult, result);
        System.out.println(col.getANSI_BLUE()+"SUCCESS!"+col.getANSI_RESET()+"\n");
    }
    
    /**
     * Test of isEmpty method, of class Tile.
     */
    @Test
    public void testIsEmpty2() {
        System.out.println(col.getANSI_BLUE()+"Test that the tile comes out as not empty"+col.getANSI_RESET());
        Tile instance = new Tile();
        
        instance.setVaule('7'); //Counted as empty char in Tile class
        boolean expResult = false; //Should NOT be empty
        boolean result = instance.isEmpty();
        
        assertEquals(expResult, result);
        System.out.println(col.getANSI_BLUE()+"SUCCESS!"+col.getANSI_RESET()+"\n");
    }
    
}
