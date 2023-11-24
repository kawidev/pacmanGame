package model;


/* Rola: Reprezentuje planszę gry, zawierającą komórki (Cell).
Implementacja: Zarządza siatką komórek, określa położenie ścian,
ścieżek oraz może przechowywać informacje o miejscach pojawiania się kropek i power pelletów.*/
public class Board {
    private CellType[][] maze;
    private int rows;
    private int cols;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        maze = new CellType[rows][cols];
        generateMaze();
    }

    public void generateMaze() {
        MazeGenerator mazeGenerator = new MazeGenerator(rows, cols);
        maze = mazeGenerator.getMaze();
    }

    public boolean canMove(int x, int y, Direction direction) {
        // Logika sprawdzania możliwości ruchu
        // Przykład dla ruchu w prawo
        switch (direction) {
            case RIGHT:
                return x + 1 < cols && maze[y][x + 1] != CellType.WALL;
            // Dodaj logikę dla pozostałych kierunków
        }
        return false;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public CellType getCellType(int x, int y) {
        return maze[x][y];
    }

    public void setCellType(int x, int y, CellType type) {
        maze[x][y] = type;
    }
}