package src.domain;
import java.io.Serializable;

public class VideoGame implements Serializable, Displayable {
    private String id;
    private String title;
    private String genre;
    private float rating;
    private float price;
    private int stock;

    public VideoGame(String title, String genre, float rating, float price, String id, int stock) {
        if (stock < 0) throw new IllegalArgumentException("El stock del videojuego no puede ser negativo.");
        if (id == null || id.trim().isBlank()) throw new IllegalArgumentException("El ID del videojuego no puede estar vacio.");

        this.stock = stock;
        this.id = id.trim();
        setTitle(title);
        setGenre(genre);
        setRating(rating);
        setPrice(price);
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public float getRating() {
        return rating;
    }

    public float getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isBlank()) throw new IllegalArgumentException("El título del videojuego no puede estar vacio.");
        this.title = title.trim();
    }

    public void setGenre(String genre) {
        if (genre == null || genre.trim().isBlank()) throw new IllegalArgumentException("El genero no puede estar vacío.");
        
        String[] genres = {"Action", "Adventure", "RPG", "Strategy", "Simulation", "Racing", "Sports", "Casual"};

        for (String g : genres) {
            if (g.equals(genre.trim())) {
                this.genre = genre.trim();
                return;
            }
        }
        throw new IllegalArgumentException("El genero del videojuego no es valido.");
    }
    
    public void setRating(float rating) {
        if (rating < 0.0 || rating > 10.0) throw new IllegalArgumentException("La calificación del videojuego debe estar entre 0.0 y 10.0.");
        this.rating = rating;
    }

    public void setPrice(float price) {
        if (price < 0) throw new IllegalArgumentException("El precio del videojuego no puede ser negativo.");
        this.price = price;
    }

    public synchronized void reduceStock() {
        if (this.stock <= 0) throw new IllegalStateException("Stock insuficiente.");
        this.stock -= 1;
    }
    
    public synchronized void addStock(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("La cantidad de stock a agregar no puede ser negativa o cero.");
        this.stock += amount;
    }

    @Override
    public String displayObject() {
        return String.format("%-6s  %-60s  %-12s  %-6s  %-7s  %-5s%n",
                    id, title, genre, rating, price, stock);
    }
}
