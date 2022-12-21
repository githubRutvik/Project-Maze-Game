package mazeGame;

import mazeObjects.Exit;
import mazeObjects.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**Level details:
Mission: find the exit to win the game
Health level of the player will decrease by 5 every second.
Game Over when the health level of the player is less than or equals to zero.
 */
public class LevelTwo implements KeyListener {

    private final GameMap gameMap;
    private final Player player;
    private final Display display;
    private ProgressListener progressListener;
    private Exit exit;
    private boolean hasFoundExit = false;

    LevelTwo(GameMap gameMap, Player player, Display display) {
        this.gameMap = gameMap;
        this.player = player;
        this.display = display;
        init();
    }

    void setProgressListener(ProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    private void init() {
        // init game objects
        gameMap.addToMap(player);

        exit = new Exit(Exit.generateExitPosition(gameMap, player));
        gameMap.addToMap(exit);

        display.update();
    }

    /**
     * Starts the game loop of level two.
     */
    void start() {
        while (true) {
            if (player.isDead()) {
                progressListener.levelFailed();
                break;
            }
            if (hasFoundExit) {
                progressListener.levelTwoCompleted();
                break;
            }
            player.reduceHealthLevelBy(5);
            display.update();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkIfPlayerHasFoundExit() {
        if (player.getPosition().equals(exit.getPosition()))
            hasFoundExit = true;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
                final int DIRECTION = e.getKeyCode();
                if (gameMap.validateMovement(player, DIRECTION)) {
                    gameMap.removeFromMap(player);
                    player.move(DIRECTION);
                    gameMap.addToMap(player);
                    checkIfPlayerHasFoundExit();
                    display.update();
                } else {
                    display.invalidMovementMessage();
                }
                break;
            case KeyEvent.VK_F6:
                Game.isVisibilityMode = !Game.isVisibilityMode;
                display.update();
                break;
            default:
                break;
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
