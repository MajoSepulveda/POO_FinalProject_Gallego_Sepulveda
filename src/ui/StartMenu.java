package src.ui;

import src.domain.Store;

public class StartMenu {

    public static void show(Store store, ConsoleUI console) {
        while (true) {
            System.out.println("======================================");
            System.out.println("   BIENVENIDO/A A LA TIENDA DE JUEGOS");
            System.out.println("======================================");
            System.out.println("1) Entrar a la tienda");
            System.out.println("2) Salir");

            int option = console.readInt("Seleccione una opción: ");
            switch (option) {
                case 1:
                    StoreMenu.show(store, console);
                    break;
                case 2:
                    System.out.println("Gracias por visitar la tienda. Saliendo...");
                    return;
                default:
                    System.out.println("Opción inválida. Intenta de nuevo.");
            }
            System.out.println();
        }
    }
}
