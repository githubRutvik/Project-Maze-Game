package mazeObjects;


public class Player extends GameObject implements Movable {

    public static final int INITIAL_HEALTH_LEVEL = 250;
    private static final char DEFAULT_ICON = 'J';
    private static final int INITIAL_VISIBILITY = 2;
    private int visibility;
    private int healthLevel;
    private boolean hasTorch;

    
    public Player(Position position) {
        super(DEFAULT_ICON, position);
        this.visibility = INITIAL_VISIBILITY;
        this.healthLevel = INITIAL_HEALTH_LEVEL;
        this.hasTorch = false;
    }
    //Visibility is the number of blocks that are visible
   
    public int getVisibility() {
        return visibility;
    }

    public int getHealthLevel() {
        return healthLevel;
    }

    public void reduceHealthLevelBy(int value) {
        healthLevel -= value;
    }

    public boolean isDead() {
        return healthLevel <= 0;
    }

    public boolean hasTorch() {
        return hasTorch;
    }

    public void foundTorch() {
        hasTorch = true;
        visibility += Torch.VISIBILITY_BOOST;
    }

    public void move(int direction) {
        switch (direction) {
            case DIRECTION_UP:
                this.setY(this.getY() - 2);
                break;
            case DIRECTION_DOWN:
                this.setY(this.getY() + 2);
                break;
            case DIRECTION_LEFT:
                this.setX(this.getX() - 4);
                break;
            case DIRECTION_RIGHT:
                this.setX(this.getX() + 4);
                break;
            default:
                break;
        }
    }
}

