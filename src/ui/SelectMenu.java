package src.ui;

import src.domain.*;

/**
 * Handles the selection and purchasing process for a single VideoGame chosen by the user.
 * It guides the user through identifying the game and then completing the purchase for a customer.
 */
public class SelectMenu {
    /**
     * Prompts the user to enter a VideoGame ID and attempts to find the corresponding game in the store.
     * If found, it calls the showSelectedMenu for further action.
     * @param store The main Store object containing game data.
     * @param console The ConsoleUI object for user input and output.
     * @return The found VideoGame object, or null if the input is empty, the store is not initialized, or the game is not found.
     */
    public static VideoGame SelectById(Store store, ConsoleUI console) {
        String id = console.readVideoGameID("\nIngrese el ID del videojuego para seleccionar.");

        VideoGame game = store.findVideoGameById(id);
        if (game == null) {
            console.writeLine("\nNo se encontró juego con ID: " + id);
            return null;
        }

        console.sleep(200);
        showSelectedMenu(game, store, console);
        return game;
    }

    /**
     * Displays a menu for a successfully selected VideoGame, giving the option to buy or return.
     * This method loops until the user chooses to exit.
     * @param game The VideoGame object that was selected.
     * @param store The main Store object.
     * @param console The ConsoleUI object for input and output.
     */
    public static void showSelectedMenu(VideoGame game, Store store, ConsoleUI console) {
        if (game == null || store == null) return;

        while (true) {
            console.cls();
            console.writeLine("Has seleccionado el videojuego: " + game.getTitle());
            console.writeLine("1) Comprar videojuego.");
            console.writeLine("2) Volver.");

            int option = console.readInt("\nSelecione una opción: ");
            switch (option) {
                case 1:
                    console.sleep(200);
                    handleBuy(game, store, console);
                    return;
                case 2:
                    console.writeLine("\nVolviendo a la tienda...");
                    console.sleep(1000);
                    return;
                default:
                    console.writeLine("\nOpción inválida. Intente de nuevo.");
                    console.sleep(1000);
            }
        }
    }

    /**
     * Handles the complex logic for purchasing a selected VideoGame.
     * This method prompts for Customer ID, checks stock and balance, asks for confirmation,
     * and finally processes the sale (including unique Sale ID input).
     * @param game The VideoGame object to be purchased.
     * @param store The main Store object to process the transaction.
     * @param console The ConsoleUI object for all interaction.
     */
    private static void handleBuy(VideoGame game, Store store, ConsoleUI console) {
        if (game == null || store == null) return;

        if (game.getStock() <= 0) {
            console.writeLine("\nNo hay stock disponible para este juego.");
            console.sleep(1000);
            return;
        }

        boolean finished = false;
        while (!finished) {
            String customerId = console.readCustomerID("\nIngrese el ID del cliente para realizar la compra.");

            Customer customer = store.findCustomerById(customerId);
            if (customer == null) {
                console.writeLine("\nCliente no encontrado con ID: " + customerId + ".");
                console.sleep(1000);
                break;
            }

            float before = customer.getBalance();
            float price = game.getPrice();
            console.writeStringf("Saldo cliente antes: $%.2f | Precio del juego: $%.2f%n", before, price);

            if (before < price) {
                console.writeLine("Saldo insuficiente para realizar la compra.");
                console.sleep(1000);
                break;
            }

            String confirm = console.readValidString("¿Confirmar compra? (s/n)").toLowerCase();
            if (!confirm.equals("s") && !confirm.equals("y")) {
                console.writeLine("Compra cancelada.");
                console.sleep(1000);
                break;
            }

            while (true) {
                String saleId = store.generateSaleId();
                try {
                    store.processSale(customer.getId(), game.getId(), saleId);
                    float after = customer.getBalance();
                    console.writeStringf("\nCompra realizada con éxito. Saldo antes: $%.2f | Precio: $%.2f | Saldo ahora: $%.2f%n",
                            before, price, after);
                    console.writeLine("Stock restante del juego: " + game.getStock());
                } catch (Exception e) {
                    console.writeLine("\nNo se pudo completar la compra: " + e.getMessage());
                }
                finished = true;
                break;
            }
        }
    }
}
