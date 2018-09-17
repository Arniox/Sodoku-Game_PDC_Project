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
public class GamePrinterTest {
    TextColours col = new TextColours(); //Create text colour object
    
    public GamePrinterTest() {
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
     * Test of assigner method, of class GamePrinter.
     */
    @Test
    public void testAssigner() {
        System.out.println(col.getANSI_BLUE()
                           +"Test that the assigner is assigning the correct numbers to the correct characters"
                           +col.getANSI_RESET());
        char[][] seed = new char[][]{{'a','b','c','d','e','f','g','h','i'},
                                     {'a','b','c','d','e','f','g','h','i'},
                                     {'a','b','c','d','e','f','g','h','i'},
                                     {'a','b','c','d','e','f','g','h','i'},
                                     {'a','b','c','d','e','f','g','h','i'},
                                     {'a','b','c','d','e','f','g','h','i'},
                                     {'a','b','c','d','e','f','g','h','i'},
                                     {'a','b','c','d','e','f','g','h','i'},
                                     {'a','b','c','d','e','f','g','h','i'},};
        GamePrinter instance = new GamePrinter();
        char[][] expResult = new char[][]{{'1','2','3','4','5','6','7','8','9'},
                                          {'1','2','3','4','5','6','7','8','9'},
                                          {'1','2','3','4','5','6','7','8','9'},
                                          {'1','2','3','4','5','6','7','8','9'},
                                          {'1','2','3','4','5','6','7','8','9'},
                                          {'1','2','3','4','5','6','7','8','9'},
                                          {'1','2','3','4','5','6','7','8','9'},
                                          {'1','2','3','4','5','6','7','8','9'},
                                          {'1','2','3','4','5','6','7','8','9'},};
        char[][] result = instance.assigner(seed, false); //Do not randomly assign numbers
        assertArrayEquals(expResult, result);
        System.out.println(col.getANSI_BLUE()+"SUCCESS!"+col.getANSI_RESET()+"\n");
    }
    
    /**
     * Test of assigner method puts out same length, of class GamePrinter.
     */
    @Test
    public void testAssigner2() {
        System.out.println(col.getANSI_BLUE()
                           +"Test for length of char[][] that the random assigner spits out the same size ranbomised game as seed"
                           +col.getANSI_RESET());
        char[][] seed = new char[][]{{'a','b','c','d','e','f','g','h','i'},
                                     {'a','b','c','d','e','f','g','h','i'},
                                     {'a','b','c','d','e','f','g','h','i'},
                                     {'a','b','c','d','e','f','g','h','i'},
                                     {'a','b','c','d','e','f','g','h','i'},
                                     {'a','b','c','d','e','f','g','h','i'},
                                     {'a','b','c','d','e','f','g','h','i'},
                                     {'a','b','c','d','e','f','g','h','i'},
                                     {'a','b','c','d','e','f','g','h','i'},};
        GamePrinter instance = new GamePrinter();
        char[][] result = instance.assigner(seed, true); //Randomly assign numbers
        int resultSize = result.length;
        int expectedResultSize = 9;
        
        assertEquals(expectedResultSize, resultSize);
        System.out.println(col.getANSI_BLUE()+"SUCCESS!"+col.getANSI_RESET()+"\n");
    }
    
    
}
