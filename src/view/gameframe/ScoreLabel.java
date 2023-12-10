package view.gameframe;

import model.GameModel;

import javax.swing.*;
import java.awt.*;

public class ScoreLabel extends JLabel {

    private GameModel gameModel;
    private ImageIcon image;

    public ScoreLabel(GameModel gameModel) {
        this.gameModel = gameModel;
        image = new ImageIcon("resources/score.png");
    }

    @Override
    public void paintComponent(Graphics g) {


        g.setColor(Color.BLACK);
        // Draw the black background rectangle for the score
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight()/2, this);
        // Set the color for the text
        g.setColor(Color.WHITE);



        // Set the font for the text
        g.setFont(new Font("SansSerif", Font.BOLD, 20)); // Adjust font size as necessary

        // Retrieve the score to display
        String scoreText = Integer.toString(gameModel.getPacman().getScore()); // Replace currentScore with the actual score variable
        FontMetrics metrics = g.getFontMetrics();

        // Determine the x coordinate for the text
        int x = (getWidth() - metrics.stringWidth(scoreText)) / 2;

        // Determine the y coordinate for the text, assuming you want to place it in the center of the bottom half
        int y = ((getHeight() / 2) + ((getHeight() / 2) - metrics.getHeight()) / 2) + metrics.getAscent();

        // Draw the string such that it's centered on the area below the image
        g.drawString(scoreText, x, y);
    }
}
