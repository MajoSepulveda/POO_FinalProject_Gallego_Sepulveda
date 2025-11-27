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
            console.cls();
            console.writeLine("----------- GESTIÓN DE VIDEOJUEGOS -----------");
            console.writeLine("1) Agregar videojuego.");
            console.writeLine("2) Eliminar videojuego.");
            console.writeLine("3) Modificar videojuego.");
            console.writeLine("4) Lista de videojuegos.");
            console.writeLine("5) Volver.");

            int option = console.readInt("\nSeleccione una opción: ");
            switch (option) {
                case 1:
                    console.sleep(200);
                    addVideoGame(store, console);
                    break;
                case 2:
                    console.sleep(200);
                    removeVideoGame(store, console);
                    break;
                case 3:
                    console.sleep(200);
                    modifyVideoGame(store, console);
                    break;
                case 4:
                    console.sleep(200);
                    listVideoGames(store, console);
                    break;
                case 5:
                    console.writeLine("\nVolviendo...");
                    console.sleep(1000);
                    return;
                default:
                    console.writeLine("\nOpción inválida. Intente de nuevo.");
                    console.sleep(1000);
            }   
        }
    }

    /**
    * Prompts the user for all video game details (ID, Title, Genre, Rating, Price, Stock) and adds a new video game to the store.
    * Handles potential exceptions during data input or game creation.
    * @param store The Store object to which the video game will be added.
    * @param console The ConsoleUI object for input/output operations.
    */
    private static void addVideoGame(Store store, ConsoleUI console) {
        try {
            console.cls();
            String id = console.readVideoGameID("ID del videojuego: ");
            String title = console.readValidString("Título: ");
            String genre = console.readGenre("Género: ");
            float rating = console.readRating("Calificación: ");
            float price = console.readPositiveFloat("Precio: ");
            int stock = console.readStock("Stock: ");
            VideoGame vg = new VideoGame(title, genre, rating, price, id, stock);
            store.addGame(vg);
            console.writeLine("Videojuego agregado: ID = " + vg.getId() + " Título = " + vg.getTitle());
        } catch (Exception e) {
            console.writeError("No se pudo agregar el videojuego: " + e.getMessage());
        }
        console.sleep(2000);
    }

    /**
    * Prompts the user for a video game ID and attempts to remove the game from the store.
    * Handles exceptions if the ID is invalid or the video game is not found.
    * @param store The Store object from which the video game will be removed.
    * @param console The ConsoleUI object for input/output operations.
    */
    private static void removeVideoGame(Store store, ConsoleUI console) {
        try {
            console.cls();
            String id = console.readVideoGameID("ID del videojuego a eliminar: ");
            VideoGame game = store.findVideoGameById(id);

            if (game == null) {
                console.writeLine("\nVideojuego no encontrado con ID: " + id);
                console.sleep(1500);
                return;
            }
        
            String confirmationMessage = String.format("¿Está seguro de que desea eliminar el videojuego '%s' (ID: %s)? Escriba 'SI' para confirmar: ", game.getTitle(), game.getId());
            String confirmation = console.readString(confirmationMessage);

            if (confirmation.trim().equalsIgnoreCase("SI")) {
                store.removeVideoGame(id);
                console.writeSucces("Videojuego eliminado: " + game.getTitle() + " (ID: " + id + ")");
            } else {
                console.writeLine("Operación de eliminación cancelada.");
            }
        } catch (Exception e) {
            console.writeError("No se pudo eliminar el videojuego: " + e.getMessage());
        }
        console.sleep(2000);
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
            console.cls();
            String id = console.readVideoGameID("Ingrese ID del videojuego a modificar: ");
            VideoGame g = store.findVideoGameById(id);
            if (g == null) {
                console.writeLine("\nVideojuego no encontrado. Verifique el ID.");
                console.sleep(1500);
                break;
            }

            boolean done = false;
            while (!done) {
                console.cls();
                console.writeLine("\nVideojuego actual: " + g.getTitle());
                console.writeLine("1) Cambiar título.");
                console.writeLine("2) Cambiar género.");
                console.writeLine("3) Cambiar rating.");
                console.writeLine("4) Cambiar precio.");
                console.writeLine("5) Agregar stock.");
                console.writeLine("6) Volver.");

                int option = console.readInt("\nSeleccione una opción: ");
                console.cls();

                switch (option) {
                    case 1:
                        String newTitle = console.readValidString("Nuevo titulo: ");
                        try {
                            g.setTitle(newTitle);
                            console.writeSucces("Título actualizado.");
                        } catch (Exception e) {
                            console.writeError(e.getMessage());
                        }
                        break;
                    case 2:
                        String newGenre = console.readGenre("Nuevo género (Accion, Aventura, Rol, Estrategia, Simulacion, Carreras, Deportes, Casual): ");
                        try {
                            g.setGenre(newGenre);
                            console.writeSucces("Género actualizado.");
                        } catch (Exception e) {
                            console.writeError(e.getMessage());
                        }
                        break;
                    case 3:
                        float newRating = console.readRating("Nuenvo rating (0.0 - 10.0): ");
                        try {
                            g.setRating(newRating);
                            console.writeSucces("Rating actualizado.");
                        } catch (NumberFormatException nfe) {
                            console.writeError("Número inválido.");
                        } catch (Exception e) {
                            console.writeError(e.getMessage());
                        }
                        break;
                    case 4:
                        float newPrice = console.readPositiveFloat("Nuevo precio: ");
                        try {
                            g.setPrice(newPrice);
                            console.writeSucces("Precio actualizado.");
                        } catch (NumberFormatException nfe) {
                            console.writeError("Número inválido.");
                        } catch (Exception e) {
                            console.writeError(e.getMessage());
                        }
                        break;
                    case 5:
                        int addStock = console.readStock("Cantidad a agregar al stock: ");
                        try {
                            g.addStock(addStock);
                            console.writeSucces("Stock actualizado.");
                            console.writeLine("Nuevo stock: " + g.getStock());
                        } catch (NumberFormatException nfe) {
                            console.writeError("Número inválido.");
                        } catch (Exception e) {
                            console.writeError(e.getMessage());
                        }
                        break;
                    case 6:
                        done = true;
                        break;
                    default:
                        console.writeLine("\nOpción inválida. Intente de nuevo");
                        console.sleep(1000);
                }
            }
            console.sleep(2000);
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
        console.cls();
        if (games == null || games.isEmpty()) {
            console.writeLine("No hay videojuegos registrados.");
            return;
        }
        console.writeLine("--- VIDEOJUEGOS ---");
        for (VideoGame g : games) {
            console.printDisplayableDetails(g);
        }
        
        console.readString("Presione cualquier tecla para continuar.");
        console.sleep(200);
    }
}
