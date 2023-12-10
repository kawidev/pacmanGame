package view.gameframe;

import model.GameModel;
import util.CustomTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameFrame extends JFrame {

    private static GameFrame instance;
    private GameModel gameModel;
    private GameTable gameTable;
    private CustomTableModel customTableModel;
    private JLabel livesLabel;
    private JLabel scoreLabel;
    private JLabel timeLabel;
    private JPanel statusBar;
    private JPanel dotsView;

    private GameOverPanel gameOverPanel;
    private EndPanel endPanel;

    private boolean isGameOverOnTheScreen;

    public static int tableWidth, tableHeight;

    private GameFrame(String title, GameModel gameModel) {
        super(title);

        this.gameModel = gameModel;

        setBackground(Color.BLACK);
        setUpFrame();
        initializeStatusBar();
        initializeGameTable();
        dotsView = new JPanel();
        add(dotsView);
        addEventListeners();

        gameOverPanel = new GameOverPanel();
        add(gameOverPanel, BorderLayout.CENTER);
        gameOverPanel.setBounds(0, 0, getWidth(), getHeight());
        gameOverPanel.setVisible(false);
        isGameOverOnTheScreen = false;

        endPanel = new EndPanel();
        add(endPanel, BorderLayout.CENTER);
        endPanel.setBounds(0, 0, getWidth(), getHeight());
        endPanel.setVisible(false);

        pack();
        validate();

    }

    public static GameFrame getInstance(String title, GameModel gameModel) {
        if(instance == null) {
            instance = new GameFrame(title, gameModel);
        }
        return instance;
    }

    public static void removeInstance() {
        if(instance != null) instance = null;
    }
        private void setUpFrame() {
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setResizable(true);
            setBackground(Color.BLACK);
            setMaximizedBounds(new Rectangle(getMaxScreenBounds()));
            setLayout(new BorderLayout());
            setLocationRelativeTo(null);
            setVisible(true);
        }

    private void initializeStatusBar() {
        statusBar = new JPanel(new GridLayout(1, 3)); // 1 rząd, 3 kolumny

        livesLabel = new LiveLabel(gameModel);
        scoreLabel = new ScoreLabel(gameModel);
        timeLabel = new TimeLabel(gameModel);

        statusBar.add(livesLabel);
        statusBar.add(scoreLabel);
        statusBar.add(timeLabel);

        // Ustaw preferowany rozmiar paska statusu
        int statusBarHeight = getPreferredSize().height / 12;
        statusBar.setPreferredSize(new Dimension(getWidth(), statusBarHeight));

        // Dodanie panelu statusu do głównego okna
        add(statusBar, BorderLayout.SOUTH);
    }

        private void initializeGameTable() {
            this.customTableModel = new CustomTableModel(gameModel);
            this.gameTable = new GameTable(customTableModel);

            gameTable.setOpaque(false);
            gameTable.setFillsViewportHeight(true);

            tableWidth = gameTable.getWidth();
            tableHeight = gameTable.getHeight();

            add(gameTable);
    }

    private void addEventListeners() {
        SwingUtilities.invokeLater(() -> {
            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    Insets insets = getInsets();

                    // Calculate the height of the status bar as a fraction of the frame's height
                    int statusBarHeight = getSize().height / 12;
                    statusBar.setPreferredSize(new Dimension(getWidth(), statusBarHeight));
                    statusBar.revalidate();

                    // Calculate the available height for the game table
                    int availableHeight = getSize().height - insets.top - insets.bottom - statusBarHeight;

                    int rows = gameTable.getRowCount();
                    int columns = gameTable.getColumnCount();

                    // Calculate the minimum size required for the game table based on the maze dimensions
                    int minWidth = columns * 10 + insets.left + insets.right;
                    int minHeight = rows * 10 + insets.top + insets.bottom + statusBarHeight;
                    setMinimumSize(new Dimension(minWidth, minHeight));

                    // Calculate the new dimensions for the cells of the maze
                    int width = getWidth() - insets.left - insets.right;
                    int cellWidth = Math.max(width / columns, 10);
                    int cellHeight = Math.max(availableHeight / rows, 10);

                    // Set the new row height and column width for the game table
                    gameTable.setRowHeight(cellHeight);
                    for (int i = 0; i < columns; i++) {
                        gameTable.getColumnModel().getColumn(i).setMinWidth(cellWidth);
                    }
                    gameTable.setPreferredSize(new Dimension(getWidth() - insets.left - insets.right, availableHeight));
                    gameTable.setFillsViewportHeight(true);

                    gameOverPanel.setBounds(0, 0, getWidth(), getHeight());
                    endPanel.setBounds(0, 0, getWidth(), getHeight());

                    // Revalidate and repaint the components to apply changes
                    gameTable.revalidate();
                    gameTable.repaint();

                    // Update the size of the game table to fit the new dimensions
                    gameTable.setSize(width, availableHeight);
                }
            });
        });

    }


    private Rectangle getMaxScreenBounds() {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice[] gd = ge.getScreenDevices();
            Rectangle bounds = new Rectangle();

            for (GraphicsDevice device : gd) {
                GraphicsConfiguration gc = device.getDefaultConfiguration();
                bounds = bounds.union(gc.getBounds());
            }

            Insets screenInsets = getToolkit().getScreenInsets(getGraphicsConfiguration());
            bounds.x += screenInsets.left;
            bounds.y += screenInsets.top;
            bounds.width -= (screenInsets.left + screenInsets.right);
            bounds.height -= (screenInsets.top + screenInsets.bottom);

            return bounds;
        }

        public GameTable getGameTable() {
            return gameTable;
        }

        public CustomTableModel getTableModel() {
            return customTableModel;
        }

        public GameOverPanel getGameOverPanel() {
            return gameOverPanel;
        }

    public void notifyGameOver() {
        gameTable.setVisible(false);
        gameTable.setFocusable(false);
        statusBar.setVisible(false);
        System.out.println("Game Over - panel should be visible now");
        gameOverPanel.setSize(getWidth(), getHeight());
        gameOverPanel.setVisible(true);
        gameOverPanel.getNameField().setFocusable(true);


    }

    public void notifyGameEndPanel() {
        gameTable.setVisible(false);
        gameTable.setFocusable(false);
        statusBar.setVisible(false);
        gameOverPanel.setVisible(false);
        gameOverPanel.getNameField().setFocusable(false);
        endPanel.setVisible(true);
        endPanel.setFocusable(true);
        endPanel.requestFocus();
    }

    public void notifyPlayAgain() {
        setFocusable(true);
        requestFocusInWindow();
        gameTable.setVisible(true);
        gameTable.setFocusable(false);
        statusBar.setVisible(true);
        //System.out.println("Game Over - panel should be visible now");
        gameOverPanel.setSize(getWidth(), getHeight());
        gameOverPanel.setVisible(false);
        gameOverPanel.getNameField().setFocusable(false);
        endPanel.setVisible(false);
        endPanel.setFocusable(false);
    }

    public EndPanel getEndPanel() {
        return endPanel;
    }
}
