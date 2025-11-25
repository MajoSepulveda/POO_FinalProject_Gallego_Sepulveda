package src.data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import src.domain.*;

/**
 * Handles the persistent storage operations for the application. 
 * Provides static methods for serializing/deserializing the Store object and loading initial data from CSV files.
 */
public class DataStorage {
    
    // --- SERIALIZATION METHODS (Saving/Loading the Store Object) ---
    
    /**
     * Serializes and saves the entire Store object to a binary file.
     * This method saves the current state of the store, including all its contents.
     * @param store The Store object to be saved.
     * @param filename The name of the file to write the serialized object to.
     * @throws IOException If an I/O error occurs during writing to the file.
     */
    public static void save(Store store, String filename) throws IOException {
        //Using the try structure ensures that the ObjectOutputStream and FileOutputStream are automatically closed upon exiting the try block, even if an IOException occurs.
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(store);
        } 
    }

    /**
     * Loads and deserializes a Store object from a binary file.
     * @param filename The name of the file to read the serialized object from.
     * @return The loaded Store object.
     * @throws IOException If an I/O error occurs during reading from the file.
     * @throws ClassNotFoundException If the class of a serialized object cannot be found.
     */
    public static Store load(String filename) throws IOException, ClassNotFoundException {
        //Using the try structure ensures that the ObjectOutputStream and FileOutputStream are automatically closed upon exiting the try block, even if an IOException occurs.
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (Store) in.readObject(); 
        } 
    }

    // --- CSV LOADING METHODS (Initial Data) ---
    
    /**
     * Loads a list of VideoGame objects from a CSV file.
     * Assumes the CSV format is: title, genre, rating, price, ID, stock.
     * @param filename The path to the CSV file.
     * @return A List of VideoGame objects created from the file.
     * @throws IOException If an I/O error occurs.
     */
    public static List<VideoGame> loadVideogames(String filename) throws IOException {
    ArrayList<VideoGame> videoGamesList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))){
            String header = br.readLine();
            if (header == null) return videoGamesList;
            String line;
            
            while ((line = br.readLine()) != null){
                String[] data = line.split(",");

                if (data.length >= 6){
                    try {
                        String tittle = data[0].trim();
                        String genre = data[1].trim();
                        float ranking = Float.parseFloat(data[2].trim());
                        float price = Float.parseFloat(data[3].trim());
                        String ID = data[4].trim();
                        int stock = Integer.parseInt(data[5].trim());

                        VideoGame newVideogame = new VideoGame(tittle, genre, ranking, price, ID, stock);
                        videoGamesList.add(newVideogame);

                    } catch (NumberFormatException e){
                        System.err.println("Advertencia: Se omitió la línea '" + line + "' por formato de número inválido.");
                    }
                } else {
                    System.err.println("Advertencia: Se omitió la línea '" + line + "' porque tiene una cantidad de columnas incorrecta.");
                }
            }
            return videoGamesList;
        }
    }

    /**
     * Loads a list of Customer objects from a CSV file.
     * Assumes the CSV format is: name, ID, balance.
     * @param filename The path to the CSV file.
     * @return A List of Customer objects created from the file.
     * @throws IOException If an I/O error occurs.
     */
    public static List<Customer> loadCustomers(String filename) throws IOException {
    ArrayList<Customer> customerList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))){
            String header = br.readLine();
            if (header == null) return customerList;
            String line;

            while ((line = br.readLine()) != null){
                String[] data = line.split(",");

                if (data.length >= 3){
                    try {
                        String name = data[0].trim();
                        String id = data[1].trim();
                        float balance = Float.parseFloat(data[2].trim());

                        Customer newCustomer = new Customer(name, id, balance);
                        customerList.add(newCustomer);
                    } catch (NumberFormatException e){
                        System.err.println("Advertencia: Se omitió la línea '" + line + "' por formato de número inválido.");
                    }
                } else {
                    System.err.println("Advertencia: Se omitió la línea '" + line + "' por tener una cantidad de columnas incorrecta.");
                }
            }
            return customerList;
        }
    } 
    
    /**
     * Loads a list of Sale objects from a CSV file.
     * This requires a reference to the existing Store to look up Customer and VideoGame objects.
     * Assumes the CSV format is: saleId, customerId, videoGameId, amount, date.
     * @param filename The path to the CSV file.
     * @param store The Store instance containing the customer and game catalogs.
     * @return A List of Sale objects.
     * @throws IOException If an I/O error occurs.
     */
    public static List<Sale> loadSales(String filename, Store store) throws IOException {
        ArrayList<Sale> saleList = new ArrayList<>();

        if (store == null) {
            System.err.println("Advertencia: El parámetro 'store' es null. No se pueden cargar ventas sin un Store inicializado.");
            return saleList;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filename))){
            String header = br.readLine();
            if (header == null) return saleList;
            String line;

            while ((line = br.readLine()) != null){
                String[] data = line.split(",");

                if (data.length >= 5) {
                    try {
                        String saleId = data[0].trim();
                        String customerId = data[1].trim();
                        String videoGameId = data[2].trim();
                        float amount = Float.parseFloat(data[3].trim());
                        String date = data[4].trim();

                        Customer customer = store.findCustomerById(customerId);
                        VideoGame videoGame = store.findVideoGameById(videoGameId);

                        // Ambos deben existir para crear una venta válida
                        if (customer != null && videoGame != null){
                            Sale newSale = new Sale(videoGame, customer, saleId, amount, date);
                            saleList.add(newSale);
                        } else {
                            System.err.println("Advertencia: No se encontró el Cliente (" + customerId + ") o el Juego (" + videoGameId + ") para la venta " + saleId + ". Línea omitida.");
                        }
                    } catch (NumberFormatException e){
                        System.err.println("Advertencia: Se omitió la línea '" + line + "' por formato de número inválido.");

                    }
                } else {
                    System.err.println("Advertencia: Se omitió la línea '" + line + "' porque tiene una cantidad de columnas incorrecta.");
                }
            }
        }
        return saleList;
    }
}
