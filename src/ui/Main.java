package src.ui;

import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import src.domain.*;
import src.data.*;

/**
 * Main class for the application. Handles initialization, data loading 
 * (from serialization or CSV), saving, and program termination.
 */
public class Main {
    // --- Data File Constants ---
    private static final String MAIN_FILE = "src/data/store_data.ser";
    private static final String BACKUP_FILE = "src/data/store_backup.ser";
    private static final String CUSTOMERS_CSV = "src/data/CustomersList.csv";
    private static final String GAMES_CSV = "src/data/VideogamesList.csv";
    private static final String SALES_CSV = "src/data/SalesList.csv";

    private static ConsoleUI console;
     // --- Loading Methods ---

    /**
     * Attempts to load the initial application data from CSV files.
     * This is used when no serialized state is found.
     * @param scanner Scanner instance to handle user input (if needed later).
     * @return An initialized Store object loaded with CSV data, or null if loading fails critically.
     */
    private static Store loadFromCSV(Scanner scanner) {
        try {
            // Load Customers and VideoGames first as they are needed for Sale objects
            List<Customer> customers = DataStorage.loadCustomers(CUSTOMERS_CSV);
            List<VideoGame> videoGames = DataStorage.loadVideogames(GAMES_CSV);
            Store store = new Store(videoGames, customers);
            // Load Sales, which requires the initialized Store to link Customer/VideoGame objects
            List<Sale> sales = DataStorage.loadSales(SALES_CSV, store);
            store.loadInitialSales(sales);
            console.writeLine("\nDatos cargados desde los archivos CSV con éxito.");
            return store;

        } catch (IOException e) {
            console.writeLine("Error al cargar datos desde archivos CSV. " + e.getMessage());
            console.writeLine("Verifique la existencia y formato de los archivos CSV.");
            return null;
        }
    }

    /**
     * Handles the scenario where loading the serialized main file fails.
     * Prompts the user to choose between loading from CSV or starting empty.
     * @param input Scanner instance for user input.
     * @return A newly initialized Store (from CSV or empty), or null if the user chooses to exit.
     */
    private static Store handleFailedLoad(Scanner input) {
        console.writeLine("\n--- OPCIONES DE INICIO ---");
        console.writeLine("1. Cargar datos iniciales desde archivos CSV.");
        console.writeLine("2. Iniciar la tienda vacía.");
        String option = console.readString("Seleccione una opción (1 o 2): ");
            
        if (option.equals("1")) {
            return loadFromCSV(input);
        } else if (option.equals("2")) { 
            console.writeLine("\nIniciando tienda vacía con éxito.");
            return new Store(); 
        } else {
            console.writeLine("\nOpción inválida. El programa finalizará.");
            return null;
        }
    }

    // --- Saving Method ---

    /**
     * Handles the application shutdown saving process, implementing a primary 
     * file and a backup file strategy for robustness.
     * @param store The Store object containing the current session data.
     */
    private static void handleSave(Store store) {
        try {
            // Attempt 1: Save to the main file
            DataStorage.save(store, MAIN_FILE);
            console.writeLine("\nDatos de la tienda guardados con éxito en " + MAIN_FILE);
        } catch (IOException e) {
            console.writeLine("\nAvertencia: Falló el guardado principal en " + MAIN_FILE + ". Intentando respaldo en " + BACKUP_FILE);

            // Attempt 2: Save to the backup file
            try {
                DataStorage.save(store, BACKUP_FILE);
                console.writeLine("\nDatos guardados con éxito en el archivo de respaldo " + BACKUP_FILE);
            } catch (IOException innerE) {
                // Both attempts failed
                console.writeLine("\nERROR CRÍTICO: Fallaron ambos intentos de guardado. Los datos de esta sesión no se guardarán. " + innerE.getMessage());
            }
        }
    }

    // --- Main Method ---

    /**
     * The main entry point of the application. Manages the lifecycle: 
     * initialization, loading persistent data, running the core logic, and saving on exit.
     * @param args Command line arguments (unused).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Store store;
        
        console = new ConsoleUI(scanner);

        // --- Load Serialized Data --- 
        try{
            store = DataStorage.load(MAIN_FILE);
            console.writeLine("\nDatos de la tienda cargados con éxito desde " + MAIN_FILE + "\n Inicializando programa...");
        } catch (IOException | ClassNotFoundException e){
            console.writeLine("\nError al cargar los datos de la tienda desde " + MAIN_FILE + ": " + e.getMessage());
            store = handleFailedLoad(scanner);
        }
        
        // --- Program Execution and Shutdown ---
        if (store != null) {
            StartMenu.show(store, console);

            console.writeLine("\nGuardando cambios de la sesión...");
            handleSave(store);
        }
        
        scanner.close();
        console.writeLine("\nPrograma finalizado.");
    }
}
