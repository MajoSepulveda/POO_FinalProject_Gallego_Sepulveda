package src.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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

    /**
     * Primary constructor for the Sale class.
     * @param videoGame The game sold in this transaction.
     * @param customer The customer who made the purchase.
     * @param id The unique identifier for the sale transaction.
     * @param amount The total transaction amount.
     * @param date The date of the sale.
     * @throws IllegalArgumentException if any required field is null/empty, amount is negative, or date is in the future.
     */
    public Sale(VideoGame videoGame, Customer customer, String id, float amount, LocalDate date) {
        if (videoGame == null) throw new IllegalArgumentException("El videojuego no puede estar vacío.");
        if (customer == null) throw new IllegalArgumentException("El cliente no puede estar vacío.");
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("El ID no puede estar vacío.");
        if (amount < 0) throw new IllegalArgumentException("El monto no puede ser negativo.");
        if (date == null) throw new IllegalArgumentException("La fecha no puede estar vacía.");

        LocalDate now = LocalDate.now();
        if (date.isAfter(now)) throw new IllegalArgumentException("La fecha de venta no puede ser futura");

        this.videoGame = videoGame;
        this.customer = customer;
        this.id = id.trim();
        this.amount = amount;
        this.date = date;
    }

    /**
     * Secondary constructor that accepts the date as a String and delegates to the primary constructor.
     * @param videoGame The game sold in this transaction.
     * @param customer The customer who made the purchase.
     * @param id The unique identifier for the sale transaction.
     * @param amount The total transaction amount.
     * @param dateString The sale date in "dd/MM/yyyy" format.
     */
    public Sale(VideoGame videoGame, Customer customer, String id, float amount, String dateString) {
        this(videoGame, customer, id, amount, parseDate(dateString));
    }

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
    
    /**
     * Parses a date string into a LocalDate object using the predefined format (dd/MM/yyyy).
     * @param dateString The string representation of the date.
     * @return A LocalDate object.
     * @throws IllegalArgumentException if the dateString is null or the format is invalid.
     */
    public static LocalDate parseDate(String dateString) {
        if (dateString == null) throw new IllegalArgumentException("La fecha no puede esta vacía");
        try {
            return LocalDate.parse(dateString, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido, use dd/MM/yyyy", e);
        }
    }

    /**
     * Returns the sale date formatted as a String (dd/MM/yyyy).
     * @return The formatted date string.
     */
    public String getFormattedDate() {
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
