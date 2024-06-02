package palew.model;

import java.util.Objects;

/**
 * This class represents a monetary amount used in the model
 */
public class Amount {
    private final double amount; 

    /**
     * Constructs an Amount with the specified value.
     *
     * @param amount the value of the amount
     */
    public Amount(double amount) {
        this.amount = amount;
    }

    /**
     * Returns the value of the amount.
     *
     * @return the value of the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Subtracts a specified amount from this amount and returns the result as a new Amount.
     *
     * @param amount the amount to subtract
     * @return a new Amount representing the result of the subtraction
     */
    public Amount minus(Amount amount) {
        return new Amount(this.amount - amount.getAmount());
    }

    /**
     * Compares if this amount is less than a specified amount.
     *
     * @param amount the amount to compare with
     * @return true if this amount is less than the specified amount, false otherwise
     */
    public boolean isLessThan(Amount amount) {
        return this.amount < amount.amount;
    }


    @Override
    /**
     * Indicates whether some other object is "equal to" this amount instance.
     * Overrides the default equals method to provide custom equality logic for Amount objects.
     * 
     * @param obj the object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    public boolean equals(Object obj) {

        if (this == obj) return true;
        

        if (obj == null || getClass() != obj.getClass()) return false;
        

        Amount amount1 = (Amount) obj;
        return Double.compare(amount1.amount, amount) == 0;
    }
    
    @Override
    /**
     * Returns a hash code value for the object.
     * Overrides the default hashCode method to provide a custom hash code for Amount objects.
     * 
     * @return a hash code value for this object.
     */
    public int hashCode() {
        return Objects.hash(amount);
    }
    
    /**
     * Returns a string representation of the amount, formatted to two decimal places.
     *
     * @return a string representation of the amount
     */
    @Override
    public String toString() {
        return String.format("%.2f", amount);
    }
}
