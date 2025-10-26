public class VideoGames {

    private String title;
    private String genre;
    private int rating;
    private float price;
    private int id;
    private int stock;

    public String getTitle(){
        return title;
    }

    public String getGenre(){
        return genre;
    }

    public int getRating(){
        return rating;
    }

    public float getPrice(){
        return price;
    }

    public int getID(){
        return id;
    }

    public int getStock(){
        return stock;
    }

    public void setTitle(String newTitle){
        title = newTitle;
    }

   public boolean setGenre(String newGenre) {
    String[] genres = {"Action", "Adventure", "RPG", "Strategy", "Simulation", "Racing", "Sports", "Casual"};
    for (String genre : genres) {
        if (genre.equals(newGenre)) {
            genre = newGenre;
            return true;
        }
    }
    return false;
}
    
    public boolean setRating(int newRating) {
        if (newRating >= 0 && newRating <= 100) {
            rating = newRating;
            return true;
        }
        return false;
    }

    public float setPrice(float newPrice){
        if (newPrice >= 0.0) {
            price = newPrice;
        }
        return price;
    }

    public void setID(int newID){
        id = newID;
    }

    public boolean setStock(int newStock){
        if (newStock >= 0) {
            stock = newStock;
            return true;
        }
        return false;
    }


}
