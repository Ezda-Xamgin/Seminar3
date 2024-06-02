package palew.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import palew.DTO.ItemAndRunningTotalDTO;
import palew.DTO.ItemDTO;
import palew.DTO.SaleStateDTO;
import palew.DTO.SaleSummaryDTO;

/**
 * Represents a sale transaction, including the items purchased, their quantities,
 * the total price, and the time of the sale.
 */
public class Sale {
    private LocalDateTime timeOfSale; // The time when the sale was made
    private Amount totalPrice; // The running total price of the sale
    private Amount totalPriceIncludingVAT; // The running total price of the sale including VAT
    private List<ItemAndRunningTotalDTO> items; // List to store the items and their quantities
    private boolean isCompleted; // Flag to indicate if the sale is completed (Valid payment has been made)

    /**
     * Initializes a new instance of the Sale class, 
     * sets the time of the sale, initializes the items list,
     * and sets the initial total prices to zero.
     */
    public Sale() {
        setTimeOfSale();
        items = new ArrayList<>();
        totalPrice = new Amount(0);
        totalPriceIncludingVAT = new Amount(0);
        isCompleted = false;
    }

    /**
     * Sets the time of the sale to the current time.
     */
    private void setTimeOfSale() {
        this.timeOfSale = LocalDateTime.now();
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
     * Gets the running total price of the sale.
     * 
     * @return the running total price
     */
    public Amount getRunningTotal() {
        return totalPrice;
    }

    /**
     * Gets the running total price of the sale including VAT.
     * 
     * @return the running total price including VAT
     */
    public Amount getRunningTotalIncludingVAT() {
        return totalPriceIncludingVAT;
    }

    /**
     * Registers an item in the sale with the specified quantity.
     * 
     * @param item the item being registered
     * @param quantity the quantity of the item being registered
     * @return a SaleSummaryDTO containing the item, quantity, total price,
     *         and the total price including VAT
     * @throws IllegalArgumentException if item is null or quantity is less than or equal to 0
     */
    public SaleSummaryDTO registerItem(ItemDTO item, int quantity) throws IllegalArgumentException {
        if (item == null || quantity <= 0) {
            throw new IllegalArgumentException("Item cannot be null and quantity must be greater than 0");
        }

        ItemAndRunningTotalDTO itemAndRunningTotalDTO = updateSaleState(item, quantity);

        return new SaleSummaryDTO(itemAndRunningTotalDTO, totalPriceIncludingVAT, totalPrice);
    }

    /**
     * Updates the sale state with a new item by adjusting the total prices and
     * adding the item to the list.
     * 
     * @param item the item being purchased
     * @param quantity the quantity of the item being purchased
     * @return the updated ItemAndRunningTotalDTO
     */
    private ItemAndRunningTotalDTO updateSaleState(ItemDTO item, int quantity) {
        int index = indexIfAlreadyRegistered(item);
    
        ItemAndRunningTotalDTO itemAndRunningTotalDTO;
        if (index != -1) {
            ItemAndRunningTotalDTO existingItem = items.get(index);
            int newQuantity = existingItem.getQuantity() + quantity;
            itemAndRunningTotalDTO = new ItemAndRunningTotalDTO(item, newQuantity);
            items.set(index, itemAndRunningTotalDTO);
        } else {
            itemAndRunningTotalDTO = new ItemAndRunningTotalDTO(item, quantity);
            items.add(itemAndRunningTotalDTO);
        }
    
        Amount itemTotal = new Amount(item.getPrice().getAmount() * quantity); // Calculate the total price for the item
        Amount itemTotalIncludingVAT = new Amount(itemTotal.getAmount() * (1 + item.getVatRate())); // Calculate the total price including VAT
        totalPrice = new Amount(totalPrice.getAmount() + itemTotal.getAmount()); // Update the running total price
        totalPriceIncludingVAT = new Amount(totalPriceIncludingVAT.getAmount() + itemTotalIncludingVAT.getAmount()); // Update the running total price including VAT
    
        return itemAndRunningTotalDTO;
    }
    

    /**
     * Checks if an item is already registered in the sale and returns its index.
     * 
     * @param searchedItem the item being searched for
     * @return the index of the item if found, -1 otherwise
     */
    private int indexIfAlreadyRegistered(ItemDTO searchedItem) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getItem().getItemID().equals(searchedItem.getItemID())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Completes the sale, records the payment, and returns the sale state DTO.
     * 
     * @param paidAmount the amount paid by the customer
     * @return a SaleStateDTO containing the sale state information which is to be sent to external systems
     * @throws IllegalArgumentException if the paid amount is less than the total price including VAT
     */
    public SaleStateDTO recordPayment(Amount paidAmount) throws IllegalArgumentException {
        if (paidAmount.isLessThan(totalPriceIncludingVAT)) {
            throw new IllegalArgumentException("Insufficient payment amount.");
        }
        Amount change = paidAmount.minus(totalPriceIncludingVAT);
        isCompleted = true;
        return new SaleStateDTO(this, paidAmount, change);
    }

    /**
     * Gets the list of registered items in the sale.
     * 
     * @return a list of ItemAndRunningTotalDTO objects representing the items and their quantities
     */
    public List<ItemAndRunningTotalDTO> getItems() {
        return new ArrayList<>(items); 
    }


    /**
     * Returns the state of the sale
     * 
     * @return a True value if a valid payment has been recorded, otherwise it returns false
     */
    public boolean isCompleted() {
        return isCompleted;
    }
}
