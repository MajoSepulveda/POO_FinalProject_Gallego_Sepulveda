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
            console.cls();
            console.writeLine("----------- GESTIÓN DE VENTAS  -----------");
            console.writeLine("1) Eliminar venta.");
            console.writeLine("2) Lista de ventas.");
            console.writeLine("3) Volver.");

            int option = console.readInt("\nSeleccione una opción: ");
            switch (option) {
                case 1:
                    console.sleep(200);
                    removeSale(store, console);
                    break;
                case 2:
                    console.sleep(200);
                    listSales(store, console);
                    break;
                case 3:
                    console.writeLine("\nVolviendo...");
                    console.sleep(1000);
                    return;
                default:
                    console.writeLine("\nOpción inválida. Intente de nuevo.");
                    console.sleep(1000);
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
            console.cls();
            String id = console.readSaleId("Ingrese el ID de la venta a eliminar: ");
            Sale sale = store.findSaleById(id);

            if (sale == null) {
                console.writeLine("\nVenta no encontrada con ID: " + id);
                console.sleep(1500);
                return;
            }
        
            String confirmationMessage = String.format("\n¿Está seguro de que desea eliminar la Venta ID: %s? Esta acción es irreversible. Escriba 'SI' para confirmar: ", sale.getId());
            String confirmation = console.readString(confirmationMessage);

            if (confirmation.trim().equalsIgnoreCase("SI")) {
                store.removeSale(id);
                console.writeSucces("Venta eliminada: " + sale.getId());
            } else {
                console.writeLine("Operación de eliminación de venta cancelada.");
            }
        } catch (Exception e) {
            console.writeError("No se pudo eliminar la venta: " + e.getMessage());
        }
        console.sleep(2000);
    }

    /**
    * Retrieves the list of all sales from the store and displays their details to the console.
    * Uses the Sale object's displayObject method for formatting.
    * @param store The Store object to retrieve the sales list from.
    * @param console The ConsoleUI object for output operations.
    */
    private static void listSales(Store store, ConsoleUI console) {
        List<Sale> sales = store.getSales();
        console.cls();
        if (sales == null || sales.isEmpty()) {
            console.writeLine("No hay ventas registradas.");
            console.sleep(1500);
            return;
        }
        console.writeLine("--- VENTAS ---");
        for (Sale s : sales) {
            console.printDisplayableDetails(s);
        }
        console.readString("\nPresione ENTER para continuar.");
        console.sleep(200);
    }
}
