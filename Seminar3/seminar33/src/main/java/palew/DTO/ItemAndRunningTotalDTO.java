package palew.DTO;
import palew.model.Amount;

/**
 * This class represents an item that has been added to the sale and its running total 
 * based on the quantity and VAT.
 */
public class ItemAndRunningTotalDTO {

    private final int quantity;
    private final ItemDTO item;
    private final Amount runningTotal;
    private final Amount totalIncludingVAT;

    /**
     * Constructs an ItemAndRunningTotalDTO with the specified item and quantity.
     *
     * @param item the item being purchased
     * @param quantity the quantity of the item being purchased
     * @throws IllegalArgumentException if item is null or quantity is less than or equal to 0
     */
    public ItemAndRunningTotalDTO(ItemDTO item, int quantity) throws IllegalArgumentException {
        if (item == null || quantity <= 0) {
            throw new IllegalArgumentException("Item cannot be null and quantity must be greater than 0");
        }
        this.item = item;
        this.quantity = quantity;
        this.runningTotal = new Amount(item.getPrice().getAmount() * quantity);
        this.totalIncludingVAT = new Amount(runningTotal.getAmount() * (1 + item.getVatRate()));
    }

    /**
     * Returns the quantity of the item.
     *
     * @return the quantity of the item
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Returns the item being purchased.
     *
     * @return the item being purchased
     */
    public ItemDTO getItem() {
        return item;
    }

    /**
     * Returns the total price for the item based on the quantity purchased.
     *
     * @return the total price for the item
     */
    public Amount getRunningTotal() {
        return runningTotal;
    }

    /**
     * Returns the total price including VAT for the item based on the quantity purchased.
     *
     * @return the total price including VAT for the item
     */
    public Amount getTotalIncludingVAT() {
        return totalIncludingVAT;
    }
}

