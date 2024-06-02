package palew.DTO;

import palew.model.Amount;

/**
 * This DTO represents an item with its details such as ID, price, VAT rate, and description.
 */
public class ItemDTO {

    // Essential information about each item
    private final String itemID;
    private final Amount price;
    private final double vatRate;
    private final String itemDescription;

    /**
     * Constructs an ItemDTO with the specified details.
     * 
     * @param itemID the identifier for the item
     * @param price the price of the item
     * @param vatRate the VAT rate applicable to the item (should be between 0 and 1)
     * @param itemDescription a brief description of the item
     * @throws IllegalArgumentException if itemID is null or empty, or if price is negative, 
     *                                  or if vatRate is not between 0 and 1, or if itemDescription is null or empty
     */
    public ItemDTO(String itemID, double price, double vatRate, String itemDescription) {
        if (itemID == null || itemID.trim().isEmpty()) throw new IllegalArgumentException("Item ID must be a valid non-empty string.");
        if (price < 0) throw new IllegalArgumentException("Price cannot be negative.");
        if (vatRate < 0 || vatRate > 1) throw new IllegalArgumentException("VAT rate must be between 0 and 1.");
        if (itemDescription == null || itemDescription.isEmpty()) throw new IllegalArgumentException("Please include an item description.");
        
        this.itemID = itemID;
        this.price = new Amount(price);
        this.vatRate = vatRate;
        this.itemDescription = itemDescription;
    }

    /**
     * Gets the ID for the item.
     * 
     * @return the item ID
     */
    public String getItemID() {
        return itemID;
    }

    /**
     * Gets the price of the item.
     * 
     * @return the price of the item
     */
    public Amount getPrice() {
        return price;
    }

    /**
     * Gets the VAT rate applicable to the item.
     * 
     * @return the VAT rate (between 0 and 1)
     */
    public double getVatRate() {
        return vatRate;
    }

    /**
     * Gets the description of the item.
     * 
     * @return the item description
     */
    public String getItemDescription() {
        return itemDescription;
    }
}
