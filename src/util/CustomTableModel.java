package util;

import model.CellType;
import model.GameModel;
import model.Ghost;

import javax.swing.table.AbstractTableModel;

public class CustomTableModel extends AbstractTableModel  {

    private final GameModel gameModel;

    public CustomTableModel(GameModel gameModel) {
        this.gameModel = gameModel;
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

        if(gameModel.getPacman().getPosition().x == columnIndex && gameModel.getPacman().getPosition().y == rowIndex)
            return CellType.PACMAN;

        for (Ghost ghost : gameModel.getGhosts()) {
            if (ghost.getPosition().x == columnIndex && ghost.getPosition().y == rowIndex)
                return CellType.GHOST;
        }

        return gameModel.getCellType(rowIndex, columnIndex);
    }
}

