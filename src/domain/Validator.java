package src.domain;

import java.util.List;

/**
 * Static class containing business rule validation logic for application data 
 * (VideoGames, Customers, etc.).
 * It follows the "Fail-Fast" pattern: if validation fails, it throws an IllegalArgumentException.
 */
public class Validator {

    // --- Format Rule Constants (Regex) ---
    
    /** Pattern for Customer ID: Exactly 10 digits. */
    private static final String CLIENT_ID_REGEX = "^\\d{10}$"; 
    
    /** Pattern for VideoGame ID: One uppercase letter followed by 3 digits (e.g., A001). */
    private static final String GAME_ID_FORMAT_REGEX = "^[A-Z]\\d{3}$";

    /** List of valid genres for the application. */
    private static final List<String> VALID_GENRES = List.of("Action", "Adventure", "RPG", "Strategy", "Simulation", "Racing", "Sports", "Casual");

    // --- IDENTIFIER VALIDATION METHODS ---

    /**
     * Validates and returns the Customer ID.
     * Ensures it's a 10-digit String and its numerical value is positive (> 0).
     * @param id The ID string to validate.
     * @return The validated and trimmed Customer ID (String).
     * @throws IllegalArgumentException if the ID is null, empty, not 10 digits, or zero.
     */
    public static String getValidCustomerId(String id) {
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("El id del cliente no puede estar vacío.");

        String cleanedId = id.trim();
        if (!cleanedId.matches(CLIENT_ID_REGEX)) throw new IllegalArgumentException("El id del cliente debe tener 10 dígitos.");
    
        if (Long.parseLong(cleanedId) <= 0) throw new IllegalArgumentException("El ID debe ser un número positivo mayor a 0.");

        return cleanedId; 
    }

    /**
     * Validates and returns the VideoGame ID.
     * Ensures it complies with the format: 1 uppercase letter + 3 digits (e.g., A001).
     * @param id The ID string to validate.
     * @return The validated and trimmed VideoGame ID (String).
     * @throws IllegalArgumentException if the ID is null, empty, or does not match the format.
     */
    public static String getValidGameId(String id) {
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("El id del  videojuego no puede estar vacío.");

        String cleanedId = id.trim();
        if (!cleanedId.matches(GAME_ID_FORMAT_REGEX)) throw new IllegalArgumentException("Formato del id incorrecto (debe ser 1 letra + 3 dígitos, ej. A001).");

        return cleanedId; 
    }
    
    // --- TEXT STRING VALIDATION METHODS ---

    /**
     * Validates that a text string (title, name, etc.) is not null, empty, or only whitespace.
     * * @param text The text string to validate.
     * @param fieldName The name of the field (e.g., "customer name") for clear error messages.
     * @return The validated and trimmed string.
     * @throws IllegalArgumentException if the string is invalid.
     */
    public static String getValidString(String text){
        if (text == null || text.trim().isEmpty()) throw new IllegalArgumentException("El campo no puede estar vacío.");

        return text.trim();
    }

    /**
     * Validates and returns a Genre that exists in the list of valid genres.
     * The method is case-insensitive on input but returns the normalized value from the list.
     * * @param genre The genre string to validate.
     * @return The normalized genre string (the exact value from VALID_GENRES list).
     * @throws IllegalArgumentException if the genre is not valid.
     */
    public static String getValidGenre(String genre) {
        if (genre == null || genre.trim().isEmpty()) {
            throw new IllegalArgumentException("El género no puede estar vacío.");
        }

        String cleanedGenre = genre.trim();
    
        for (String validGenre : VALID_GENRES) {
            if (cleanedGenre.equalsIgnoreCase(validGenre)) {
                return validGenre; 
            }
        }
        throw new IllegalArgumentException("El género '" + cleanedGenre + "' no es un género válido. " + "Los géneros aceptados son: " + VALID_GENRES);
    }

    // --- NUMERICAL VALUE VALIDATION (FLOAT) ---

    /**
     * Validates and returns a Rating value.
     * Ensures the value is numeric and is within the range of 0.0 to 10.0.
     * * @param ratingString The rating string to validate.
     * @return The validated float value.
     * @throws IllegalArgumentException if it's not a number or is out of range.
     */
    public static float getValidRating(String ratingString) {
        if (ratingString == null || ratingString.trim().isEmpty()) throw new IllegalArgumentException("La calificación no puede estar vacía.");

        try {
            float rating = Float.parseFloat(ratingString.trim());
            if (rating < 0.0f || rating > 10.0f) throw new IllegalArgumentException("La calificación debe estar entre 0.0 y 10.0.");
            return rating;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La calificación debe ser un número decimal.", e);
        }
    }

    /**
     * Validates and returns a non-negative monetary value (price or balance).
     * @param floatString The price/balance string to validate.
     * @param fieldName The name of the field (e.g., "price") for the error message.
     * @return The validated float value (>= 0.0f).
     * @throws IllegalArgumentException if it's not a number or is negative.
     */
    public static float getValidPositiveFloat(String floatString) {
        if (floatString == null || floatString.trim().isEmpty()) throw new IllegalArgumentException("El campo no puede estar vacío.");
        try {
            float value = Float.parseFloat(floatString.trim());
            if (value < 0.0f) throw new IllegalArgumentException("El valor no puede ser negativo.");
            return value;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El campo debe ser un número decimal válido.", e);
        }
    }
    
    // --- NUMERICAL VALUE VALIDATION (INT) ---

    /**
     * Validates and returns the VideoGame stock quantity.
     * Ensures the value is an integer and is not negative.
     * @param stockString The stock string to validate.
     * @return The validated integer value (>= 0).
     * @throws IllegalArgumentException if it's not an integer or is negative.
     */
    public static int getValidStock(String stockString) {
        if (stockString == null || stockString.trim().isEmpty()) throw new IllegalArgumentException("El stock no puede estar vacío.");
        try {
            int stock = Integer.parseInt(stockString.trim());
            if (stock < 0) throw new IllegalArgumentException("El stock no puede ser negativo.");
            
            return stock;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El stock debe ser un número entero válido.", e);
        }   
    }

    // --- CROSS-FIELD BUSINESS RULES ---
    
    /**
     * Validates the business rule: the initial letter of the Game ID must match 
     * the initial letter of the Genre.
     * * @param id The validated Game ID (e.g., "S123").
     * @param genre The validated Genre string (e.g., "Sports").
     * @return true if the first letter matches (case-insensitive).
     */
    public static boolean doesGameIdMatchGenre(String id, String genre) {
        if (id == null || id.isEmpty() || genre == null || genre.isEmpty()) return false;

        // Gets the first letter in upper case
        char idInitial = id.trim().toUpperCase().charAt(0);
        // Gets the first letter of the genre in upper case.
        char genreInitial = genre.trim().toUpperCase().charAt(0);

        return idInitial == genreInitial;
    }
}
