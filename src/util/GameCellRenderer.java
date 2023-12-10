package util;

import model.*;

import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class GameCellRenderer extends DefaultTableCellRenderer {

    private Object value;
    private int row, column;


    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);


        setIcon(null);
        setBackground(null);
        setText("");

        this.value = value;
        this.row = row;
        this.column = column;

        setBackground(Color.RED);

        if (value instanceof CellType) {
            CellType cellType = (CellType) value;
            if (cellType == CellType.WALL) {
                //    new Rectangle(column, row, )
                setBackground(Color.BLACK);
            }
        }
        return this;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        List<GameEntity> entities = GameEntity.getEntitiesAt(column, row);

        boolean hasGhost = false;
        boolean hasPacman = false;
        Dot dot = null;

        for (Object entity : entities) {
            if (entity instanceof Ghost) {
                hasGhost = true;
            } else if (entity instanceof Dot) {
                dot = (Dot) entity;
            } else if (entity instanceof Pacman) {
                hasPacman = true;
            }
        }

        if (dot != null && dot.isActive()) {
            // Rysowanie kropki...
            g.setColor(Color.BLACK); // Ustaw kolor kropki
            int diameter = 5; // Ustaw średnicę kropki
            int x = (getWidth() - diameter) / 2;  // Oblicz współrzędną X środka komórki
            int y = (getHeight() - diameter) / 2; // Oblicz współrzędną Y środka komórki
            g.fillOval(x, y, diameter, diameter); // Rysowanie kropki
        }


        if (value instanceof CellType) {
            CellType cellType = (CellType) value;

            if (cellType == CellType.PACMAN) {
                // Rysowanie Pacmana
                g.setColor(Color.YELLOW); // Kolor Pacmana
                g.fillOval(0, 0, getWidth(), getHeight()); // Rysowanie Pacmana na całej komórce
            } else if (cellType == CellType.GHOST) {
                // Rysowanie ducha
                g.setColor(Color.PINK); // Kolor ducha
                g.fillOval(0, 0, getWidth(), getHeight());
            }
        }
    }
}

