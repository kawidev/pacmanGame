package view.gameframe;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class EndPanel extends JPanel {

    private Image playAgainImg, quitGameImg;

    public EndPanel() {

        setLayout(null);
        setBackground(Color.BLACK);


        try {
            playAgainImg = ImageIO.read(new File("resources/playagaininfo.png"));
            quitGameImg = ImageIO.read(new File("resources/quitinfo.png"));
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception...
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        if(playAgainImg != null) {
            int playAgainWidth = panelWidth * 4/5;
            int playAgainHeight = panelHeight /2;
            int playAgainX = (panelWidth - playAgainWidth) / 2;
            int playAgainY = (panelHeight - playAgainHeight) / 5;

            g.drawImage(playAgainImg, playAgainX, playAgainY, playAgainWidth, playAgainHeight, this);
        }
        if(quitGameImg != null) {
            int quitWidth = panelWidth * 4 / 5;
            int quitHeight = panelHeight / 2;
            int quitX = (panelWidth - quitWidth) / 2;
            int quitY = panelHeight * 3/5;

            g.drawImage(quitGameImg, quitX, quitY, quitWidth, quitHeight, this);
        }
    }


}
