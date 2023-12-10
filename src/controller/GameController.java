package controller;


import model.*;
import view.gameframe.EndPanel;
import view.gameframe.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

// Łączy model z widokiem, obsługuje logikę gry i reakcje na działania użytkownika.

/* Krok 2: Zarządzanie Stanem Gry
GameController powinien mieć metody do aktualizacji stanu gry, na przykład ruch Pacmana,
reakcje na zebranie kropek czy interakcje z duchami. Możesz również zaimplementować metody do obsługi pauzy w grze, końca gry itp.

Krok 3: Interakcja z Widokiem
Klasa powinna komunikować się z GamePanel lub innymi klasami widoku,
aby aktualizować interfejs użytkownika w odpowiedzi na zmiany w stanie gry.
Na przykład, po każdym ruchu Pacmana, plansza gry powinna być odświeżana. */
public class GameController {

    private static GameController instance;
    private GameModel gameModel;
    private GameFrame gameFrame;

    private GameController(int rows, int cols) {


        gameModel = GameModel.getInstance(rows, cols);
        gameFrame = GameFrame.getInstance("PACMAN GAME", gameModel);
        setupKeyBindings();
        startRefreshThread();
        startGame();

        SwingUtilities.invokeLater(() -> {
            gameFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    GameController.removeInstance();
                    GameFrame.removeInstance();
                    GameModel.removeInstance();
                }
            });
        });
    }

    public static GameController getInstance(int rows, int cols) {
        if(instance == null) {
            instance = new GameController(rows, cols);
        }
        return instance;
    }

    public static GameController getInstance() {
        return instance;
    }

    public static void removeInstance() {
        if(instance != null) instance = null;
    }

    public void startGame() {

        Thread gameThread = new Thread( () -> {
        while (!gameModel.isGameOver()) {
            try {
                Thread.sleep(16); // 60 fps
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            checkCollisions();
        }
        gameFrame.notifyGameOver();
      });
        gameThread.start();
    }

    public void checkCollisions() {
        Pacman pacman = gameModel.getPacman();
        List<Ghost> ghosts = gameModel.getGhosts();
        List<Dot> dots = gameModel.getDots();

        // Pobierz obecną pozycję Pacmana.
        Point pacmanPos = pacman.getPosition();

        // Sprawdź kolizje tylko w miejscu, gdzie znajduje się Pacman.
        List<GameEntity> entitiesHere = GameEntity.getEntitiesAt(pacmanPos.x, pacmanPos.y);

        boolean collidedWithGhost = false;
        Dot eatenDot = null;

        for (GameEntity entity : entitiesHere) {
            if (entity instanceof Ghost) {
                collidedWithGhost = true;
                System.out.println("kolizja z duchem");
                break; // Nie musimy szukać dalej, jeśli już wykryto kolizję z duchem.
            } else if (entity instanceof Dot && ((Dot) entity).isActive()) {
                eatenDot = (Dot) entity;
                System.out.println("kropka zjedzona");
                // Tutaj zakładam, że tylko jedna aktywna kropka może być w danej komórce.
            }
        }

        // Obsługa kolizji z duchem.
        if (collidedWithGhost) {
            pacman.takeLife();
            if (pacman.getLives() > 0) {
                // Resetujemy stany wszystkich encji.
                for (Ghost ghost : ghosts) {
                    ghost.reset();
                }
                for (Dot dot : dots) {
                    dot.reset();
                }
                pacman.reset();
            } else {
                // Gra się kończy.
                //gameModel.setGameOver(true);
            }
        }

        // Obsługa zjedzenia kropki.
        if (eatenDot != null) {
            pacman.addScore(eatenDot);
            eatenDot.setActiveState(false); // Deaktywujemy kropkę.
            System.out.println("kropka zjedzona");
        }
        SwingUtilities.invokeLater(() -> {
            gameFrame.getTableModel().fireTableDataChanged();
            gameFrame.repaint();
        });
    }


    public void startRefreshThread() {
        Thread refreshThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(16); // 60 fps
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
                // Pamiętaj o aktualizacji na EDT
                SwingUtilities.invokeLater(() -> {
                    gameFrame.getTableModel().fireTableDataChanged();
                    //model.onCollision();
                });
            }
        });
        refreshThread.start();
    }

    private void setupKeyBindings() {
        gameFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("Key pressed: " + e.getKeyCode()); // Debugowanie
                Direction newDirection = null;
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                            newDirection = Direction.UP;
                            break;
                        case KeyEvent.VK_DOWN:
                            newDirection = Direction.DOWN;
                            break;
                        case KeyEvent.VK_LEFT:
                            newDirection = Direction.LEFT;
                            break;
                        case KeyEvent.VK_RIGHT:
                            newDirection = Direction.RIGHT;
                            break;
                    }
                    if (newDirection != null) {
                        gameModel.getPacman().setDirection(newDirection);
                        gameFrame.getGameTable().repaint();
                    }
                }
            });


        JTextField nameField = gameFrame.getGameOverPanel().getNameField();
        //nameField.requestFocusInWindow();
        nameField.addActionListener(e -> {
            System.out.println("Naslkuch");
                    String playerName = gameFrame.getGameOverPanel().getPlayerName();
                    if (playerName.length() > 5) {
                        Player player = Player.getPlayerByName(playerName);
                        if (player == null) {
                            new Player(playerName, gameModel);
                        } else {
                            player.updateScore(gameModel.getPacman().getScore());
                        }
                        System.out.println("Gracz dodany");
                        Player.save();
                        // poinformuj widok o mozliwosci zmiany okna na ostatnie.
                        gameFrame.notifyGameEndPanel();
                    }
                });
        EndPanel endPanel = gameFrame.getEndPanel();
        endPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    gameModel.playAgain();
                    startGame();
                    gameFrame.notifyPlayAgain();
                } else if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_Q) {
                    // Instrukcje wykonywane po naciśnięciu CTRL+SHIFT+Q
                    gameFrame.dispose();
                    GameController.removeInstance();
                    GameModel.removeInstance();
                    GameFrame.removeInstance();
                }
            }
        });
    }
}
