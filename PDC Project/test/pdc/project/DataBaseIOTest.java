/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc.project;

import java.util.ArrayList;
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
public class DataBaseIOTest {
    TextColours col = new TextColours(); //Create text colour object
    
    public DataBaseIOTest() {
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

    @Test
    public void testGetRandomSeed() {
        System.out.println(col.getANSI_BLUE()
                           +"Testing the mathematical length of the seed. It should always equal 9x9"
                           +col.getANSI_RESET());
        int specificSeed = -5; //Get random seed rather than specific seed
        DataBaseIO instance = new DataBaseIO();
        
        int expResult = 9;
        ArrayList array = instance.getRandomSeed(specificSeed);
        char[][] seed = (char[][])array.get(0);
        int result = seed.length;
        
        assertEquals(expResult, result);
        System.out.println(col.getANSI_BLUE()+"SUCCESS!!"+col.getANSI_RESET()+"\n");
    }

    /**
     * Test of getScore method, of class DataBaseIO.
     */
    @Test
    public void testGetScore() {
        System.out.println(col.getANSI_BLUE()
                           +"Test that the get score method returns both score and games played"
                           +col.getANSI_RESET());
        DataBaseIO instance = new DataBaseIO();
        
        int expResult = 2;
        ArrayList array = instance.getScore();
        int result = array.size();
        
        assertEquals(expResult, result);
        System.out.println(col.getANSI_BLUE()+"SUCCESS!!"+col.getANSI_RESET()+"\n");
    }
    
    /**
     * Test of getScore method, of class DataBaseIO.
     */
    @Test
    public void testGetScore2() {
        System.out.println(col.getANSI_BLUE()
                           +"Reset score and check that the score and games played are both 0"
                           +col.getANSI_RESET());
        DataBaseIO instance = new DataBaseIO();
        instance.saveScore(0, 0);
        
        ArrayList expResult = new ArrayList();
        expResult.add(0);
        expResult.add(0);
        
        ArrayList result = instance.getScore();
        
        assertEquals(expResult, result);
        System.out.println(col.getANSI_BLUE()+"SUCCESS!!"+col.getANSI_RESET()+"\n");
    }

    /**
     * Test of convertArrayToString method, of class DataBaseIO.
     */
    @Test
    public void testConvertArrayToString() {
        System.out.println(col.getANSI_BLUE()
                           +"Testing that this method properly converts a char[][] to a string"
                           +col.getANSI_RESET());
        char[][] array = new char[][]{{'1','2','3','4','5','6','7','8','9'},
                                      {'1','2','3','4','5','6','7','8','9'},
                                      {'1','2','3','4','5','6','7','8','9'},
                                      {'1','2','3','4','5','6','7','8','9'},
                                      {'1','2','3','4','5','6','7','8','9'},
                                      {'1','2','3','4','5','6','7','8','9'},
                                      {'1','2','3','4','5','6','7','8','9'},
                                      {'1','2','3','4','5','6','7','8','9'},
                                      {'1','2','3','4','5','6','7','8','9'}};
        DataBaseIO instance = new DataBaseIO();
        
        String expResult = "123456789123456789123456789123456789123456789123456789123456789123456789123456789";
        String result = instance.convertArrayToString(array);
        
        assertEquals(expResult, result);
        System.out.println(col.getANSI_BLUE()+"SUCCESS!!"+col.getANSI_RESET()+"\n");
    }
    
}
