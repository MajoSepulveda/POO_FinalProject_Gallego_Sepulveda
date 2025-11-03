package domain;
import java.io.Serializable;

public class Customer implements Serializable {
    private String name;
    private int ID;
    private float balance;

    public Customer(String name, int ID, float balance){
        this.ID = ID;
        setName(name);
        setBalance(balance);
    }

    public int getID(){
        return ID;
    }

    public String getName(){
        return name;
    }

    public float getBalance(){
        return balance;
    }

    public void setName(String name){
        if (name == null || name.trim().isBlank()){
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacío."); 
        }
        this.name = name.trim();
    }

    public void setBalance(float balance){
        if (balance < 0){
            throw new IllegalArgumentException("El saldo del cliente no puede ser negativo.");
        }
        this.balance = balance;
    }
}
