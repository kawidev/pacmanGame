package controller;

import model.Player;
import model.ScoreModel;
import view.score_frame.ScoreFrame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class ScoreController {


    private static ScoreController instance;
    private ScoreModel scoreModel;
    private ScoreFrame scoreFrame;

    private ScoreController() {
        this.scoreModel = ScoreModel.getInstance(Player.getPlayers());
        this.scoreFrame = ScoreFrame.getInstance(scoreModel);

        SwingUtilities.invokeLater(() -> {
            scoreFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    scoreFrame.closeWindow();
                    ScoreModel.removeInstance();
                    ScoreController.removeInstance();
                }
            });
        });
    }
    public static ScoreController getInstance() {
        if(instance == null) {
            instance = new ScoreController();
        }
        return instance;
    }

    public static void removeInstance() {
        if(instance != null) {
            instance = null;
        }
    }
}
