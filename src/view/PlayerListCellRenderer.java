package view;

import model.Player;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;

public class PlayerListCellRenderer extends JPanel implements ListCellRenderer<Player> {

    private JLabel nameLabel;
    private JLabel scoreLabel;
    private JLabel timeLabel;

    public PlayerListCellRenderer() {
        setLayout(new GridLayout(1, 3, 10, 0)); // 1 row, 3 columns, 10px horizontal gap

        nameLabel = new JLabel();
        nameLabel.setHorizontalAlignment(JLabel.CENTER);

        scoreLabel = new JLabel();
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);

        timeLabel = new JLabel();
        timeLabel.setHorizontalAlignment(JLabel.CENTER);

        add(nameLabel);
        add(scoreLabel);
        add(timeLabel);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Player> list, Player value, int index, boolean isSelected, boolean cellHasFocus) {
        nameLabel.setText(value.getName());
        scoreLabel.setText(String.valueOf(value.getScore()));
        timeLabel.setText(formatTime(value.getTime()));

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        setEnabled(list.isEnabled());


        adaptFontSizeAndGaps(list);
        setOpaque(true);
        return this;
    }

    public void adaptFontSizeAndGaps(JList<?> list) {
        int listWidth = list.getWidth();

        // Use a more responsive font size calculation
        // For instance, start decreasing the font size more aggressively when the list width shrinks
        int fontSize = Math.max(6, listWidth / 30); // Ustal minimalną wielkość czcionki

        Font font = list.getFont().deriveFont((float) fontSize);
        nameLabel.setFont(font);
        scoreLabel.setFont(font);
        timeLabel.setFont(font);

        // No need to set the gaps if you're just using the GridLayout's default behavior
        // If you want dynamic gaps, uncomment the following lines
        // int gap = Math.max(1, listWidth / 100); // At least 1 pixel gap
        // GridLayout layout = (GridLayout) getLayout();
        // layout.setHgap(gap);

        // Set the preferred size with enough space for the text and some padding
        int cellHeight = getFontMetrics(font).getHeight() + 10; // 10 pixels padding


        // This call to revalidate() is necessary if you're changing layout properties
        // If you're not changing gaps, you can comment this out
        // list.revalidate();
    }


    private String formatTime(long time) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(time) + " s";
    }
}
