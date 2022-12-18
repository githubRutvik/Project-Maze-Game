package mazeGame;

import mazeObjects.Player;

import javax.swing.*;
import java.util.Scanner;

/**
 *This class will have entire Game Logic
	
	The game has two levels 'Level 1' & 'Level 2'
	So if the player wants to win the game the he/she has to complete both the levels.
	If the player fails to complete Level 1 then the Game ends.
 */
public class Game implements ProgressListener {

    static boolean isVisibilityMode = true;
    private JFrame jFrame;
    private GameMap gameMap;
    private Player player;
    private Display display;
    private LevelOne levelOne;
    private LevelTwo levelTwo;

    public Game() {
        init();
        setupWindowForKeyListener();
        gameLevelOne();
    }

    /**
     * Player has to give the Maze Dimensions
     * The difficulty level of the game increases as the maze sie increases.
     * <p>
     * Note: the maze height and width must be at least 6 because there will be 5 game objects in level one.
     * Each game object will be scattered randomly in different rows and columns. 
     * No two game objects will appear in the same row nor the same column. 
     * Therefore the maze must have a minimum size of 6x6.
     */
    private void init() {
        Scanner s = new Scanner(System.in);
        int mazeHeight, mazeWidth;
        do {
            System.out.print("Enter maze height (min 6): ");
            while (!s.hasNextInt()) {
                System.out.print("That's not a number! Enter again: ");
                s.next();
            }
            mazeHeight = s.nextInt();
        } while (mazeHeight < 5);
        do {
            System.out.print("Enter maze width (min 6): ");
            while (!s.hasNextInt()) {
                System.out.print("That's not a number! Enter again: ");
                s.next();
            }
            mazeWidth = s.nextInt();
        } while (mazeWidth < 5);

        gameMap = new GameMap(mazeHeight, mazeWidth);
        player = new Player(gameMap.getRandomPosition());
        display = new Display(gameMap.getMap(), player);
        display.gameIntroMessage();
    }

    /**
     * Player has to navigate the player in map using arrow keys.
     
     * Note: The player has to click on the window first before playing so that the KeyEvents
     * can be captured by the window.
     */
    private void setupWindowForKeyListener() {
        jFrame = new JFrame();
        jFrame.setVisible(true);
        jFrame.setSize(100, 100);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Initializes LevelOne, setup the ProgressListener and KeyListener before starting level one.
     */
    private void gameLevelOne() {
        levelOne = new LevelOne(gameMap, player, display);
        levelOne.addProgressListener(this);
        jFrame.addKeyListener(levelOne);
        levelOne.start();
    }

    /**
     * Initializes LevelTwo, setup the ProgressListener and KeyListener before starting level two.
     */
    private void gameLevelTwo() {
        levelTwo = new LevelTwo(gameMap, player, display);
        levelTwo.setProgressListener(this);
        jFrame.addKeyListener(levelTwo);
        levelTwo.start();
    }

    @Override
    public void levelOneCompleted() {
        jFrame.removeKeyListener(levelOne);
        display.nextLevelMessage();
        gameLevelTwo();
    }

    @Override
    public void levelTwoCompleted() {
        jFrame.removeKeyListener(levelTwo);
        display.winMessage();
        System.exit(0);
    }

    @Override
    public void levelFailed() {
        display.loseMessage();
        System.exit(0);
    }
}

