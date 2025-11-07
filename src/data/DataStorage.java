package src.data;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import src.domain.*;

public class DataStorage {
    
    //Serializar la clase Store
    public static void save(Store store, String filename){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))){
            out.writeObject(store);
        } catch (IOException e){
            System.err.println("Error al guardar la información: " + e.getMessage());
        }
    }

    public static Store load(String filename){
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))){
            return (Store) in.readObject();
        } catch (IOException | ClassNotFoundException e){
            System.err.println("Error al cargar la información: " + e.getMessage());
            return null;
        }
    }

    //Cargar listas desde archivos CSV
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
                        int ID = Integer.parseInt(data[1].trim());
                        float balance = Float.parseFloat(data[2].trim());

                        Customer newCustomer = new Customer(name, ID, balance);
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
                        int customerId = Integer.parseInt(data[1].trim());
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
