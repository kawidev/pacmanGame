package model;

import java.awt.*;

public class Ghost {
    private int x, y; // Pozycja na planszy

    public Ghost(Point startPoint) {
        this.x = startPoint.x;
        this.y = startPoint.y;
    }

    public Ghost(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
