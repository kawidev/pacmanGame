package model;

/* GameModel będzie centralnym miejscem przechowywania i zarządzania stanem gry. W kontekście gry Pacman, może to obejmować:

Pozycje i stany postaci (Pacman, duchy).
Stan planszy (pozycja ścian, kropek, power pelletów).
Wynik gry, liczba żyć, aktualny poziom.
Inne zmienne gry, jak ulepszenia.*/

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameModel {
    private Board board; // Plansza gry
    private Pacman pacman; // Postać gracza
    private ArrayList<Ghost> ghosts;
    private int score; // Wynik gry
    private int lives; // Liczba żyć

    private final int GHOST_FOR_CELLS = 60;

    private GameLevel gameLevel;

    private MazeGenerator mazeGenerator;
    private CellType[][] maze;
    private int rows;
    private int cols;

    public GameModel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.board = new Board(rows, cols); // Inicjalizacja planszy

        ghosts = new ArrayList<>(); // Inicjalizacja duchów
        // Ustawianie pozostałych elementów gry
        mazeGenerator = new MazeGenerator(rows, cols);
        generateMaze();

        CellFinder firstCellFinder = (CellType[][] maze) -> {
            for (int r = 0; r < maze.length; r++) {
                for (int c = 0; c < maze[0].length; c++) {
                    if (maze[r][c] == CellType.EMPTY) {
                        return new Point(c, r); // Return the first empty cell encountered
                    }
                }
            }
            return null; // Return null if no empty cell is found
        };

        EmptyCellCounter emptyCells = (CellType[][] maze) -> {
            int count = 0;
            for (CellType[] row : maze) {
                for (CellType cell : row) {
                    if (cell == CellType.EMPTY) {
                        count++;
                    }
                }
            }
            return count;
        };

        int emptyCellsNum = emptyCells.calculate(maze);

        createGhostsInSectors(emptyCellsNum / GHOST_FOR_CELLS, new int[]{5, 6, 7, 8, 9});

        Point startPoint = firstCellFinder.findFirstEmptyCell(maze);
        this.pacman = new Pacman(this,startPoint); // Inicjalizacja Pacmana na srodku
        pacman.startMoving();
    }


    public void generateMaze() {
        mazeGenerator.generateMaze(); // Generuj labirynt
        maze = mazeGenerator.getMaze(); // Pobierz wygenerowany labirynt
    }

    private void createGhostsInSectors(int totalGhosts, int[] sectors) {
        int ghostsPerSector = totalGhosts / sectors.length;

        for (int sector : sectors) {
            for (int i = 0; i < ghostsPerSector; i++) {
                Ghost ghost = createGhostInSector(sector);
                if (ghost != null) {
                    ghosts.add(ghost);
                }
            }
        }
    }

    private Ghost createGhostInSector(int sector) {
        int sectorRowStart = ((sector - 1) / 3) * (rows / 3);
        int sectorColStart = ((sector - 1) % 3) * (cols / 3);
        int sectorRowEnd = sectorRowStart + (rows / 3);
        int sectorColEnd = sectorColStart + (cols / 3);

        Random rand = new Random();
        int row, col, attempts = 0;
        do {
            row = rand.nextInt(sectorRowEnd - sectorRowStart) + sectorRowStart;
            col = rand.nextInt(sectorColEnd - sectorColStart) + sectorColStart;
            attempts++;
            if (attempts > 50) return null; // Unikamy nieskończonej pętli
        } while (maze[row][col] != CellType.EMPTY);

        return new Ghost(row, col);
    }

    public CellType getCellType(int row, int col) {
        return maze[row][col];
    }
    public boolean canMove(Direction direction) {
        // Sprawdź, czy ruch w danym kierunku jest możliwy
        // Ta metoda powinna sprawdzić, czy nowa pozycja Pacmana nie jest ścianą i nie wychodzi poza planszę
        return true;
    }

    public void movePacman(Direction direction) {
        if (canMove(direction)) {
            pacman.move();
            pacman.setDirection(direction);
        }
    }

    public Board getBoard() {
        return board;
    }
    public Pacman getPacman() {
        return pacman;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public CellType[][] getMaze() {
        return maze;
    }
}
