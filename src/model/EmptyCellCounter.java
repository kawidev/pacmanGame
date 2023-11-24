package model;

@FunctionalInterface
public interface EmptyCellCounter {
    int calculate(CellType[][] maze);
}
