package domain;

public class Customer {
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
            this.name = "Unknown";  
        } else {
            this.name = name.trim();
        }
    }

    public void setID(int ID){
        if (ID < 0){
            this.ID = -1;
        } else {
            this.ID = ID;
        }
    }

    public void setBalance(float balance){
        if (balance < 0){
            this.balance = -1;
        } else{
            this.balance = balance;
        }
    }
}
