package model;

import java.util.Collections;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Random;

public class MazeGenerator {
    private final int rows;
    private final int cols;
    private final CellType[][] maze;

    public MazeGenerator(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.maze = new CellType[rows][cols];
        generateMaze();
    }

    public void generateMaze() {
        for (CellType[] row : maze) {
            Arrays.fill(row, CellType.WALL);
        }

        Random rand = new Random();
        // Start from a random cell
        int r = rand.nextInt(rows - 2) + 1;
        int c = rand.nextInt(cols - 2) + 1;
        maze[r][c] = CellType.EMPTY;

        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{r, c});

        while (!stack.isEmpty()) {
            int[] cell = stack.pop();
            List<int[]> neighbours = getNeighbours(cell[0], cell[1]);

            if (!neighbours.isEmpty()) {
                stack.push(cell);
                int[] chosen = neighbours.get(rand.nextInt(neighbours.size()));
                // Remove the wall between the current cell and the chosen cell
                int inBetweenR = (cell[0] + chosen[0]) / 2;
                int inBetweenC = (cell[1] + chosen[1]) / 2;
                maze[inBetweenR][inBetweenC] = CellType.EMPTY;
                maze[chosen[0]][chosen[1]] = CellType.EMPTY;
                stack.push(chosen);
            }
        }

        // Dodawanie połączeń dla ślepych zaułków
        addConnectionsToDeadEnds();
    }

    private void addConnectionsToDeadEnds() {
        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < cols - 1; j++) {
                // Sprawdzamy, czy to ślepy zaułek
                if (maze[i][j] == CellType.EMPTY && isDeadEnd(i, j)) {
                    // Sprawdzamy, czy można przebić ścianę
                    punchThroughIfPossible(i, j);
                }
            }
        }
    }

    private List<int[]> getNeighbours(int r, int c) {
        List<int[]> neighbours = new ArrayList<>();
        for (int[] d : new int[][]{{-2, 0}, {2, 0}, {0, -2}, {0, 2}}) {
            int nr = r + d[0];
            int nc = c + d[1];
            if (nr >= 1 && nr < rows - 1 && nc >= 1 && nc < cols - 1 && maze[nr][nc] == CellType.WALL) {
                neighbours.add(new int[]{nr, nc});
            }
        }
        Collections.shuffle(neighbours);
        return neighbours;
    }

    private boolean isDeadEnd(int row, int col) {
        int openPaths = 0;
        for (int[] d : new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}}) {
            int nr = row + d[0];
            int nc = col + d[1];
            if (maze[nr][nc] == CellType.EMPTY) {
                openPaths++;
            }
        }
        return openPaths == 1;
    }

    private void punchThroughIfPossible(int row, int col) {
        for (int[] d : new int[][]{{-2, 0}, {2, 0}, {0, -2}, {0, 2}}) {
            int nr = row + d[0];
            int nc = col + d[1];
            if (nr >= 1 && nr < rows - 1 && nc >= 1 && nc < cols - 1 && maze[nr][nc] == CellType.EMPTY) {
                int wallRow = (row + nr) / 2;
                int wallCol = (col + nc) / 2;
                if (maze[wallRow][wallCol] == CellType.WALL && !createsLoop(wallRow, wallCol, row, col)) {
                    maze[wallRow][wallCol] = CellType.EMPTY;
                }
            }
        }
    }

    private boolean createsLoop(int wallRow, int wallCol, int cellRow, int cellCol) {
        int emptyCount = 0;
        for (int[] d : new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}}) {
            int nr = wallRow + d[0];
            int nc = wallCol + d[1];
            if (nr == cellRow && nc == cellCol) continue; // ignore the cell itself
            if (maze[nr][nc] == CellType.EMPTY) {
                emptyCount++;
            }
        }
        return emptyCount > 1; // if more than 1 empty neighbour, it creates a loop
    }

    public CellType[][] getMaze() {
        return maze;
    }
}
