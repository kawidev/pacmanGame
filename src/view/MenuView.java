package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuView extends JFrame {
    private JButton newGameButton;
    private JButton highScoresButton;
    private JButton exitButton;

    public MenuView() {
        initializeMenu();
    }

    private void initializeMenu() {
        setTitle("Pacman Game Menu");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1)); // 3 rows, 1 column layout

        newGameButton = new JButton("Nowa Gra");
        highScoresButton = new JButton("Najlepsze Wyniki");
        exitButton = new JButton("Wyjście");

        add(newGameButton);
        add(highScoresButton);
        add(exitButton);

        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    public JButton getNewGameButton() {
        return newGameButton;
    }

    public JButton getHighScoresButton() {
        return highScoresButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }
}
