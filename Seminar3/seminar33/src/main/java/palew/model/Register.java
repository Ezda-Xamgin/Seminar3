package palew.model;

/**
 * Represents a cash register that keeps track of the balance.
 */
public class Register {
    private Amount balance;

    /**
     * Constructs a Register with an initial balance of 0.
     */
    public Register() {
        this.balance = new Amount(0);
    }

    /**
     * Updates the balance by adding the specified amount.
     * 
     * @param amount the amount to add to the balance.
     * @throws IllegalArgumentException if amount is null
     */
    public void updateBalance(Amount amount) throws IllegalArgumentException {
        if (amount == null) throw new IllegalArgumentException("Amount must not be null");
        balance = new Amount(balance.getAmount() + amount.getAmount());
    }

    /**
     * Gets the current balance of the register.
     * 
     * @return the current balance.
     */
    public Amount getBalance() {
        return balance;
    }
}