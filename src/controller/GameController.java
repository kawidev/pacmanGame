package controller;


import model.Direction;
import model.GameModel;
import view.GamePanel;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// Łączy model z widokiem, obsługuje logikę gry i reakcje na działania użytkownika.

/* Krok 2: Zarządzanie Stanem Gry
GameController powinien mieć metody do aktualizacji stanu gry, na przykład ruch Pacmana,
reakcje na zebranie kropek czy interakcje z duchami. Możesz również zaimplementować metody do obsługi pauzy w grze, końca gry itp.

Krok 3: Interakcja z Widokiem
Klasa powinna komunikować się z GamePanel lub innymi klasami widoku,
aby aktualizować interfejs użytkownika w odpowiedzi na zmiany w stanie gry.
Na przykład, po każdym ruchu Pacmana, plansza gry powinna być odświeżana. */
public class GameController {
    private GamePanel panel;
    private GameModel model;
    private JFrame gameFrame;

    public GameController(int rows, int cols) {
        model = new GameModel(rows, cols);
        panel = new GamePanel(model);
        gameFrame = new JFrame("Pacman Game");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.add(panel);
        gameFrame.pack(); // Dopasowanie rozmiaru ramki do zawartości
        gameFrame.setVisible(true);
        gameFrame.setLocationRelativeTo(null);
        setupKeyBindings();
    }

    private void setupKeyBindings() {
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("Key pressed: " + e.getKeyCode()); // Debugowanie
                Direction newDirection = null;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP: newDirection = Direction.UP; break;
                    case KeyEvent.VK_DOWN: newDirection = Direction.DOWN; break;
                    case KeyEvent.VK_LEFT: newDirection = Direction.LEFT; break;
                    case KeyEvent.VK_RIGHT: newDirection = Direction.RIGHT; break;
                }
                if (newDirection != null) {
                    model.getPacman().setDirection(newDirection);
                    //model.getPacman().move();
                    panel.getGameTable().repaint(); // Zakładając, że GamePanel ma metodę getGameTable()
                }
            }
        });
        panel.setFocusable(true);
        panel.requestFocusInWindow();
    }
}