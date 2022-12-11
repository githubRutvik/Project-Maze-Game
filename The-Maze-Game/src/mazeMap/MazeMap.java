package mazeMap;

// Maze Map Generation

import java.util.Random;


public final class MazeMap {

    public static final char VERTICAL_WALL = '|';
    public static final char HORIZONTAL_WALL = '-';
    public static final char EMPTY_SPACE = ' ';
    private final int UP = 0;
    private final int LEFT = 1;
    private final int DOWN = 2;
    private final int RIGHT = 3;
    private final int rows, columns;
    private final char[][] maze;

//Taking the rows and columns from the user
    public MazeMap(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        maze = new char[rows * 2 + 1][columns * 4 + 1];
        initAllWalls();
    }

//Displaying the (2D Array) maze generated with dimensions given by user 
    public char[][] generate() {

        boolean[][] isVisited = new boolean[rows][columns];
        int remainingBlocks = rows * columns;
        int row = 0, column = 0;
        isVisited[row][column] = true;
        remainingBlocks--;

        while (remainingBlocks > 0) {
            int direction = getDirection(row, column);
            int n_row = (direction == UP) ? row - 1 : (direction == DOWN) ? row + 1 : row;
            int n_column = (direction == LEFT) ? column - 1 : (direction == RIGHT) ? column + 1 : column;

            if (!isVisited[n_row][n_column]) {
                carveWall(row, column, direction);
                isVisited[n_row][n_column] = true;
                remainingBlocks--;
            }
            row = n_row;
            column = n_column;
        }

        return maze;
    }

//Building Maze Walls
    
    private void initAllWalls() {
        for (int row = 0; row < maze.length; row++) {
            if (row % 2 == 0) {
                for (int column = 0; column < maze[0].length; column++) {
                    if (column % 4 == 0)
                        maze[row][column] = '+';
                    else
                        maze[row][column] = HORIZONTAL_WALL;
                }
            } else {
                for (int column = 0; column < maze[0].length; column++) {
                    if (column % 4 == 0)
                        maze[row][column] = VERTICAL_WALL;
                    else
                        maze[row][column] = EMPTY_SPACE;
                }
            }
        }
    }


    //This function makes a passage in the maze 
    //Walls are replaced with the spaces to carve a passage in maze
    private void carveWall(int row, int column, int direction) {
        switch (direction) {
            case UP:
                for (int i = column * 4 + 1; i <= column * 4 + 3; i++)
                    maze[row * 2][i] = EMPTY_SPACE;
                break;
            case DOWN:
                for (int i = column * 4 + 1; i <= column * 4 + 3; i++)
                    maze[row * 2 + 2][i] = EMPTY_SPACE;
                break;
            case LEFT:
                maze[row * 2 + 1][column * 4] = EMPTY_SPACE;
                break;
            case RIGHT:
                maze[row * 2 + 1][column * 4 + 4] = EMPTY_SPACE;
                break;
        }
    }


    // This function will give some random direction or hint we can say from the current position of the player in the maze.
    // It will help player to solve the maze efficiently
    private int getDirection(int row, int column) {
        Random r = new Random();
        int direction;

        if (column == 0) {
            if (row == 0) {
                // down or right
                direction = DOWN + r.nextInt(2);
            } else if (row == rows - 1) {
                // up or right
                direction = RIGHT * r.nextInt(2);
            } else {
                // up or down or right
                direction = r.nextInt(3);
                direction = (direction == LEFT) ? RIGHT : direction;
            }
        } else if (column == columns - 1) {
            if (row == 0) {
                // down or left
                direction = LEFT + r.nextInt(2);
            } else if (row == rows - 1) {
                // up or left
                direction = r.nextInt(LEFT + 1);
            } else {
                // up or down or left
                direction = r.nextInt(3);
            }
        } else if (row == 0) {
            // left or right or down
            direction = LEFT + r.nextInt(RIGHT);
        } else if (row == rows - 1) {
            // left or right or up
            direction = r.nextInt(3);
            direction = (direction == DOWN) ? RIGHT : direction;
        } else {
            // any directions
            direction = r.nextInt(4);
        }

        return direction;
    }
}

