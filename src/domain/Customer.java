package domain;

public class Customer {
    private static final String DEFAULT_NAME = "Unknown";
    private static final int INVALID_ID = -1;
    private static final float INVALID_BALANCE = -1;
    
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
            this.name = DEFAULT_NAME;  
        } else {
            this.name = name.trim();
        }
    }

    public void setID(int ID){
        if (ID < 0){
            this.ID = INVALID_ID;
        } else {
            this.ID = ID;
        }
    }

    public void setBalance(float balance){
        if (balance < 0){
            this.balance = INVALID_BALANCE;
        } else {
            this.balance = balance;
        }
    }
}
