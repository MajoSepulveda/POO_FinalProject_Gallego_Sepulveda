package src.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Sale implements Serializable {
    private VideoGame videoGame;
    private Customer customer;
    private String id;
    private float amount;
    private LocalDate date;

    public Sale(VideoGame videoGame, Customer customer, String id, float amount, LocalDate date) {
        if (videoGame == null) {
            throw new IllegalArgumentException("El videojuego no puede estar vacío");
        }
        if (customer == null) {
            throw new IllegalArgumentException("El cliente no puede estar vacío");
        }
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID no puede estar vacío");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("El monto no puede ser menor a cero");
        }
        if (date == null) {
            throw new IllegalArgumentException("La fecha no puede estar vacía");
        }

        LocalDate now = LocalDate.now();
        if (date.isAfter(now)) {
            throw new IllegalArgumentException("La fecha de venta no puede ser futura");
        }

        this.videoGame = videoGame;
        this.customer = customer;
        this.id = id.trim();
        this.amount = amount;
        this.date = date;
    }

    public Sale(VideoGame videoGame, Customer customer, String id, float amount, String dateString) {
        this(videoGame, customer, id, amount, parseDate(dateString));
    }

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    public VideoGame getVideoGame() {
        return videoGame;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getId() {
        return id;
    }

    public float getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public static LocalDate parseDate(String dateString) {
        if (dateString == null) throw new IllegalArgumentException("La fecha no puede esta vacía");
        try {
            return LocalDate.parse(dateString, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido, use dd/MM/yyyy", e);
        }
    }

    public String getFormattedDate() {
        return date.format(DATE_FORMATTER);
    }
}
