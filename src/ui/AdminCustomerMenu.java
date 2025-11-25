package src.ui;

import src.domain.*;
import java.util.List;

public class AdminCustomerMenu {
    public static void show(Store store, ConsoleUI console){

        while (true) {
            System.out.println("----------- GESTIÓN DE CLIENTES  -----------");
            System.out.println("1) Agregar cliente.");
            System.out.println("2) Eliminar cliente.");
            System.out.println("3) Modificar cliente.");
            System.out.println("4) Lista de clientes.");
            System.out.println("5) Volver al menú principal.");
            System.out.print("Seleccione una opción: ");

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
                    console.writeLine("Volviendo al menú principal...");
                    return;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
    }

    private static void addCustomer(Store store, ConsoleUI console) {
        try {
            String name = console.readString("Nombre del cliente: ");
            String id = console.readString("ID: ");
            float balance = console.readFloat("Saldo inicial: ");
            Customer c = new Customer(name, id, balance);
            store.addCustomer(c);
            console.writeLine("Cliente agregado: ID = " + c.getId() + " Nombre = " + c.getName());
        } catch (Exception e) {
            console.writeLine("No se pudo agregar el cliente: " + e.getMessage());
        }
    }

    private static void removeCustomer(Store store, ConsoleUI console) {
        try {
            String id = console.readString("ID del cliente a eliminar: ");
            store.removeCustomer(id);
            console.writeLine("Cliente eliminado: " + id);
        } catch (Exception e) {
            console.writeLine("No se pudo eliminar el cliente: " + e.getMessage());
        }
    }

    private static void modifyCustomer(Store store, ConsoleUI console) {
        if (store == null) return;
        while (true) {
            String id = console.readString("Ingrese ID del cliente a modificar (ENTER para cancelar): ");
            Customer customer = store.findCustomerById(id);
            if (customer == null) {
                console.writeLine("Cliente no encontrado. Intente nuevamente.");
                continue;
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
                        String name = console.readString("Nuevo nombre");
                        try {
                            customer.setName(name);
                            console.writeLine("Nombre actualizado.");
                        } catch (Exception e) {
                            console.writeLine("Error: " + e.getMessage());
                        }
                        break;
                    case 2:
                        float amount = console.readFloat("Monto a agregar (ENTER para cancelar)");
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

    private static void listCustomers(Store store, ConsoleUI console) {
        List<Customer> customers = store.getCustomers();
        if (customers == null || customers.isEmpty()) {
            console.writeLine("No hay clientes registrados.");
            return;
        }
        console.writeLine("--- CLIENTES ---");
        for (Customer c : customers) {
            System.out.printf("ID: %d | Nombre: %s | Saldo: $%.2f%n", c.getId(), c.getName(), c.getBalance());
        }
    }
}
