package src.ui;

import src.domain.*;
import java.util.List;

/**
* Handles the administrative menu for managing sales within the store system.
* Provides options to remove sales and list all registered sales.
*/
public class AdminSaleMenu {

    /**
    * Displays the main Sales Management menu loop and handles user navigation.
    * @param store The main Store object containing sales data.
    * @param console The ConsoleUI object used for user input and output.
    */
    public static void show(Store store, ConsoleUI console){

        while (true) {
            console.writeLine("----------- GESTIÓN DE CLIENTES  -----------");
            console.writeLine("1) Eliminar venta.");
            console.writeLine("2) Lista de ventas.");
            console.writeLine("3) Volver.");

            int option = console.readInt("Seleccione una opción; ");
            switch (option) {
                case 1:
                    removeSale(store, console);
                    break;
                case 2:
                    listSales(store, console);
                    break;
                case 3:
                    console.writeLine("Volviendo...");
                    console.sleep(1000);
                    return;
                default:
                    console.writeLine("Opción inválida. Intente de nuevo.");
            }
        }
    }

    /**
    * Prompts the user for a Sale ID and attempts to remove the corresponding sale from the store.
    * Handles exceptions if the ID is invalid or the sale is not found.
    * @param store The Store object from which the sale will be removed.
    * @param console The ConsoleUI object for input/output operations.
    */
    private static void removeSale(Store store, ConsoleUI console) {
        try {
            String id = console.readint("ID de la venta a eliminar: ");
            store.removeSale(id);
            console.writeLine("Venta eliminada: " + id);
        } catch (Exception e) {
            console.writeLine("No se pudo eliminar la venta: " + e.getMessage());
        }
    }

    /**
    * Retrieves the list of all sales from the store and displays their details to the console.
    * Uses the Sale object's displayObject method for formatting.
    * @param store The Store object to retrieve the sales list from.
    * @param console The ConsoleUI object for output operations.
    */
    private static void listSales(Store store, ConsoleUI console) {
        List<Sale> sales = store.getSales();
        if (sales == null || sales.isEmpty()) {
            console.writeLine("No hay ventas registradas.");
            return;
        }
        console.writeLine("--- VENTAS ---");
        for (Sale s : sales) {
            console.printDisplayableDetails(s);
        }
    }
    
}
