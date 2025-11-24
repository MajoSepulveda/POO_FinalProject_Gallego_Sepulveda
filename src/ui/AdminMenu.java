package src.ui;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import src.domain.Store;

public class AdminMenu {

    public static void Show(Store store, Scanner input) {
        if (input == null) input = new Scanner(System.in);
        if (store == null) {
            System.out.println("Tienda no inicializada.");
            return;
        }
        
        while (true) {
            System.out.println("\n--- ADMINISTRACIÓN DE LA TIENDA ---");
            System.out.println("1) Gestionar clientes.");
            System.out.println("2) Gestionar videojuegos.");
            System.out.println("3) Gestionar ventas.");
            System.out.println("4) Generar reporte."); 
            System.out.println("5) Volver.");
            System.out.print("Seleccione una opción: ");

            String option = input.nextLine().trim();
            try {
                switch (option) {
                    case "1": AdminCustomerMenu.Show(store, input);
                    case "2": AdminVideoGameMenu.Show(store, input);
                    case "3": AdminSaleMenu.Show(store, input);
                    case "4": generateIncomeReport(store, input);
                    case "5": return;
                    default: System.out.println("Opción inválida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void generateIncomeReport(Store store, Scanner input) {
        try {
            LocalDate[] period = readPeriod(input);
            String report = store.generateIncomeReport(period[0], period[1]);
            System.out.println(report);
        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha inválido. Use dd-MM-yyyy.");
        } catch (Exception e) {
            System.out.println("Error al generar reporte: " + e.getMessage());
        }
    }

    private static LocalDate[] readPeriod(Scanner input) {
        System.out.print("Fecha inicio (dd-MM-yyyy): ");
        String startStr = input.nextLine().trim();
        System.out.print("Fecha fin (dd-MM-yyyy): ");
        String endStr = input.nextLine().trim();
        LocalDate start = LocalDate.parse(startStr);
        LocalDate end = LocalDate.parse(endStr);
        return new LocalDate[]{start, end};
    }
}