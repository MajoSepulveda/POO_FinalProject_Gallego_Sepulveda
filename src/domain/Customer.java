package src.domain;
import java.io.Serializable;

public class Customer implements Serializable, Displayable {
    private String name;
    private int id;
    private float balance;

    public Customer(String name, int id, float balance) {
        if (id <= 0) throw new IllegalArgumentException("El id del cliente no puede ser negativo.");
        this.id = id;
        setName(name);
        setBalance(balance);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getBalance() {
        return balance;
    }

    public void setName(String name) {
        if (name == null || name.trim().isBlank()) throw new IllegalArgumentException("El nombre del cliente no puede estar vacío.");
        this.name = name.trim();
    }

    public void setBalance(float balance) {
        if (balance < 0)throw new IllegalArgumentException("El saldo del cliente no puede ser negativo.");
        this.balance = balance;
    }

    public void reduceBalance(float amount) {
        if (amount < 0) throw new IllegalArgumentException("El monto a reducir no puede ser negativo.");
        else if (amount > balance) throw new IllegalArgumentException("El monto a reducir no puede ser mayor que el saldo actual.");
        balance -= amount;
    }

    public void addBalance(float amount) {
        if (amount < 0) throw new IllegalArgumentException("El monto a agregar no puede ser negativo.");
        balance += amount;
    }

    @Override
    public String displayObject() {
        return String.format("Cliente ID: %d | Nombre: %s | Saldo: $%.2f", id, name, balance);
    }
}
