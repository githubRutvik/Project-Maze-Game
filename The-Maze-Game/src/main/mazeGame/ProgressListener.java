package main.mazeGame;

/**
 * The listener interface for receiving level progress feedback.
 * The class that is interested in processing level progress feedback has to
 * implements this interface.
 * A progress feedback is generated when the player has either completed or failed a level.
 */

public interface ProgressListener {

    /**
     * Invoked when level one is completed.
     * See the class description of {LevelOne} for the level completion details.
     */
    void levelOneCompleted();

    /**
     * Invoked when level two is completed.
     * See the class description of {LevelTwo} for the level completion details.
     */
    void levelTwoCompleted();

    /**
     * Invoked when the Player is dead to end the game.
     */
    void levelFailed();
}

