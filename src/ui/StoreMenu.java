package src.ui;

import java.util.List;
import src.domain.*;

/**
 * Represents the main menu of the store, accessible after starting the application.
 * Provides options for browsing the catalog, searching for games, accessing administration, or returning to the start menu.
 */
public class StoreMenu {
    /**
     * Displays the main Store menu loop and handles user navigation.
     * @param store The main Store object instance.
     * @param console The ConsoleUI object used for user input and output.
     */
    public static void show(Store store, ConsoleUI console) {
        while (true) {
            console.writeLine("----------- TIENDA -----------");
            console.writeLine("1) Mostrar catálogo completo.");
            console.writeLine("2) Buscar juego.");
            console.writeLine("3) Administrar la tienda.");
            console.writeLine("4) Volver al menú principal.");

            int option = console.readInt("Seleccione una opción: ");
            switch (option) {
                case 1:
                    showCatalog(store, console);
                    break;
                case 2:
                    SearchMenu.show(store, console);
                    break;
                case 3:
                    AdminMenu.Show(store, console);
                    break;
                case 4:
                    console.writeLine("Volviendo al menú principal...");
                    console.sleep(1000);
                    return;
                default:
                    console.writeLine("Opción inválida. Intente de nuevo.");
            }
        }
    }

    /**
     * Retrieves the complete list of video games from the store and displays them in a formatted table.
     * After displaying the catalog, it allows the user to select a game by ID for purchase or further action.
     * @param store The Store object to retrieve the catalog from.
     * @param console The ConsoleUI object for output operations.
     */
    private static void showCatalog(Store store, ConsoleUI console) {
        if (store == null) {
            console.writeError("Tienda no inicializada.");
            return;
        }

        List<VideoGame> games = store.getVideoGames();
        if (games == null || games.isEmpty()) {
            console.writeLine("Catálogo vacío.");
            return;
        }

        // Display header
        console.writeStringf("%-6s  %-60s  %-12s  %-6s  %-7s  %-5s%n",
                "ID", "TÍTULO", "GÉNERO", "RATING", "PRECIO", "STOCK");
        console.writeLine("-------------------------------------------------------------------------------------------------------------");

        // Display each game using its displayObject method
        for (VideoGame g : games) {
            console.printDisplayableDetails(g);
        }
        
        // Allows the user to select one of the displayed games
        SelectMenu.SelectById(store, console);
    }
}