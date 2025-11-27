package src.ui;

import src.domain.*;
import java.util.List;

/**
* Handles the administrative menu for managing customer entities within the store system.
* Provides options to add, remove, modify, and list customers.
*/
public class AdminCustomerMenu {

    /**
    * Displays the main Customer Management menu loop and handles user navigation.
    * @param store The main Store object containing customer data.
    * @param console The ConsoleUI object used for user input and output.
    */
    public static boolean show(Store store, ConsoleUI console){

        while (true) {
            console.cls();
            console.writeLine("----------- GESTIÓN DE CLIENTES  -----------");
            console.writeLine("1) Agregar cliente.");
            console.writeLine("2) Eliminar cliente.");
            console.writeLine("3) Modificar cliente.");
            console.writeLine("4) Lista de clientes.");
            console.writeLine("5) Volver.");
            
            int option = console.readInt("\nSeleccione una opción: ");
            switch (option) {
                case 1:
                    console.sleep(option);
                    addCustomer(store, console);
                    console.sleep(option);
                    break;
                case 2:
                    console.sleep(option);
                    removeCustomer(store, console);
                    console.sleep(option);
                    break;
                case 3:
                    console.sleep(option);
                    modifyCustomer(store, console);
                    console.sleep(option);
                    break;
                case 4:
                    console.sleep(option);
                    listCustomers(store, console);
                    console.sleep(option);
                    break;
                case 5:
                    console.writeLine("\nVolviendo...");
                    console.sleep(1000);
                    return true;
                default:
                    console.writeLine("\nOpción inválida. Intente de nuevo.");
                    console.sleep(1000);
            }
        }
    }

    /**
    * Prompts the user for customer details (Name, ID, Balance) and adds a new customer to the store.
    * Handles potential exceptions during data input or customer creation.
    * @param store The Store object to which the customer will be added.
    * @param console The ConsoleUI object for input/output operations.
    */
    private static void addCustomer(Store store, ConsoleUI console) {
        console.cls();
        try {
            String name = console.readValidName("Nombre del cliente: ");
            String id = console.readCustomerID("ID: ");
            float balance = console.readPositiveFloat("Saldo inicial: ");
            Customer c = new Customer(name, id, balance);
            store.addCustomer(c);
            console.writeLine("Cliente agregado: ID = " + c.getId() + " Nombre = " + c.getName());
        } catch (Exception e) {
            console.writeError("No se pudo agregar el cliente: " + e.getMessage());
        }
    }

    /**
    * Prompts the user for a customer ID and attempts to remove the customer from the store.
    * Handles exceptions if the ID is invalid or the customer is not found.
    * @param store The Store object from which the customer will be removed.
    * @param console The ConsoleUI object for input/output operations.
    */
    private static void removeCustomer(Store store, ConsoleUI console) {
        console.cls();
        try {
            String id = console.readCustomerID("ID del cliente a eliminar: ");
            Customer customer = store.findCustomerById(id);

            if (customer == null) {
                console.writeLine("\nCliente no encontrado con ID: " + id);
                return;
            }

            String confirmationMessage = String.format("¿Está seguro de que desea eliminar al cliente %s (ID: %s)? Escriba 'SI' para confirmar: ", customer.getName(), customer.getId());
            String confirmation = console.readString(confirmationMessage);

            if (confirmation.trim().equalsIgnoreCase("SI")) {
                store.removeCustomer(id);
                console.writeSucces("Cliente eliminado: " + customer.getName() + " (ID: " + id + ")");
            } else {
                console.writeLine("Operación de eliminación cancelada.");
            }
        } catch (Exception e) {
        console.writeError("No se pudo eliminar el cliente: " + e.getMessage());
        }
    }

    /**
    * Allows the user to find and modify an existing customer's details in a sub-menu loop.
    * Modification options include changing the name and adding balance.
    * @param store The Store object containing the customer data.
    * @param console The ConsoleUI object for input/output operations.
    */
    private static void modifyCustomer(Store store, ConsoleUI console) {
        if (store == null) return;
        while (true) {
            console.cls();
            String id = console.readCustomerID("Ingrese ID del cliente a modificar: ");
            Customer customer = store.findCustomerById(id);
            if (customer == null) {
                console.writeLine("\nCliente no encontrado. Verifique el ID.");
                break;
            }

            boolean done = false;
            while (!done) {
                console.cls();
                console.writeLine("\nCliente actual: " + customer);
                console.writeLine("1) Cambiar nombre.");
                console.writeLine("2) Agregar saldo.");
                console.writeLine("3) Volver.");

                int option = console.readInt("\nSeleccione una opción");
                console.cls();
                switch (option) {
                    case 1:
                        String name = console.readValidName("Nuevo nombre: ");
                        try {
                            customer.setName(name);
                            console.writeSucces("Nombre actualizado.");
                        } catch (Exception e) {
                            console.writeError(e.getMessage());
                        }
                        break;
                    case 2:
                        float amount = console.readPositiveFloat("Monto a agregar.");
                        try {
                            customer.addBalance(amount);
                            console.writeSucces("Saldo agregado. Nuevo saldo: $" + String.format("%.2f", customer.getBalance()));
                        } catch (NumberFormatException nfe) {
                            console.writeError("Número inválido.");
                        } catch (Exception e) {
                            console.writeError(e.getMessage());
                        }
                        break;
                    case 3:
                        done = true;
                        break;
                    default:
                        console.writeLine("\nOpción inválida. Intente de nuevo.");
                }
            }
            return;
        }
    }

    /**
    * Retrieves the list of all customers from the store and displays their details to the console.
    * @param store The Store object to retrieve the customer list from.
    * @param console The ConsoleUI object for output operations.
    */
    private static void listCustomers(Store store, ConsoleUI console) {
        List<Customer> customers = store.getCustomers();
        console.cls();
        if (customers == null || customers.isEmpty()) {
            console.writeLine("No hay clientes registrados.");
            return;
        }
        console.writeLine("--- CLIENTES ---");
        for (Customer c : customers) {
            console.printDisplayableDetails(c);
        }
    }
}
