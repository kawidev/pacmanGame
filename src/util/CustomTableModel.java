package util;

import model.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.net.URL;

public class CustomTableModel extends AbstractTableModel {

    private ImageIcon[][] pacmanImages; // Tablica ikon dla animacji
    private GameModel gameModel;


    public CustomTableModel(GameModel gameModel) {
        this.gameModel = gameModel;
        this.pacmanImages = new ImageIcon[4][4]; // 4 kierunki, 3 etapy otwierania otworu gębowego :D
        loadPacmanImages();
    }

    private void loadPacmanImages() {

        //up
        pacmanImages[Direction.UP.ordinal()][0] = new ImageIcon("resources/pacman/up/up_1.png");
        pacmanImages[Direction.UP.ordinal()][1] = new ImageIcon("resources/pacman/up/up_2.png");
        pacmanImages[Direction.UP.ordinal()][2] = new ImageIcon("resources/pacman/up/up_3.png");
        pacmanImages[Direction.UP.ordinal()][3] = new ImageIcon("resources/pacman/up/up_4.png");


        // down
        pacmanImages[Direction.DOWN.ordinal()][0] = new ImageIcon("resources/pacman/down/down_1.png");
        pacmanImages[Direction.DOWN.ordinal()][1] = new ImageIcon("resources/pacman/down/down_2.png");
        pacmanImages[Direction.DOWN.ordinal()][2] = new ImageIcon("resources/pacman/down/down_3.png");
        pacmanImages[Direction.DOWN.ordinal()][3] = new ImageIcon("resources/pacman/down/down_4.png");


        //left
        pacmanImages[Direction.LEFT.ordinal()][0] = new ImageIcon("resources/pacman/left/left_1.png");
        pacmanImages[Direction.LEFT.ordinal()][1] = new ImageIcon("resources/pacman/left/left_2.png");
        pacmanImages[Direction.LEFT.ordinal()][2] = new ImageIcon("resources/pacman/left/left_3.png");
        pacmanImages[Direction.LEFT.ordinal()][3] = new ImageIcon("resources/pacman/left/left_4.png");


        //right
        pacmanImages[Direction.RIGHT.ordinal()][0] = new ImageIcon("resources/pacman/right/right_1.png");
        pacmanImages[Direction.RIGHT.ordinal()][1] = new ImageIcon("resources/pacman/right/right_2.png");
        pacmanImages[Direction.RIGHT.ordinal()][2] = new ImageIcon("resources/pacman/right/right_3.png");
        pacmanImages[Direction.RIGHT.ordinal()][3] = new ImageIcon("resources/pacman/right/right_4.png");

    }



    public void startPacmanAnimation() {
        new Thread(() -> {
            while (true) {
                // Aktualizuj stan animacji Pacmana
                gameModel.getPacman().updateAnimation();

                // Aktualizuj widok
                updatePacmanPosition();

                try {
                    Thread.sleep(50); // Ustaw odpowiednie opóźnienie
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }).start();
    }


    @Override
    public int getRowCount() {
        // Zwraca liczbę wierszy planszy, co odpowiada wysokości planszy
        return gameModel.getBoard().getRows();
    }

    @Override
    public int getColumnCount() {
        // Zwraca liczbę kolumn planszy, co odpowiada szerokości planszy
        return gameModel.getBoard().getCols();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (isPacmanCell(columnIndex, rowIndex)) {
            Direction direction = gameModel.getPacman().getDirection();
            int frame = gameModel.getPacman().getAnimationFrame();
            return pacmanImages[direction.ordinal()][frame];
        }

        CellType cellType = gameModel.getCellType(rowIndex, columnIndex);
        switch (cellType) {
            case WALL:
                return Color.BLUE; // Możesz użyć obiektu Color lub ścieżki do obrazka ściany
            case EMPTY:
                return Color.WHITE; // Możesz użyć obrazka ścieżki
            // Dodaj inne przypadki dla różnych typów komórek
            default:
                return null;
        }
    }



    private boolean isPacmanCell(int x, int y) {
        // Zwraca true, jeśli komórka odpowiada pozycji Pacmana
        if(gameModel.getPacman().getX() == x && gameModel.getPacman().getY() == y)
            return true;
        return false;
    }

//    private ImageIcon getPacmanAnimationForCurrentDirection() {
//        // Zwraca odpowiednią ikonę na podstawie aktualnego stanu i kierunku Pacmana
//        // Może to wymagać śledzenia aktualnej klatki animacji i kierunku Pacmana
//    }

    public void updatePacmanPosition() {
        SwingUtilities.invokeLater(this::fireTableDataChanged);
    }
}

