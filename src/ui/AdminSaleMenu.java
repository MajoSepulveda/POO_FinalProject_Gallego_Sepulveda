package src.ui;

import src.domain.*;
import java.util.List;
import java.util.Scanner;

public class AdminSaleMenu {

    public static void show(Store store, Scanner input){

        if (input == null) input = new Scanner(System.in);
        while (true) {
            System.out.println("----------- GESTIÓN DE CLIENTES  -----------");
            System.out.println("1) Eliminar venta.");
            System.out.println("2) Lista de ventas.");
            System.out.println("3) Volver al menú principal.");
            System.out.print("Seleccione una opción: ");

            String option = input.nextLine().trim();
            switch (option) {
                case "1":
                    removeSale(store, input);
                    break;
                case "2":
                    listSales(store);
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
    }

    private static void removeSale(Store store, Scanner input) {
        try {
            System.out.print("ID de la venta a eliminar: ");
            String id = input.nextLine().trim();
            store.removeSale(id);
            System.out.println("Venta eliminada: " + id);
        } catch (Exception e) {
            System.out.println("No se pudo eliminar la venta: " + e.getMessage());
        }
    }

    private static void listSales(Store store) {
        List<Sale> sales = store.getSales();
        if (sales == null || sales.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }
        System.out.println("--- VENTAS ---");
        for (Sale s : sales) {
            System.out.printf("ID: %s | Fecha: %s | Monto: $%.2f | ClienteID: %d | Juego: %s (%s)%n",
                    s.getId(), s.getDate(), s.getAmount(), s.getCustomer().getId(),
                    s.getVideoGame().getTitle(), s.getVideoGame().getId());
        }
    }
    
}
