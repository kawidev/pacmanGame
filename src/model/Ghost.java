package model;

import java.awt.*;

public class Ghost extends GameEntity {
    private volatile boolean moving;
    private Direction direction;

    private Thread moveThread;

    private final Point startPoint = new Point();
    private final int initialSpeed = 200;
    private final Direction initialDirection;

    private int speed;


    public Ghost(int x, int y, GameModel gameModel) {
        super(x, y, gameModel);
        startPoint.x = x;
        startPoint.y = y;
        this.moving = true;
        this.speed = initialSpeed;
        this.direction = Direction.LEFT;
        initialDirection = direction;
        startMoving();
    }

    @Override
    public void reset() {
        this.x = startPoint.x;
        this.y = startPoint.y;
        this.speed = initialSpeed;
        this.direction = initialDirection;
    }

    @Override
    public void playAgain() {
        this.reset();
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

    public Direction getDirection() { return direction; }
}
