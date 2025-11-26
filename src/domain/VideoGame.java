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
     * @throws IllegalArgumentException if any validation fails
     */
    public VideoGame(String title, String genre, float rating, float price, String id, int stock) {
        setStock(stock);
        setId(id);
        setTitle(title);
        setGenre(genre);
        setRating(rating);
        setPrice(price);

        validateIdGenreMatch();
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
     * Sets the stock quantity, ensuring it is non-negative.
     * @param stock The new stock amount.
     * @throws IllegalArgumentException if the stock is negative.
     */
    private void setStock(int stock){
        if (stock < 0) throw new IllegalArgumentException("El stock no puede ser negativo."); 
        this.stock = stock;
    }

    /**
     * Sets the unique Game ID. Delegates format and type validation to the Validator.
     * @param id The ID string.
     * @throws IllegalArgumentException if the ID is null, empty, or fails the format rules defined in the Validator.
     */
    private void setId(String id){
        this.id = Validator.getValidGameId(id);
    }

    /**
     * Sets the title of the game.
     * @param title The new title.
     * @throws IllegalArgumentException if the title is null, empty, or only whitespace.
     */
    public void setTitle(String title) {
        this.title = Validator.getValidString(title);
    }

    /**
     * Sets the genre of the game, validating against a predefined list.
     * @param genre The new genre.  
     * @throws IllegalArgumentException if the genre is not recognized or is empty.
     */
    public void setGenre(String genre) {
        this.genre = Validator.getValidGenre(genre);
    }
    
    /**
     * Sets the rating of the game.
     * @param rating The new rating value.
     * @throws IllegalArgumentException if the rating is outside the valid range (0.0 to 10.0).
     */
    public void setRating(float rating) {
        if (rating < 0.0f || rating > 10.0f) throw new IllegalArgumentException("Rating must be between 0.0 and 10.0.");
        this.rating = rating;
    }

    /**
     * Sets the price of the game.
     * @param price The new price value.
     * @throws IllegalArgumentException if the price is negative.
     */
    public void setPrice(float price) {
        if (price < 0.0f) throw new IllegalArgumentException("Price cannot be negative.");
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
     * Ensures the business rule is met: the first letter of the game ID 
     * must match the first letter of the genre.
     * @throws IllegalArgumentException if the ID and Genre do not match initials.
     */
    private void validateIdGenreMatch() {
        boolean matches = Validator.doesGameIdMatchGenre(this.id, this.genre);

        if (!matches) {
            char idInitial = this.id.trim().toUpperCase().charAt(0);
            char genreInitial = this.genre.trim().toUpperCase().charAt(0);

            throw new IllegalArgumentException(String.format("La validacíon del ID del videojuego falló. La incicial del ID ('%c') debe seri igual al la incial del género correspondiente ('%c').", idInitial, genreInitial));
        }
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
