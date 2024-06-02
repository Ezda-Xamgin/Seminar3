package palew.model;

import java.time.format.DateTimeFormatter;
import java.util.List;

import palew.DTO.ItemAndRunningTotalDTO;
import palew.DTO.SaleStateDTO;

/**
 * Represents a receipt printed after a payment has been made.
 * Proves payment for the sale.
 */
public class Receipt {
    private final SaleStateDTO saleState;

    /**
     * Creates a new instance of Receipt.
     * 
     * @param saleState the information about the state of the sale 
     * including items, total price including VAT, paid amount, and change.
     * @throws IllegalArgumentException if the DTO is null
     */
    public Receipt(SaleStateDTO saleState) throws IllegalArgumentException{
        if (saleState == null) throw new IllegalArgumentException("Sale state DTO must not be null.");
        this.saleState = saleState;
    }

    /**
     * Returns a formatted String with the information in the Receipt.
     * 
     * @return a String representation of the Receipt object.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        builder.append("----------------------- BEGINNING OF RECEIPT -----------------------\n");
        builder.append("Time of sale: ").append(saleState.getTimeOfSale().format(dateFormatter)).append(" ").append(saleState.getTimeOfSale().format(timeFormatter)).append("\n");
        
        List<ItemAndRunningTotalDTO> itemList = saleState.getItemList();
        for (ItemAndRunningTotalDTO item : itemList) {
            builder.append(item.getItem().getItemDescription()).append(" ")
                   .append(item.getQuantity()).append(" x ")
                   .append(item.getItem().getPrice().getAmount()).append(" ")
                   .append(item.getRunningTotal().getAmount()).append(" SEK\n");
        }

        builder.append("\nTotal: ").append(saleState.getTotalPriceIncludingVAT().getAmount()).append(" SEK\n");
        builder.append("VAT: ").append(String.format("%.2f", calculateTotalVAT())).append(" SEK\n");
        builder.append("\nCash: ").append(saleState.getPaidAmount().getAmount()).append(" SEK\n");
        builder.append("Change: ").append(saleState.getChange().getAmount()).append(" SEK\n");
        builder.append("----------------------- END OF RECEIPT -----------------------");

        return builder.toString();
    }

    /**
     * Calculates the total VAT amount from the sale items to represent in the receipt.
     * 
     * @return the total VAT amount.
     */
    private double calculateTotalVAT() {
        double totalVAT = 0;
        for (ItemAndRunningTotalDTO item : saleState.getItemList()) {
            double itemNetPrice = item.getItem().getPrice().getAmount();
            double itemVAT = itemNetPrice * item.getItem().getVatRate();
            totalVAT += itemVAT * item.getQuantity();
        }
        return totalVAT;
    }
}
