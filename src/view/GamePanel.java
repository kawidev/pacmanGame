package view;


import model.GameModel;
import util.CustomTableModel;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//Główny panel do rysowania elementów gry, w tym planszy i postaci.
public class GamePanel extends JPanel {
    private JTable gameTable;

    public GamePanel(GameModel gameModel) {
        CustomTableModel tableModel = new CustomTableModel(gameModel);
        gameTable = new JTable(tableModel);

        gameTable.setRowSelectionAllowed(false);
        gameTable.setColumnSelectionAllowed(false);
        gameTable.setRowHeight(50);

        gameTable.getTableHeader().setReorderingAllowed(false);
        gameTable.getTableHeader().setVisible(false);

        for (int i = 0; i < gameTable.getColumnCount(); i++) {
            gameTable.getColumnModel().getColumn(i).setPreferredWidth(50);
            gameTable.getColumnModel().getColumn(i).setMinWidth(50);
            gameTable.getColumnModel().getColumn(i).setMaxWidth(50);
        }
        JScrollPane scrollPane = new JScrollPane(gameTable);

        // Dopasowujemy rozmiar JScrollPane do preferowanego rozmiaru okna gry:
        scrollPane.setPreferredSize(new Dimension(550, 550));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scrollPane, BorderLayout.CENTER);

        for (int i = 0; i < gameTable.getColumnCount(); i++) {
            TableColumn tableColumn = gameTable.getColumnModel().getColumn(i);
            tableColumn.setPreferredWidth(50);
        }
        gameTable.setFocusable(false);

        gameTable.setDefaultRenderer(Object.class, new ImageRenderer());

        tableModel.startPacmanAnimation();

        gameTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Pusty handler, aby uniknąć reakcji na kliknięcia myszką
            }
        });

        setLayout(new BorderLayout());
        add(new JScrollPane(gameTable), BorderLayout.CENTER);

        setFocusable(true);
        requestFocusInWindow();
    }

    public JTable getGameTable() {
        return gameTable;
    }
}
