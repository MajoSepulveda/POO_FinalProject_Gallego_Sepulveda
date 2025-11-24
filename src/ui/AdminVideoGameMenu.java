package src.ui;

import src.domain.*;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class AdminVideoGameMenu {

    public static void show(Store store, Scanner input){

        if(input == null) input = new Scanner(System.in);
        while (true) {
            System.out.println("----------- GESTIÓN DE VIDEOJUEGOS  -----------");
            System.out.println("1) Agregar videojuego.");
            System.out.println("2) Eliminar videojuego.");
            System.out.println("3) Modificar videojuego.");
            System.out.println("4) Lista de videojuegos.");
            System.out.println("5) Volver al menú principal.");
            System.out.print("Seleccione una opción: ");

            String option = input.nextLine().trim();
            switch (option) {
                case "1":
                    addVideoGame(store, input);
                    break;
                case "2":
                    removeVideoGame(store, input);
                    break;
                case "3":
                    modifyVideoGame(store, input);
                    break;
                case "4":
                    listVideoGames(store);
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }   
        }
    }

    private static void addVideoGame(Store store, Scanner input) {
        try {
            System.out.print("ID del videojuego: ");
            String id = input.nextLine().trim();
            System.out.print("Título: ");
            String title = input.nextLine().trim();
            System.out.print("Género: ");
            String genre = input.nextLine().trim();
            System.out.print("Rating: ");
            float rating = Float.parseFloat(input.nextLine().trim());
            System.out.print("Precio: ");
            float price = Float.parseFloat(input.nextLine().trim());
            System.out.print("Stock: ");
            int stock = Integer.parseInt(input.nextLine().trim());
            VideoGame vg = new VideoGame(title, genre, rating, price, id, stock);
            store.addGame(vg);
            System.out.println("Videojuego agregado: ID = " + vg.getId() + " Título = " + vg.getTitle());
        } catch (Exception e) {
            System.out.println("No se pudo agregar el videojuego: " + e.getMessage());
        }
    }

    private static void removeVideoGame(Store store, Scanner input) {
        try {
            System.out.print("ID del videojuego a eliminar (ej. A001): ");
            String id = input.nextLine().trim();
            store.removeVideoGame(id);
            System.out.println("Videojuego eliminado: " + id);
        } catch (Exception e) {
            System.out.println("No se pudo eliminar el videojuego: " + e.getMessage());
        }
    }

    private static void modifyVideoGame(Store store, Scanner input) {
        if (store == null) return;
        while (true) {
            System.out.print("Ingrese ID del videojuego a modificar (ENTER para cancelar): ");
            String id = input.nextLine().trim();
            if (id.isEmpty()) return;
            VideoGame g = store.findVideoGameById(id);
            if (g == null) {
                System.out.println("Videojuego no encontrado. Intente nuevamente.");
                continue;
            }

            boolean done = false;
            while (!done) {
                System.out.println("\nVideojuego actual: " + g);
                System.out.println("1) Cambiar título");
                System.out.println("2) Cambiar género");
                System.out.println("3) Cambiar rating");
                System.out.println("4) Cambiar precio");
                System.out.println("5) Agregar stock");
                System.out.println("6) Volver");
                System.out.print("Seleccione: ");
                String option = input.nextLine().trim();
                switch (option) {
                    case "1":
                        System.out.print("Nuevo título: ");
                        String newTitle = input.nextLine().trim();
                        try {
                            g.setTitle(newTitle);
                            System.out.println("Título actualizado.");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    case "2":
                        System.out.print("Nuevo género (Action, Adventure, RPG, Strategy, Simulation, Racing, Sports, Casual): ");
                        String newGenre = input.nextLine().trim();
                        try {
                            g.setGenre(newGenre);
                            System.out.println("Género actualizado.");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    case "3":
                        System.out.print("Nuevo rating (0.0 - 10.0): ");
                        String newRating = input.nextLine().trim();
                        try {
                            float r = Float.parseFloat(newRating);
                            g.setRating(r);
                            System.out.println("Rating actualizado.");
                        } catch (NumberFormatException nfe) {
                            System.out.println("Número inválido.");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    case "4":
                        System.out.print("Nuevo precio: ");
                        String newPrice = input.nextLine().trim();
                        try {
                            float p = Float.parseFloat(newPrice);
                            g.setPrice(p);
                            System.out.println("Precio actualizado.");
                        } catch (NumberFormatException nfe) {
                            System.out.println("Número inválido.");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    case "5":
                        System.out.print("Cantidad a agregar al stock: ");
                        String addStock = input.nextLine().trim();
                        try {
                            int a = Integer.parseInt(addStock);
                            g.addStock(a);
                            System.out.println("Stock actualizado. Nuevo stock: " + g.getStock());
                        } catch (NumberFormatException nfe) {
                            System.out.println("Número inválido.");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    case "6":
                        done = true;
                        break;
                    default:
                        System.out.println("Opción inválida.");
                }
            }
            return;
        }
    }

    private static void listVideoGames(Store store) {
        List<VideoGame> games = store.getVideoGames();
        if (games == null || games.isEmpty()) {
            System.out.println("No hay videojuegos registrados.");
            return;
        }
        System.out.println("--- VIDEOJUEGOS ---");
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
    }
}
