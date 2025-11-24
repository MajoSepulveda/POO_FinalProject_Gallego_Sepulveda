package src.domain;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.List;

public class Store implements Serializable {
    private ArrayList<VideoGame> videoGames;
    private ArrayList<Customer> customers;
    private ArrayList<Sale> sales;

        /*Crea una tienda vacía*/
        public Store() {
            this.videoGames = new ArrayList<>();
            this.customers = new ArrayList<>();
            this.sales = new ArrayList<>();
        }

        /*Crea una tienda solo con los videojuegos y clientes */
        public Store(List<VideoGame> videoGames, List<Customer> customers) {
            if (videoGames == null) this.videoGames = new ArrayList<>();
            else this.videoGames = new ArrayList<>(videoGames);

            if (customers == null) this.customers = new ArrayList<>();
            else this.customers = new ArrayList<>(customers);

            this.sales = new ArrayList<>();
        }

        /*Construye una tienda a partir de listas ya cargadas*/
        public Store(List<VideoGame> videoGames, List<Customer> customers, List<Sale> sales) {
            if (videoGames == null) this.videoGames = new ArrayList<>();
            else this.videoGames = new ArrayList<>(videoGames);

            if (customers == null) this.customers = new ArrayList<>();
            else this.customers = new ArrayList<>(customers);

            if (sales == null) this.sales = new ArrayList<>();
            else this.sales = new ArrayList<>(sales);
        }

    public void loadInitialSales(List<Sale> sales) {
        if (sales != null) this.sales = new ArrayList<>(sales);
    }

    public ArrayList<VideoGame> getVideoGames() {
        return videoGames;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public ArrayList<Sale> getSales() {
        return sales;
    }

    public VideoGame findVideoGameById(String id) {
        if (id == null) throw new IllegalArgumentException("id no puede ser null");
        for (VideoGame videoGame : this.videoGames){
            if (Objects.equals(videoGame.getId(), id)){
                return videoGame;
            }
        }
        return null;
    }
    
    public Customer findCustomerById(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID de cliente inválido.");
        for (Customer customer : this.customers){
            if (customer.getId() == id){
                return customer;
            }
        }
        return null;
    }

    public Sale findSaleById(String id) {
        if (id == null) throw new IllegalArgumentException("id no puede ser null");
        for (Sale sale : this.sales){
            if (Objects.equals(sale.getId(), id)){
                return sale;
            }
        }
        return null;
    }

    public void addGame(VideoGame newVideoGame) {
        if (newVideoGame == null) throw new IllegalArgumentException("El videojuego no puede estar vacío");
        if (findVideoGameById(newVideoGame.getId()) != null) throw new IllegalArgumentException("Ya existe un videojuego registrado con el ID proporcionado.");
        this.videoGames.add(newVideoGame);
    }

    public void addCustomer(Customer newCustomer) {
        if (newCustomer == null) throw new IllegalArgumentException("El cliente no puede estar vacío");
        if (findCustomerById(newCustomer.getId()) != null) throw new IllegalArgumentException("Ya existe un cliente registrado con el ID proporcionado.");
        this.customers.add(newCustomer);
    }

    public void addSale(Sale newSale) {
        if (newSale == null) throw new IllegalArgumentException("La venta no puede estar vacía");
        if (findSaleById(newSale.getId()) != null) throw new IllegalArgumentException("Ya existe una venta registrada con el ID proporcionado.");
        this.sales.add(newSale);
    }

    public void removeVideoGame(String id) {
        VideoGame gameToRemove = findVideoGameById(id);
        if (gameToRemove == null) throw new IllegalArgumentException("El videojuego con el ID proporcionado no existe.");
        this.videoGames.remove(gameToRemove);
    }

    public void removeCustomer(int id) {
        Customer customerToRemove = findCustomerById(id);
        if (customerToRemove == null) throw new IllegalArgumentException("El cliente con el ID proporcionado no existe.");
        this.customers.remove(customerToRemove);
    }

    public void removeSale(String id) {
        Sale saleToRemove = findSaleById(id);
        if (saleToRemove == null) {
            throw new IllegalArgumentException("La venta con el ID proporcionado no existe.");
        }
        this.sales.remove(saleToRemove);
    }

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

    //Método para realizar una venta
    public void processSale (int customerId, String videoGameId, String saleId) {
        VideoGame videoGame = findVideoGameById(videoGameId);
        Customer customer = findCustomerById(customerId);

        if (videoGame == null || customer == null) throw new IllegalArgumentException("Cliente o videojuego no encontrado");
        if (videoGame.getStock() <= 0) throw new IllegalStateException("No hay stock disponible para el videojuego solicitado.");
        if (customer.getBalance() < videoGame.getPrice()) throw new IllegalStateException("El cliente no tiene suficiente saldo.");

        customer.reduceBalance(videoGame.getPrice());
        Sale sale = new Sale(videoGame, customer, saleId, videoGame.getPrice(), java.time.LocalDate.now());
        this.sales.add(sale);
        videoGame.reduceStock();
    }

    //Métodos auxiliares para generar reportes
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

    public float getTotalIncome(List<Sale> sales){
        float totalIncome = 0;
        for (Sale sale: sales){
            totalIncome += sale.getAmount();
        }
        return totalIncome;
    }

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
}
