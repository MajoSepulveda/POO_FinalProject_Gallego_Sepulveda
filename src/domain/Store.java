package src.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.List;

/**
 * Represents the main store entity, managing the inventory (VideoGames), 
 * customer records (Customers), and transaction history (Sales).
 * Implements Serializable to allow the entire application state to be persisted.
 */
public class Store implements Serializable {
    private ArrayList<VideoGame> videoGames;
    private ArrayList<Customer> customers;
    private ArrayList<Sale> sales;
    
    // Defines the standard date format (DD-MM-YYYY) used for converting LocalDate objects into strings
    private static final DateTimeFormatter SALE_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    // --- Constructors ---

    /**
     * Default constructor: Creates an empty store with initialized, empty lists.
     */
    public Store() {
        this.videoGames = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.sales = new ArrayList<>();
    }

    /**
     * Constructor for initial loading of games and customers. Sales list remains empty.
     * @param videoGames Initial list of video games.
     * @param customers Initial list of customers.
     */
    public Store(List<VideoGame> videoGames, List<Customer> customers) {
        if (videoGames == null) this.videoGames = new ArrayList<>();
        else this.videoGames = new ArrayList<>(videoGames);

        if (customers == null) this.customers = new ArrayList<>();
        else this.customers = new ArrayList<>(customers);

        this.sales = new ArrayList<>();
    }

    /**
     * Constructor for full store restoration from existing data (e.g., deserialization).
     * @param videoGames Initial list of video games.
     * @param customers Initial list of customers.
     * @param sales Initial list of sales.
     */
    public Store(List<VideoGame> videoGames, List<Customer> customers, List<Sale> sales) {
        if (videoGames == null) this.videoGames = new ArrayList<>();
        else this.videoGames = new ArrayList<>(videoGames);

        if (customers == null) this.customers = new ArrayList<>();
        else this.customers = new ArrayList<>(customers);

        if (sales == null) this.sales = new ArrayList<>();
        else this.sales = new ArrayList<>(sales);
    }

    /**
     * Helper method to load initial sales from a data source (e.g., CSV).
     * This is used separately from the constructor when sales need to be loaded 
     * after customers and games are already available.
     * @param sales The list of sales to load into the store's transaction history.
     */
    public void loadInitialSales(List<Sale> sales) {
        if (sales != null) this.sales = new ArrayList<>(sales);
    }

    // --- Getters ---

    /**
     * @return The list of video games (inventory).
     */
    public ArrayList<VideoGame> getVideoGames() {
        return videoGames;
    }

    /**
     * @return The list of registered customers.
     */
    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    /**
     * @return The list of recorded sales transactions.
     */
    public ArrayList<Sale> getSales() {
        return sales;
    }

    // --- Find Methods ---

    /**
     * Searches for a video game by its unique ID.
     * @param id The ID of the video game to find.
     * @return The VideoGame object, or null if not found.
     * @throws IllegalArgumentException if the ID is null.
     */
    public VideoGame findVideoGameById(String id) {
        if (id == null) throw new IllegalArgumentException("El id no puede estar vacío.");
        for (VideoGame videoGame : this.videoGames){
            if (Objects.equals(videoGame.getId(), id)){
                return videoGame;
            }
        }
        return null;
    }
    
    /**
     * Searches for a customer by their unique ID.
     * @param id The ID of the customer to find.
     * @return The Customer object, or null if not found.
     * @throws IllegalArgumentException if the ID is non-positive.
     */
    public Customer findCustomerById(String id) {
        if (id == null) throw new IllegalArgumentException("El id no puede estar vacío.");
        for (Customer customer : this.customers){
            if (Objects.equals(customer.getId(), id)){
                return customer;
            }
        }
        return null;
    }

