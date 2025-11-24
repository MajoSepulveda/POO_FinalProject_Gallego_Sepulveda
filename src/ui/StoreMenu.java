package src.ui;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import src.domain.*;

public class StoreMenu {

    public static void show(Store store, Scanner input) {
        if (input == null) input = new Scanner(System.in);
        while (true) {
            System.out.println("----------- TIENDA -----------");
            System.out.println("1) Mostrar catálogo completo.");
            System.out.println("2) Buscar juego.");
            System.out.println("3) Administrar la tienda.");
            System.out.println("4) Volver al menú principal.");
            System.out.print("Seleccione una opción: ");

            String option = input.nextLine().trim();
            switch (option) {
                case "1":
                    showCatalog(store, input);
                    break;
                case "2":
                    //aca va el menú de busqueda.
                    break;
                case "3":
                    AdminMenu.Show(store, input);
                    break;
                case "4":
                    System.out.println("Volviendo al menú principal...");
                    return;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
            System.out.println();
        }
    }

    private static void showCatalog(Store store, Scanner input) {
        if (store == null) {
            System.out.println("Error: tienda no inicializada.");
            return;
        }

        List<VideoGame> games = store.getVideoGames();
        if (games == null || games.isEmpty()) {
            System.out.println("Catálogo vacío.");
            return;
        }

        System.out.printf("%-6s  %-60s  %-12s  %-6s  %-7s  %-5s%n",
                "ID", "TÍTULO", "GÉNERO", "RATING", "PRECIO", "STOCK");
        System.out.println("-------------------------------------------------------------------------------------------------------------");

        for (VideoGame g : games) {
            String id = Objects.toString(g.getId(), "");
            String title = Objects.toString(g.getTitle(), "");
            String genre = Objects.toString(g.getGenre(), "");
            String rating = String.format("%.1f", g.getRating());
            String price = String.format("%.2f", g.getPrice());
            String stock = String.valueOf(g.getStock());
            System.out.printf("%-6s  %-60s  %-12s  %-6s  %-7s  %-5s%n",
                    id, title, genre, rating, price, stock);
        }
        SelectMenu.SelectById(store, input);
    }
}