package src.domain;
import java.io.Serializable;

/**
 * Represents a customer entity in the store system. 
 * Implements Serializable for persistence and Displayable for console output.
 */
public class Customer implements Serializable, Displayable {
    private String name;
    private int id;
    private float balance;

    /**
     * Constructor for the Customer class.
     * @param name The name of the customer.
     * @param id The unique identifier for the customer.
     * @param balance The current account balance.
     * @throws IllegalArgumentException if id is non-positive.
     */
    public Customer(String name, int id, float balance) {
        if (id <= 0) throw new IllegalArgumentException("El id del cliente no puede ser negativo.");
        this.id = id;
        setName(name);
        setBalance(balance);
    }

    /**
     * @return The customer's unique identifier.
     */
    public int getId() {
        return id;
    }

    /**
     * @return The customer's name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The customer's current account balance.
     */
    public float getBalance() {
        return balance;
    }

    /**
     * Sets the customer's name.
     * @param name The new name for the customer.
     * @throws IllegalArgumentException if the name is null, empty, or only whitespace.
     */
    public void setName(String name) {
        if (name == null || name.trim().isBlank()) throw new IllegalArgumentException("El nombre del cliente no puede estar vacío.");
        this.name = name.trim();
    }

    /**
     * Sets the customer's account balance.
     * @param balance The new balance amount.
     * @throws IllegalArgumentException if the balance is negative.
     */
    public void setBalance(float balance) {
        if (balance < 0)throw new IllegalArgumentException("El saldo del cliente no puede ser negativo.");
        this.balance = balance;
    }

    /**
     * Reduces the customer's balance by a specified amount (used for purchases).
     * @param amount The amount to deduct from the balance.
     * @throws IllegalArgumentException if the amount is negative or exceeds the current balance.
     */
    public void reduceBalance(float amount) {
        if (amount < 0) throw new IllegalArgumentException("El monto a reducir no puede ser negativo.");
        else if (amount > balance) throw new IllegalArgumentException("El monto a reducir no puede ser mayor que el saldo actual.");
        balance -= amount;
    }

    /**
     * Increases the customer's balance by a specified amount (used for deposits/top-ups).
     * @param amount The amount to add to the balance.
     * @throws IllegalArgumentException if the amount is negative.
     */
    public void addBalance(float amount) {
        if (amount < 0) throw new IllegalArgumentException("El monto a agregar no puede ser negativo.");
        balance += amount;
    }

    /**
     * Provides a concise and formatted summary of the customer data for console output.
     * Implements the Displayable interface.
     * @return A formatted string with customer ID, name, and balance.
     */
    @Override
    public String displayObject() {
        return String.format("Cliente ID: %d | Nombre: %s | Saldo: $%.2f", id, name, balance);
    }
}
