package main.mazeObjects;


public abstract class GameObject {

    private final char icon;
    private Position position;


    GameObject(char icon, Position position) {
        this.icon = icon;
        this.position = position;
    }

 
    public char getIcon() {
        return icon;
    }


    public Position getPosition() {
        return position;
    }

 
    public int getX() {
        return position.getX();
    }
    //Sets the x-coordinate of this game object to the specified x-coordinate.
    
    void setX(int x) {
        position = new Position(x, position.getY());
    }

    public int getY() {
        return position.getY();
    }
    //Sets the y-coordinate of this game object to the specified y-coordinate.
  
    void setY(int y) {
        position = new Position(position.getX(), y);
    }


    public double distanceTo(GameObject obj) {
        return Math.sqrt(Math.pow(((this.getX() - obj.getX()) / 4.0), 2)
                + Math.pow(((this.getY() - obj.getY()) / 2.0), 2));
    }
}
