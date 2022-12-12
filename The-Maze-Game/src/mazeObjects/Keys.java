package mazeObjects;


/**
 * This class has code for functioning of 'Key' objects in the game.
 *
 */
public class Key extends GameObject {

    private static final char DEFAULT_ICON = 'K';


    public Key(Position position) {
        super(DEFAULT_ICON, position);
    }

    /**
	Returns the movement hints for the player from its current location in the maze with arrows 
	indicating the directions to move in.

     */
    public String getDirectionHints(Position playerPosition) {
        StringBuilder sb = new StringBuilder();

        if (this.getX() > playerPosition.getX())
            sb.append("-> ");
        else if (this.getX() < playerPosition.getX())
            sb.append("<- ");

        if (this.getY() > playerPosition.getY())
            sb.append("v ");
        else if (this.getY() < playerPosition.getY())
            sb.append("^ ");

        return sb.toString();
    }
}


