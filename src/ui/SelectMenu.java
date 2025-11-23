package src.ui;

import java.util.Objects;
import java.util.Scanner;
import src.domain.Store;
import src.domain.VideoGame;
import src.domain.Customer;

public class SelectMenu {

    public static VideoGame SelectById(Store store, Scanner scanner) {
        if (store == null) {
            System.out.println("Tienda no inicializada.");
            return null;
        }
        if (scanner == null) scanner = new Scanner(System.in);

        System.out.print("Ingrese ID del videojuego para seleccionar (ENTER para volver): ");
        String id = scanner.nextLine().trim();
        if (id.isEmpty()) return null;

        VideoGame game = store.findVideoGameById(id);
        if (game == null) {
            System.out.println("No se encontró juego con ID: " + id);
            return null;
        }

        showSelectedMenu(game, store, scanner);
        return game;
    }

    public static void showSelectedMenu(VideoGame game, Store store, Scanner scanner) {
        if (game == null || store == null) return;
        if (scanner == null) scanner = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("Has seleccionado el videojuego: " + Objects.toString(game.getTitle(), ""));
            System.out.println("1) Comprar videojuego");
            System.out.println("2) Volver");
            System.out.print("Seleccione una opción: ");

            String option = scanner.nextLine().trim();
            switch (option) {
                case "1":
                    handleBuy(game, store, scanner);
                    return;
                case "2":
                    return;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
    }

        private static void handleBuy(VideoGame game, Store store, Scanner scanner) {
        if (game == null || store == null) return;
        if (scanner == null) scanner = new Scanner(System.in);

        if (game.getStock() <= 0) {
            System.out.println("No hay stock disponible para este juego.");
            return;
        }

        boolean finished = false;
        while (!finished) {
            System.out.print("Ingrese ID de cliente (entero) para realizar la compra (ENTER para cancelar): ");
            String custInput = scanner.nextLine().trim();
            if (custInput.isEmpty()) {
                System.out.println("Compra cancelada.");
                break;
            }

            Integer customerId;
            try {
                customerId = Integer.parseInt(custInput);
            } catch (NumberFormatException e) {
                System.out.println("ID de cliente inválido. Intente de nuevo o presione ENTER para cancelar.");
                continue;
            }

            Customer customer = store.findCustomerById(customerId);
            if (customer == null) {
                System.out.println("Cliente no encontrado con ID: " + customerId + ". Intente de nuevo o presione ENTER para cancelar.");
                continue;
            }

            float before = customer.getBalance();
            float price = game.getPrice();
            System.out.printf("Saldo cliente antes: $%.2f | Precio del juego: $%.2f%n", before, price);

            if (before < price) {
                System.out.println("Saldo insuficiente para realizar la compra. Ingrese otro ID o presione ENTER para cancelar.");
                continue;
            }

            System.out.print("Confirmar compra? (s/n): ");
            String confirm = scanner.nextLine().trim().toLowerCase();
            if (!confirm.equals("s") && !confirm.equals("y")) {
                System.out.println("Compra cancelada.");
                break;
            }

            String saleId;
            while (true) {
                System.out.print("Ingrese ID para la venta (ej. S001) (ENTER para cancelar): ");
                saleId = scanner.nextLine().trim();
                if (saleId.isEmpty()) {
                    System.out.println("Compra cancelada.");
                    finished = true;
                    break;
                }
                if (store.findSaleById(saleId) != null) {
                    System.out.println("Ese ID ya existe. Intente otro ID o presione ENTER para cancelar.");
                    continue;
                }
                try {
                    store.processSale(customer.getId(), game.getId(), saleId);
                    float after = customer.getBalance();
                    System.out.printf("Compra realizada con éxito. Saldo antes: $%.2f | Precio: $%.2f | Saldo ahora: $%.2f%n",
                            before, price, after);
                    System.out.println("Stock restante del juego: " + game.getStock());
                } catch (Exception e) {
                    System.out.println("No se pudo completar la compra: " + e.getMessage());
                }
                finished = true;
                break;
            }
        }
    }
}