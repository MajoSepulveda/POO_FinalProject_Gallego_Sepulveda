package src.ui;
import java.util.List;
import src.domain.*;

/**
 * Manages the interactive search and filtering interface for VideoGames.
 * This class allows the user to iteratively set optional filters (Title, Genre, Max Price, Min Rating).
 */
public class SearchMenu {

    /**
     * Guides the user through the video game filtering process.
     * The menu allows the user to set optional search parameters and displays the results once the user selects the "Apply Filter" option.
     * @param store The central data store containing the list of VideoGames to filter.
     * @param console The ConsoleUI instance used for all safe input/output operations.
     */
    public static void show(Store store, ConsoleUI console){
        String currentTitle = null;
        String currentGenre = null;
        Float currentMaxPrice = null; 
        Float currentMinRating = null; 
        int option = 0;

        while (option != 5 && option != 7) {
            console.cls();
            console.writeLine("\n---- CONFIGURACIÓN DE FILTROS ----\n");
            console.writeLine("1) Título: " + (currentTitle != null ? currentTitle : "[No Aplicado]"));
            console.writeLine("2) Género: " + (currentGenre != null ? currentGenre : "[No Aplicado]"));
            console.writeLine("3) Precio Máximo: " + (currentMaxPrice != null ? "$" + currentMaxPrice : "[No Aplicado]"));
            console.writeLine("4) Rating Mínimo: " + (currentMinRating != null ? currentMinRating : "[No Aplicado]"));
            console.writeLine("5) Aplicar Filtros y Mostrar Resultados");
            console.writeLine("6) Restablecer Filtros");
            console.writeLine("7) Volver");
            option = console.readInt("\nSelecione una opción: ");
            console.writeLine("------------------------------\n");

            switch (option) {
                case 1:
                    currentTitle = console.readString("Ingrese el título que desea: ");
                    break;
                case 2: 
                    currentGenre = console.readString("Ingrese el género que desea: ");
                    break;
                case 3:
                    currentMaxPrice = console.readFloatObject("Ingrese el precio máximo que desea: ");
                    break;
                case 4: 
                    currentMinRating = console.readFloatObject("Ingrese el rating mínimo que desea: ");
                    break;
                case 6: 
                    currentTitle = null;
                    currentGenre = null;
                    currentMaxPrice = null; 
                    currentMinRating = null; 

                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        } 
        
        if (option == 7){
            console.writeLine("Volviendo al menú principal sin aplicar filtros...");
            console.sleep(1000);
            return;
        }

        console.sleep(1000);
        console.cls();

        List<VideoGame> results = store.filterVideoGames(currentTitle, currentGenre, currentMaxPrice, currentMinRating);

        if (results.isEmpty()) {
            console.writeLine("\nNo se encontraron videojuegos que coincidan con los filtros aplicados.");
        } else {
            displayVideoGameList(results, console, null);
            SelectMenu.SelectById(store, console);
            console.sleep(2000);
            console.cls();
        }   
    }

    /**
     * Displays a list of VideoGame objects to the console in a structured format.
     * @param videoGames The list of VideoGame objects to be displayed.
     * @param console The ConsoleUI instance used for printing the output.
     */
    private static void displayVideoGameList(List<VideoGame> videoGames, ConsoleUI console, Displayable videoGame) {
        console.writeLine("\n--- RESULTADOS ---");
        console.writeStringf("%-6s  %-60s  %-12s  %-6s  %-7s  %-5s%n",
                "ID", "TÍTULO", "GÉNERO", "RATING", "PRECIO", "STOCK");
        console.writeLine("-------------------------------------------------------------------------------------------------------------");

        for (VideoGame game : videoGames) {
            console.printDisplayableDetails(game);
        }
        
        console.writeLine("------------------");
    }
}
