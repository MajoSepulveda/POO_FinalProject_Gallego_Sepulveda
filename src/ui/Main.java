package src.ui;

import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import src.domain.*;
import src.data.*;

/**
 * Main class for the application. Handles initialization, data loading (from serialization or CSV), saving, and program termination.
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
     * @param scanner Scanner instance to handle user input.
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
            console.writeSucces("\nDatos cargados desde los archivos CSV con éxito.");
            console.readString("Pulse ENTER para continuar");
            return store;
        } catch (IOException e) {
            console.writeError("\nCarga de datos desde archivos CSV incorrecta. " + e.getMessage());
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
        console.cls();
        console.writeLine("--- OPCIONES DE INICIO ---");
        console.writeLine("1) Cargar datos iniciales desde archivos CSV.");
        console.writeLine("2) Iniciar la tienda vacía.");
        int option = console.readInt("\nSeleccione una opción (1 o 2): ");
            
        if (option == 1) {
            console.sleep(500);
            return loadFromCSV(input);
        } else if (option == 2) { 
            console.writeLine("\nIniciando tienda vacía...");
            return new Store(); 
        } else {
            console.writeError("Opción inválida. El programa finalizará.");
            return null;
        }
    }

    // --- Saving Method ---

    /**
     * Handles the application shutdown saving process, implementing a primary file and a backup file if the first one fails.
     * @param store The Store object containing the current session data.
     */
    private static void handleSave(Store store) {
        try {
            // Attempt 1: Save to the main file
            DataStorage.save(store, MAIN_FILE);
            console.writeSucces("\nDatos de la tienda guardados con éxito en " + MAIN_FILE);
        } catch (IOException e) {
            console.writeError("\nAvertencia: Falló el guardado principal en " + MAIN_FILE + ". Intentando respaldo en " + BACKUP_FILE);

            // Attempt 2: Save to the backup file
            try {
                DataStorage.save(store, BACKUP_FILE);
                console.writeSucces("\nDatos guardados con éxito en el archivo de respaldo " + BACKUP_FILE);
            } catch (IOException innerE) {
                // Both attempts failed
                console.writeError("\nERROR CRÍTICO: Fallaron ambos intentos de guardado. Los datos de esta sesión no se guardarán. " + innerE.getMessage());
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
        console.cls();
        console.sleep(200);
        try{
            store = DataStorage.load(MAIN_FILE);
            console.writeSucces("Datos de la tienda cargados con éxito desde " + MAIN_FILE);
            console.writeLine("\nIniciando programa...");
            console.sleep(2000);
        } catch (IOException | ClassNotFoundException e){
            console.writeError("No se pudo cargar los datos de la tienda desde el archivo: " + e.getMessage());
            console.writeLine("Se presentarán otras opciones de incio.");
            console.sleep(4000);

            store = handleFailedLoad(scanner);
            console.sleep(1500);
            console.cls();
        }
        
        // --- Program Execution and Shutdown ---
        if (store != null) {
            StartMenu.show(store, console);
            console.cls();
            console.writeLine("Guardando cambios de la sesión...");
            handleSave(store);
            console.sleep(3000);
        }
        
        scanner.close();
        console.cls();
        console.sleep(500);
        console.writeLine("Programa finalizado.");
    }
}
