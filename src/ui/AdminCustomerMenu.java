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
            console.writeLine("----------- GESTIÓN DE CLIENTES  -----------");
            console.writeLine("1) Agregar cliente.");
            console.writeLine("2) Eliminar cliente.");
            console.writeLine("3) Modificar cliente.");
            console.writeLine("4) Lista de clientes.");
            console.writeLine("5) Volver.");
            
            int option = console.readInt("Seleccione una opción: ");
            switch (option) {
                case 1:
                    addCustomer(store, console);
                    break;
                case 2:
                    removeCustomer(store, console);
                    break;
                case 3:
                    modifyCustomer(store, console);
                    break;
                case 4:
                    listCustomers(store, console);
                    break;
                case 5:
                    console.writeLine("Volviendo...");
                    console.sleep(1000);
                    return true;
                default:
                    console.writeLine("Opción inválida. Intente de nuevo.");
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
        try {
            String name = console.readValidString("Nombre del cliente: ");
            String id = console.readCustomerID("ID: ");
            float balance = console.readPositiveFloat("Saldo inicial: ");
            Customer c = new Customer(name, id, balance);
            store.addCustomer(c);
            console.writeLine("Cliente agregado: ID = " + c.getId() + " Nombre = " + c.getName());
        } catch (Exception e) {
            console.writeLine("No se pudo agregar el cliente: " + e.getMessage());
        }
    }

    /**
    * Prompts the user for a customer ID and attempts to remove the customer from the store.
    * Handles exceptions if the ID is invalid or the customer is not found.
    * @param store The Store object from which the customer will be removed.
    * @param console The ConsoleUI object for input/output operations.
    */
    private static void removeCustomer(Store store, ConsoleUI console) {
        try {
            String id = console.readCustomerID("ID del cliente a eliminar: ");
            store.removeCustomer(id);
            console.writeLine("Cliente eliminado: " + id);
        } catch (Exception e) {
            console.writeLine("No se pudo eliminar el cliente: " + e.getMessage());
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
            String id = console.readCustomerID("Ingrese ID del cliente a modificar: ");
            Customer customer = store.findCustomerById(id);
            if (customer == null) {
                console.writeLine("Cliente no encontrado. Verifique el ID.");
                break;
            }

            boolean done = false;
            while (!done) {
                console.writeLine("\nCliente actual: " + customer);
                console.writeLine("1) Cambiar nombre");
                console.writeLine("2) Agregar saldo");
                console.writeLine("3) Volver");
                int option = console.readInt("Seleccione una opción");
                switch (option) {
                    case 1:
                        String name = console.readValidString("Nuevo nombre");
                        try {
                            customer.setName(name);
                            console.writeLine("Nombre actualizado.");
                        } catch (Exception e) {
                            console.writeLine("Error: " + e.getMessage());
                        }
                        break;
                    case 2:
                        float amount = console.readPositiveFloat("Monto a agregar.");
                        try {
                            customer.addBalance(amount);
                            console.writeLine("Saldo agregado. Nuevo saldo: $" + String.format("%.2f", customer.getBalance()));
                        } catch (NumberFormatException nfe) {
                            console.writeLine("Número inválido.");
                        } catch (Exception e) {
                            console.writeLine("Error: " + e.getMessage());
                        }
                        break;
                    case 3:
                        done = true;
                        break;
                    default:
                        console.writeLine("Opción inválida.");
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
