package mazeObjects;


public class Torch extends GameObject {

    static final int VISIBILITY_BOOST = 2;
    private static final char DEFAULT_ICON = '%';

  
    public Torch(Position position) {
        super(DEFAULT_ICON, position);
    }

}
