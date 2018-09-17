/*
 * Nikkolas Diehl - bjy5305 16945724.
 * Project 1 - PDC Project
 * .
 */
package pdc.project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Nikkolas Diehl - bjy5305 16945724.
 */
public class GUIModel implements ActionListener, ChangeListener, KeyListener{

    //Set up a MVC
    private GUIControl control = new GUIControl(this);
    private GUIView myView;
    
    /**
     * Take in GUIView mainView and set this.myView to mainView
     * @param mainView GUIView
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public GUIModel(GUIView mainView){
        myView = mainView;
        
    }
    /**
     * Returns GUIView
     * @return myView GUIView
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public GUIView getMyView() {
        return this.myView;
    }
    
    //Enum for MENUSTATE (which menu you're currently in
    public enum MENUSTATE{
        MAIN,
        INSTRUCTIONS,
        NEWSEEDS,
        CONTINUE,
        NEWGAMESTG1,
        GAMEINPROGRESS,
        WINSTATUSSCREEN
    }
    private MENUSTATE state = MENUSTATE.MAIN;
    
    
    //--EVENT LISTENERS--
    //ACTION PERFORMED EVENT LISTENER
    @Override
    public void actionPerformed(ActionEvent e) {
       Object source=e.getSource();
       if(source==myView.getExitButton()
            || source==myView.getExitWithoutSaving()
            || source==myView.getExitOnClose()){
           control.closeAll();
           myView.dispose();
       }
       if(source==myView.getNewGame()){
           control.newGameButtonFunction();
       }
       if(source==myView.getContinueGame()){
           control.continueGameButtonFunction();
       }
       if(source==myView.getNewSeeds()){
           state = MENUSTATE.NEWSEEDS;
           this.changeMenu();
       }
       if(source==myView.getInstructions()){
           state = MENUSTATE.INSTRUCTIONS;
           this.changeMenu();
       }
       if(source==myView.getBack()){
           state = MENUSTATE.MAIN;
           this.changeMenu();
       }
       if(source==myView.getGenerate()){
           control.generateButtonFunction();
       }
       if(source==myView.getSetDifficulty()){
           control.setDifficultyButtonFunction();
       }
       if(source==myView.getContinueButton()){
           control.continueButtonFunction(true);
       }
       if(source==myView.getFinishGame()){
           control.finishGameButtonFunction();
       }
       if(source==myView.getSaveAndExit()){
           control.saveAndExitButtonFunction(true);
       }
       if(source==myView.getSavedGame()){
           control.savedGameButtonFunction();
       }
       
    }
    
    //CHANGE EVENT LISTENER
    @Override
    public void stateChanged(ChangeEvent e) {
        Object source=e.getSource();
        if(source==myView.getAmountOfSeeds()){
            myView.getValue().setText("Generate "+myView.getAmountOfSeeds().getValue()+" seeds.");

            control.setAverageTime();
            //value.setText(Runtime.getRuntime().maxMemory());
        }
        if(source==myView.getDifficultyRating()){
            switch(myView.getDifficultyRating().getValue()){
                case 1:
                    control.setDiffSliderTextColour(Color.WHITE);
                    break;
                case 2:
                    control.setDiffSliderTextColour(Color.GREEN);
                    break;
                case 3:
                    control.setDiffSliderTextColour(Color.YELLOW);
                    break;
                case 4:
                    control.setDiffSliderTextColour(Color.RED);
                    break;
                case 5:
                    control.setDiffSliderTextColour(Color.MAGENTA);
                    break;
            }
        }
        
    }
    
    //KEY LISTENERS
    @Override
    public void keyTyped(KeyEvent e) {
        //Removes any values that are not 1-9
        char c = e.getKeyChar();
        
        if((c < '1') || (c > '9') && (c != KeyEvent.VK_BACK_SPACE)){
            e.consume(); //ignore event
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public void keyReleased(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    //Functions
    /**
     * Change menu function changes the state of menu and sets menus to false or true
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public void changeMenu(){
        if(state == MENUSTATE.MAIN){
            myView.setMain(true);
            myView.setInstructions(false);
            myView.setNewSeeds(false);
            myView.setNewGameStg1(false);
            myView.setInProgressGame(false);
            myView.setWinStatus(false);
            myView.setContinueGame(false);
            control.setBackButton(false);
            
            
            myView.getBackGroundText().setVisible(true);
            myView.getBackGroundText().setIcon(new ImageIcon("resources/buttonUI/titleM.png"));
        }else if(state == MENUSTATE.INSTRUCTIONS){
            myView.setMain(false);
            myView.setInstructions(true);
            myView.setNewSeeds(false);
            myView.setNewGameStg1(false);
            myView.setInProgressGame(false);
            myView.setWinStatus(false);
            myView.setContinueGame(false);
            control.setBackButton(true);
            
            
            myView.getBackGroundText().setVisible(true);
            myView.getBackGroundText().setIcon(new ImageIcon("resources/buttonUI/titleI.png"));
        }else if(state == MENUSTATE.NEWSEEDS){
            myView.setMain(false);
            myView.setInstructions(false);
            myView.setNewSeeds(true);
            myView.setNewGameStg1(false);
            myView.setInProgressGame(false);
            myView.setWinStatus(false);
            myView.setContinueGame(false);
            control.setBackButton(true);
            
            
            myView.getBackGroundText().setVisible(true);
            myView.getBackGroundText().setIcon(new ImageIcon("resources/buttonUI/titleS.png"));
        }else if(state == MENUSTATE.NEWGAMESTG1){
            myView.setMain(false);
            myView.setInstructions(false);
            myView.setNewSeeds(false);
            myView.setNewGameStg1(true);
            myView.setInProgressGame(false);
            myView.setWinStatus(false);
            myView.setContinueGame(false);
            control.setBackButton(true);
            
           
            myView.getBackGroundText().setVisible(true);
            myView.getBackGroundText().setIcon(new ImageIcon("resources/buttonUI/titleG.png"));
        }else if(state == MENUSTATE.GAMEINPROGRESS){
            myView.setMain(false);
            myView.setInstructions(false);
            myView.setNewSeeds(false);
            myView.setNewGameStg1(false);
            myView.setInProgressGame(true);
            myView.setWinStatus(false);
            myView.setContinueGame(false);
            control.setBackButton(false);
            
            
            myView.getBackGroundText().setVisible(false);
        }else if(state == MENUSTATE.CONTINUE){
            myView.setMain(false);
            myView.setInstructions(false);
            myView.setNewSeeds(false);
            myView.setNewGameStg1(false);
            myView.setInProgressGame(false);
            myView.setWinStatus(false);
            myView.setContinueGame(true);
            control.setBackButton(true);
            
            
            myView.getBackGroundText().setVisible(true);
            myView.getBackGroundText().setIcon(new ImageIcon("resources/buttonUI/titleC.png"));
        }else if(state == MENUSTATE.WINSTATUSSCREEN){
            myView.setMain(false);
            myView.setInstructions(false);
            myView.setNewSeeds(false);
            myView.setNewGameStg1(false);
            myView.setInProgressGame(false);
            myView.setWinStatus(true);
            myView.setContinueGame(false);
            control.setBackButton(true);
            
            myView.getBackGroundText().setVisible(false);
        }
    }
    
    
    //Setters
    public void setState(MENUSTATE state) {
        this.state = state;
    }

    //Gettters
    public MENUSTATE getState() {
        return this.state;
    }
    public GUIControl getControl() {
        return this.control;
    }
    public float getAverageCalcTime() {
        return control.getAverageCalcTime();
    }
    public SodokuGenerationAlgorithm getGameSeed() {
        return control.getGameSeed();
    }
    public DataBaseIO getDbIO() {
        return control.getDbIO();
    }
    public GamePrinter getGamePrinter() {
        return control.getGamePrinter();
    }
    public Font getFontForLabel() {
        return control.getFontForLabel();
    }
    public Font getFontForTextArea() {
        return control.getFontForTextArea();
    }
    public Point getFrameSize() {
        return control.getFrameSize();
    }
    public Point getCenterPoint() {
        return control.getCenterPoint();
    }
    
}
