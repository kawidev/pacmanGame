package model;

import java.awt.*;

import static model.Direction.*;

public class Pacman extends GameEntity  {
    private Direction direction; // Kierunek ruchu
    private int animationFrame;
    private int speed;
    private volatile boolean moving;

    private final Point startPosition = new Point();
    private final int initialSpeed = 200;
    private final Direction initialDirection = LEFT;

    private int score;
    private int lives;

    private Thread moveThread;

    public Pacman(int x, int y, GameModel gameModel) {
        super(x, y, gameModel);
        startPosition.x = x;
        startPosition.y = y;
        this.moving = true;
        this.direction = initialDirection; // Przykładowy początkowy kierunek
        this.speed = initialSpeed;
        this.score = 0;
        this.lives = 3;
    }

    public void reset() {
        this.x = startPosition.x;
        this.y = startPosition.y;
        this.direction = initialDirection;
        this.speed = initialSpeed;
    }

    @Override
    public void playAgain() {
        reset();
        this.score = 0;
        this.lives = 3;
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
        int newX = gameModel.getPacman().getPosition().x;
        int newY = gameModel.getPacman().getPosition().y;

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
            // Sprawdź, czy nowa pozycja jest typu EMPTY lub DOT
            if (gameModel.getCellType(newY, newX) == CellType.EMPTY || gameModel.getCellType(newY, newX) == CellType.DOT) {
                this.setPosition(newX, newY);
            }
        }
    }
    public synchronized void setDirection(Direction newDirection) {
        this.direction = newDirection;
    }

    public void addScore(GameEntity eatable) {
        if (eatable instanceof Dot) {
            this.score += 1;
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public int getAnimationFrame() {
        return animationFrame;
    }

    public Point getPosition() {
        return new Point(x, y);
    }

    public int getLives() {
        return lives;
    }
    public void takeLife() {
        this.lives--;
    }

    public int getScore() {
        return score;
    }
}
