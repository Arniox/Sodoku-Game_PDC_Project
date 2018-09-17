/*
 * Nikkolas Diehl - bjy5305 16945724.
 * Project 1 - PDC Project
 * .
 */
package pdc.project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author Nikkolas Diehl - bjy5305 16945724.
 */
public class GUIView extends JFrame{
    
    
    private GUIModel model = new GUIModel(this);
    
    //Main Elements
    private JPanel backGround;
    private JButton back;
    
    //Main Menu Elements
    private JLabel backGroundText;
    private JButton newGame;
    private JButton continueGame;
    private JButton newSeeds;
    private JButton instructions;
    private JButton exitButton;
    
    //Instruction Menu Elements
    private JTextArea information;
    private JScrollPane infoScroller;
    
    //New Seeds Menu Elements
    private JLabel consoleLabel;
    private JLabel infoLabel;
    private JLabel value;
    private JLabel time;
    private JTextArea consoleLog;
    private JTextArea data;
    private JScrollPane logScroller;
    private JScrollPane dataScroller;
    private JSlider amountOfSeeds;
    private JButton generate;
    
    //Continue game Elements
    private JLabel gameFoundStatus;
    private JButton savedGame;
    
    //New game stage 1 Elements
    private JLabel steps;
    private JLabel seedsToUseLabel;
    private JLabel seedToUse;
    private JLabel gameToUseLabel;
    private JLabel gameToUse;
    private JSlider difficultyRating;
    private JButton setDifficulty;
    private JButton continueButton;
    
    //New game stage 2 Elements
    private JTextField[][] textFieldGrid;
    private JLabel gamesPlayedLabel;
    private JLabel totalScoreLabel;
    private JButton saveAndExit;
    private JButton exitWithoutSaving;
    private JButton finishGame;
    
    //In progress game Elements
    private JLabel winLostStatus;
    private JLabel whatToDoNow;
    private JButton exitOnClose;
    
