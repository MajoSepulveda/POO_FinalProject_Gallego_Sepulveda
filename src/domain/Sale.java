package src.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a sale transaction in the store. 
 * Implements Serializable for persistence and Displayable for console output.
 */
public class Sale implements Serializable, Displayable {
    private VideoGame videoGame;
    private Customer customer;
    private String id;
    private float amount;
    private LocalDate date;

    // Defined format for date consistency across the application.
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /**
     * Primary constructor for the Sale class.
     * @param videoGame The game sold in this transaction.
     * @param customer The customer who made the purchase.
     * @param id The unique identifier for the sale transaction.
     * @param amount The total transaction amount.
     * @param date The sale date in "dd-MM-yyyy" format..
     * @throws IllegalArgumentException if any required field is null/empty, amount is negative, or date is in the future.
     */
    public Sale(VideoGame videoGame, Customer customer, String id, float amount, String dateString) {
        setVideoGame(videoGame);
        setCustomer(customer);
        setId(id);
        setAmount(amount);
        setDate(dateString);
    }

    /**
     * @return The VideoGame object associated with this sale.
     */
    public VideoGame getVideoGame() {
        return videoGame;
    }

    /**
     * @return The Customer object associated with this sale.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @return The unique ID of the sale transaction.
     */
    public String getId() {
        return id;
    }

    /**
     * @return The total monetary amount of the sale.
     */
    public float getAmount() {
        return amount;
    }

    /**
     * @return The LocalDate object representing the sale date.
     */
    public LocalDate getDate() {
        return date;
    }

    // --- Setters ---

    /**
     * Sets the VideoGame for the sale, ensuring it is not null.
     * @param videoGame The game sold.
     * @throws IllegalArgumentException if the game is null.
     */
    private void setVideoGame(VideoGame videoGame){
        if (videoGame == null) throw new IllegalArgumentException("El videojuego no puede estar vacío.");
        this.videoGame = videoGame;
    }

    /**
     * Sets the Customer for the sale, ensuring it is not null.
     * @param customer The customer who made the purchase.
     * @throws IllegalArgumentException if the customer is null.
     */
    private void setCustomer(Customer customer){
        if (customer == null) throw new IllegalArgumentException("El cliente no puede estar vacío.");
        this.customer = customer;
    }

    /**
     * Sets the unique Sale ID. Delegates format and type validation to the Validator.
     * @param id The ID string.
     * @throws IllegalArgumentException if the ID is null, empty, or fails the validation rules defined in the Validator.
     */
    private void setId(String id){
        this.id = Validator.getValidSaleId(id);
    } 

    /**
     * Sets the sale amount, ensuring it is not negative.
     * @param amount The total transaction amount.
     * @throws IllegalArgumentException if the amount is negative.
     */
    private void setAmount(float amount){
        if (amount < 0.0f) throw new IllegalArgumentException("El monto no puede ser negativo.");
        this.amount = amount;
    }
    
    /**
     * Sets the sale date, performing DOUBLE VALIDATION:
     * 1. External (Validator): Checks format (dd-MM-yyyy) and logical date validity.
     * 2. Internal (Business Rule): Checks that the date is not in the future.
     * @param dateString The date as a string.
     * @throws IllegalArgumentException if format is invalid or date is in the future.
     */
    private void setDate(String dateString){
        LocalDate validatedDate = Validator.getValidDate(dateString);
        
        LocalDate now = LocalDate.now();
        if (validatedDate.isAfter(now)) {
            throw new IllegalArgumentException("Sale date cannot be in the future.");
        }
        this.date = validatedDate;
    }

    /**
     * Returns the sale date formatted as a String (dd/MM/yyyy).
     * @return The formatted date string.
     */
    private String getFormattedDate() {
        return date.format(DATE_FORMATTER);
    }

    /**
     * Provides a concise and formatted summary of the sale data for console output.
     * Implements the Displayable interface.
     * @return A formatted string summarizing the sale details.
     */
    @Override
    public String displayObject() {
        return String.format("Venta ID: %s | Cliente: %s | Juego: %s | Monto: $%.2f | Fecha: %s",
            id, customer.getName(), videoGame.getTitle(), amount, getFormattedDate());
    }
}
