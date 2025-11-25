package src.domain;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Validator {

    // Patrones de Expresión Regular para las nuevas reglas

    // 1. Cliente ID: Exactamente 10 dígitos (ni más, ni menos).
    // ^      -> Inicio de la cadena
    // \d{10} -> Exactamente 10 dígitos (0-9)
    // $      -> Fin de la cadena
    private static final String CLIENT_ID_REGEX = "^\\d{10}$"; 
    
    // 2. VideoGame ID: Una letra mayúscula seguida de exactamente 3 números.
    // ^      -> Inicio de la cadena
    // [A-Z]  -> Una sola letra mayúscula
    // \d{3}  -> Exactamente 3 dígitos (0-9)
    // $      -> Fin de la cadena
    private static final String GAME_ID_FORMAT_REGEX = "^[A-Z]\\d{3}$";
    
    // Lista de géneros válidos (para validaciones de negocio adicionales)
    // ... (Mantienes tu lista de VALID_GENRES) ...


    // ----------------------------------------------
    // 1. VALIDACIÓN DE CLIENTE ID (10 DÍGITOS)
    // ----------------------------------------------

    /**
     * Valida que el ID de cliente sea una cadena de exactamente 10 dígitos.
     * @param clientId El ID de cliente ingresado por el usuario.
     * @return true si el formato es válido.
     */
    public static boolean isValidClientId(String clientId) {
        if (clientId == null) {
            return false;
        }
        // Utiliza la expresión regular para verificar el patrón
        return Pattern.matches(CLIENT_ID_REGEX, clientId.trim());
    }


    // ----------------------------------------------
    // 2. VALIDACIÓN DE VIDEOJUEGO ID (G123, A456, etc.)
    // ----------------------------------------------

    /**
     * PASO 1: Valida el FORMATO general del ID de juego: 1 Letra Mayúscula + 3 Dígitos.
     * Esto verifica si cumple el patrón, pero no si la letra coincide con el género.
     * @param gameId El ID de juego a validar.
     * @return true si el formato es Letra+3Dígitos.
     */
    public static boolean isValidVideoGameIdFormat(String gameId) {
        if (gameId == null) {
            return false;
        }
        return Pattern.matches(GAME_ID_FORMAT_REGEX, gameId.trim());
    }
    
    /**
     * PASO 2: Valida la REGLA DE NEGOCIO: que la letra inicial del ID coincida
     * con la inicial del género.
     * @param gameId El ID de juego (ej: "S123").
     * @param genre La cadena del género (ej: "Sports").
     * @return true si la primera letra del ID coincide con la inicial del género (en mayúscula).
     */
    public static boolean doesGameIdMatchGenre(String gameId, String genre) {
        if (gameId == null || gameId.isEmpty() || genre == null || genre.isEmpty()) {
            return false;
        }

        // 1. Obtiene la primera letra del ID y la convierte a mayúscula
        char idInitial = gameId.trim().toUpperCase().charAt(0);
        
        // 2. Obtiene la primera letra del género y la convierte a mayúscula
        char genreInitial = genre.trim().toUpperCase().charAt(0);
        
        // 3. Compara si son iguales
        return idInitial == genreInitial;
    }
    
    // ----------------------------------------------
    // 3. VALIDACIONES DE RANGO (De la respuesta anterior)
    // ----------------------------------------------

    /**
     * Valida que un precio o saldo sea un valor positivo.
     */
    public static boolean isPositiveValue(float value) {
        return value >= 0.0f;
    }
    
    // ... (Otros métodos de rango y tipo) ...
}