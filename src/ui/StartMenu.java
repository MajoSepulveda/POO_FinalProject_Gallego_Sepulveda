package src.ui;

import src.domain.Store;

/**
 * Represents the initial menu displayed when the application starts.
 * It serves as the main entry point for the user interface, offering options to enter the store or exit the application.
 */
public class StartMenu {

    /**
     * Displays the welcome screen and main menu loop.
     * Handles user selection for entering the store interface or exiting the program.
     * @param store The main Store object instance.
     * @param console The ConsoleUI object used for user input and output.
     */
    public static void show(Store store, ConsoleUI console) {
        while (true) {
            console.cls();
            console.writeLine("========================================");
            console.writeLine("   BIENVENIDO/A A LA TIENDA DE JUEGOS");
            console.writeLine("========================================");
            console.writeLine("1) Entrar a la tienda.");
            console.writeLine("2) Salir.");

            int option = console.readInt("\nSeleccione una opción: ");
            switch (option) {
                case 1:
                    // Enters the main store browsing and interaction menu
                    console.sleep(200);
                    StoreMenu.show(store, console);
                    break;
                case 2:
                    console.writeLine("\nGracias por visitar la tienda. Saliendo...");
                    console.sleep(1500);
                    return; // Terminates the application loop
                default:
                    console.writeLine("\nOpción inválida. Intenta de nuevo.");
                    console.sleep(1000);
            }
        }
    }
}
