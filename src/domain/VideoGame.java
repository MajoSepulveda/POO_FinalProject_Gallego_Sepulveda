
import java.io.Serializable;

public class VideoGame implements Serializable {

    private String id;
    private String title;
    private String genre;
    private float rating;
    private float price;
    private int stock;

    public VideoGame(String title, String genre, float rating, float price, String id, int stock){
        setID(id);
        setTitle(title);
        setGenre(genre);
        setRating(rating);
        setPrice(price);
        setStock(stock);
    }

    public String getID(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getGenre(){
        return genre;
    }

    public float getRating(){
        return rating;
    }

    public float getPrice(){
        return price;
    }

    public int getStock(){
        return stock;
    }

    public void setID(String id){
        if (id == null || id.trim().isBlank()) {
            throw new IllegalArgumentException("El ID del videojuego no puede estar vacio");
        }
        this.id = id;
    }

    public void setTitle(String title){
        if (title == null || title.trim().isBlank()) {
            throw new IllegalArgumentException("El título del videojuego no puede estar vacio");
        }
        this.title = title;
    }

    public void setGenre(String genre) {
        String[] genres = {"Action", "Adventure", "RPG", "Strategy", "Simulation", "Racing", "Sports", "Casual"};
        for (String g : genres) {
            if (genre == null || genre.trim().isBlank() || !genre.equals(g)) {
                throw new IllegalStateException("EL genero del videojuego no es valido");
            }
        }
        this.genre = genre;
    }
    
    public void setRating(float rating) {
        if (rating < 0.0 || rating > 10.0) {
            throw new IllegalArgumentException("La calificación del videojuego debe estar entre 0.0 y 10.0");
        }
        this.rating = rating;
    }

    public void setPrice(float price){
        if (price < 0) {
            throw new IllegalArgumentException("El precio del videojuego no puede ser negativo");
        }
        this.price = price;
    }

    public void setStock(int stock){
        if (stock <= 0) {
            throw new IllegalArgumentException("El stock del videojuego debe ser mayor a 0");
        }
        this.stock = stock;
    }
}
