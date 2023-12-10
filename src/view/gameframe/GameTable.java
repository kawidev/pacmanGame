package view.gameframe;

import util.CustomTableModel;
import util.GameCellRenderer;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;

public class GameTable extends JTable {

    public GameTable(AbstractTableModel dm) {
        super(dm);
        customTable(this);
    }

    private void customTable(JTable gameTable) {
        gameTable.setRowSelectionAllowed(false); // Wyłączenie możliwości zaznaczania wierszy
        gameTable.setColumnSelectionAllowed(false); // Wyłączenie możliwości zaznaczania kolumn
        gameTable.setCellSelectionEnabled(false); // Wyłączenie możliwości zaznaczania pojedynczych komórek
        gameTable.setShowGrid(false); // Usunięcie linii siatki
        gameTable.setTableHeader(null); // Usunięcie nagłówka tabeli
        gameTable.setIntercellSpacing(new Dimension(0, 0)); // Ustawienie odstępu między komórkami na zero
        gameTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // Automatyczne skalowanie kolumn
        gameTable.setFocusable(false);
        gameTable.setDefaultRenderer(Object.class, new GameCellRenderer());
        //System.out.println("Renderer set");
    }

    public int getCellHeight() {
        Rectangle cellRect = this.getCellRect(0, 0, true);
        return cellRect.getSize().height;
    }
    public int getCellWidth() {
        Rectangle cellRect = this.getCellRect(0, 0, true);
        return cellRect.getSize().width;
    }

    public Rectangle getCellSize() {
        return this.getCellRect(0, 0, true);

    }



}
