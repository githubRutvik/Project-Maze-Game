package main.mazeObjects;

public class Key extends GameObject {

    private static final char DEFAULT_ICON = 'K';

   
    public Key(Position position) {
        super(DEFAULT_ICON, position);
    }

    	//To get direction hints to the keys from the current position of the player
   
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