    /**
     * Searches for a sale transaction by its unique ID.
     * @param id The ID of the sale to find.
     * @return The Sale object, or null if not found.
     * @throws IllegalArgumentException if the ID is null.
     */
    public Sale findSaleById(String id) {
        if (id == null) throw new IllegalArgumentException("El id no puede estar vacío.");
        for (Sale sale : this.sales){
            if (Objects.equals(sale.getId(), id)){
                return sale;
            }
        }
        return null;
    }

    // --- Add Methods ---

    /**
     * Adds a new video game to the store's inventory.
     * @param newVideoGame The VideoGame object to add.
     * @throws IllegalArgumentException if the game is null or an ID conflict exists.
     */
    public void addGame(VideoGame newVideoGame) {
        if (newVideoGame == null) throw new IllegalArgumentException("El videojuego no puede estar vacío");
        if (findVideoGameById(newVideoGame.getId()) != null) throw new IllegalArgumentException("Ya existe un videojuego registrado con el ID proporcionado.");
        this.videoGames.add(newVideoGame);
    }

    /**
     * Adds a new customer to the store's records.
     * @param newCustomer The Customer object to add.
     * @throws IllegalArgumentException if the customer is null or an ID conflict exists.
     */
    public void addCustomer(Customer newCustomer) {
        if (newCustomer == null) throw new IllegalArgumentException("El cliente no puede estar vacío");
        if (findCustomerById(newCustomer.getId()) != null) throw new IllegalArgumentException("Ya existe un cliente registrado con el ID proporcionado.");
        this.customers.add(newCustomer);
    }

    /**
     * Adds a new sale transaction to the history.
     * @param newSale The Sale object to add.
     * @throws IllegalArgumentException if the sale is null or an ID conflict exists.
     */
    public void addSale(Sale newSale) {
        if (newSale == null) throw new IllegalArgumentException("La venta no puede estar vacía");
        if (findSaleById(newSale.getId()) != null) throw new IllegalArgumentException("Ya existe una venta registrada con el ID proporcionado.");
        this.sales.add(newSale);
    }

    // --- Remove Methods ---

    /**
     * Removes a video game from the inventory by its ID.
     * @param id The ID of the game to remove.
     * @throws IllegalArgumentException if the game does not exist.
     */
    public void removeVideoGame(String id) {
        VideoGame gameToRemove = findVideoGameById(id);
        if (gameToRemove == null) throw new IllegalArgumentException("El videojuego con el ID proporcionado no existe.");
        this.videoGames.remove(gameToRemove);
    }

    /**
     * Removes a customer from the records by their ID.
     * @param id The ID of the customer to remove.
     * @throws IllegalArgumentException if the customer does not exist.
     */ 
    public void removeCustomer(String id) {
        Customer customerToRemove = findCustomerById(id);
        if (customerToRemove == null) throw new IllegalArgumentException("El cliente con el ID proporcionado no existe.");
        this.customers.remove(customerToRemove);
    }

    /**
     * Removes a sale transaction from the history by its ID.
     * @param id The ID of the sale to remove.
     * @throws IllegalArgumentException if the sale does not exist.
     */
    public void removeSale(String id) {
        Sale saleToRemove = findSaleById(id);
        if (saleToRemove == null) {
            throw new IllegalArgumentException("La venta con el ID proporcionado no existe.");
        }
        this.sales.remove(saleToRemove);
    }

    // --- Business Logic ---

