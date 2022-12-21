package mazeObjects;

public class Armour extends GameObject{
	
		static final int HEALTH_BOOST = 5;
	    private static final char DEFAULT_ICON = '@';

	Armour(char icon, Position position) {
		super(DEFAULT_ICON, position);
	}

}
