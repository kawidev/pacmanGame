package model;

import java.util.ArrayList;
import java.util.List;

public class Dot extends GameEntity {

    private static List<Dot> allDotsInMaze = new ArrayList<>();
    private boolean isActive; // zamiast usuwac obiekt kropki z planszy a pozniej meczyc sie z potencjalnymi
    // NullPointerException postanowilem po kolizcji "Wylaczac ja na czas 45 sekund"
    int inactiveTime = 45000; // 45 s w ms

    public Dot(int x, int y, GameModel gameModel) {
        super(x, y, gameModel);
        this.isActive = true;
        allDotsInMaze.add(this);
    }

    @Override
    public void reset() {
        this.isActive = true;
    }

    @Override
    public void playAgain() {
        this.reset();
    }


    public boolean isActive() {
        return isActive;
    }

    public void setActiveState(boolean state) {
        this.isActive = state;
    }

}
