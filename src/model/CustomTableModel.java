package util;

import model.CellType;
import model.Dot;
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

        if(gameModel.getPacman().getPosition().x == columnIndex && gameModel.getPacman().getPosition().y == rowIndex) {
            return switch (gameModel.getPacman().getDirection()) {
                case UP -> CellType.PACMAN_UP;
                case DOWN -> CellType.PACMAN_DOWN;
                case LEFT -> CellType.PACMAN_LEFT;
                case RIGHT -> CellType.PACMAN_RIGHT;
            };
        }


        for (Ghost ghost : gameModel.getGhosts()) {
            if (ghost.getPosition().x == columnIndex && ghost.getPosition().y == rowIndex)
                return CellType.GHOST;
        }

        Dot dot = gameModel.getDotAt(columnIndex, rowIndex);

        if (dot != null && dot.isActive()) {
            return CellType.ACTIVE_DOT;
        } else if(dot != null && !dot.isActive()) {
            return CellType.INACTIVE_DOT;
        }

        return gameModel.getCellType(rowIndex, columnIndex);
    }

    public void cellUpdated(int x, int y) {
        this.fireTableCellUpdated(x, y);
    }
}

