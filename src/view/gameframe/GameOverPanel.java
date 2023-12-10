package view.gameframe;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class GameOverPanel extends JPanel {
    private BufferedImage gameOverImage;
    private BufferedImage typeYourNameImage;
    private BufferedImage pressEnterImage;
    private JTextField nameField;

    private boolean lastView;

    public GameOverPanel() {

        try {
            gameOverImage = ImageIO.read(new File("resources/gameover.png"));
            typeYourNameImage = ImageIO.read(new File("resources/typename.png"));
            pressEnterImage = ImageIO.read(new File("resources/pressenter.png"));
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception...
        }

        setLayout(null); // Ustawiamy null layout, aby móc ręcznie umiejscowić komponenty
        setBackground(Color.BLACK);

        nameField = new JTextField(10);
        nameField.getCaret().setVisible(false);
        nameField.getCaret().setSelectionVisible(false);
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, nameField.getPreferredSize().height));
        nameField.setBackground(Color.BLACK);
        nameField.setForeground(Color.WHITE);
        nameField.setCaretColor(Color.BLACK);
        nameField.setHorizontalAlignment(JTextField.CENTER);
        nameField.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        nameField.setFont(new Font("Arial", Font.BOLD, 20)); // Ustaw właściwą czcionkę
        nameField.requestFocusInWindow();

        // Słuchacz zmiany rozmiaru
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Umieszczanie nameField pomiędzy "TYPE YOUR NAME BELOW" i "& PRESS ENTER"
                int fieldWidth = getWidth() / 2;
                int fieldHeight = nameField.getPreferredSize().height;
                int fieldX = (getWidth() - fieldWidth) / 2;
                int fieldY = (int) (getHeight() / 3) + (getHeight() / 10); // Pod "TYPE YOUR NAME BELOW"

                nameField.setBounds(fieldX, fieldY, fieldWidth, fieldHeight);
                repaint();
            }
        });

        add(nameField);
        this.lastView = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        if (gameOverImage != null) {
            int gameOverWidth = panelWidth * 3 / 5; // 60% szerokości panelu
            int gameOverHeight = panelHeight / 3; // 1/3 wysokości panelu
            int gameOverX = (panelWidth - gameOverWidth) / 2;
            int gameOverY = 0; // Na górze panelu

            g.drawImage(gameOverImage, gameOverX, gameOverY, gameOverWidth, gameOverHeight, this);

        }
            // Jeśli obraz "TYPE YOUR NAME BELOW" istnieje, rysuj go pod obrazem "GAME OVER"
        if (typeYourNameImage != null) {
            int typeNameWidth = panelWidth / 2; // 50% szerokości panelu
            int typeNameHeight = panelHeight / 10; // 10% wysokości panelu
            int typeNameX = (panelWidth - typeNameWidth) / 2;
            int typeNameY = panelHeight / 3; // Bezpośrednio pod obrazem "GAME OVER"

            g.drawImage(typeYourNameImage, typeNameX, typeNameY, typeNameWidth, typeNameHeight, this);
        }

        // Rysowanie obrazu "& PRESS ENTER" pod obrazem "TYPE YOUR NAME BELOW"
        // Umieszczanie pola tekstowego pomiędzy obrazami
        int nameFieldY = (int) (getHeight() / 3) + (getHeight() / 10); // Pod "TYPE YOUR NAME BELOW"
        int pressEnterY = nameFieldY + nameField.getPreferredSize().height + 20; // Dodajemy odstęp

        if (pressEnterImage != null) {
            int pressEnterWidth = getWidth() / 2; // 50% szerokości panelu
            int pressEnterHeight = getHeight() / 10; // 10% wysokości panelu
            int pressEnterX = (getWidth() - pressEnterWidth) / 2;

            g.drawImage(pressEnterImage, pressEnterX, pressEnterY, pressEnterWidth, pressEnterHeight, this);
        }
    }

    public String getPlayerName() {
        return nameField.getText(); // Założenie, że nameField to JTextField
    }

    public JTextField getNameField() {
        return nameField;
    }


    public boolean isLastView() {
        return lastView;
    }

}
