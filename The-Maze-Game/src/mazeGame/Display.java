package mazeGame;

import mazeObjects.Key;
import mazeObjects.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static mazeMap.MazeMap.*;


/**
 * A class encapsulating the methods required for printing stuffs on the console.
 */
class Display {

    private static final char FOG = '#';
    private final char[][] map;
    private final Player player;
    private final int height;
    private final int width;

    /**
     * map the 2D array of characters representing the game map with maze and game objects
     * player the object
     */
    
    Display(char[][] map, Player player) {
        this.map = map;
        this.height = map.length;
        this.width = map[0].length;
        this.player = player;
    }

    void update(ArrayList<Key> keys) {
        clearScreen();
        char[][] displayMap = (Game.isVisibilityMode) ? createCoveredMap() : map;

        for (int row = 0; row < displayMap.length; row++) {
            System.out.print(new String(displayMap[row]));
            switch (row) {
                case 2:
                    System.out.printf("\tKey(s) collected: %d/%d\n", LevelOne.NUMBER_OF_KEYS - keys.size(), LevelOne.NUMBER_OF_KEYS);
                    break;
                case 4:
                    System.out.printf("\tHint to the nearest key: %s\n", getNearestKeyHints(keys));
                    break;
                case 6:
                    System.out.printf("\tVisibility: %d blocks\n", player.getVisibility());
                    break;
                case 8:
                    System.out.printf("\tHealth: %d/%d\n", (player.isDead()) ? 0 : player.getHealthLevel(), Player.INITIAL_HEALTH_LEVEL);
                    break;
                default:
                    System.out.println();
            }
        }
    }

    private String getNearestKeyHints(ArrayList<Key> keys) {
        if (keys.size() == 0) {
            return "-";
        } else if (keys.size() == 1) {
            return keys.get(0).getDirectionHints(player.getPosition());
        } else {
            Key nearestKey = keys.get(0);
            for (int i = 1; i < keys.size(); i++) {
                if (keys.get(i).distanceTo(player) < nearestKey.distanceTo(player))
                    nearestKey = keys.get(i);
            }
            return nearestKey.getDirectionHints(player.getPosition());
        }
    }
    
    void update() {
        clearScreen();
        char[][] displayMap = (Game.isVisibilityMode) ? createCoveredMap() : map;

        for (int row = 0; row < displayMap.length; row++) {
            System.out.print(new String(displayMap[row]));
            switch (row) {
                case 2:
                    System.out.printf("\tKey(s) collected: %d/%d\n", LevelOne.NUMBER_OF_KEYS, LevelOne.NUMBER_OF_KEYS);
                    break;
                case 4:
                    System.out.println("\tHint to the nearest key: -");
                    break;
                case 6:
                    System.out.printf("\tVisibility: %d blocks\n", player.getVisibility());
                    break;
                case 8:
                    System.out.printf("\tHealth: %d/%d\n", (player.isDead()) ? 0 : player.getHealthLevel(), Player.INITIAL_HEALTH_LEVEL);
                    break;
                default:
                    System.out.println();
            }
        }
    }