    /**
     * Filters the video game inventory based on multiple criteria (title, genre, price, rating).
     * Null or empty filter parameters are ignored.
     * @param titleFilter Filter by title (case-insensitive, partial match).
     * @param genreFilter Filter by genre (case-insensitive, partial match).
     * @param maxPrice Maximum acceptable price.
     * @param minRating Minimum acceptable rating.
     * @return A List of VideoGame objects that satisfy all provided criteria.
     */
    public List<VideoGame> filterVideoGames(String titleFilter, String genreFilter, Float maxPrice, Float minRating) { 
        List<VideoGame> filteredList = new ArrayList<>();

        String normalizedTitle = null;
        if (titleFilter != null && !titleFilter.trim().isEmpty()) normalizedTitle = titleFilter.trim().toLowerCase();

        String normalizedGenre = null;
        if (genreFilter != null && !genreFilter.trim().isEmpty()) normalizedGenre = genreFilter.trim().toLowerCase();

        for (VideoGame videoGame : this.videoGames) {
            boolean matches = true; 

            if (normalizedTitle != null) {
                if (!videoGame.getTitle().toLowerCase().contains(normalizedTitle)) matches = false;
            }
            if (matches && normalizedGenre != null) {
                if (!videoGame.getGenre().toLowerCase().contains(normalizedGenre)) matches = false;
            }
            if (matches && maxPrice != null) {
                if (videoGame.getPrice() > maxPrice) matches = false;
            }
            if (matches && minRating != null) { 
                if (videoGame.getRating() < minRating) matches = false;
            }
            if (matches) {
                filteredList.add(videoGame);
            }
        }
        return filteredList;
    }

    /**
     * Executes a complete sales transaction, including validation, inventory reduction, 
     * customer balance update, and sale record creation.
     * @param customerId ID of the customer making the purchase.
     * @param videoGameId ID of the game being purchased.
     * @param saleId Unique ID for the new sale record.
     * @throws IllegalArgumentException if customer or game is not found.
     * @throws IllegalStateException if stock is zero or customer balance is insufficient.
     */
    public void processSale (String customerId, String videoGameId, String saleId) {
        VideoGame videoGame = findVideoGameById(videoGameId);
        Customer customer = findCustomerById(customerId);

        if (videoGame == null || customer == null) throw new IllegalArgumentException("Cliente o videojuego no encontrado");
        if (videoGame.getStock() <= 0) throw new IllegalStateException("No hay stock disponible para el videojuego solicitado.");
        if (customer.getBalance() < videoGame.getPrice()) throw new IllegalStateException("El cliente no tiene suficiente saldo.");

        customer.reduceBalance(videoGame.getPrice());
        // Uses the defined SALE_DATE_FORMATTER to convert the current LocalDate object (java.time.LocalDate.now()) 
        // into a formatted String (DD-MM-YYYY) as required by the Sale constructor for internal validation.
        Sale sale = new Sale(videoGame, customer, saleId, videoGame.getPrice(), java.time.LocalDate.now().format(SALE_DATE_FORMATTER)); 
        this.sales.add(sale);
        videoGame.reduceStock();
    }

    /**
     * Filters the sales list to include only transactions that occurred within a specified date range (inclusive).
     * @param startDate The start date of the period (inclusive).
     * @param endDate The end date of the period (inclusive).
     * @return A list of Sale objects within the period.
     * @throws IllegalArgumentException if dates are null or the start date is after the end date.
     */
    private List<Sale> getSalesInPeriod(java.time.LocalDate startDate, java.time.LocalDate endDate) {
    
        if (startDate == null || endDate == null) throw new IllegalArgumentException("Las fechas de inicio y fin no pueden ser nulas.");
        if (startDate.isAfter(endDate)) throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha final.");

        List<Sale> periodSales = new ArrayList<>();
    
        for (Sale sale : this.sales) {
            java.time.LocalDate saleDate = sale.getDate();
            boolean isAfterOrEqualStart = saleDate.isAfter(startDate) || saleDate.isEqual(startDate);
            boolean isBeforeOrEqualEnd = saleDate.isBefore(endDate) || saleDate.isEqual(endDate);
        
            if (isAfterOrEqualStart && isBeforeOrEqualEnd) periodSales.add(sale);
        }
        return periodSales;
    }

    /**
     * Calculates the total income generated from a given list of sales.
     * @param sales The list of sales transactions.
     * @return The total sum of all sale amounts.
     */
    private float getTotalIncome(List<Sale> sales){
        float totalIncome = 0;
        for (Sale sale: sales){
            totalIncome += sale.getAmount();
        }
        return totalIncome;
    }

