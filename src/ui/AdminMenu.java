package src.ui;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import src.domain.Store;
import src.domain.Customer;
import src.domain.VideoGame;
import src.domain.Sale;

public class AdminMenu {

    public static void show(Store store, Scanner input) {
        if (input == null) input = new Scanner(System.in);
        if (store == null) {
            System.out.println("Tienda no inicializada.");
            return;
        }
        
        while (true) {
            System.out.println("\n--- ADMINISTRACIÓN DE LA TIENDA ---");
            System.out.println("1) Agregar cliente");
            System.out.println("2) Agregar videojuego");
            System.out.println("3) Modificar cliente");
            System.out.println("4) Modificar videojuego");
            System.out.println("5) Modificar venta");
            System.out.println("6) Eliminar cliente");
            System.out.println("7) Eliminar videojuego");
            System.out.println("8) Eliminar venta");
            System.out.println("9) Listar clientes");
            System.out.println("10) Listar videojuegos");
            System.out.println("11) Listar ventas");
            System.out.println("12) Listar ventas en un periodo de tiempo");
            System.out.println("13) Ingreso total en un periodo de tiempo");
            System.out.println("14) Género más vendido en un periodo de tiempo");
            System.out.println("15) Juego más vendido en un periodo de tiempo");
            System.out.println("16) Generar reporte de ingresos en un periodo de tiempo");
            System.out.println("17) Ingresos totales");
            System.out.println("18) Género más vendido");
            System.out.println("19) Juego más vendido");
            System.out.println("20) Volver");
            System.out.print("Seleccione una opción: ");

            String option = input.nextLine().trim();
            try {
                switch (option) {
                    case "1": addCustomer(store, input); break;
                    case "2": addVideoGame(store, input); break;
                    case "3": modifyCustomer(store, input); break;
                    case "4": modifyVideoGame(store, input); break;
                    case "5": modifySale(store, input); break;
                    case "6": removeCustomer(store, input); break;
                    case "7": removeVideoGame(store, input); break;
                    case "8": removeSale(store, input); break;
                    case "9": listCustomers(store); break;
                    case "10": listVideoGames(store); break;
                    case "11": listSales(store); break;
                    case "12": listSalesInPeriod(store, input); break;
                    case "13": totalIncomePeriod(store, input); break;
                    case "14": topGenrePeriod(store, input); break;
                    case "15": topGamePeriod(store, input); break;
                    case "16": generateIncomeReport(store, input); break;
                    case "17": showTotalIncome(store); break;
                    case "18": showTopGenreTotal(store); break;
                    case "19": showTopGameTotal(store); break;
                    case "20": return;
                    default: System.out.println("Opción inválida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void addCustomer(Store store, Scanner scanner) {
        try {
            System.out.print("Nombre del cliente: ");
            String name = scanner.nextLine().trim();
            System.out.print("ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Saldo inicial: ");
            float balance = Float.parseFloat(scanner.nextLine().trim());

            Customer c = new Customer(name, id, balance);
            store.addCustomer(c);
            System.out.println("Cliente agregado: ID = " + c.getId() + " Nombre = " + c.getName());
        } catch (Exception e) {
            System.out.println("No se pudo agregar el cliente: " + e.getMessage());
        }
    }

    private static void addVideoGame(Store store, Scanner input) {
        try {
            System.out.print("ID del videojuego: ");
            String id = input.nextLine().trim();
            System.out.print("Título: ");
            String title = input.nextLine().trim();
            System.out.print("Género: ");
            String genre = input.nextLine().trim();
            System.out.print("Rating: ");
            float rating = Float.parseFloat(input.nextLine().trim());
            System.out.print("Precio: ");
            float price = Float.parseFloat(input.nextLine().trim());
            System.out.print("Stock: ");
            int stock = Integer.parseInt(input.nextLine().trim());

            VideoGame vg = new VideoGame(title, genre, rating, price, id, stock);
            store.addGame(vg);
            System.out.println("Videojuego agregado: ID = " + vg.getId() + " Título = " + vg.getTitle());
        } catch (Exception e) {
            System.out.println("No se pudo agregar el videojuego: " + e.getMessage());
        }
    }

    private static void modifyCustomer(Store store, Scanner scanner) {
        if (store == null) return;
        while (true) {
            System.out.print("Ingrese ID del cliente a modificar (ENTER para cancelar): ");
            String in = scanner.nextLine().trim();
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
                String option = scanner.nextLine().trim();
                switch (option) {
                    case "1":
                        System.out.print("Nuevo nombre: ");
                        String name = scanner.nextLine().trim();
                        try {
                            c.setName(name);
                            System.out.println("Nombre actualizado.");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    case "2":
                        System.out.print("Monto a agregar (ENTER para cancelar): ");
                        String addBalance = scanner.nextLine().trim();
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

    private static void modifyVideoGame(Store store, Scanner input) {
        if (store == null) return;
        while (true) {
            System.out.print("Ingrese ID del videojuego a modificar (ENTER para cancelar): ");
            String id = input.nextLine().trim();
            if (id.isEmpty()) return;
            VideoGame g = store.findVideoGameById(id);
            if (g == null) {
                System.out.println("Videojuego no encontrado. Intente nuevamente.");
                continue;
            }

            boolean done = false;
            while (!done) {
                System.out.println("\nVideojuego actual: " + g);
                System.out.println("1) Cambiar título");
                System.out.println("2) Cambiar género");
                System.out.println("3) Cambiar rating");
                System.out.println("4) Cambiar precio");
                System.out.println("5) Agregar stock");
                System.out.println("6) Reducir stock");
                System.out.println("7) Volver");
                System.out.print("Seleccione: ");
                String option = input.nextLine().trim();
                switch (option) {
                    case "1":
                        System.out.print("Nuevo título: ");
                        String newTitle = input.nextLine().trim();
                        try {
                            g.setTitle(newTitle);
                            System.out.println("Título actualizado.");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    case "2":
                        System.out.print("Nuevo género (Action, Adventure, RPG, Strategy, Simulation, Racing, Sports, Casual): ");
                        String newGenre = input.nextLine().trim();
                        try {
                            g.setGenre(newGenre);
                            System.out.println("Género actualizado.");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    case "3":
                        System.out.print("Nuevo rating (0.0 - 10.0): ");
                        String newRating = input.nextLine().trim();
                        try {
                            float r = Float.parseFloat(newRating);
                            g.setRating(r);
                            System.out.println("Rating actualizado.");
                        } catch (NumberFormatException nfe) {
                            System.out.println("Número inválido.");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    case "4":
                        System.out.print("Nuevo precio: ");
                        String newPrice = input.nextLine().trim();
                        try {
                            float p = Float.parseFloat(newPrice);
                            g.setPrice(p);
                            System.out.println("Precio actualizado.");
                        } catch (NumberFormatException nfe) {
                            System.out.println("Número inválido.");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    case "5":
                        System.out.print("Cantidad a agregar al stock: ");
                        String addStock = input.nextLine().trim();
                        try {
                            int a = Integer.parseInt(addStock);
                            g.addStock(a);
                            System.out.println("Stock actualizado. Nuevo stock: " + g.getStock());
                        } catch (NumberFormatException nfe) {
                            System.out.println("Número inválido.");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    case "6":
                        System.out.print("Cantidad a reducir del stock: ");
                        String reduceStock = input.nextLine().trim();
                        try {
                            int remove = Integer.parseInt(reduceStock);
                            if (remove < 0) throw new IllegalArgumentException("Cantidad inválida.");
                            for (int i = 0; i < remove; i++) {
                                g.reduceStock();
                            }
                            System.out.println("Stock actualizado. Nuevo stock: " + g.getStock());
                        } catch (NumberFormatException nfe) {
                            System.out.println("Número inválido.");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    case "7":
                        done = true;
                        break;
                    default:
                        System.out.println("Opción inválida.");
                }
            }
            return;
        }
    }

    private static void modifySale(Store store, Scanner scanner) {
        if (store == null) return;
        while (true) {
            System.out.print("Ingrese ID de la venta a modificar (ENTER para cancelar): ");
            String id = scanner.nextLine().trim();
            if (id.isEmpty()) return;
            Sale s = store.findSaleById(id);
            if (s == null) {
                System.out.println("Venta no encontrada. Intente nuevamente.");
                continue;
            }

            System.out.println("AVISO: Modificar una venta NO ajusta automáticamente saldos ni stock histórico.");
            boolean done = false;
            while (!done) {
                System.out.println("\nVenta actual: " + s);
                System.out.println("1) Cambiar monto");
                System.out.println("2) Cambiar fecha (dd/MM/yyyy)");
                System.out.println("3) Volver");
                System.out.print("Seleccione: ");
                String opt = scanner.nextLine().trim();
                switch (opt) {
                    case "1":
                        System.out.print("Nuevo monto (ej. 49.99): ");
                        String mStr = scanner.nextLine().trim();
                        try {
                            float m = Float.parseFloat(mStr);
                            VideoGame vg = s.getVideoGame();
                            Customer c = s.getCustomer();
                            store.removeSale(s.getId());
                            try {
                                Sale newSale = new Sale(vg, c, s.getId(), m, s.getDate());
                                store.addSale(newSale);
                                System.out.println("Monto actualizado.");
                                s = newSale;
                            } catch (Exception ex) {
                                store.addSale(s);
                                System.out.println("No se pudo actualizar la venta: " + ex.getMessage());
                            }
                        } catch (NumberFormatException nfe) {
                            System.out.println("Número inválido.");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    case "2":
                        System.out.print("Nueva fecha (dd/MM/yyyy): ");
                        String newDate = scanner.nextLine().trim();
                        try {
                            VideoGame vg2 = s.getVideoGame();
                            Customer c2 = s.getCustomer();
                            store.removeSale(s.getId());
                            try {
                                Sale newSale2 = new Sale(vg2, c2, s.getId(), s.getAmount(), newDate);
                                store.addSale(newSale2);
                                System.out.println("Fecha actualizada.");
                                s = newSale2;
                            } catch (Exception ex) {
                                store.addSale(s);
                                System.out.println("No se pudo actualizar la fecha: " + ex.getMessage());
                            }
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

    private static void removeCustomer(Store store, Scanner scanner) {
        try {
            System.out.print("ID del cliente a eliminar (entero): ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            store.removeCustomer(id);
            System.out.println("Cliente eliminado: " + id);
        } catch (Exception e) {
            System.out.println("No se pudo eliminar el cliente: " + e.getMessage());
        }
    }

    private static void removeVideoGame(Store store, Scanner scanner) {
        try {
            System.out.print("ID del videojuego a eliminar (ej. A001): ");
            String id = scanner.nextLine().trim();
            store.removeVideoGame(id);
            System.out.println("Videojuego eliminado: " + id);
        } catch (Exception e) {
            System.out.println("No se pudo eliminar el videojuego: " + e.getMessage());
        }
    }

    private static void removeSale(Store store, Scanner scanner) {
        try {
            System.out.print("ID de la venta a eliminar (ej. S001): ");
            String id = scanner.nextLine().trim();
            store.removeSale(id);
            System.out.println("Venta eliminada: " + id);
        } catch (Exception e) {
            System.out.println("No se pudo eliminar la venta: " + e.getMessage());
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

    private static void listVideoGames(Store store) {
        List<VideoGame> games = store.getVideoGames();
        if (games == null || games.isEmpty()) {
            System.out.println("No hay videojuegos registrados.");
            return;
        }
        System.out.println("--- VIDEOJUEGOS ---");
        for (VideoGame g : games) {
            System.out.printf("ID: %s | Título: %s | Género: %s | Rating: %.1f | Precio: $%.2f | Stock: %d%n",
                    g.getId(), g.getTitle(), g.getGenre(), g.getRating(), g.getPrice(), g.getStock());
        }
    }

    private static void listSales(Store store) {
        List<Sale> sales = store.getSales();
        if (sales == null || sales.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }
        System.out.println("--- VENTAS ---");
        for (Sale s : sales) {
            System.out.printf("ID: %s | Fecha: %s | Monto: $%.2f | ClienteID: %d | Juego: %s (%s)%n",
                    s.getId(), s.getDate(), s.getAmount(), s.getCustomer().getId(),
                    s.getVideoGame().getTitle(), s.getVideoGame().getId());
        }
    }

    private static void listSalesInPeriod(Store store, Scanner scanner) {
        try {
            LocalDate[] period = readPeriod(scanner);
            List<Sale> sales = store.getSalesInPeriod(period[0], period[1]);
            if (sales.isEmpty()) {
                System.out.println("No se registraron ventas en ese periodo.");
                return;
            }
            System.out.println("--- VENTAS EN PERIODO ---");
            for (Sale s : sales) {
                System.out.printf("ID: %s | Fecha: %s | Monto: $%.2f | ClienteID: %d | Juego: %s (%s)%n",
                        s.getId(), s.getDate(), s.getAmount(), s.getCustomer().getId(),
                        s.getVideoGame().getTitle(), s.getVideoGame().getId());
            }
        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha inválido. Use yyyy-MM-dd.");
        } catch (Exception e) {
            System.out.println("Error al listar ventas en periodo: " + e.getMessage());
        }
    }

    private static void totalIncomePeriod(Store store, Scanner scanner) {
        try {
            LocalDate[] period = readPeriod(scanner);
            List<Sale> sales = store.getSalesInPeriod(period[0], period[1]);
            float total = store.getTotalIncome(sales);
            System.out.printf("Ingreso total entre %s y %s: $%.2f%n", period[0], period[1], total);
        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha inválido. Use yyyy-MM-dd.");
        } catch (Exception e) {
            System.out.println("Error al calcular ingreso total: " + e.getMessage());
        }
    }

    private static void topGenrePeriod(Store store, Scanner scanner) {
        try {
            LocalDate[] period = readPeriod(scanner);
            List<Sale> sales = store.getSalesInPeriod(period[0], period[1]);
            String topGenre = store.getTopSellingGenre(sales);
            System.out.println("Género más vendido en el periodo: " + topGenre);
        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha inválido. Use yyyy-MM-dd.");
        } catch (Exception e) {
            System.out.println("Error al obtener género top: " + e.getMessage());
        }
    }

    private static void topGamePeriod(Store store, Scanner scanner) {
        try {
            LocalDate[] period = readPeriod(scanner);
            List<Sale> sales = store.getSalesInPeriod(period[0], period[1]);
            VideoGame top = store.getTopSellingGame(sales);
            if (top == null) {
                System.out.println("No hay juegos vendidos en el periodo.");
            } else {
                System.out.printf("Juego más vendido: %s (ID: %s)%n", top.getTitle(), top.getId());
            }
        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha inválido. Use yyyy-MM-dd.");
        } catch (Exception e) {
            System.out.println("Error al obtener juego top: " + e.getMessage());
        }
    }

    private static void generateIncomeReport(Store store, Scanner scanner) {
        try {
            LocalDate[] period = readPeriod(scanner);
            String report = store.generateIncomeReport(period[0], period[1]);
            System.out.println(report);
        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha inválido. Use yyyy-MM-dd.");
        } catch (Exception e) {
            System.out.println("Error al generar reporte: " + e.getMessage());
        }
    }

    private static void showTotalIncome(Store store) {
        float total = store.getTotalIncome(store.getSales());
        System.out.printf("Ingreso total acumulado: $%.2f%n", total);
    }

    private static void showTopGenreTotal(Store store) {
        String top = store.getTopSellingGenre(store.getSales());
        System.out.println("Género más vendido (total): " + top);
    }

    private static void showTopGameTotal(Store store) {
        VideoGame top = store.getTopSellingGame(store.getSales());
        if (top == null) System.out.println("No hay ventas registradas.");
        else System.out.printf("Juego más vendido (total): %s (ID: %s)%n", top.getTitle(), top.getId());
    }

    private static LocalDate[] readPeriod(Scanner scanner) {
        System.out.print("Fecha inicio (yyyy-MM-dd): ");
        String startStr = scanner.nextLine().trim();
        System.out.print("Fecha fin (yyyy-MM-dd): ");
        String endStr = scanner.nextLine().trim();
        LocalDate start = LocalDate.parse(startStr);
        LocalDate end = LocalDate.parse(endStr);
        return new LocalDate[]{start, end};
    }
}