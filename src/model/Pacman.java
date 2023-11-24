package model;

import java.awt.*;

import static model.Direction.*;

public class Pacman {
    private int x, y; // Pozycja na planszy
    private Direction direction; // Kierunek ruchu
    private GameModel gameModel; // Referencja do modelu gry, aby sprawdzić kolizje
    private int animationFrame;

    private int speed;
    private volatile boolean moving;

    private Thread moveThread;

    public Pacman(GameModel gameModel, Point startPoint) {
        this.gameModel = gameModel;
        this.x = startPoint.x;
        this.y = startPoint.y;
        this.moving = true;
        this.direction = LEFT; // Przykładowy początkowy kierunek
        this.speed = 400;
    }

    // Metoda do aktualizacji animacji
    public void updateAnimation() {
        animationFrame = (animationFrame + 1) % 4;
    }


    public void startMoving() {
        moveThread = new Thread(() -> {
            while (moving) {
                synchronized (this) {
                    move();
                }
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

    public synchronized void move() {
        // Sprawdź kierunek i zaktualizuj pozycję Pacmana, jeśli ruch jest możliwy
        int newX = gameModel.getPacman().getX();
        int newY = gameModel.getPacman().getY();

        switch (gameModel.getPacman().getDirection()) {
            case UP:
                newY--; // Zmniejsz Y, aby iść do góry
                break;
            case DOWN:
                newY++; // Zwiększ Y, aby iść w dół
                break;
            case LEFT:
                newX--; // Zmniejsz X, aby iść w lewo
                break;
            case RIGHT:
                newX++; // Zwiększ X, aby iść w prawo
                break;
        }

        if (newX >= 0 && newX < gameModel.getCols() && newY >= 0 && newY < gameModel.getRows()) {
            // Sprawdź, czy nowa pozycja jest typu EMPTY
            if (gameModel.getCellType(newY, newX) == CellType.EMPTY) {
                this.setPosition(newX, newY);
            }
        }
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

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public synchronized void setDirection(Direction newDirection) {
        this.direction = newDirection;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getAnimationFrame() {
        return animationFrame;
    }
}
