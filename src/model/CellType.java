package model;

public enum CellType {
    WALL, // Ściana
    PATH, // Ścieżka, po której porusza się Pacman i duchy
    DOT, // Kropka do zbierania przez Pacmana
    POWER_PELLET,
    PACMAN,
    GHOST,
    EMPTY// Power pellet dający Pacmanowi specjalne moce
}
