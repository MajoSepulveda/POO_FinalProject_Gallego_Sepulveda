package src.ui;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import src.domain.*;

/**
* Handles the main administrative menu for the store application.
* Provides access to sub-menus for managing customers, video games, sales, and generating reports.
*/
public class AdminMenu {
    /**
    * Displays the main administration menu loop and handles navigation to sub-menus.
    * @param store The main Store object instance.
    * @param console The ConsoleUI object used for user input and output.
    */
    public static void Show(Store store, ConsoleUI console) {
        while (true) {
            console.cls();
            console.writeLine("--- ADMINISTRACIÓN DE LA TIENDA ---");
            console.writeLine("1) Gestionar clientes.");
            console.writeLine("2) Gestionar videojuegos.");
            console.writeLine("3) Gestionar ventas.");
            console.writeLine("4) Generar reporte."); 
            console.writeLine("5) Volver al menú principal.");

            int option = console.readInt("\nSeleccione una opción: ");
            try {
                switch (option) {
                    case 1:
                        // Navigates to the Customer Management menu
                        console.sleep(200);
                        AdminCustomerMenu.show(store, console);
                        console.sleep(200);
                        break;
                    case 2:
                        // Navigates to the Video Game Management menu
                        console.sleep(200);
                        AdminVideoGameMenu.show(store, console);
                        console.sleep(200);
                        break;
                    case 3:
                        // Navigates to the Sales Management menu
                        console.sleep(200);
                        AdminSaleMenu.show(store, console);
                        console.sleep(200);
                        break;
                    case 4:
                        // Generates a financial report for a specific period
                        console.sleep(200);
                        generateIncomeReport(store, console);
                        console.sleep(200);
                        break;
                    case 5:
                        console.writeLine("\nVolviendo...");
                        console.sleep(1000);
                        return;
                    default: 
                        System.out.println("\nOpción inválida. Intente de nuevo.");
                        console.sleep(1000);
                }
            } catch (Exception e) {
                console.writeError(e.getMessage());
            }
        }
    }

    /**
    * Prompts the user for a start and end date and generates an report from the store data.
    * Handles exceptions related to date parsing and report generation.
    * @param store The Store object to generate the report from.
    * @param console The ConsoleUI object for input/output operations.
    */
    private static void generateIncomeReport(Store store, ConsoleUI console) {
        console.cls();
        try {
            LocalDate[] period = readPeriod(console);
            String report = store.generateIncomeReport(period[0], period[1]);
            console.writeLine(report);
        } catch (DateTimeParseException e) {
            console.writeError("Formato de fecha inválido. Use dd-MM-yyyy.");
        } catch (IllegalArgumentException e){
            console.writeError("Fecha incorrecta: " + e.getMessage());
        } catch (Exception e) {
            console.writeError("Reporte no generado: " + e.getMessage());
        }
    }

    /**
    * Prompts the user to input the start and end dates for a period in dd-MM-yyyy format.
    * Note: This method may throw a DateTimeParseException if the input format is incorrect.
    * @param console The ConsoleUI object for input/output operations.
    * @return A two-element array where element 0 is the start date (LocalDate) and element 1 is the end date (LocalDate).
    */
    private static LocalDate[] readPeriod(ConsoleUI console) {
        String startStr = console.readValidString("Fecha inicio (dd-MM-yyyy): ");
        String endStr = console.readValidString("Fecha fin (dd-MM-yyyy): ");
        LocalDate start = Validator.getValidDate(startStr);
        LocalDate end = Validator.getValidDate(endStr);
        return new LocalDate[]{start, end};
    }
}