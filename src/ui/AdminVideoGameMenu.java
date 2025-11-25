package src.ui;

import src.domain.*;
import java.util.List;

/**
* Handles the administrative menu for managing VideoGame entities within the store system.
* Provides options to add, remove, modify, and list video games.
*/
public class AdminVideoGameMenu {

    /**
    * Displays the main Video Game Management menu loop and handles user navigation.
    * @param store The main Store object containing video game data.
    * @param console The ConsoleUI object used for user input and output.
    */
    public static void show(Store store, ConsoleUI console){

        while (true) {
            console.writeLine("----------- GESTIÓN DE VIDEOJUEGOS  -----------");
            console.writeLine("1) Agregar videojuego.");
            console.writeLine("2) Eliminar videojuego.");
            console.writeLine("3) Modificar videojuego.");
            console.writeLine("4) Lista de videojuegos.");
            console.writeLine("5) Volver.");

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
                    console.writeLine("Volviendo...");
                    console.sleep(1000);
                    return;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }   
        }
    }

    /**
    * Prompts the user for all video game details (ID, Title, Genre, Rating, Price, Stock)
    * and adds a new video game to the store.
    * Handles potential exceptions during data input or game creation.
    * @param store The Store object to which the video game will be added.
    * @param console The ConsoleUI object for input/output operations.
    */
    private static void addVideoGame(Store store, ConsoleUI console) {
        try {
            String id = console.readString("ID del videojuego: ");
            String title = console.readString("Titulo: ");
            String genre = console.readString("Genero: ");
            float rating = console.readFloat("Rating: ");
            float price = console.readFloat("Precio: ");
            int stock = console.readInt("Stock: ");
            VideoGame vg = new VideoGame(title, genre, rating, price, id, stock);
            store.addGame(vg);
            console.writeLine("Videojuego agregado: ID = " + vg.getId() + " Título = " + vg.getTitle());
        } catch (Exception e) {
            console.writeLine("No se pudo agregar el videojuego: " + e.getMessage());
        }
    }

    /**
    * Prompts the user for a video game ID and attempts to remove the game from the store.
    * Handles exceptions if the ID is invalid or the video game is not found.
    * @param store The Store object from which the video game will be removed.
    * @param console The ConsoleUI object for input/output operations.
    */
    private static void removeVideoGame(Store store, ConsoleUI console) {
        try {
            String id = console.readString("ID del videojuego a eliminar: ");
            store.removeVideoGame(id);
            console.writeLine("Videojuego eliminado: " + id);
        } catch (Exception e) {
            console.writeLine("No se pudo eliminar el videojuego: " + e.getMessage());
        }
    }

    /**
    * Allows the user to find and modify an existing video game's details in a sub-menu loop.
    * Modification options include changing title, genre, rating, price, and adding stock.
    * @param store The Store object containing the video game data.
    * @param console The ConsoleUI object for input/output operations.
    */
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

    /**
    * Retrieves the list of all video games from the store and displays their details to the console.
    * Uses the VideoGame object's displayObject method for formatting.
    * @param store The Store object to retrieve the video game list from.
    * @param console The ConsoleUI object for output operations.
    */
    private static void listVideoGames(Store store, ConsoleUI console) {
        List<VideoGame> games = store.getVideoGames();
        if (games == null || games.isEmpty()) {
            console.writeLine("No hay videojuegos registrados.");
            return;
        }
        console.writeLine("--- VIDEOJUEGOS ---");
        for (VideoGame g : games) {
            console.printDisplayableDetails(g);
        }
    }
}
