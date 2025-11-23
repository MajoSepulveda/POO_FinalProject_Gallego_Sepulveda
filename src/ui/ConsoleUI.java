package src.ui;
import java.util.Scanner;

/**
 * Represents the command-line User Interface (UI) for the application.
 * This class handles safe data reading (int, float, String) and message output.
 * @author Majo Sepúlveda
 */
public class ConsoleUI {
    private final Scanner input;

    /**
     * Initializes the console interface with a specific data input source.
     * @param input The Scanner object used to read user input.
     */
    public ConsoleUI(Scanner input) {
        this.input = input;
    }

    /**
     * Prints a given message to the console followed by a newline character.
     * @param message The message string to be displayed to the user.
     */
    public void writeLine(String message) {
        System.out.println(message);
    }

    /**
     * Prompts the user for input and reads a valid integer.
     * The method repeats the prompt until a correctly formatted integer is entered, 
     * handling NumberFormatException errors.
     * @param message The prompt message to display to the user.
     * @return The validated integer value entered by the user.
     */
    public int readInt(String message) {
        System.out.print(message);
        while (true){
            String inputLine = input.nextLine();
        
            if (inputLine.isBlank()) { 
                System.err.println("Error: La entrada no puede estar vacía o contener solo espacios. Intente de nuevo.");
                System.out.print(message);
                continue;
            }

            try {
                return Integer.parseInt(inputLine.trim());
            } catch (NumberFormatException e) {
                System.err.println("Error: Ingrese un número entero válido.");
                System.out.print(message);
            }
        }
    }

    /**
     * Prompts the user for a decimal number (float).
     * @param message The prompt message to display to the user.
     * @return The validated number as a primitive float. 
     */
    public float readFloat(String message) {
        System.out.print(message);
    
        while (true) {
            String inputLine = input.nextLine(); 
        
            if (inputLine.isBlank()) {
                System.err.println("Error: La entrada no puede estar vacía o contener solo espacios. Intente de nuevo.");
                System.out.print(message);
                continue;
            }
        
            try {
                return Float.parseFloat(inputLine.trim()); 
            } catch (NumberFormatException e) {
                System.err.println("Error: Ingrese un número decimal válido.");
                System.out.print(message);
            }
        }
    }

    /**
     * Prompts the user for a decimal number (Float).
     * If the user enters a value, it returns as a Float object; otherwise, it returns null.
     * @param message The prompt message to display to the user.
     * @return The validated number as a Float object, or null if the input is blank.
     */
    public Float readFloatObject(String message) {
        System.out.print(message);
    
        while (true) {
            String inputLine = input.nextLine(); 
        
            if (inputLine.isBlank()) {
                return null;
            }
        
            try {
                return Float.parseFloat(inputLine.trim()); 
            } catch (NumberFormatException e) {
                System.err.println("Error: Ingrese un número decimal válido.");
                System.out.print(message);
            }
        }
    }
    
    /**
     * Prompts the user for a text string, ensuring the input is not empty or blank.
     * @param message The prompt message to display to the user.
     * @return The validated and trimmed String entered by the user.
     */
    public String readString(String message) {
        System.out.print(message);
        String result = null;
    
        while (result == null || result.isBlank()) {
            String inputLine = input.nextLine();
            result = inputLine.trim();         

            if (result.isBlank()) {
                System.err.println("Error: La entrada no puede estar vacía o contener solo espacios. Intente de nuevo.");
                System.out.print(message); 
            }
        }
        return result;
    }
}
