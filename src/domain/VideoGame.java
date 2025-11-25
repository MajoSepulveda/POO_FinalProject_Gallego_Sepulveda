package src.domain;
import java.io.Serializable;

/**
 * Represents a video game entity in the store's catalog.
 * Implements Serializable for persistence and Displayable for console output.
 */
public class VideoGame implements Serializable, Displayable {
    private String id;
    private String title;
    private String genre;
    private float rating;
    private float price;
    private int stock;

    /**
     * Constructor for the VideoGame class.
     * @param title The title of the game.
     * @param genre The genre of the game (must be one from the predefined list).
     * @param rating The user rating (must be between 0.0 and 10.0).
     * @param price The selling price of the game.
     * @param id The unique identifier for the game (e.g., A001, R123).
     * @param stock The current quantity in stock.
     * @throws IllegalArgumentException if stock is negative, ID is empty, or any setter validation fails.
     */
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

    /**
     * @return The unique ID of the game.
     */
    public String getId() {
        return id;
    }

    /**
     * @return The title of the game.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return The genre of the game.
     */
    public String getGenre() {
        return genre;
    }

    /**
     * @return The game's rating (0.0 to 10.0).
     */
    public float getRating() {
        return rating;
    }

    /**
     * @return The selling price of the game.
     */
    public float getPrice() {
        return price;
    }

    /**
     * @return The current quantity of the game in stock.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets the title of the game.
     * @param title The new title.
     * @throws IllegalArgumentException if the title is null or empty.
     */
    public void setTitle(String title) {
        if (title == null || title.trim().isBlank()) throw new IllegalArgumentException("El título del videojuego no puede estar vacio.");
        this.title = title.trim();
    }

    /**
     * Sets the genre of the game, validating against a predefined list.
     * @param genre The new genre.
     * @throws IllegalArgumentException if the genre is null, empty, or not found in the valid list.
     */
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
    
    /**
     * Sets the rating of the game.
     * @param rating The new rating value.
     * @throws IllegalArgumentException if the rating is outside the range 0.0 to 10.0.
     */
    public void setRating(float rating) {
        if (rating < 0.0 || rating > 10.0) throw new IllegalArgumentException("La calificación del videojuego debe estar entre 0.0 y 10.0.");
        this.rating = rating;
    }

    /**
     * Sets the price of the game.
     * @param price The new price value.
     * @throws IllegalArgumentException if the price is negative.
     */
    public void setPrice(float price) {
        if (price < 0) throw new IllegalArgumentException("El precio del videojuego no puede ser negativo.");
        this.price = price;
    }

    /**
     * Reduces the stock count by one unit.
     * Uses 'synchronized' to ensure thread-safe operations in a multi-threaded environment.
     * @throws IllegalStateException if the stock is already zero or negative.
     */
    public synchronized void reduceStock() {
        if (this.stock <= 0) throw new IllegalStateException("Stock insuficiente.");
        this.stock -= 1;
    }
    
    /**
     * Increases the stock count by a specified amount.
     * Uses 'synchronized' to ensure thread-safe operations.
     * @param amount The quantity to add to the stock.
     * @throws IllegalArgumentException if the amount to add is non-positive.
     */
    public synchronized void addStock(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("La cantidad de stock a agregar no puede ser negativa o cero.");
        this.stock += amount;
    }

    /**
     * Provides a detailed and formatted summary of the video game for console output.
     * Implements the Displayable interface.
     * @return A formatted string with game details.
     */
    @Override
    public String displayObject() {
        return String.format("%-6s  %-60s  %-12s  %-6s  %-7s  %-5s%n",
                    id, title, genre, rating, price, stock);
    }
}
