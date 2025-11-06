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

    public ArrayList<Sale> getSales(){
        return sales;
    }

    private Customer findCustomerById(int id){
        if (this.customers == null) return null;
        for (Customer customer : this.customers){
            try {
                if (customer.getId() == id) return customer;
            } catch (Exception e) {
            
            }
        }
        return null;
    }

    private VideoGame findVideoGameById(String id){
        if (this.videoGames == null) return null;
        for (VideoGame videoGame : this.videoGames){
            try {
                if (videoGame.getId().equals(id)) return videoGame;
            } catch (Exception e) {
                // Si no hay getID, omitimos
            }
        }
         return null;
    }

    public Sale findSaleById(String id){
        if (this.sales == null) return null;
        for (Sale sale : this.sales){
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
        if (findVideoGameById(newVideoGame.getId()) != null){
            throw new IllegalArgumentException("El videojuego ya existe en la tienda");
        }
        this.videoGames.add(newVideoGame);
    }

    public void addCustomer(Customer newCustomer){
        if (newCustomer == null){
            throw new IllegalArgumentException("El cliente no puede estar vacío");
        }
        if (findCustomerById(newCustomer.getId()) != null){
            throw new IllegalArgumentException("El cliente ya existe en la tienda");
        }
        this.customers.add(newCustomer);
    }

    public void addSale(Sale newSale){
        if (newSale == null){
            throw new IllegalArgumentException("La venta no puede estar vacía");
        }
        if (findSaleById(newSale.getId()) != null){
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

    public void processSale (int customerId, String videoGameId, String saleId){
        VideoGame videoGame = findVideoGameById(videoGameId);
        Customer customer = findCustomerById(customerId);
        if (videoGame == null || customer == null){
            throw new IllegalArgumentException("Cliente o videojuego no encontrado");
        }
        customer.reduceBalance(videoGame.getPrice());
        Sale sale = new Sale(videoGame, customer, saleId, videoGame.getPrice(), java.time.LocalDate.now());
        this.sales.add(sale);
        videoGame.reduceStock();
    }

    public float incomeReort(){
        float totalIncome = 0;
        for (Sale sale: this.sales){
            totalIncome += sale.getAmount();
        }
        return totalIncome;
    }
}
