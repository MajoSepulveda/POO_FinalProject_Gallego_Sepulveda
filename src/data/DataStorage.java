package data;
import java.io.*;
import java.util.ArrayList;

import domain.*;

public class DataStorage {
    
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

    public static ArrayList<Videogame> loadVideogames(String filename){
        ArrayList<Videogames> videogamesList = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))){
            br.readLine(); //Salta la primera línea del archivo (encabezados)

            while ((line = br.readLine()) != null){
                String[] data = line.split(",");

                if (data.length == 6){
                    try {
                        String tittle = data[0];
                        String genre = data[1];
                        float ranking = Float.parseFloat(data[2]);
                        float price = Float.parseFloat(data[3]);
                        String ID = data[4];
                        int stock = Integer.parseInt(data[5]);

                        Videogame newVideogame = new Videogame(tittle, genre, ranking, price, ID, stock);
                        videogamesList.add(newVideogame);

                    } catch (NumberFormatException e){
                        System.err.println("Advertencia: Se omitió la línea '" + line + "' por formato de número inválido.");
                    }
                }
            }
            return videogamesList;
        }
    }

    public static ArrayList<Customer> loadCustomers(String filename){
        ArrayList<Customer> customerList = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))){
            br.readLine();

            while ((line = br.readLine()) != null){
                String[] data = line.split(",");

                if (data.length == 3){
                    try {
                        String name = data[0];
                        int ID = Integer.parseInt(data[1]);
                        float balance = Float.parseFloat(data[2]);

                        Customer newCustomer = new Customer(name, ID, balance);
                        customerList.add(newCustomer);
                    } catch (NumberFormatException e){
                        System.err.println("Advertencia: Se omitió la línea '" + line + "' por formato de número inválido.");
                    }
                }
            }
            return customerList;
        }
    }   
}
