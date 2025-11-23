package src.ui;

import java.util.Scanner;
import src.domain.Store;

public class StartMenu {

    public static void show(Store store, Scanner scanner) {
        if (scanner == null) scanner = new Scanner(System.in);
        while (true) {
            System.out.println("======================================");
            System.out.println("   BIENVENIDO/A A LA TIENDA DE JUEGOS");
            System.out.println("======================================");
            System.out.println("1) Entrar a la tienda");
            System.out.println("2) Salir");
            System.out.print("Seleccione una opción: ");

            String option = scanner.nextLine().trim();
            switch (option) {
                case "1":
                    StoreMenu.show(store, scanner);
                    break;
                case "2":
                    System.out.println("Gracias por visitar la tienda. Saliendo...");
                    return;
                default:
                    System.out.println("Opción inválida. Intenta de nuevo.");
            }
            System.out.println();
        }
    }
}