    /**
     * Validation of walls is needed because the player can't see through walls.
     */
    private char[][] createCoveredMap() {

        char[][] coveredMap = new char[height][width];

        // cover the whole map with shadow first
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                coveredMap[i][j] = FOG;
            }
        }
        // uncover the player
        coveredMap[player.getY()][player.getX()] = player.getIcon();

        for (int j = player.getX() - 2; j <= player.getX() + 2; j++) {
            // uncover the map from player position to up
            for (int i = player.getY() - 1; i >= player.getY() - player.getVisibility() * 2 - 1 && i >= 0; i--) {
                coveredMap[i][j] = map[i][j];
                if (map[i][player.getX()] == HORIZONTAL_WALL) break;
            }
            // uncover the map from player position to down
            for (int i = player.getY() + 1; i <= player.getY() + player.getVisibility() * 2 + 1 && i < height; i++) {
                coveredMap[i][j] = map[i][j];
                if (map[i][player.getX()] == HORIZONTAL_WALL) break;
            }
        }

        for (int i = player.getY() - 1; i <= player.getY() + 1; i++) {
            // uncover the map from player position to left
            for (int j = player.getX() - 1; j >= player.getX() - player.getVisibility() * 4 - 2 && j >= 0; j--) {
                coveredMap[i][j] = map[i][j];
                if (map[player.getY()][j] == VERTICAL_WALL) break;
            }
            // uncover the map from player position to right
            for (int j = player.getX() + 1; j <= player.getX() + player.getVisibility() * 4 + 2 && j < width; j++) {
                coveredMap[i][j] = map[i][j];
                if (map[player.getY()][j] == VERTICAL_WALL) break;
            }
        }

        return coveredMap;
    }

    /**
     * Prints the game intro on the console, invoked before level one starts.
     */
    void gameIntroMessage() {
        clearScreen();
        String[] intros = {"Hello there, you are trapped in a deadly maze.\n\n",
                "To escape from the maze, you must find all the keys, 'K', that are scattered in the maze.\n",
                "After collecting all the keys, the exit, 'E', will appear at the corner of the maze.\n\n",
                "The Maze is completely fogged up , '#', so your visibility is limited to two blocks.\n",
                "However, there're Light switches in the maze, '%', hidden somewhere. That will help you boost your visibility in the fog.\n\n",
                "Every movement will cost you a drop of blood. So make your move wisely! (Press the arrow keys to move).\n\n",
                "Press ENTER to continue... "};
        for (String intro : intros) {
            printWithDelay(intro, 40);
            delay(500);
        }
        // wait for player to press enter
        Scanner s = new Scanner(System.in);
        String input;
        do {
            input = s.nextLine();
        } while (!input.equals(""));
    }

    /**
     * Prints a message indicating victory on the console, invoked when the player has completed both levels.
     */
    void winMessage() {
        int mid_H = height / 2;
        int mid_W = width / 2;

        clear(mid_H, mid_W);

        map[mid_H][mid_W - 3] = 'V';
        map[mid_H][mid_W - 2] = 'I';
        map[mid_H][mid_W - 1] = 'C';
        map[mid_H][mid_W] = 'T';
        map[mid_H][mid_W + 1] = 'O';
        map[mid_H][mid_W + 2] = 'R';
        map[mid_H][mid_W + 3] = 'Y';
        map[mid_H][mid_W + 4] = '!';

        Game.isVisibilityMode = false;
        update();
    }

    /**
     * Prints a message indicating game over on the console, invoked when the player has lost the game.
     */
    void loseMessage() {
        int mid_H = height / 2;
        int mid_W = width / 2;

        clear(mid_H, mid_W);

        map[mid_H][mid_W - 4] = 'G';
        map[mid_H][mid_W - 3] = 'A';
        map[mid_H][mid_W - 2] = 'M';
        map[mid_H][mid_W - 1] = 'E';
        map[mid_H][mid_W] = ' ';
        map[mid_H][mid_W + 1] = 'O';
        map[mid_H][mid_W + 2] = 'V';
        map[mid_H][mid_W + 3] = 'E';
        map[mid_H][mid_W + 4] = 'R';
        map[mid_H][mid_W + 5] = '!';

        Game.isVisibilityMode = false;
        update();
    }

    /**
     * Prints a message indicating next level on the console, invoked when the player has completed level one.
     */
    void nextLevelMessage() {
        Game.isVisibilityMode = false;
        update();
        delay(500);
        printWithDelay("\nALL KEYS FOUND! ", 80);
        delay(500);
        printWithDelay("BUT...\nWE'RE NOT DONE YET...", 80);
        delay(800);
        printWithDelay("\nFIND THE EXIT BEFORE YOUR HP BECOMES ZERO!", 70);
        delay(1000);
        Game.isVisibilityMode = true;
    }

    /**
     * Prints a message indicating invalid movement on the console, invoked when the player's movement is not valid.
     */
    void invalidMovementMessage() {
        System.out.println("You can't walk through walls, my friend..");
    }

    private void printWithDelay(String message, int millis) {
        if (message.length() > 0) {
            for (int i = 0; i < message.length(); i++) {
                System.out.print(message.charAt(i));
                delay(millis);
            }
        }
    }

    private void delay(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clears the console output.
     */
    private void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clears the middle of the game map based on the specified middle vertical and horizontal index.
     * mid_H the middle vertical index
     * mid_W the middle horizontal index
     */
    private void clear(int mid_H, int mid_W) {
        for (int i = mid_H - 1; i <= mid_H + 1; i++) {
            for (int j = mid_W - 7; j <= mid_W + 7; j++) {
                map[i][j] = EMPTY_SPACE;
            }
        }
    }
}