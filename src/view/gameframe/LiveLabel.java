package view.gameframe;

import model.GameModel;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Objects;

public class LiveLabel extends JLabel {

    private GameModel gameModel;
    private Image[] images;
    private Image liveBanner;

    public LiveLabel(GameModel gameModel) {
        this.gameModel = gameModel;

        setBackground(Color.BLACK);
        this.images = new Image[3];

        images[0] = loadImage("pacman/one_live.png");
        images[1] = loadImage("pacman/two_lives.png");
        images[2] = loadImage("pacman/three_lives.png");

        liveBanner = loadImage("lives.png");
    }

    private Image loadImage(String path) {
        URL imageUrl = getClass().getResource(path);
        if (imageUrl == null) {
            System.out.println("Resource not found for path: " + path);
            // Try alternative loading method
            imageUrl = ClassLoader.getSystemResource(path);
            if (imageUrl == null) {
                System.out.println("Resource still not found using system class loader for path: " + path);
                return null;
            }
        }
        return new ImageIcon(imageUrl).getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Call to the superclass's paintComponent method to ensure the panel is cleared properly.

        int leftLives = gameModel.getPacman().getLives();

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw the live banner image at the top of the component.
        int bannerWidth = liveBanner.getWidth(null);
        // Obliczenie współrzędnej x tak, aby środek 'liveBanner' był w środku panelu
        int bannerX = (getWidth() - bannerWidth) / 2;

        // Wysokość 'liveBanner' może być stała lub też obliczona w zależności od potrzeb

        // Rysowanie 'liveBanner' na środku panelu w poziomie
        g.drawImage(liveBanner, 0, 0, getWidth(), getHeight()/2, this);

        // Calculate the x position for the lives image to center it horizontally.
        // Assuming 'liveBanner' takes the entire width, and images[] contains images for lives, each taking a third of the width.
        int imageWidth = getWidth() / 3;
        int xPosition = (getWidth() - imageWidth) / 2;

        switch (leftLives) {
            case 1: {
                g.drawImage(images[0], xPosition, getHeight() / 2, imageWidth, getHeight() / 2, this);
                break;
            }
            case 2: {
                g.drawImage(images[1], xPosition, getHeight() / 2, imageWidth, getHeight() / 2, this);
                break;
            }
            case 3: {
                g.drawImage(images[2], xPosition, getHeight() / 2, imageWidth, getHeight() / 2, this);
                break;
            }
        }
    }
}

