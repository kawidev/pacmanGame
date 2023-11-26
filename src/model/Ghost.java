package model;

import view.GhostView;

import javax.swing.*;
import java.awt.*;

public class Ghost {
    private int x, y;

    private GameModel gameModel;
    private GhostView ghostView;

    private volatile boolean moving;

    private Direction direction;

    private Thread moveThread;


    private int speed;

    public Ghost(GameModel gameModel, Point startPoint) {
        this.gameModel = gameModel;
        this.x = startPoint.x;
        this.y = startPoint.y;
        this.moving = true;
        this.speed = 600;
        this.direction = Direction.LEFT;
    }

    public Ghost(GameModel gameModel, int x, int y) {
        this.gameModel = gameModel;
        this.x = x;
        this.y = y;
        this.moving = true;
        this.speed = 600;
        this.direction = Direction.LEFT;
        startMoving();
    }

    public void startMoving() {
        moveThread = new Thread(() -> {
            while (moving) {
                move();
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException e) {
                    moving = false;
                    Thread.currentThread().interrupt();
                }
            }
        });
        moveThread.start();
    }
    private void move() {
        // Logika decydująca o zmianie kierunku
        Direction newDirection = findNewDirection();

        // Zaktualizuj kierunek tylko jeśli nie jest to kierunek przeciwny do obecnego
        if (!isOppositeDirection(newDirection, this.direction)) {
            this.direction = newDirection;
        }

        // Sprawdź, czy następny ruch nie jest na ścianę i czy jest w granicach labiryntu
        if (canMoveInDirection(newDirection)) {
            // Logika ruchu ducha
            switch (newDirection) {
                case UP:
                    y--;
                    break;
                case DOWN:
                    y++;
                    break;
                case LEFT:
                    x--;
                    break;
                case RIGHT:
                    x++;
                    break;
            }
        }
    }
    private boolean canMoveInDirection(Direction direction) {
        Point nextPosition = getNextPosition(direction);
        // Sprawdź czy pole w następnym kierunku nie jest ścianą i czy jest w granicach labiryntu
        return isWithinBounds(nextPosition) && gameModel.getMaze()[nextPosition.y][nextPosition.x] != CellType.WALL;
    }

    private boolean isWithinBounds(Point position) {
        return position.x >= 0 && position.x < gameModel.getCols() &&
                position.y >= 0 && position.y < gameModel.getRows();
    }


    private Direction findNewDirection() {
        // Tu będzie Twoja logika wyboru nowego kierunku, np. na podstawie pozycji Pacmana
        // Na potrzeby przykładu wybieram losowy kierunek
        Direction[] directions = Direction.values();
        return directions[(int) (Math.random() * directions.length)];
    }


    private Point getNextPosition(Direction direction) {
        int newX = x, newY = y;
        switch (direction) {
            case UP: newY--; break;
            case DOWN: newY++; break;
            case LEFT: newX--; break;
            case RIGHT: newX++; break;
        }
        return new Point(newX, newY);
    }

    private boolean isOppositeDirection(Direction d1, Direction d2) {
        return (d1 == Direction.UP && d2 == Direction.DOWN) ||
                (d1 == Direction.DOWN && d2 == Direction.UP) ||
                (d1 == Direction.LEFT && d2 == Direction.RIGHT) ||
                (d1 == Direction.RIGHT && d2 == Direction.LEFT);
    }



    // Gettery i settery
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    public Direction getDirection() { return direction; }

    public Point getPosition() {
        return new Point(this.x, this.y);
    }
}
