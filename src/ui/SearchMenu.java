package src.ui;

import java.util.List;
import java.util.Scanner;
import src.domain.*;

public class SearchMenu {
    public static void searchMenu(Store store, Scanner input){
        String currentTitle = null;
        String currentGenre = null;
        Float currentMaxPrice = null; 
        Float currentMinRating = null; 
        int option = 0;

        while (option != 5 && option != 6) {
            System.out.println("1. Título: " + (currentTitle != null ? currentTitle : "[No Aplicado]"));
            System.out.println("2. Género: " + (currentGenre != null ? currentGenre : "[No Aplicado]"));
            System.out.println("3. Precio Máximo: " + (currentMaxPrice != null ? "$" + currentMaxPrice : "[No Aplicado]"));
            System.out.println("4. Rating Mínimo: " + (currentMinRating != null ? currentMinRating : "[No Aplicado]"));
            System.out.println("5. APLICAR FILTRO y Mostrar Resultados");
            System.out.println("6. Volver al menú anterior (sin aplicar)");
            System.out.print("\nIngrese la opción (1-6) o el filtro a cambiar: ");
            option = Integer.parseInt(input.nextLine());

            switch (option) {
                case 1:
                    System.out.print("Ingrese Título: ");
                    currentTitle = input.nextLine();
                    break;
                case 2: 
                    System.out.println("Ingrese Género: ");
                    currentGenre = input.nextLine();
                    break;
                case 3:
                    System.out.print("Ingrese Precio Máximo: ");
                    currentMaxPrice = Float.parseFloat(input.nextLine());
                    break;
                case 4: 
                    System.out.println("Ingrese Rating: ");
                    currentMinRating = Float.parseFloat(input.nextLine());
                default:
                    System.out.println("Opción inválida.");
            }
        }    
        List<VideoGame> results = store.filterVideoGames(currentTitle, currentGenre,currentMaxPrice, currentMinRating);

        System.out.println("\n--- RESULTADOS DE LA BÚSQUEDA (" + results.size() + " encontrados) ---");
        if (results.isEmpty()) {
            System.out.println("No se encontraron videojuegos que coincidan con los filtros aplicados.");
        } else {
            // En lugar de System.out.println(results); (que imprime la dirección de memoria de la lista)
            // Debes implementar un método auxiliar para mostrar los detalles de los VideoGames.
            // Ejemplo: displayVideoGameResults(results);
        }   
    }
}
