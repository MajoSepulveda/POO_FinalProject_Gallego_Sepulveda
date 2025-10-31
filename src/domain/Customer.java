package src.domain;
import java.io.Serializable;

public class Customer implements Serializable {
    private String name;
    private int ID;
    private float balance;

    public Customer(String name, int ID, float balance){
        setName(name);
        setID(ID);
        setBalance(balance);
    }

    public String getName(){
        return name;
    }

    public int getID(){
        return ID;
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

    public void setID(int ID){
        if (ID < 0){
            throw new IllegalArgumentException("El ID del cliente no puede ser negativo.");
        }
        this.ID = ID;
    }

    public void setBalance(float balance){
        if (balance < 0){
            throw new IllegalArgumentException("El saldo del cliente no puede ser negativo.");
        }
        this.balance = balance;
    }
}
