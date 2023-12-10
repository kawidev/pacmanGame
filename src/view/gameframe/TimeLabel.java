package view.gameframe;

import model.GameModel;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class TimeLabel extends JLabel {

    private GameModel gameModel;

    private Image image;

    public TimeLabel(GameModel gameModel) {
        this.gameModel = gameModel;

        image = loadImage("time.png");
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
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.drawImage(image, 0, 0, getWidth(), getHeight()/2, this);
    }
}
