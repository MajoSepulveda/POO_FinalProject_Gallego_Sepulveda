package src.ui;
import java.util.Scanner;

/**
 * Represents the command-line User Interface (UI) for the application.
 * This class handles safe data reading (int, float, String) and message output.
 */
public class ConsoleUI {
    private final Scanner input;

    /**
     * Pauses the program execution for the specified duration in milliseconds.
     * @param milliseconds The duration to pause execution, measured in milliseconds 
     */
    public void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); 
        }
    }

    /**
     * Clears the console screen using standard ANSI escape sequences.
     * This method effectively simulates clearing the screen on modern terminal environments like PowerShell, Unix-based terminals (macOS/Linux), and most modern IDE consoles.
     */
    public void cls() {
        System.out.print("\033[H\033[2J"); 
        System.out.flush(); 
    }
    
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
     * Prompts the user for an int number (int).
     * @param message The prompt message to display to the user.
     * @return The validated int number entered by the user.
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
     * Prompts the user for a text string (String).
     * @param message The prompt message to display to the user.
     * @return The validated and trimmed text String entered by the user.
     */
    public String readString(String message) {
        System.out.print(message);
    
        while (true) {
            String inputLine = input.nextLine().trim();

            if (inputLine.isBlank()) {
                System.err.println("Error: La entrada no puede estar vacía o contener solo espacios. Intente de nuevo.");
                System.out.print(message);
                continue;
            }
            return inputLine; 
        }
    }
}
