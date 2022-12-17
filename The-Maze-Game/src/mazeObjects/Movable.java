package mazeObjects;

import java.awt.event.KeyEvent;

//A Movable is Game object that can change players position in the maze.

public interface Movable {
    int DIRECTION_UP = KeyEvent.VK_UP;
    int DIRECTION_DOWN = KeyEvent.VK_DOWN;
    int DIRECTION_LEFT = KeyEvent.VK_LEFT;
    int DIRECTION_RIGHT = KeyEvent.VK_RIGHT;

    void move(int direction);
}

