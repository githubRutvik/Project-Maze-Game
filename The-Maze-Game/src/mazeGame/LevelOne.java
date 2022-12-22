package mazeGame;

import mazeObjects.Key; 
import mazeObjects.Player;
import mazeObjects.Torch;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Logic for Level 1
 * The player has to collect all the keys to flash the torch.
 * Then the entire Maze will become visible.
 * Every move of the player will cost 1 Health Point
 */
class LevelOne implements KeyListener {

    static final int NUMBER_OF_KEYS = 3;
    private final GameMap gameMap;
    private final Player player;
    private final Display display;
    private ProgressListener progressListener;
    private Torch torch;
    private ArrayList<Key> keys;

    
    LevelOne(GameMap gameMap, Player player, Display display) {
        this.gameMap = gameMap;
        this.player = player;
        this.display = display;
        init();
    }


    void addProgressListener(ProgressListener progressListener) {
        this.progressListener = progressListener;
    }


    private void init() {
        // init game objects
        gameMap.addToMap(player);

        keys = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_KEYS; i++) {
            Key key = new Key(gameMap.getRandomPosition());
            keys.add(key);
            gameMap.addToMap(key);
        }
        torch = new Torch(gameMap.getRandomPosition());
        gameMap.addToMap(torch);

        display.update(keys);
    }


    void start() {
        while (true) {
            if (player.isDead()) {
                progressListener.levelFailed();
                break;
            }
            // 0 means all keys are collected
            if (keys.size() == 0) {
                gameMap.removeFromMap(torch);
                progressListener.levelOneCompleted();
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private void checkIfPlayerHasReachedItem() {
        for (Key key : keys) {
            if (player.getPosition().equals(key.getPosition())) {
                keys.remove(key);
                keys.trimToSize();
                System.out.println(keys.size());
                return;
            }
        }
        if (!player.hasTorch() && player.getPosition().equals(torch.getPosition()))
            player.foundTorch();
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
                    checkIfPlayerHasReachedItem();
                    player.reduceHealthLevelBy(1);
                    display.update(keys);
                } else {
                    display.invalidMovementMessage();
                }
                break;
            case KeyEvent.VK_F6:
                Game.isVisibilityMode = !Game.isVisibilityMode;
                display.update(keys);
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

