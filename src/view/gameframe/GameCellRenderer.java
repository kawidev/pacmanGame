package util;

import model.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class GameCellRenderer extends DefaultTableCellRenderer {

    private Object value;
    private int mouthAngle = 0; // Kąt ust Pacmana
    private boolean isMouthClosing = false; // Czy usta się zamykają
    private long lastUpdateTime = 0;

    private BufferedImage ghostImage;

    public GameCellRenderer() {
        try {
            ghostImage = ImageIO.read(new File("resources/ghost.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);


        setIcon(null);
        setBackground(null);
        setText("");

        this.value = value;

        setBackground(Color.RED);

        if (value instanceof CellType) {
            CellType cellType = (CellType) value;
            if (cellType == CellType.WALL) {
                setBackground(Color.BLACK);
            }

        }
        return this;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (value instanceof CellType) {
            CellType cellType = (CellType) value;
            int size = Math.min(getWidth(), getHeight());
            g.setColor(Color.YELLOW);

            // Ustawienie kątów początkowych dla górnej i dolnej połowy ust Pacmana
            int startAngleUpper = 0;
            int startAngleLower = 0;
            int arcAngle; // Zakres łuku dla każdej połowy ust

            // Obliczanie kątów początkowych dla górnej i dolnej połowy
            switch (cellType) {
                case PACMAN_UP:
                    g.setColor(Color.YELLOW); // Ustawienie koloru na żółty dla ciała Pacmana
                    g.fillArc(0, 0, getWidth(), getHeight(), 0, 360); // Rysowanie pełnego ciała Pacmana

                    g.setColor(getBackground()); // Ustawienie koloru na tło, aby 'wyciąć' usta
                    // Wysokość ust równa połowie średnicy

                    // Rysowanie 'wycięcia' dla ust
                    g.fillArc(0, 0, getWidth(), getHeight(), 90 - mouthAngle / 2, mouthAngle);

                    break;
                case PACMAN_DOWN:
                    g.setColor(Color.YELLOW); // Ustawienie koloru na żółty dla ciała Pacmana
                    g.fillArc(0, 0, getWidth(), getHeight(), 0, 360); // Rysowanie pełnego ciała Pacmana

                    g.setColor(getBackground()); // Ustawienie koloru na tło, aby 'wyciąć' usta

                    // Rysowanie 'wycięcia' dla ust, ale tym razem na dole
                    g.fillArc(0, 0, getWidth(), getHeight(), 270 - mouthAngle / 2, mouthAngle);

                    break;

                case PACMAN_LEFT:
                    startAngleUpper = 360 - mouthAngle / 2;
                    startAngleLower = mouthAngle / 2;
                    arcAngle = 180 - mouthAngle; // Zakres łuku dla każdej połowy ust

                    g.fillArc(0, 0, getWidth(), getHeight(), startAngleUpper, arcAngle);
                    g.fillArc(0, 0, getWidth(), getHeight(), startAngleLower, -arcAngle); // Ujemny zakres łuku dla symetrycznego efektu
                    break;
                case PACMAN_RIGHT:
                    startAngleUpper = 30 - mouthAngle / 2; // Kąt początkowy górnej połowy ust.
                    startAngleLower = 330 + mouthAngle; // Kąt początkowy dolnej połowy ust.
                    arcAngle = 300; // Zakres łuku dla ust, 300 stopni dla pełnego otwarcia.

                    // Rysowanie górnej połowy ust Pacmana.
                    g.fillArc(0, 0, getWidth(), getHeight(), startAngleUpper, arcAngle);
                    // Rysowanie dolnej połowy ust Pacmana.
                    g.fillArc(0, 0, getWidth(), getHeight(), startAngleLower, -arcAngle);
                    break;

                case GHOST:
                    g.drawImage(ghostImage,0, 0, getWidth(), getHeight(), this);
                    return;
                case ACTIVE_DOT:
                    // Rysowanie aktywnego kropka, omijamy rysowanie ust
                    g.fillOval(0, 0, getWidth()/3, getHeight()/3);
                    return;
            }


        }
        updateMouthAngle();
    }

    private void updateMouthAngle() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdateTime > 100) {
            mouthAngle += isMouthClosing ? -5 : 5;
            if (mouthAngle < 0 || mouthAngle > 45) {
                isMouthClosing = !isMouthClosing;
                mouthAngle = Math.max(0, Math.min(mouthAngle, 45)); // Zapewniamy, że kąt ust jest w przedziale 0-45
            }

            lastUpdateTime = currentTime;
            repaint(); // Wywołanie ponownego rysowania komponentu
        }
    }





}

