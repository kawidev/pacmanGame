package main;

import controller.MenuController;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Uruchomienie interfejsu użytkownika w wątku dystrybucji zdarzeń (EDT)
        SwingUtilities.invokeLater(() -> {
            MenuController menuController = new MenuController();
            // W MenuController powinna być implementacja, która pokazuje menu
            // i obsługuje działania użytkownika, takie jak wybór "Nowa Gra"
        });
    }
}
