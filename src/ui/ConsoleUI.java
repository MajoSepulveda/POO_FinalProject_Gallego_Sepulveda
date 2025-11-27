package src.ui;

import java.time.LocalDate;
import java.util.Scanner;
import src.domain.*;

/**
 * Represents the command-line User Interface (UI) for the application.
 * This class handles safe data reading (int, float, String) and message output.
 */
public class ConsoleUI {
    private final Scanner input;

    // Defines the ANSI escape code sequence to initiate font color in the console output.
    private static final String ANSI_RED = "\u001b[31m";
    public static final String ANSI_GREEN = "\u001b[32m";

    //Defines the ANSI escape code sequence to RESET the font color to the console's default setting.
    private static final String ANSI_RESET = "\u001b[0m";

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

    public void writePrompt(String message) {
        System.out.print(message);
    }

    /**
     * Prints a formatted string to the console, similar to System.out.printf, but using the defined writeLine method for consistency.
     * @param format The format string (e.g., "%-15s %5.2f").
     * @param args The arguments referenced by the format specifiers.
     */
    public void writeStringf(String format, Object... args) {
        String formattedMessage = String.format(format, args);
        this.writeLine(formattedMessage);
    }

    /**
     * Writes an error message to the standard error stream (System.err).
     * It formats the message with a consistent prefix for clarity.
     * @param message The error message to display.
     */
    public void writeError(String message) {
        String coloredMessage = ANSI_RED + "[ERROR] " + message + ANSI_RESET;
        this.writeLine(coloredMessage);
    }   

    public void writeSucces(String message) {
        String coloredMessage = ANSI_GREEN +  message + ANSI_RESET;
        this.writeLine(coloredMessage);
    } 

    /**
     * Reads a line of text from the console, prompts the user, and trims whitespace.
     * Returns an empty string ("") if the user only presses Enter or enters whitespace.
     * This method ensures the input is never null.
     * @param message The prompt message to display.
     * @return The trimmed input string.
     */
    public String readString(String message) {
        this.writePrompt(message);
        String inputLine = input.nextLine().trim();
        return inputLine; 
    }

    /**
     * Prompts the user for an int number (int).
     * @param message The prompt message to display to the user.
     * @return The validated int number entered by the user.
     */
    public int readInt(String message) {
        while (true){
            String inputLine = this.readString(message); 
        
            if (inputLine.isBlank()) { 
                this.writeError("La entrada no puede estar vacía o contener solo espacios. Intente de nuevo.");
                this.sleep(500);
                continue;
            }
            try {
                return Integer.parseInt(inputLine.trim());
            } catch (NumberFormatException e) {
                this.writeError("Ingrese un número entero válido.");
                this.sleep(500);
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
        while (true) {
            String inputLine = this.readString(message); 
        
            if (inputLine.isBlank()) return null;

            try {
                return Float.parseFloat(inputLine.trim()); 
            } catch (NumberFormatException e) {
                this.writeError("Ingrese un número decimal válido.");
                this.sleep(500); 
            }
        }
    }

    /**
     * Prints the displayable details of an object to the console.
     * It uses the displayObject() method defined by the Displayable interface to retrieve the formatted string, and then outputs it using the central output method (writeLine).
     * @param object The object implementing the Displayable interface to be displayed.
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
                this.writeError(e.getMessage());
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
                this.writeError(e.getMessage());
                this.sleep(500); 
            }
        }
    }

    /**
     * Prompts the user repeatedly for a valid Sale ID.
     * Ensures the input is a non-empty string that represents a positive integer.
     * @param message The prompt message to display to the user.
     * @return The validated Sale ID (String).
     */
    public String readSaleId(String message){
        while (true) {
            String inputId = this.readString(message);

            try {
                return Validator.getValidSaleId(inputId);
            } catch (IllegalArgumentException e) {
                this.writeError("Error: " + e.getMessage());
                this.sleep(500); 
            }
        }
    }

    /**
     * Prompts the user repeatedly for a required text string (Name, Title, etc.).
     * @param message The prompt message to display.
     * @return The validated non-empty string.
     */
    public String readValidString(String message) {    
        while (true) {
            String inputLine = this.readString(message);

            try {
                return Validator.getValidString(inputLine);
            } catch (IllegalArgumentException e) {
                this.writeError(e.getMessage());
                this.sleep(500); 
            }
        }
    }

    /**
     * Prompts the user repeatedly for a required name string.
     * @param message The prompt message to display.
     * @return The validated non-empty string.
     */
    public String readValidName(String message) {    
        while (true) {
            String inputLine = this.readString(message);

            try {
                return Validator.getValidName(inputLine);
            } catch (IllegalArgumentException e) {
                this.writeError(e.getMessage());
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
            String inputLine = this.readString(message);

            try {
                return Validator.getValidGenre(inputLine);
            } catch (IllegalArgumentException e) {
                this.writeError(e.getMessage());
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
            String inputLine = this.readString(message);

            try {
                return Validator.getValidRating(inputLine);
            } catch (IllegalArgumentException e) {
                this.writeError(e.getMessage());
                this.sleep(500); 
            }
        }
    }

    /**
     * Prompts the user repeatedly for a valid positive float (Price or Balance).
     * @param message The prompt message to display.
     * @return The validated non-negative float value.
     */
    public float readPositiveFloat(String message) {
        while (true) {
            String inputLine = this.readString(message);

            try {
                return Validator.getValidPositiveFloat(inputLine);
            } catch (IllegalArgumentException e) {
                this.writeError(e.getMessage());
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
                this.writeError(e.getMessage());
                this.sleep(500); 
            }
        }
    }

    /**
     * Prompts the user repeatedly for a valid date in DD-MM-YYYY format.
     * @param prompt The message to display to the user.
     * @return The validated LocalDate object.
     */
    public LocalDate readValidDate(String prompt) {
        while (true) {
            String inputString = this.readString(prompt);

            try {
                return Validator.getValidDate(inputString);
            } catch (IllegalArgumentException e) {
                this.writeError(e.getMessage());
                this.sleep(500); 
            }
        }
    }
}
