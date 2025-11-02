package src.domain;
import java.io.Serializable;

public class Sale implements Serializable {
    private VideoGame videoGame;
    private Customer customer;

    public Sale(VideoGame videoGame, Customer customer){
        setVideoGame(videoGame);
        setCustomer(customer);
    }

    public VideoGame getVideoGame(){
        return videoGame;
    }

    public Customer getCustomer(){
        return customer;
    }
    public void setVideoGame(VideoGame videoGame){
        if (videoGame == null) {
            throw new IllegalArgumentException("El videojuego no puede estar vacío");
        }
        this.videoGame = videoGame;
    }

    public void setCustomer(Customer customer){
        if (customer == null) {
            throw new IllegalArgumentException("El cliente no puede estar vacío");
        }
        this.customer = customer;
    }

}
