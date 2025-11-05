package src.domain;
import java.io.Serializable;

public class VideoGame implements Serializable {

    private String id;
    private String title;
    private String genre;
    private float rating;
    private float price;
    private int stock;

    public VideoGame(String title, String genre, float rating, float price, String id, int stock){
        setTitle(title);
        setGenre(genre);
        setRating(rating);
        setPrice(price);
        if (stock < 0) {
            throw new IllegalArgumentException("El stock del videojuego no puede ser negativo");
        }
        this.stock = stock;

        if (id == null || id.trim().isBlank()) {
            throw new IllegalArgumentException("El ID del videojuego no puede estar vacio");
        }
        this.id = id;
    }

    public String getID(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getGenre(){
        return genre;
    }

    public float getRating(){
        return rating;
    }

    public float getPrice(){
        return price;
    }

    public int getStock(){
        return stock;
    }

    public void setTitle(String title){
        if (title == null || title.trim().isBlank()) {
            throw new IllegalArgumentException("El título del videojuego no puede estar vacio");
        }
        this.title = title;
    }

    public void setGenre(String genre) {
        if (genre == null || genre.trim().isBlank()) {
            throw new IllegalArgumentException("El genero no puede estar vacío");
        }
        String[] genres = {"Action", "Adventure", "RPG", "Strategy", "Simulation", "Racing", "Sports", "Casual"};
        for (String g : genres) {
            if (g.equals(genre)) {
                this.genre = genre;
                return;
            }
        }
        throw new IllegalArgumentException("El genero del videojuego no es valido");
    }
    
    public void setRating(float rating) {
        if (rating < 0.0 || rating > 10.0) {
            throw new IllegalArgumentException("La calificación del videojuego debe estar entre 0.0 y 10.0");
        }
        this.rating = rating;
    }

    public void setPrice(float price){
        if (price < 0) {
            throw new IllegalArgumentException("El precio del videojuego no puede ser negativo");
        }
        this.price = price;
    }

    public synchronized void reduceStock() {
        if (this.stock <= 0) {
            throw new IllegalStateException("Stock insuficiente");
        }
        this.stock -= 1;
    }

    public synchronized void addStock(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("La cantidad a agregar debe ser mayor que 0");
        }
        int newStock = (int) this.stock + (int) amount;
        this.stock = (int) newStock;
    }

    
}
