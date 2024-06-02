package palew.DTO;

import palew.model.Amount;

/**
 * Represents the relevant information of a scanItem-call, 
 * including the item details, the price of the item,
 * and the current total price including VAT of the whole sale.
 * 
 */
public class SaleSummaryDTO {
    private final ItemAndRunningTotalDTO itemAndRunningTotal;
    private final Amount totalPriceIncludingVAT;
    private final Amount totalPrice;

    /**
     * Constructs a SaleSummaryDTO with the specified item details and the current
     * total price including VAT of the sale.
     *
     * @param itemAndRunningTotal the item details and running total
     * @param totalPrice the total price of the current sale
     * @param totalPriceIncludingVAT the total price of the current sale including VAT
     * @throws IllegalArgumentException if itemAndRunningTotal, totalPriceIncludingVAT, or totalPrice is null, 
     *                                  or if totalPriceIncludingVAT or totalPrice have negative values
     */
    public SaleSummaryDTO(ItemAndRunningTotalDTO itemAndRunningTotal, Amount totalPriceIncludingVAT, Amount totalPrice)
        throws IllegalArgumentException {
        if (itemAndRunningTotal == null) throw new IllegalArgumentException("the itemAndRunningTotalDTO must not be null.");
        if (totalPriceIncludingVAT == null) throw new IllegalArgumentException("Total price including VAT must not be null.");
        if (totalPrice == null) throw new IllegalArgumentException("Total price must not be null.");
        if (totalPriceIncludingVAT.getAmount() < 0) throw new IllegalArgumentException("Total price including VAT cannot be negative.");
        if (totalPrice.getAmount() < 0) throw new IllegalArgumentException("Total price cannot be negative.");

        this.itemAndRunningTotal = itemAndRunningTotal;
        this.totalPriceIncludingVAT = totalPriceIncludingVAT;
        this.totalPrice = totalPrice;
    }
    /**
     * Gets the item details and running total.
     * 
     * @return the item details and running total of the registered item
     */
    public ItemAndRunningTotalDTO getItemAndRunningTotal() {
        return itemAndRunningTotal;
    }

    /**
     * Gets the current total price of the sale.
     * 
     * @return the total price
     */
    public Amount getTotalPrice(){
        return totalPrice;
    }

    /**
     * Gets the current total price of the sale including VAT
     * 
     * @return the total price including VAT
     */
    public Amount getTotalPriceIncludingVAT() {
        return totalPriceIncludingVAT;
    }
}

