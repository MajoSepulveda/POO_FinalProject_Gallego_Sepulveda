package src.ui;

import java.util.List;
import src.domain.*;

public class StoreMenu {

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
                    return;
                default:
                    console.writeLine("Opción inválida. Intente de nuevo.");
            }
            System.out.println();
        }
    }

    private static void showCatalog(Store store, ConsoleUI console) {
        if (store == null) {
            console.writeLine("Error: tienda no inicializada.");
            return;
        }

        List<VideoGame> games = store.getVideoGames();
        if (games == null || games.isEmpty()) {
            console.writeLine("Catálogo vacío.");
            return;
        }

        System.out.printf("%-6s  %-60s  %-12s  %-6s  %-7s  %-5s%n",
                "ID", "TÍTULO", "GÉNERO", "RATING", "PRECIO", "STOCK");
        console.writeLine("-------------------------------------------------------------------------------------------------------------");

        for (VideoGame g : games) {
            g.displayObject();
        }
        SelectMenu.SelectById(store, console);
    }
}