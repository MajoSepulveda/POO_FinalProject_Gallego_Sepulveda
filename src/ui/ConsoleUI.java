package src.ui;
import java.util.Scanner;
import src.domain.*;

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
     * Prints a formatted string to the console, similar to System.out.printf, 
     * but using the defined writeLine method for consistency (and automatic newline).
     * @param format The format string (e.g., "%-15s %5.2f").
     * @param args The arguments referenced by the format specifiers.
     */
    public void writeStringf(String format, Object... args) {
        String formattedMessage = String.format(format, args);
        this.writeLine(formattedMessage);
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
        
            if (inputLine.isBlank()) return null;
            try {
                return Float.parseFloat(inputLine.trim()); 
            } catch (NumberFormatException e) {
                System.err.println("Error: Ingrese un número decimal válido.");
                System.out.print(message);
            }
        }
    }
    
    /**
     * Reads a line of text from the console, prompts the user, and trims whitespace.
     * Returns an empty string ("") if the user only presses Enter or enters whitespace.
     * This method ensures the input is never null.
     * @param message The prompt message to display.
     * @return The trimmed input string.
     */
    public String readString(String message) {
        System.out.print(message);
        String inputLine = input.nextLine().trim();
        return inputLine; 
    }

    /**
     * Prompts the user repeatedly for a valid Customer ID (10 digits, positive value).
     * @param message The prompt message to display.
     * @return The validated Customer ID (String).
     */
    public void printDisplayableDetails(Displayable object) {
        this.writeLine(object.displayObject());
    }

    /**
     * Prompts the user repeatedly for a valid Customer ID (10 digits, positive value).
     * @param message The prompt message to display.
     * @return The validated Customer ID (String).
     */
    public String readCustomerID(String message) {
        while (true) {
            String inputId = this.readString(message);

            try {
                return Validator.getValidCustomerId(inputId);
            } catch (IllegalArgumentException e) {
                System.err.println("Error: " + e.getMessage());
                this.sleep(500); 
            }
        }
    }

    /**
     * Prompts the user repeatedly for a valid VideoGame ID (1 letter + 3 digits).
     * @param message The prompt message to display.
     * @return The validated VideoGame ID (String).
     */
    public String readVideoGameID(String message) {
        while (true) {
            String inputId = this.readString(message);

            try {
                return Validator.getValidGameId(inputId);
            } catch (IllegalArgumentException e) {
                System.err.println("Error: " + e.getMessage());
                this.sleep(500); 
            }
        }
    }

    /**
     * Prompts the user repeatedly for a required text string (Name, Title, etc.).
     * NOTE: This method requires the field name to call the Validator correctly.
     * @param message The prompt message to display.
     * @param fieldName The name of the field (e.g., "customer name") for the Validator.
     * @return The validated non-empty string.
     */
    public String readValidString(String message) {    
        while (true) {
            String inputText = this.readString(message);

            try {
                return Validator.getValidString(inputText);
            } catch (IllegalArgumentException e) {
                System.err.println("Validation Error: " + e.getMessage());
                this.sleep(500); 
            }
        }
    }

    /**
     * Prompts the user repeatedly for a valid Genre from the predefined list.
     * @param message The prompt message to display.
     * @return The validated and normalized Genre string.
     */
    public String readGenre(String message) {    
        while (true) {
            String inputText = this.readString(message);

            try {
                return Validator.getValidGenre(inputText);
            } catch (IllegalArgumentException e) {
                System.err.println("Validation Error: " + e.getMessage());
                this.sleep(500); 
            }
        }
    }

    /**
     * Prompts the user repeatedly for a valid Rating (float between 0.0 and 10.0).
     * @param message The prompt message to display.
     * @return The validated Rating (float).
     */
    public float readRating(String message) {
        while (true) {
            String inputString = this.readString(message);

            try {
                return Validator.getValidRating(inputString);
            } catch (IllegalArgumentException e) {
                System.err.println("Validation Error: " + e.getMessage());
                this.sleep(500); 
            }
        }
    }

    /**
     * Prompts the user repeatedly for a valid positive float (Price or Balance).
     * NOTE: This method requires the field name to call the Validator correctly.
     * @param message The prompt message to display.
     * @param fieldName The name of the field (e.g., "price") for the Validator.
     * @return The validated non-negative float value.
     */
    public float readPositiveFloat(String message) {
        while (true) {
            String inputString = this.readString(message);

            try {
                return Validator.getValidPositiveFloat(inputString);
            } catch (IllegalArgumentException e) {
                System.err.println("Validation Error: " + e.getMessage());
                this.sleep(500); 
            }
        }
    }

    /**
     * Prompts the user repeatedly for a valid non-negative integer for Stock.
     * @param message The prompt message to display.
     * @return The validated Stock quantity (int).
     */    
    public int readStock(String message) {
        while (true) {
            String inputString = this.readString(message);

            try {
                return Validator.getValidStock(inputString);
            } catch (IllegalArgumentException e) {
                System.err.println("Validation Error: " + e.getMessage());
                this.sleep(500); 
            }
        }
    }
}
