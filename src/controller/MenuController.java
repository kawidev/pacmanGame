package controller;


import view.MenuView;

import javax.swing.*;

//Zarządza interakcjami w menu głównym
public class MenuController {
    private MenuView menuView;

    public MenuController() {
        menuView = new MenuView();

        menuView.getNewGameButton().addActionListener(e -> startNewGame());
        menuView.getHighScoresButton().addActionListener(e -> showHighScores());
        menuView.getExitButton().addActionListener(e -> exitGame());

    }
    private void startNewGame() {
        // Prośba o rozmiar planszy
        String rows = JOptionPane.showInputDialog(menuView, "Wprowadź liczbę wierszy:", "Nowa Gra", JOptionPane.QUESTION_MESSAGE);
        String cols = JOptionPane.showInputDialog(menuView, "Wprowadź liczbę kolumn:", "Nowa Gra", JOptionPane.QUESTION_MESSAGE);

        // Konwersja na liczby i walidacja
        try {
            int numRows = Integer.parseInt(rows);
            int numCols = Integer.parseInt(cols);

            // Sprawdzenie, czy rozmiary są w akceptowalnym zakresie
            if (numRows >= 10 && numRows <= 100 && numCols >= 10 && numCols <= 100) {
                new GameController(numRows, numCols);
            } else {
                // Wyświetl wiadomość o błędzie, jeśli rozmiary są poza zakresem
                JOptionPane.showMessageDialog(menuView, "Rozmiar planszy musi być między 10 a 100.", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            // Wyświetl wiadomość o błędzie, jeśli dane wejściowe nie są liczbami
            JOptionPane.showMessageDialog(menuView, "Nieprawidłowe dane wejściowe.", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }

        private void showHighScores() {
            // Implementacja wyświetlania najlepszych wyników
        }

        private void exitGame() {
            System.exit(0);
        }
}
