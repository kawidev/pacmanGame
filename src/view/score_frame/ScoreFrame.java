package view.score_frame;


import model.ScoreModel;
import model.Player;
import view.PlayerListCellRenderer;
import view.gameframe.TimeLabel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScoreFrame extends JFrame {

    private static ScoreFrame instance;
    private ScoreModel scoreModel;
    private JList<Player> highScore;
    private JLabel highScoreLabel;

    private JScrollPane scrollPane;

    private JLabel nameLabel, scoreLabel, timeLabel;

    private ScoreFrame(ScoreModel scoreModel) {
        this.scoreModel = scoreModel;
        setTitle("High Scores");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(580, 600));
        setLocationRelativeTo(null);
        setBackground(Color.BLACK);


        highScoreLabel = new ScoreFrameHighScoreLabel(this);

        Dimension initialSize = new Dimension(getWidth(), (int) (getHeight() * 0.35));
        highScoreLabel.setPreferredSize(initialSize);
        highScoreLabel.setOpaque(false);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // High score label constraints
        gbc.gridx = 0; // First column
        gbc.gridy = 0; // First row
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Span across all columns
        gbc.fill = GridBagConstraints.BOTH; // Fill both horizontal and vertical space
        gbc.weightx = 1.0; // Give row some weight
        gbc.weighty = 0.35; // Take up a quarter of vertical space
        add(highScoreLabel, gbc);


        highScore = new JList<>(scoreModel);
        highScore.setCellRenderer(new PlayerListCellRenderer());
        highScore.setBackground(Color.BLACK);
        highScore.setForeground(Color.WHITE);
        highScore.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                // Override without calling super to prevent any selection
            }
        });
        scrollPane = new JScrollPane(highScore);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        gbc.gridy = 1; // Second row
        gbc.weightx = 1.0;
        gbc.weighty = 0.65; // Take up the rest of the space
        add(scrollPane, gbc);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                revalidate();
                adjustFontSizes();

            }
        });
        pack();
        setVisible(true);
    }

    private void adjustFontSizes() {
        // Calculate the font size based on the current width of the JList
        int listWidth = highScore.getWidth();
        // Ensure we also consider the height of the viewport for the font size calculation
        int listHeight = scrollPane.getViewport().getHeight();
        int fontSize = Math.max(6, Math.min(listWidth / 30, listHeight / 3)); // Adjust this ratio as needed

        // Update the font for the entire JList
        Font newFont = highScore.getFont().deriveFont((float) fontSize);
        highScore.setFont(newFont);

        // Recalculate the cell heights based on the new font
        int cellHeight = highScore.getFontMetrics(newFont).getHeight() + 2;
        highScore.setFixedCellHeight(cellHeight);

        // Force the JList to update its layout
        highScore.revalidate();
        highScore.repaint();
    }



    public static ScoreFrame getInstance(ScoreModel scoreModel) {
        if (instance == null) {
            instance = new ScoreFrame(scoreModel);
        }
        return instance;
    }

    public void closeWindow() {
        instance = null;
        dispose();
    }
}
