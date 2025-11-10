package src.ui;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;

import src.domain.*;
import src.data.*;

public class Main {
    private static final String MAIN_FILE = "src/data/store_data.ser";
    private static final String BACKUP_FILE = "src/data/store_backup.ser";
    private static final String CUSTOMERS_CSV = "src/data/customers.csv";
    private static final String GAMES_CSV = "src/data/videogames.csv";
    private static final String SALES_CSV = "src/data/sales.csv";

    private static Store loadFromCSV(Scanner scanner){
        try {
            List<Customer> customers = DataStorage.loadCustomers(CUSTOMERS_CSV);
            List<VideoGame> videoGames = DataStorage.loadVideogames(GAMES_CSV);
            Store store = new Store(videoGames, customers);
            List<Sale> sales = DataStorage.loadSales(SALES_CSV, store);
            store.loadInitialSales(sales);
            System.out.println("\nDatos cargados desde los archivos CSV con éxito.");
            return store;

        } catch (IOException e) {
            System.err.println("Error al cargar datos desde archivos CSV. " + e.getMessage());
            System.err.println("Verifique la existencia y formato de los archivos CSV.");
            return null;
        }
    }

    private static Store handleFailedLoad(Scanner scanner){
        System.out.println("\n--- OPCIONES DE INICIO ---");
        System.out.println("1. Cargar datos iniciales desde archivos CSV.");
        System.out.println("2. Iniciar la tienda vacía.");
        System.out.print("Seleccione una opción (1 o 2): ");

        String option = scanner.nextLine();
            
        if (option.equals("1")) {
            return loadFromCSV(scanner);
        } else if (option.equals("2")) {   
            System.out.println("\nIniciando tienda vacía con éxito.");
            return new Store(); 
        } else {
            System.err.println("\nOpción inválida. El programa finalizará.");
            return null;
        }
    }

    private static void handleSave(Store store) {
        // Intento 1: Guardar en el archivo principal
        try {
            DataStorage.save(store, MAIN_FILE);
            System.out.println("\nDatos de la tienda guardados con éxito en " + MAIN_FILE);
        } catch (IOException e) {
            System.err.println("\nAvertencia: Falló el guardado principal en " + MAIN_FILE + ". Intentando respaldo en " + BACKUP_FILE);
        
            // Intento 2: Guardar en el archivo de respaldo
            try {
                DataStorage.save(store, BACKUP_FILE);
                System.out.println("\nDatos guardados con éxito en el archivo de respaldo " + BACKUP_FILE);
            } catch (IOException innerE) {
                // Fallaron ambos intentos
                System.err.println("\nERROR CRÍTICO: Fallaron ambos intentos de guardado. Los datos de esta sesión no se guardarán. " + innerE.getMessage());
            }
        }
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Store store;

        try{
            store = DataStorage.load(MAIN_FILE);
            System.out.println("\nDatos de la tienda cargados con éxito desde " + MAIN_FILE + "\n Inicializando programa...");
        } catch (IOException | ClassNotFoundException e){
            System.err.println("\nError al cargar los datos de la tienda desde " + MAIN_FILE + ": " + e.getMessage());
            store = handleFailedLoad(scanner);
        }
        
        if (store != null) { //inicia el programa
            

            System.out.println("\nGuardando cambios de la sesión..."); //Cuando se va a cerrar el programa
            handleSave(store);
        }
        
        scanner.close();
        System.out.println("\nPrograma finalizado.");
    }
}
