public class VideoGames {

    private String title;
    private String genre;
    private float rating;
    private float price;
    private String id;
    private int stock;

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

    public String getID(){
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
        for (String g : genres) {
            if (g.equals(newGenre)) {
                this.genre = newGenre;
                return true;
            }
        }
        return false;
    }
    
    public boolean setRating(float newRating) {
        if (newRating >= 0.0 && newRating <= 10.0) {
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

    public void setID(String newID){
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
