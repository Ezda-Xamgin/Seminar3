package palew.DTO;

import java.time.LocalDateTime;
import java.util.List;

import palew.model.Amount;
import palew.model.Sale;

/**
 * This class represents the state of a sale, including
 * details such as the time of the sale, the list of items purchased, the total
 * price including VAT, the amount paid by the customer, and the change.
 * It's purpose is to send a copy of all the sale state information to the external systems 
 * after a payment has been recorded
 */
public class SaleStateDTO {
    private final LocalDateTime timeOfSale;
    private final List<ItemAndRunningTotalDTO> itemList;
    private final Amount totalPriceIncludingVAT;
    private final Amount paidAmount;
    private final Amount change;

    /**
     * Constructs a SaleStateDTO using the given Sale object, paid amount, and change.
     * 
     * @param sale the Sale object containing the details of the sale
     * @param paidAmount the amount paid by the customer
     * @param change the change to be given back to the customer
     * @throws IllegalArgumentException if sale, paidAmount, or change is null, 
     *                                  or if paidAmount or change have negative values
     */
    public SaleStateDTO(Sale sale, Amount paidAmount, Amount change) throws IllegalArgumentException {
        if (sale == null) throw new IllegalArgumentException("Sale must not be null.");
        if (paidAmount == null) throw new IllegalArgumentException("Paid amount must not be null.");
        if (change == null) throw new IllegalArgumentException("Change must not be null.");
        if (paidAmount.getAmount() < 0) throw new IllegalArgumentException("Paid amount cannot be negative.");
        if (change.getAmount() < 0) throw new IllegalArgumentException("Change cannot be negative.");

        this.timeOfSale = sale.getTimeOfSale();
        this.itemList = sale.getItems();
        this.totalPriceIncludingVAT = sale.getRunningTotalIncludingVAT();
        this.paidAmount = paidAmount;
        this.change = change;
    }

    /**
     * Gets the time when the sale was made.
     * 
     * @return the time of the sale
     */
    public LocalDateTime getTimeOfSale() {
        return timeOfSale;
    }

    /**
     * Gets the list of items registered in the sale.
     * 
     * @return a list of ItemAndRunningTotalDTO objects representing the items and their quantities
     */
    public List<ItemAndRunningTotalDTO> getItemList() {
        return itemList;
    }

    /**
     * Gets the total price of the sale including VAT.
     * 
     * @return the total price including VAT
     */
    public Amount getTotalPriceIncludingVAT() {
        return totalPriceIncludingVAT;
    }

    /**
     * Gets the amount paid by the customer.
     * 
     * @return the amount paid by the customer
     */
    public Amount getPaidAmount() {
        return paidAmount;
    }

    /**
     * Gets the change to be given back to the customer.
     * 
     * @return the change to be given back to the customer
     */
    public Amount getChange() {
        return change;
    }
}

