package model;

import java.io.*;
import java.util.*;

public class Player implements Serializable {


    @Serial
    private static final long serialVersionUID = 1L; // Dodaj to w klasie Player

    private static List<Player> players = new ArrayList<>();
    private transient GameModel gameModel;
    private String playerName;
    private int playerScore;
    private long playerTime;

    public Player(String playerName, GameModel gameModel) {
        this.gameModel = gameModel;
        this.playerName = playerName;
        this.playerScore = gameModel.getPacman().getScore();
        this.playerTime = gameModel.getPlayerTime();
        players.add(this);
    }

    public static void save() {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("score.dat"))) {
            oos.writeObject(players);
            System.out.println("Serializacja zakonczona");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void load() {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("score.dat"))) {
            players = (List<Player>) ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Player getPlayerByName(String playerName) {
        for(Player player : players) {
            if (player.playerName.equals(playerName)) {
                return player;
            }
        }
        return null;
    }

    public static List<Player> getPlayers() {
        return players;
    }

    public void updateScore(int score) {
        if(score > this.playerScore) {
            this.playerScore = score;
        }
    }

    public String getName() {
        return playerName;
    }

    public int getScore() {
        return playerScore;
    }

    public long getTime() {
        return playerTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(playerName, player.playerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerName);
    }
}
