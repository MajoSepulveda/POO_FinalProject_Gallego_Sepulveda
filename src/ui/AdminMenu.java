package src.ui;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import src.domain.Store;

public class AdminMenu {

    public static void Show(Store store, ConsoleUI console) {
        if (store == null) {
            console.writeLine("Tienda no inicializada.");
            return;
        }
        
        while (true) {
            console.writeLine("\n--- ADMINISTRACIÓN DE LA TIENDA ---");
            console.writeLine("1) Gestionar clientes.");
            console.writeLine("2) Gestionar videojuegos.");
            console.writeLine("3) Gestionar ventas.");
            console.writeLine("4) Generar reporte."); 
            console.writeLine("5) Volver.");

            int option = console.readInt("Seleccione una opción: ");
            try {
                switch (option) {
                    case 1:
                        AdminCustomerMenu.show(store, console);
                        break;
                    case 2:
                        AdminVideoGameMenu.show(store, console);
                        break;
                    case 3:
                        AdminSaleMenu.show(store, console);
                        break;
                    case 4:
                        generateIncomeReport(store, console);
                        break;
                    case 5:
                        console.writeLine("Volviendo...");
                        return;
                    default: System.out.println("Opción inválida.");
                }
            } catch (Exception e) {
                console.writeLine("Error: " + e.getMessage());
            }
        }
    }

    private static void generateIncomeReport(Store store, ConsoleUI console) {
        try {
            LocalDate[] period = readPeriod(console);
            String report = store.generateIncomeReport(period[0], period[1]);
            console.writeLine(report);
        } catch (DateTimeParseException e) {
            console.writeLine("Formato de fecha inválido. Use dd-MM-yyyy.");
        } catch (Exception e) {
            console.writeLine("Error al generar reporte: " + e.getMessage());
        }
    }

    private static LocalDate[] readPeriod(ConsoleUI console) {
        console.writeLine("Fecha inicio (dd-MM-yyyy): ");
        String startStr = console.readString("Fecha inicio (dd-MM-yyyy): ");
        console.writeLine("Fecha fin (dd-MM-yyyy): ");
        String endStr = console.readString("Fecha fin (dd-MM-yyyy): ");
        LocalDate start = LocalDate.parse(startStr);
        LocalDate end = LocalDate.parse(endStr);
        return new LocalDate[]{start, end};
    }
}