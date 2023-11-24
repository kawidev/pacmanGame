package model;

public class Cell {
    private int x, y;
    private CellType type; // Enum z typami komórek, np. WALL, PATH

    public Cell(int x, int y, CellType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    // Gettery, settery i inne metody
}