    /**
     * Determines the genre with the highest number of sales in a given list.
     * Note: This method uses an inefficient counting loop structure (O(n^2)).
     * @param sales The list of sales to analyze.
     * @return The name of the top-selling genre, or "N/A" if the list is empty.
     */
    private String getTopSellingGenre(List<Sale> sales) {
        if (sales == null || sales.isEmpty()) return "N/A";

        List<String> countedGenres = new ArrayList<>();
        String topGenre = "N/A";
        int maxCount = 0;
        
        for (Sale saleA : sales) {
            String genreA = saleA.getVideoGame().getGenre();
        
            if (countedGenres.contains(genreA)) continue;
        
            int currentCount = 0;
            for (Sale saleB : sales) {
                if (saleB.getVideoGame().getGenre().equals(genreA)) currentCount++;
            }
            countedGenres.add(genreA);

            if (currentCount > maxCount) {
                maxCount = currentCount;
                topGenre = genreA;
            }
        }
        return topGenre;
    }

    /**
     * Determines the video game with the highest number of sales in a given list.
     * Note: This method uses an inefficient counting loop structure (O(n^2)).
     * @param sales The list of sales to analyze.
     * @return The top-selling VideoGame object, or null if the list is empty.
     */
    private VideoGame getTopSellingGame(List<Sale> sales) {
        if (sales == null || sales.isEmpty()) return null;

        VideoGame topGame = null;
        int maxCount = 0;

        for (int i = 0; i < sales.size(); i++) {
            VideoGame gameReference = sales.get(i).getVideoGame();
            int currentCount = 0;

            for (Sale sale : sales) {
                if (sale.getVideoGame().equals(gameReference)) currentCount++;
            }
            if (currentCount > maxCount) {
                maxCount = currentCount;
                topGame = gameReference;
            }
        }
        return topGame;
    }

    /**
     * Generates a summarized income report for a specified date range.
     * The report includes total income, number of sales, top genre, and top game.
     * @param startDate The start date of the reporting period.
     * @param endDate The end date of the reporting period.
     * @return A formatted String containing the income report.
     */
    public String generateIncomeReport(LocalDate startDate, LocalDate endDate) {
        List<Sale> salesInPeriod = getSalesInPeriod(startDate, endDate);
    
        if (salesInPeriod.isEmpty()) return "No se registraron ventas entre " + startDate + " y " + endDate + ".";

        float totalIncome = getTotalIncome(salesInPeriod);
        String topGenre = getTopSellingGenre(salesInPeriod);
        VideoGame topGame = getTopSellingGame(salesInPeriod);
        
        String title;
        if (topGame == null) title = "N/A";
        else title = topGame.getTitle();

        String report = "\n--- REPORTE DE INGRESOS ---";
        report += "\nPeríodo: " + startDate + " a " + endDate;
        report += "\nTotal de Ventas: " + salesInPeriod.size();
        report += String.format("\nIngreso Total Generado: $%.2f", totalIncome);
        report += "\n-----------------------------";
        report += "\nGénero Más Vendido: " + topGenre;
        report += "\nVideojuego Más Vendido: " + title;
        report += "\n-----------------------------";
    
        return report;
    }

    /**
     * Generates the Sale ID by reversing the VideoGame ID.
     * Assumes the Game ID has already been validated.
     * @param gameId The validated Game ID (e.g., "A001").
     * @return The generated Sale ID (e.g., "100A"), or an empty string if gameId is invalid/missing.
     */
    // En la clase src.domain.Store


    public String generateSaleId() {
        int currentId = 1;
        String newSaleId;

        while (true) {
            newSaleId = String.valueOf(currentId);

            if (findSaleById(newSaleId) == null) {
                break; 
            }

            currentId++; 
        }
        return newSaleId;
    }
}
