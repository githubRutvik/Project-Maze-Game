package mazeObjects;

import mazeGame.GameMap;

public class Exit extends GameObject {

    private static final char DEFAULT_ICON = 'E';

   
    public Exit(Position position) {
        super(DEFAULT_ICON, position);
    }

   
    public static Position generateExitPosition(GameMap gameMap, Player player) {
        int x, y;
        if (player.getX() > gameMap.getWidth() / 2) {
            if (player.getY() > gameMap.getHeight() / 2) {
                // player is in quadrant 4, set exit to quadrant 2
                x = 2;
                y = 1;
            } else {
                // player is in quadrant 1, set exit to quadrant 3
                x = 2;
                y = gameMap.getHeight() - 2;
            }
        } else {
            if (player.getY() > gameMap.getHeight() / 2) {
                // player is in quadrant 3, set exit to quadrant 1
                x = gameMap.getWidth() - 3;
                y = 1;
            } else {
                // player is in quadrant 2, set exit to quadrant 4
                x = gameMap.getWidth() - 3;
                y = gameMap.getHeight() - 2;
            }
        }
        return new Position(x, y);
    }
}
