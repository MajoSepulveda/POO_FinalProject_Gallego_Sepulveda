package src.ui;

import src.domain.*;
import java.util.List;

public class AdminSaleMenu {

    public static void show(Store store, ConsoleUI console){

        while (true) {
            System.out.println("----------- GESTIÓN DE CLIENTES  -----------");
            System.out.println("1) Eliminar venta.");
            System.out.println("2) Lista de ventas.");
            System.out.println("3) Volver al menú principal.");

            int option = console.readInt("Seleccione una opción; ");
            switch (option) {
                case 1:
                    removeSale(store, console);
                    break;
                case 2:
                    listSales(store, console);
                    break;
                case 3:
                    console.writeLine("Volviendo al menú principal...");
                    return;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
    }

    private static void removeSale(Store store, ConsoleUI console) {
        try {
            String id = console.readString("ID de la venta a eliminar: ");
            store.removeSale(id);
            console.writeLine("Venta eliminada: " + id);
        } catch (Exception e) {
            console.writeLine("No se pudo eliminar la venta: " + e.getMessage());
        }
    }

    private static void listSales(Store store, ConsoleUI console) {
        List<Sale> sales = store.getSales();
        if (sales == null || sales.isEmpty()) {
            console.writeLine("No hay ventas registradas.");
            return;
        }
        console.writeLine("--- VENTAS ---");
        for (Sale s : sales) {
            s.displayObject();
        }
    }
    
}
