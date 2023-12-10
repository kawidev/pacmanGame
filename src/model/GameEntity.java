package model;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public abstract class GameEntity {


    private static List<GameEntity> gameEntityList = new ArrayList<>();
    protected int x, y;
    GameModel gameModel;

    public GameEntity(int x, int y, GameModel gameModel) {
        this.x = x;
        this.y = y;
        this.gameModel = gameModel;

        gameEntityList.add(this);
    }

    public abstract void reset();
    public abstract void playAgain();

    public Point getPosition() {
        return new Point(x, y);
    }
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static List<GameEntity> getEntitiesAt(int x, int y) {
        List<GameEntity> gameEntitiesAt = new ArrayList<>();

        for (GameEntity entity : gameEntityList) {
            if (entity.getPosition().getX() == x && entity.getPosition().getY() == y) {
                gameEntitiesAt.add(entity);
            }
        }
        return gameEntitiesAt; // Zwróć null, jeśli żadna encja nie znajduje się w podanej komórce
    }

    public static List<GameEntity> getGameEntities() {
        return gameEntityList;
    }

}
