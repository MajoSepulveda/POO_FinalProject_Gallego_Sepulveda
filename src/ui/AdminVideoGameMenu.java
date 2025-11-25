package src.ui;

import src.domain.*;
import java.util.List;

public class AdminVideoGameMenu {

    public static void show(Store store, ConsoleUI console){

        while (true) {
            System.out.println("----------- GESTIÓN DE VIDEOJUEGOS  -----------");
            System.out.println("1) Agregar videojuego.");
            System.out.println("2) Eliminar videojuego.");
            System.out.println("3) Modificar videojuego.");
            System.out.println("4) Lista de videojuegos.");
            System.out.println("5) Volver al menú principal.");
            System.out.print("Seleccione una opción: ");

            int option = console.readInt("Seleccione una opción: ");
            switch (option) {
                case 1:
                    addVideoGame(store, console);
                    break;
                case 2:
                    removeVideoGame(store, console);
                    break;
                case 3:
                    modifyVideoGame(store, console);
                    break;
                case 4:
                    listVideoGames(store, console);
                    break;
                case 5:
                    console.writeLine("Volviendo al menú principal...");
                    return;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }   
        }
    }

    private static void addVideoGame(Store store, ConsoleUI console) {
        try {
            String id = console.readString("ID del videojuego: ");
            String title = console.readString("Titulo: ");
            String genre = console.readString("Genero: ");
            float rating = console.readFloat("Rating: ");
            System.out.print("Precio: ");
            float price = console.readFloat("Precio: ");
            System.out.print("Stock: ");
            int stock = console.readInt("Stock: ");
            VideoGame vg = new VideoGame(title, genre, rating, price, id, stock);
            store.addGame(vg);
            console.writeLine("Videojuego agregado: ID = " + vg.getId() + " Título = " + vg.getTitle());
        } catch (Exception e) {
            console.writeLine("No se pudo agregar el videojuego: " + e.getMessage());
        }
    }

    private static void removeVideoGame(Store store, ConsoleUI console) {
        try {
            String id = console.readString("ID del videojuego a eliminar: ");
            store.removeVideoGame(id);
            console.writeLine("Videojuego eliminado: " + id);
        } catch (Exception e) {
            console.writeLine("No se pudo eliminar el videojuego: " + e.getMessage());
        }
    }

    private static void modifyVideoGame(Store store, ConsoleUI console) {
        if (store == null) return;
        while (true) {
            String id = console.readString("Ingrese ID del videojuego a modificar (ENTER para cancelar): ");
            VideoGame g = store.findVideoGameById(id);
            if (g == null) {
                console.writeLine("Videojuego no encontrado. Intente nuevamente.");
                continue;
            }

            boolean done = false;
            while (!done) {
                console.writeLine("\nVideojuego actual: " + g);
                console.writeLine("1) Cambiar título");
                console.writeLine("2) Cambiar género");
                console.writeLine("3) Cambiar rating");
                console.writeLine("4) Cambiar precio");
                console.writeLine("5) Agregar stock");
                console.writeLine("6) Volver");
                int option = console.readInt("Selecione una opción");
                switch (option) {
                    case 1:
                        String newTitle = console.readString("Nuevo titulo: ");
                        try {
                            g.setTitle(newTitle);
                            console.writeLine("Título actualizado.");
                        } catch (Exception e) {
                            console.writeLine("Error: " + e.getMessage());
                        }
                        break;
                    case 2:
                        String newGenre = console.readString("Nuevo género (Acción, Aventura, RPG, Estrategia, Simulación, Carreras, Deportes, Casual): ");
                        try {
                            g.setGenre(newGenre);
                            console.writeLine("Género actualizado.");
                        } catch (Exception e) {
                            console.writeLine("Error: " + e.getMessage());
                        }
                        break;
                    case 3:
                        float newRating = console.readFloat("Nuenvo rating (0.0 - 10.0): ");
                        try {
                            g.setRating(newRating);
                            console.writeLine("Rating actualizado.");
                        } catch (NumberFormatException nfe) {
                            console.writeLine("Número inválido.");
                        } catch (Exception e) {
                            console.writeLine("Error: " + e.getMessage());
                        }
                        break;
                    case 4:
                        float newPrice = console.readFloat("Nuevo precio: ");
                        try {
                            g.setPrice(newPrice);
                            console.writeLine("Precio actualizado.");
                        } catch (NumberFormatException nfe) {
                            console.writeLine("Número inválido.");
                        } catch (Exception e) {
                            console.writeLine("Error: " + e.getMessage());
                        }
                        break;
                    case 5:
                        int addStock = console.readInt("Cantidad a agregar al stock: ");
                        try {
                            g.addStock(addStock);
                            console.writeLine("Stock actualizado. Nuevo stock: " + g.getStock());
                        } catch (NumberFormatException nfe) {
                            console.writeLine("Número inválido.");
                        } catch (Exception e) {
                            console.writeLine("Error: " + e.getMessage());
                        }
                        break;
                    case 6:
                        done = true;
                        break;
                    default:
                        console.writeLine("Opción inválida.");
                }
            }
            return;
        }
    }

    private static void listVideoGames(Store store, ConsoleUI console) {
        List<VideoGame> games = store.getVideoGames();
        if (games == null || games.isEmpty()) {
            console.writeLine("No hay videojuegos registrados.");
            return;
        }
        console.writeLine("--- VIDEOJUEGOS ---");
        for (VideoGame g : games) {
            g.displayObject();
        }
    }
}
