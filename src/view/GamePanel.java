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
        this.gameTable = new JTable(tableModel);
        customGameTable(gameTable);

        JScrollPane scrollPane = new JScrollPane(gameTable);

        customScrollPane(scrollPane);
        this.add(scrollPane, BorderLayout.CENTER);

        for (int i = 0; i < gameTable.getColumnCount(); i++) {
            TableColumn tableColumn = gameTable.getColumnModel().getColumn(i);
            tableColumn.setPreferredWidth(50);
        }

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

    public void customGameTable(JTable gameTable) {
        gameTable.setRowSelectionAllowed(false);
        gameTable.setColumnSelectionAllowed(false);
        gameTable.setShowGrid(false);
        gameTable.setRowHeight(50);

        gameTable.getTableHeader().setReorderingAllowed(false);
        gameTable.getTableHeader().setVisible(false);

        for (int i = 0; i < gameTable.getColumnCount(); i++) {
            gameTable.getColumnModel().getColumn(i).setPreferredWidth(50);
            gameTable.getColumnModel().getColumn(i).setMinWidth(50);
            gameTable.getColumnModel().getColumn(i).setMaxWidth(50);
        }
        gameTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        gameTable.setFocusable(false);
        gameTable.setDefaultRenderer(Object.class, new GameCellRenderer());
    }

    public void customScrollPane(JScrollPane scrollPane) {

        scrollPane.setPreferredSize(new Dimension(1000, 1000));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    public JTable getGameTable() {
        return gameTable;
    }
}
