package view.score_frame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScoreFrameHighScoreLabel extends JLabel {

    private ScoreFrame scoreFrame;

    private BufferedImage image;

    public ScoreFrameHighScoreLabel(ScoreFrame scoreFrame) {
        this.scoreFrame = scoreFrame;

        try {
            image = ImageIO.read(new File("resources/highscore.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            // Scale the image to fit the label's width while preserving aspect ratio
            int imageWidth = image.getWidth(this);
            int imageHeight = image.getHeight(this);
            float aspectRatio = (float) imageWidth / imageHeight;

            int scaledHeight = getHeight(); // Or a fixed height if you prefer
            int scaledWidth = (int) (scaledHeight * aspectRatio);

            // Center the image horizontally if it's narrower than the label
            int x = (getWidth() - scaledWidth) / 2;

            g.drawImage(image, 0, 0, scoreFrame.getWidth(), scaledHeight, this);
        }
    }

}
