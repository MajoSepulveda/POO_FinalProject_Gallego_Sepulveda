package src.ui;

import src.domain.Store;
import src.domain.VideoGame;
import src.domain.Customer;

public class SelectMenu {

    public static VideoGame SelectById(Store store, ConsoleUI console) {
        if (store == null) {
            console.writeLine("Tienda no inicializada.");
            return null;
        }

        String id = console.readString("Ingrese el ID del videojuego para seleccionar (ENTER para volver)");
        if (id.isEmpty()) return null;

        VideoGame game = store.findVideoGameById(id);
        if (game == null) {
            console.writeLine("No se encontró juego con ID: " + id);
            return null;
        }

        showSelectedMenu(game, store, console);
        return game;
    }

    public static void showSelectedMenu(VideoGame game, Store store, ConsoleUI console) {
        if (game == null || store == null) return;

        while (true) {
            console.writeLine("Has seleccionado el videojuego: " + game.displayObject());
            console.writeLine("1) Comprar videojuego");
            console.writeLine("2) Volver");

            int option = console.readInt("selecione una opción: ");
            switch (option) {
                case 1:
                    handleBuy(game, store, console);
                    return;
                case 2:
                    return;
                default:
                    console.writeLine("Opción inválida. Intente de nuevo.");
            }
        }
    }

        private static void handleBuy(VideoGame game, Store store, ConsoleUI console) {
        if (game == null || store == null) return;

        if (game.getStock() <= 0) {
            console.writeLine("No hay stock disponible para este juego.");
            return;
        }

        boolean finished = false;
        while (!finished) {
            String customerInput = console.readString("Ingrese el ID del clinete para realizar la compra (ENTER para cancelar)");
            if (customerInput.isEmpty()) {
                console.writeLine("Compra cancelada.");
                break;
            }

            Integer customerId;
            try {
                customerId = Integer.parseInt(customerInput);
            } catch (NumberFormatException e) {
                console.writeLine("ID de cliente inválido. Intente de nuevo o presione ENTER para cancelar.");
                continue;
            }

            Customer customer = store.findCustomerById(customerId);
            if (customer == null) {
                console.writeLine("Cliente no encontrado con ID: " + customerId + ". Intente de nuevo o presione ENTER para cancelar.");
                continue;
            }

            float before = customer.getBalance();
            float price = game.getPrice();
            console.writeStringf("Saldo cliente antes: $%.2f | Precio del juego: $%.2f%n", before, price);

            if (before < price) {
                console.writeLine("Saldo insuficiente para realizar la compra. Ingrese otro ID o presione ENTER para cancelar.");
                continue;
            }

            String confirm = console.readString("¿Confirmar compra? (s/n)").toLowerCase();
            if (!confirm.equals("s") && !confirm.equals("y")) {
                console.writeLine("Compra cancelada.");
                break;
            }

            while (true) {
                String saleId = console.readString("Ingrese el ID para la venta (ENTER para cancelar): ");
                if (saleId.isEmpty()) {
                    console.writeLine("Compra cancelada.");
                    finished = true;
                    break;
                }
                if (store.findSaleById(saleId) != null) {
                    console.writeLine("Ese ID ya existe. Intente otro ID o presione ENTER para cancelar.");
                    continue;
                }
                try {
                    store.processSale(customer.getId(), game.getId(), saleId);
                    float after = customer.getBalance();
                    console.writeStringf("Compra realizada con éxito. Saldo antes: $%.2f | Precio: $%.2f | Saldo ahora: $%.2f%n",
                            before, price, after);
                    console.writeLine("Stock restante del juego: " + game.getStock());
                } catch (Exception e) {
                    console.writeLine("No se pudo completar la compra: " + e.getMessage());
                }
                finished = true;
                break;
            }
        }
    }
}