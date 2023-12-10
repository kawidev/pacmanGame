package controller;


import model.Player;
import view.MenuView;

import javax.swing.*;

//Zarządza interakcjami w menu głównym
public class MenuController {
    private MenuView menuView;

    public MenuController() {

        Player.load();

        menuView = new MenuView();

        menuView.getNewGameButton().addActionListener(e -> startNewGame());
        menuView.getHighScoresButton().addActionListener(e -> showHighScores());
        menuView.getExitButton().addActionListener(e -> exitGame());



    }
    private void startNewGame() {

        if(GameController.getInstance() == null) {
            String rows = JOptionPane.showInputDialog(menuView, "Wprowadź liczbę wierszy:", "Nowa Gra", JOptionPane.QUESTION_MESSAGE);
            String cols = JOptionPane.showInputDialog(menuView, "Wprowadź liczbę kolumn:", "Nowa Gra", JOptionPane.QUESTION_MESSAGE);

            // Konwersja na liczby i walidacja
            try {
                int numRows = Integer.parseInt(rows);
                int numCols = Integer.parseInt(cols);

                // Sprawdzenie, czy rozmiary są w akceptowalnym zakresie
                if (numRows >= 10 && numRows <= 100 && numCols >= 10 && numCols <= 100) {
                    int adjustedNumRows = numRows;
                    int adjustedNumCols = numCols;

                    if(adjustedNumRows % 2 == 0) adjustedNumRows++;
                    if(adjustedNumCols % 2 == 0) adjustedNumCols++;

                    GameController.getInstance(adjustedNumRows, adjustedNumCols);
                } else {
                    // Wyświetl wiadomość o błędzie, jeśli rozmiary są poza zakresem
                    JOptionPane.showMessageDialog(menuView, "Rozmiar planszy musi być między 10 a 100.", "Błąd", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                // Wyświetl wiadomość o błędzie, jeśli dane wejściowe nie są liczbami
                JOptionPane.showMessageDialog(menuView, "Nieprawidłowe dane wejściowe.", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

        private void showHighScores() {
            ScoreController scoreController = ScoreController.getInstance();
        }

        private void exitGame() {
            System.exit(0);
        }
}
