package main.mazeObjects;


public final class Position {

    private final int x;
    private final int y;

  
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

   
    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Position)) {
            return false;
        } else {
            Position position = (Position) obj;
            return this.x == position.x && this.y == position.y;
        }
    }

    public String toString() {
        return x + ", " + y;
    }
}

