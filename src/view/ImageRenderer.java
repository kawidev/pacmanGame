package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ImageRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        if (value instanceof ImageIcon) {
            // Ustawia ikonę, jeśli wartość jest ImageIcon
            JLabel label = new JLabel((ImageIcon) value);
            label.setOpaque(true); // Aby kolor tła był widoczny
            label.setHorizontalAlignment(JLabel.CENTER); // Wyśrodkuj ikonę w komórce
            return label;
        } else if (value instanceof Color) {
            // Ustawia kolor tła, jeśli wartość jest Color
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            this.setOpaque(true);
            this.setBackground((Color) value);
            this.setIcon(null); // Usuń ikonę
            this.setText(""); // Usuń tekst z komórki
            return this;
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }


}
