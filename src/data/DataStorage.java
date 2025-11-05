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
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    public static Store load(String filename){
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))){
            return (Store) in.readObject();
        } catch (IOException | ClassNotFoundException e){
            System.err.println("Error loading data: " + e.getMessage());
            return null;
        }
    }

    //Cargar listas desde archivos CSV
    public static ArrayList<VideoGame> loadVideogames(String filename) throws IOException {
        ArrayList<VideoGame> videoGamesList = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))){
            String header = br.readLine(); // Salta la primera línea del archivo (encabezados)
            if (header == null) return videoGamesList; // archivo vacío

            while ((line = br.readLine()) != null){
                String[] data = line.split(",");

                if (data.length >= 6){
                    try {
                        String tittle = data[0];
                        String genre = data[1];
                        float ranking = Float.parseFloat(data[2]);
                        float price = Float.parseFloat(data[3]);
                        String ID = data[4];
                        int stock = Integer.parseInt(data[5]);

                        VideoGame newVideogame = new VideoGame(tittle, genre, ranking, price, ID, stock);
                        videoGamesList.add(newVideogame);

                    } catch (NumberFormatException e){
                        System.err.println("Advertencia: Se omitió la línea '" + line + "' por formato de número inválido.");
                    }
                } else {
                    System.err.println("Advertencia: Se omitió la línea '" + line + "' por tener una cantidad de columnas incorrecta.");
                }
            }
            return videoGamesList;
        }
    }

    public static ArrayList<Customer> loadCustomers(String filename) throws IOException {
        ArrayList<Customer> customerList = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))){
            String header = br.readLine();
            if (header == null) return customerList;

            while ((line = br.readLine()) != null){
                String[] data = line.split(",");

                if (data.length >= 3){
                    try {
                        String name = data[0];
                        int ID = Integer.parseInt(data[1]);
                        float balance = Float.parseFloat(data[2]);

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
    
    public static ArrayList<Sale> loadSales(String filename, ArrayList<Customer> allCustomers, ArrayList<VideoGame> allVideoGames) throws IOException {
        ArrayList<Sale> saleList = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))){
            String header = br.readLine();
            if (header == null) return saleList;

            while ((line = br.readLine()) != null){
                String[] data = line.split(",");

                if (data.length >= 5) {
                    try {
                        String saleId = data[0];
                        int customerId = Integer.parseInt(data[1]);
                        String videoGameId = data[2];
                        float amount = Float.parseFloat(data[3]);
                        String date = data[4];

                        // Buscar objetos en las listas que fueron pasadas como parámetros
                        Customer customer = findCustomerByID(customerId, allCustomers);
                        VideoGame videoGame = findVideoGameByID(videoGameId, allVideoGames);

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
                    System.err.println("Advertencia: Se omitió la línea '" + line + "' por tener una cantidad de columnas incorrecta.");
                }
            }
        }
        return saleList;
    }

    // Helpers para buscar objetos por ID dentro de las listas pasadas
    private static Customer findCustomerByID(int id, List<Customer> customers){
        if (customers == null) return null;
        for (Customer c : customers){
            // Asumimos que Customer tiene un getter getID() o campo accesible
            try {
                if (c.getID() == id) return c;
            } catch (Exception e) {
                // Si la clase Customer no tiene getID, omitimos y seguimos
            }
        }
        return null;
    }

    private static VideoGame findVideoGameByID(String id, List<VideoGame> videoGames){
        if (videoGames == null) return null;
        for (VideoGame v : videoGames){
            try {
                if (v.getID().equals(id)) return v;
            } catch (Exception e) {
                // Si no hay getID, omitimos
            }
        }
        return null;
    }
}
