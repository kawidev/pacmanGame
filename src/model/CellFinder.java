package model;

import java.awt.*;

public interface CellFinder {
    Point findFirstEmptyCell(CellType[][] maze);
}
