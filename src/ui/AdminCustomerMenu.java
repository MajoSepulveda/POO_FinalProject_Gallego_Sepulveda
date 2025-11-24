package src.ui;

import src.domain.*;
import java.util.List;
import java.util.Scanner;

public class AdminCustomerMenu {
    public static void Show(Store store, Scanner input){

        if (input == null) input = new Scanner(System.in);
        while (true) {
            System.out.println("----------- GESTIÓN DE CLIENTES  -----------");
            System.out.println("1) Agregar cliente.");
            System.out.println("2) Eliminar cliente.");
            System.out.println("3) Modificar cliente.");
            System.out.println("4) Lista de clientes.");
            System.out.println("5) Volver al menú principal.");
            System.out.print("Seleccione una opción: ");

            String option = input.nextLine().trim();
            switch (option) {
                case "1":
                    addCustomer(store, input);
                    break;
                case "2":
                    removeCustomer(store, input);
                    break;
                case "3":
                    modifyCustomer(store, input);
                    break;
                case "4":
                    listCustomers(store);
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
    }

    private static void addCustomer(Store store, Scanner input) {
        try {
            System.out.print("Nombre del cliente: ");
            String name = input.nextLine().trim();
            System.out.print("ID: ");
            int id = Integer.parseInt(input.nextLine().trim());
            System.out.print("Saldo inicial: ");
            float balance = Float.parseFloat(input.nextLine().trim());
            Customer c = new Customer(name, id, balance);
            store.addCustomer(c);
            System.out.println("Cliente agregado: ID = " + c.getId() + " Nombre = " + c.getName());
        } catch (Exception e) {
            System.out.println("No se pudo agregar el cliente: " + e.getMessage());
        }
    }

    private static void removeCustomer(Store store, Scanner input) {
        try {
            System.out.print("ID del cliente a eliminar (entero): ");
            int id = Integer.parseInt(input.nextLine().trim());
            store.removeCustomer(id);
            System.out.println("Cliente eliminado: " + id);
        } catch (Exception e) {
            System.out.println("No se pudo eliminar el cliente: " + e.getMessage());
        }
    }

    private static void modifyCustomer(Store store, Scanner input) {
        if (store == null) return;
        while (true) {
            System.out.print("Ingrese ID del cliente a modificar (ENTER para cancelar): ");
            String in = input.nextLine().trim();
            if (in.isEmpty()) return;
            int id;
            try {
                id = Integer.parseInt(in);
            } catch (NumberFormatException e) {
                System.out.println("ID inválido. Intente nuevamente.");
                continue;
            }
            Customer c = store.findCustomerById(id);
            if (c == null) {
                System.out.println("Cliente no encontrado. Intente nuevamente.");
                continue;
            }

            boolean done = false;
            while (!done) {
                System.out.println("\nCliente actual: " + c);
                System.out.println("1) Cambiar nombre");
                System.out.println("2) Agregar saldo");
                System.out.println("3) Volver");
                System.out.print("Seleccione: ");
                String option = input.nextLine().trim();
                switch (option) {
                    case "1":
                        System.out.print("Nuevo nombre: ");
                        String name = input.nextLine().trim();
                        try {
                            c.setName(name);
                            System.out.println("Nombre actualizado.");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    case "2":
                        System.out.print("Monto a agregar (ENTER para cancelar): ");
                        String addBalance = input.nextLine().trim();
                        if (addBalance.isEmpty()) break;
                        try {
                            float amount = Float.parseFloat(addBalance);
                            c.addBalance(amount);
                            System.out.println("Saldo agregado. Nuevo saldo: $" + String.format("%.2f", c.getBalance()));
                        } catch (NumberFormatException nfe) {
                            System.out.println("Número inválido.");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    case "3":
                        done = true;
                        break;
                    default:
                        System.out.println("Opción inválida.");
                }
            }
            return;
        }
    }

    private static void listCustomers(Store store) {
        List<Customer> customers = store.getCustomers();
        if (customers == null || customers.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        System.out.println("--- CLIENTES ---");
        for (Customer c : customers) {
            System.out.printf("ID: %d | Nombre: %s | Saldo: $%.2f%n", c.getId(), c.getName(), c.getBalance());
        }
    }
}
