package model;

import javax.swing.*;
import java.util.*;

public class ScoreModel extends DefaultListModel<Player> {

    private static ScoreModel instance;
    List<Player> players;

    private ScoreModel(List<Player> players) {
        this.players = players;
        sortPlayers();
    }

    public static ScoreModel getInstance(List<Player> players) {
        if(instance == null) {
            instance = new ScoreModel(players);
        }
        return instance;
    }

    public static void removeInstance() {
        instance = null;
    }

    public void sortPlayers() {
        this.players.sort(Comparator.comparingInt(Player::getScore)
                .reversed()
                .thenComparingLong(Player::getTime));
    }

    public void updatePlayers(List<Player> newPlayers) {
        this.players.clear();
        this.players.addAll(newPlayers);
        sortPlayers();
        fireContentsChanged(this, 0, getSize() - 1); // Informuje widok o zmianie danych
    }

    @Override
    public int getSize() {
        return players.size();
    }

    @Override
    public Player getElementAt(int index) {
        return players.get(index);
    }
}