    /**
     * Activates all menus and elements
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public GUIView(){
        
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                try {
                    model.getControl().getDbIO().conn.close();
                    model.getControl().getDbIO().statement.close();
                    System.out.println(model.getControl().getDbIO().url+ " closed connection...");
                } catch (SQLException ex) {
                    Logger.getLogger(GUIView.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                dispose();
            }
        });
        
        this.setSize(model.getFrameSize().x, model.getFrameSize().y);
        this.setLocation(model.getCenterPoint()); //Sets location to exactly center of screen
        
        backGround = new BackGroundPanel();
        backGround.setLayout(null);
        
        backGroundText = new JLabel("");
        backGroundText.setIcon(new ImageIcon("resources/buttonUI/titleM.png"));
        backGroundText.setBounds(model.getFrameSize().x/2-646/2, (int)((model.getFrameSize().y)*0.02), 646, 60);
        
        back = new JButton("");
        back.setIcon(new ImageIcon("resources/buttonUI/back.png"));
        back.setRolloverEnabled(true);
        back.setRolloverIcon(new ImageIcon("resources/buttonUI/backOnHover.png"));
        back.setBorderPainted(false);
        back.setContentAreaFilled(false);
        back.setBounds(model.getFrameSize().x/2-343/2, (int)((model.getFrameSize().y)*0.84), 343, 85);
        back.addActionListener(model);
        backGround.add(back);
        
        this.mainMenuActivate(model.getFrameSize());
        this.continueGameActivate(model.getFrameSize());
        this.instructionsMenuActivate(model.getFrameSize());
        this.newSeedsMenuActivate(model.getFrameSize());
        this.newGameStg1Activate(model.getFrameSize());
        this.inProgressGameActivate(model.getFrameSize());
        this.winStatusActivate(model.getFrameSize());
        
        model.changeMenu();
        
        this.add(backGround);
    }
    
    /**
     * Activates all elements for the main menu
     * @param frameSize Point
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public void mainMenuActivate(Point frameSize){
        newGame = new JButton("");
        newGame.setIcon(new ImageIcon("resources/buttonUI/newGame.png"));
        newGame.setRolloverEnabled(true);
        newGame.setRolloverIcon(new ImageIcon("resources/buttonUI/newGameOnHover.png"));
        newGame.setBorderPainted(false);
        newGame.setContentAreaFilled(false);
        newGame.setBounds(frameSize.x/2-343/2, (int)((frameSize.y)*0.15), 343, 85);
        newGame.addActionListener(model);
        
        continueGame = new JButton("");
        continueGame.setIcon(new ImageIcon("resources/buttonUI/continueGame.png"));
        continueGame.setRolloverEnabled(true);
        continueGame.setRolloverIcon(new ImageIcon("resources/buttonUI/continueGameOnHover.png"));
        continueGame.setBorderPainted(false);
        continueGame.setContentAreaFilled(false);
        continueGame.setBounds(frameSize.x/2-343/2, (int)((frameSize.y)*0.28), 343, 85);
        continueGame.addActionListener(model);
        
        newSeeds = new JButton("");
        newSeeds.setIcon(new ImageIcon("resources/buttonUI/newSeeds.png"));
        newSeeds.setRolloverEnabled(true);
        newSeeds.setRolloverIcon(new ImageIcon("resources/buttonUI/newSeedsOnHover.png"));
        newSeeds.setBorderPainted(false);
        newSeeds.setContentAreaFilled(false);
        newSeeds.setBounds(frameSize.x/2-343/2, (int)((frameSize.y)*0.41), 343, 85);
        newSeeds.addActionListener(model);
        
        instructions = new JButton("");
        instructions.setIcon(new ImageIcon("resources/buttonUI/instructions.png"));
        instructions.setRolloverEnabled(true);
        instructions.setRolloverIcon(new ImageIcon("resources/buttonUI/instructionsOnHover.png"));
        instructions.setBorderPainted(false);
        instructions.setContentAreaFilled(false);
        instructions.setBounds(frameSize.x/2-343/2, (int)((frameSize.y)*0.54), 343, 85);
        instructions.addActionListener(model);
        
        exitButton = new JButton("");
        exitButton.setIcon(new ImageIcon("resources/buttonUI/exit.png"));
        exitButton.setRolloverEnabled(true);
        exitButton.setRolloverIcon(new ImageIcon("resources/buttonUI/exitOnHover.png"));
        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setBounds(frameSize.x/2-343/2, (int)((frameSize.y)*0.84), 343, 85);
        exitButton.addActionListener(model);
        
        
        
        backGround.add(backGroundText);
        backGround.add(newGame);
        backGround.add(continueGame);
        backGround.add(newSeeds);
        backGround.add(instructions);
        backGround.add(exitButton);
    }
    
    /**
     * Activates all elements for the instruction page
     * @param frameSize Point
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public void instructionsMenuActivate(Point frameSize){
        
        information = new JTextArea(""
                + "Sudoku rules:\n\n" +
                "Sudoku is easy to learn, yet highly addictive language-independent logic puzzles which have recently taken the whole world by storm. \n"
                + "Using pure logic and requiring no math to solve, "
                + "\nthese fascinating puzzles offer endless fun and intellectual entertainment to puzzle fans of all skills and ages.\n" +
                "\n" +
                "The Classic Sudoku is a number placing puzzle based on a 9x9 grid with several given numbers. "
                + "\nThe object is to place the numbers 1 to 9 in the empty squares so that each row, "
                + "\neach column and each 3x3 box contains the same number only once.\n" +
                "\n" +
                "Sudoku puzzles come in endless number combinations and range from very easy "
                + "\nto extremely difficult taking anything from five minutes to several hours to solve. "
                + "\nSudoku puzzles also come in many variants, each variant; slightly different than the rest, "
                + "\nand each variant offering a unique twist of brain challenging logic.\n" +
                "\n" +
                "However, make one mistake and you’ll find yourself stuck later on as you get closer to the solution\n" +
                "\n" +
                "\nClassic Sudoku:\n\n" +
                "Each puzzle consists of a 9x9 grid containing given clues in various places. \n"
                + "The object is to fill all empty squares so that the numbers 1 to 9 appear exactly once in each row, "
                + "\ncolumn and 3x3 box.");
        information.setOpaque(false);
        information.setFont(model.getFontForTextArea());
        information.setForeground(Color.WHITE);
        
        infoScroller = new JScrollPane(information);
        infoScroller.setBounds(10, (int)((frameSize.y)*0.15), (int)(frameSize.x*0.987), (int)(frameSize.y*0.68));
        infoScroller.getViewport().setOpaque(false);
        infoScroller.setOpaque(false);
        infoScroller.setBorder(BorderFactory.createEmptyBorder());
        
        backGround.add(infoScroller);
    }
    
    /**
     * Activates all the elements for the continue game menu
     * @param frameSize Point
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public void continueGameActivate(Point frameSize){
        
        gameFoundStatus = new JLabel("Test", SwingConstants.CENTER);
        gameFoundStatus.setFont(model.getFontForLabel());
        gameFoundStatus.setForeground(Color.WHITE);
        gameFoundStatus.setBounds(frameSize.x/2-500/2, (int)((frameSize.y)*0.15), 500, 20);
        
        savedGame = new JButton("");
        savedGame.setBorderPainted(true);
        savedGame.setFont(model.getFontForTextArea());
        savedGame.setForeground(Color.WHITE);
        savedGame.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        savedGame.setContentAreaFilled(false);
        savedGame.setBounds((int)(frameSize.x/2-frameSize.x/2/2), (int)((frameSize.y)*0.20), (int)(frameSize.x/2), (int)(frameSize.y/1.59));
        savedGame.setFocusable(false);
        savedGame.addActionListener(model);
        
        backGround.add(savedGame);
        backGround.add(gameFoundStatus);
    }
    
    /**
     * Activates all elements for the new seeds page
     * @param frameSize Point
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public void newSeedsMenuActivate(Point frameSize){
        
        consoleLabel = new JLabel("Console Log: ");
        consoleLabel.setFont(model.getFontForLabel());
        consoleLabel.setForeground(Color.WHITE);
        consoleLabel.setBounds(10, (int)((frameSize.y)*0.25), 200, 40);
        
        infoLabel = new JLabel("Information: ");
        infoLabel.setFont(model.getFontForLabel());
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setBounds((int)(frameSize.x*0.500), (int)((frameSize.y)*0.25), 180, 40);
        
        consoleLog = new JTextArea("");
        consoleLog.setOpaque(false);
        consoleLog.setFont(model.getFontForTextArea());
        consoleLog.setForeground(Color.WHITE);
        
        logScroller = new JScrollPane(consoleLog);
        logScroller.setBounds(10, (int)((frameSize.y)*0.30), (int)(frameSize.x*0.490), (int)(frameSize.y*0.531));
        logScroller.getViewport().setOpaque(false);
        //logScroller.setBackground(Color.BLACK);
        logScroller.setOpaque(false);
        logScroller.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        
        data = new JTextArea("");
        data.setOpaque(false);
        data.setFont(model.getFontForTextArea());
        data.setForeground(Color.WHITE);
        
        dataScroller = new JScrollPane(data);
        dataScroller.setBounds((int)(frameSize.x*0.500), (int)((frameSize.y)*0.30), (int)(frameSize.x*0.49), (int)(frameSize.y*0.531));
        dataScroller.getViewport().setOpaque(false);
        //dataScroller.setBackground(Color.BLACK);
        dataScroller.setOpaque(false);
        dataScroller.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        
        generate = new JButton("");
        generate.setIcon(new ImageIcon("resources/buttonUI/generate.png"));
        generate.setRolloverEnabled(true);
        generate.setRolloverIcon(new ImageIcon("resources/buttonUI/generateOnHover.png"));
        generate.setBorderPainted(false);
        generate.setContentAreaFilled(false);
        generate.setBounds((int)(frameSize.x*0.76), (int)((frameSize.y)*0.15), 343, 85);
        generate.addActionListener(model);
        
        amountOfSeeds = new JSlider();
        amountOfSeeds.setOpaque(false);
        amountOfSeeds.setFont(new Font("Lucida Sans", Font.PLAIN, 9));
        amountOfSeeds.setForeground(Color.WHITE);
        amountOfSeeds.setMajorTickSpacing(1);
        amountOfSeeds.setMaximum(10);
        amountOfSeeds.setMinimum(0);
        amountOfSeeds.setSnapToTicks(false);
        amountOfSeeds.setValue(6);
        amountOfSeeds.setPaintTicks(true);
        amountOfSeeds.setPaintLabels(false);
        int sliderLength = (int)(frameSize.x/1.38);
        amountOfSeeds.setBounds((int)((frameSize.x/2-sliderLength/2)-(frameSize.x*0.12)), (int)((frameSize.y)*0.16), sliderLength, 85);
        amountOfSeeds.addChangeListener(model);
        
        value = new JLabel("");
        value.setFont(new Font("Lucida Sans", Font.BOLD, 17));
        value.setForeground(Color.WHITE);
        value.setBounds(frameSize.x/2-220/2, (int)((frameSize.y)*0.15), 220, 20);
        
        time = new JLabel("");
        time.setFont(new Font("Lucida Sans", Font.ITALIC, 17));
        time.setForeground(Color.WHITE);
        time.setBounds(frameSize.x/2-300/2, (int)((frameSize.y)*0.17), 300, 20);
        model.getControl().setAverageTime();
        
        
        backGround.add(logScroller);
        backGround.add(dataScroller);
        backGround.add(consoleLabel);
        backGround.add(infoLabel);
        backGround.add(generate);
        backGround.add(amountOfSeeds);
        backGround.add(value);
        backGround.add(time);
    }
    
    /**
     * Activates all elements for the game stage 1 page
     * @param frameSize Point
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public void newGameStg1Activate(Point frameSize){
        
        steps = new JLabel("Select a Difficulty Rating - The Higher, the Harder");
        steps.setFont(model.getFontForTextArea());
        steps.setForeground(Color.WHITE);
        steps.setBounds(frameSize.x/2-524/2, (int)((frameSize.y)*0.17), 524, 33);
        
        difficultyRating = new JSlider();
        difficultyRating.setOpaque(false);
        difficultyRating.setFont(model.getFontForTextArea());
        difficultyRating.setForeground(Color.WHITE);
        difficultyRating.setMajorTickSpacing(1);
        difficultyRating.setMaximum(5);
        difficultyRating.setMinimum(1);
        difficultyRating.setSnapToTicks(false);
        difficultyRating.setValue(3);
        difficultyRating.setPaintTicks(true);
        difficultyRating.setPaintLabels(true);
        int sliderLength = (int)(frameSize.x/1.66);
        difficultyRating.setBounds((int)(frameSize.x/2-sliderLength/2), (int)((frameSize.y)*0.19), sliderLength, 85);
        difficultyRating.addChangeListener(model);
        
        setDifficulty = new JButton("");
        setDifficulty.setIcon(new ImageIcon("resources/buttonUI/ok.png"));
        setDifficulty.setRolloverEnabled(true);
        setDifficulty.setRolloverIcon(new ImageIcon("resources/buttonUI/okOnHover.png"));
        setDifficulty.setBorderPainted(false);
        setDifficulty.setContentAreaFilled(false);
        setDifficulty.setBounds((int)(frameSize.x/2-343/2), (int)((frameSize.y)*0.29), 343, 85);
        setDifficulty.addActionListener(model);
        
        seedsToUseLabel = new JLabel("", SwingConstants.CENTER);
        seedsToUseLabel.setFont(model.getFontForTextArea());
        seedsToUseLabel.setForeground(Color.WHITE);
        seedsToUseLabel.setBounds(frameSize.x/2-2000/2, (int)((frameSize.y)*0.32), 2000, 33);
        
        seedToUse = new JLabel("", SwingConstants.CENTER);
        seedToUse.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        seedToUse.setFont(model.getFontForTextArea());
        seedToUse.setForeground(Color.WHITE);
        seedToUse.setBounds(frameSize.x/2-1050/2, (int)((frameSize.y)*0.36), 1050, 35);
        
        gameToUseLabel = new JLabel("Game String: ");
        gameToUseLabel.setFont(model.getFontForTextArea());
        gameToUseLabel.setForeground(Color.WHITE);
        gameToUseLabel.setBounds(frameSize.x/2-138/2, (int)((frameSize.y)*0.42), 138, 33);
        
        gameToUse = new JLabel("", SwingConstants.CENTER);
        gameToUse.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        gameToUse.setFont(model.getFontForTextArea());
        gameToUse.setForeground(Color.WHITE);
        gameToUse.setBounds(frameSize.x/2-1050/2, (int)((frameSize.y)*0.46), 1050, 35);
        
        continueButton = new JButton("");
        continueButton.setIcon(new ImageIcon("resources/buttonUI/continue.png"));
        continueButton.setRolloverEnabled(true);
        continueButton.setRolloverIcon(new ImageIcon("resources/buttonUI/continueOnHover.png"));
        continueButton.setBorderPainted(false);
        continueButton.setContentAreaFilled(false);
        continueButton.setBounds(frameSize.x/2-343/2, (int)((frameSize.y)*0.71), 343, 85);
        continueButton.addActionListener(model);
        
        
        backGround.add(continueButton);
        backGround.add(gameToUse);
        backGround.add(gameToUseLabel);
        backGround.add(seedToUse);
        backGround.add(seedsToUseLabel);
        backGround.add(setDifficulty);
        backGround.add(difficultyRating);
        backGround.add(steps);
    }
    
    /**
     * Activates all elements for the in progress game
     * @param frameSize Point
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public void inProgressGameActivate(Point frameSize){
        
        int verticalAlign = 0;
        int horizontalAlign = 0;
        
        textFieldGrid = new JTextField[9][9];
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if((i%3)==0 && i!=0){
                    verticalAlign = (int)((frameSize.y*0.01)*i/3);
                }
                if((j%3)==0 && j!=0){
                    horizontalAlign = (int)((frameSize.y*0.011)*j/3);
                }
                
                textFieldGrid[i][j] = new JTextField();
                textFieldGrid[i][j].setFont(model.getFontForTextArea());
                textFieldGrid[i][j].setForeground(Color.WHITE);
                textFieldGrid[i][j].setBackground(Color.GRAY);
                textFieldGrid[i][j].setEditable(true);
                textFieldGrid[i][j].setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
                int xPos = (int)(frameSize.x*0.01+(i*90));
                int yPos = (int)(frameSize.y*0.10+(j*80));
                textFieldGrid[i][j].setBounds(xPos+verticalAlign, yPos+horizontalAlign, 80, 70);
                
                //Center Text
                textFieldGrid[i][j].setHorizontalAlignment(JTextField.CENTER);
                //Add KeyListener
                textFieldGrid[i][j].addKeyListener(model);
                
                //Method I got from stack overflow - https://stackoverflow.com/questions/20406870/make-jtextfield-accept-only-one-digit
                //Stops people from entering more than one digit
                AbstractDocument d = (AbstractDocument) textFieldGrid[i][j].getDocument();
                d.setDocumentFilter(new DocumentFilter(){
                    int max = 1; //Max amount of digits/inputs into a single JTextField

                    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) 
                    throws BadLocationException {
                        int documentLength = fb.getDocument().getLength(); 
                        if (documentLength - length + text.length() <= max){
                            super.replace(fb, offset, length, text.toUpperCase(), attrs);
                        }
                    }
                });
                
                backGround.add(textFieldGrid[i][j]);
            }
            horizontalAlign = 0;
        }
        
        finishGame = new JButton("");
        finishGame.setIcon(new ImageIcon("resources/buttonUI/finishGame.png"));
        finishGame.setRolloverEnabled(true);
        finishGame.setRolloverIcon(new ImageIcon("resources/buttonUI/finishGameOnHover.png"));
        finishGame.setBorderPainted(false);
        finishGame.setContentAreaFilled(false);
        finishGame.setBounds((int)(frameSize.x*0.75), (int)((frameSize.y)*0.59), 343, 85);
        finishGame.addActionListener(model);
        
        saveAndExit = new JButton("");
        saveAndExit.setIcon(new ImageIcon("resources/buttonUI/saveAndExit.png"));
        saveAndExit.setRolloverEnabled(true);
        saveAndExit.setRolloverIcon(new ImageIcon("resources/buttonUI/saveAndExitOnHover.png"));
        saveAndExit.setBorderPainted(false);
        saveAndExit.setContentAreaFilled(false);
        saveAndExit.setBounds((int)(frameSize.x*0.75), (int)((frameSize.y)*0.72), 343, 85);
        saveAndExit.addActionListener(model);
        
        exitWithoutSaving = new JButton("");
        exitWithoutSaving.setIcon(new ImageIcon("resources/buttonUI/exit.png"));
        exitWithoutSaving.setRolloverEnabled(true);
        exitWithoutSaving.setRolloverIcon(new ImageIcon("resources/buttonUI/exitOnHover.png"));
        exitWithoutSaving.setBorderPainted(false);
        exitWithoutSaving.setContentAreaFilled(false);
        exitWithoutSaving.setBounds((int)(frameSize.x*0.75), (int)((frameSize.y)*0.85), 343, 85);
        exitWithoutSaving.addActionListener(model);
        
        gamesPlayedLabel = new JLabel("");
        gamesPlayedLabel.setFont(model.getFontForTextArea());
        gamesPlayedLabel.setForeground(Color.WHITE);
        gamesPlayedLabel.setBounds((int)(frameSize.x*0.55), (int)((frameSize.y)*0.09), 300, 33);
        
        totalScoreLabel = new JLabel("");
        totalScoreLabel.setFont(model.getFontForTextArea());
        totalScoreLabel.setForeground(Color.WHITE);
        totalScoreLabel.setBounds((int)(frameSize.x*0.55), (int)((frameSize.y)*0.14), 500, 33);


        backGround.add(finishGame);
        backGround.add(gamesPlayedLabel);
        backGround.add(totalScoreLabel);
        backGround.add(exitWithoutSaving);
        backGround.add(saveAndExit);
        
    }
    
    /**
     * Activates the win status page elements
     * @param frameSize Point
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public void winStatusActivate(Point frameSize){
//        private JLabel winLostStatus;
//        private JLabel whatToDoNow;

        winLostStatus = new JLabel("", SwingConstants.CENTER);
        winLostStatus.setFont(new Font("Impact", Font.BOLD, 150));
        winLostStatus.setForeground(Color.WHITE);
        winLostStatus.setBounds(frameSize.x/2-1000/2, (int)((frameSize.y)*0.15), 1000, 150);

        whatToDoNow = new JLabel("If you want to play again, simply go back and click new game", SwingConstants.CENTER);
        whatToDoNow.setFont(model.getFontForLabel());
        whatToDoNow.setForeground(Color.WHITE);
        whatToDoNow.setBounds(frameSize.x/2-1200/2, (int)((frameSize.y)*0.29), 1200, 100);

        exitOnClose = new JButton("");
        exitOnClose.setIcon(new ImageIcon("resources/buttonUI/exit.png"));
        exitOnClose.setRolloverEnabled(true);
        exitOnClose.setRolloverIcon(new ImageIcon("resources/buttonUI/exitOnHover.png"));
        exitOnClose.setBorderPainted(false);
        exitOnClose.setContentAreaFilled(false);
        exitOnClose.setBounds(model.getFrameSize().x/2-343/2, (int)((model.getFrameSize().y)*0.71), 343, 85);
        exitOnClose.addActionListener(model);


        backGround.add(exitOnClose);
        backGround.add(winLostStatus);
        backGround.add(whatToDoNow);
    }
    
    
    /**
     * Sets all the main elements to visible(boolean)
     * @param e boolean
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public void setMain(boolean e){
        newGame.setVisible(e);
        continueGame.setVisible(e);
        newSeeds.setVisible(e);
        instructions.setVisible(e);
        exitButton.setVisible(e);
    }
    /**
     * Sets all the continue game elements to visible(boolean)
     * @param e boolean
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public void setContinueGame(boolean e){
        gameFoundStatus.setVisible(e);
        savedGame.setVisible(e);
    }
    /**
     * Sets all the new game elements to visible(boolean)
     * @param e boolean
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public void setNewGameStg1(boolean e){
        steps.setVisible(e);
        difficultyRating.setVisible(e);
        setDifficulty.setVisible(e);
        seedsToUseLabel.setVisible(false);
        seedToUse.setVisible(false);
        gameToUseLabel.setVisible(false);
        gameToUse.setVisible(false);
        continueButton.setVisible(false);
    }
    /**
     * Sets all the in progress game elements to visible(boolean)
     * @param e boolean
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public void setInProgressGame(boolean e){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                textFieldGrid[i][j].setVisible(e);
            }
        }
        saveAndExit.setVisible(e);
        exitWithoutSaving.setVisible(e);
        finishGame.setVisible(e);
        gamesPlayedLabel.setVisible(e);
        totalScoreLabel.setVisible(e);
    }
    /**
     * Sets all the win status page elements to visible(boolean)
     * @param e boolean
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public void setWinStatus(boolean e){
        winLostStatus.setVisible(e);
        whatToDoNow.setVisible(e);
        exitOnClose.setVisible(e);
    }
    /**
     * Sets all the instruction page elements to visible(boolean)
     * @param e boolean
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public void setInstructions(boolean e){
        information.setVisible(e);
        infoScroller.setVisible(e);
    }
    /**
     * Sets all the new seeds page elements to visible(boolean)
     * @param e boolean
     * @author Nikkolas Diehl - bjy5305 16945724.
     */
    public void setNewSeeds(boolean e){
        consoleLog.setVisible(e);
        logScroller.setVisible(e);
        data.setVisible(e);
        dataScroller.setVisible(e);
        consoleLabel.setVisible(e);
        infoLabel.setVisible(e);
        generate.setVisible(e);
        amountOfSeeds.setVisible(e);
        value.setVisible(e);
        value.setText("Generate "+amountOfSeeds.getValue()+" seeds.");
        time.setVisible(e);
        consoleLog.setText("");
        data.setText("");
    }
    
    
    //Getters
    public GUIModel getModel() {
        return this.model;
    }    
    public JPanel getBackGround() {
        return this.backGround;
    }
    public JButton getBack() {
        return this.back;
    }
    public JLabel getBackGroundText() {
        return this.backGroundText;
    }
    public JButton getNewGame() {
        return this.newGame;
    }
    public JButton getContinueGame() {
        return this.continueGame;
    }
    public JButton getNewSeeds() {
        return this.newSeeds;
    }
    public JButton getInstructions() {
        return this.instructions;
    }
    public JButton getExitButton() {
        return this.exitButton;
    }
    public JTextArea getInformation() {
        return this.information;
    }
    public JScrollPane getInfoScroller() {
        return this.infoScroller;
    }
    public JLabel getConsoleLabel() {
        return this.consoleLabel;
    }
    public JLabel getInfoLabel() {
        return this.infoLabel;
    }
    public JLabel getValue() {
        return this.value;
    }
    public JLabel getTime() {
        return this.time;
    }
    public JTextArea getConsoleLog() {
        return this.consoleLog;
    }
    public JTextArea getData() {
        return this.data;
    }
    public JScrollPane getLogScroller() {
        return this.logScroller;
    }
    public JScrollPane getDataScroller() {
        return this.dataScroller;
    }
    public JSlider getAmountOfSeeds() {
        return this.amountOfSeeds;
    }
    public JButton getGenerate() {
        return this.generate;
    }
    public JLabel getSteps() {
        return this.steps;
    }
    public JLabel getSeedsToUseLabel() {
        return this.seedsToUseLabel;
    }
    public JSlider getDifficultyRating() {
        return this.difficultyRating;
    }
    public JButton getSetDifficulty() {
        return this.setDifficulty;
    }
    public JLabel getSeedToUse() {
        return this.seedToUse;
    }
    public JLabel getGameToUseLabel() {
        return this.gameToUseLabel;
    }
    public JLabel getGameToUse() {
        return this.gameToUse;
    }
    public JButton getContinueButton() {
        return this.continueButton;
    }
    public JTextField[][] getTextFieldGrid() {
        return this.textFieldGrid;
    }
    public JLabel getGamesPlayedLabel() {
        return this.gamesPlayedLabel;
    }
    public JLabel getTotalScoreLabel() {
        return this.totalScoreLabel;
    }
    public JButton getSaveAndExit() {
        return this.saveAndExit;
    }
    public JButton getExitWithoutSaving() {
        return this.exitWithoutSaving;
    }
    public JButton getFinishGame(){
        return this.finishGame;
    }
    public JLabel getWinLostStatus() {
        return this.winLostStatus;
    }
    public JLabel getWhatToDoNow() {
        return this.whatToDoNow;
    }
    public JButton getExitOnClose(){
        return this.exitOnClose;
    }
    public JLabel getGameFoundStatus() {
        return this.gameFoundStatus;
    }
    public JButton getSavedGame() {
        return this.savedGame;
    }
    
}
