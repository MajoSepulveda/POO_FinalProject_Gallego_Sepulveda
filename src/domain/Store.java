package src.domain;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Store implements Serializable {
    private ArrayList<VideoGame> videoGames;
    private ArrayList<Customer> customers;
    private ArrayList<Sale> sales;

    public Store(){
        this.videoGames = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.sales = new ArrayList<>();
    }

    public ArrayList<VideoGame> getVideoGames(){
        return videoGames;
    }

    public ArrayList<Customer> getCustomers(){
        return customers;
    }

    public ArrayList<Sale> getSale(){
        return sales;
    }

    private static Customer findCustomerByID(int ID, List<Customer> customers){
        if (customers == null) return null;
        for (Customer customer : customers){
            try {
                if (customer.getID() == ID) return customer;
            } catch (Exception e) {
            
            }
        }
        return null;
    }

    private static VideoGame findVideoGameByID(String ID, List<VideoGame> videoGames){
        if (videoGames == null) return null;
        for (VideoGame videoGame : videoGames){
            try {
                if (videoGame.getID().equals(ID)) return videoGame;
            } catch (Exception e) {
                // Si no hay getID, omitimos
            }
        }
        return null;
    }

    public static Sale findSaleByID(String id, List<Sale> sales){
        if (sales == null) return null;
        for (Sale sale : sales){
            try {
                if (sale.getId().equals(id)) return sale;
            } catch (Exception e) {
                // Si no hay getID, omitimos
            }
        }
        return null;
    }

    public void addGame(VideoGame newVideoGame){
        if (newVideoGame == null){
            throw new IllegalArgumentException("El videojuego no puede estar vacío");
        }
        if (findVideoGameByID(newVideoGame.getID(), videoGames) != null){
            throw new IllegalArgumentException("El videojuego ya existe en la tienda");
        }
        this.videoGames.add(newVideoGame);
    }

    public void addCustomer(Customer newCustomer){
        if (newCustomer == null){
            throw new IllegalArgumentException("El cliente no puede estar vacío");
        }
        if (findCustomerByID(newCustomer.getID(), customers) != null){
            throw new IllegalArgumentException("El cliente ya existe en la tienda");
        }
        this.customers.add(newCustomer);
    }

    public void addSale(Sale newSale){
        if (newSale == null){
            throw new IllegalArgumentException("La venta no puede estar vacía");
        }
        if (findSaleByID(newSale.getId(), sales) != null){
            throw new IllegalArgumentException("La venta ya existe en la tienda");
        }
        this.sales.add(newSale);
    }

    private void removeVideoGame(VideoGame videoGame){
        this.videoGames.remove(videoGame);
    }

    private void removeCustomer(Customer customer){
        this.customers.remove(customer);
    }

    private void removeSale(Sale sale){
        this.sales.remove(sale);
    }

    public void manageTransaction(Sale sale){
        if (sale == null){
            throw new IllegalArgumentException("La venta no puede estar vacía");
        }
        addSale(sale);
        removeVideoGame(sale.getVideoGame());
    }    
}
