package palew.controller;

import palew.DTO.ItemDTO;
import palew.DTO.SaleStateDTO;
import palew.DTO.SaleSummaryDTO;
import palew.integration.ExternalAccountingSystem;
import palew.integration.ExternalInventorySystem;
import palew.integration.Printer;
import palew.model.Amount;
import palew.model.Receipt;
import palew.model.Register;
import palew.model.Sale;

/**
 * This class is the only controller class in the project and is responsible for 
 * making calls to the model.
 */
public class Controller {
    private Sale sale;
    private Receipt receipt;
    private final ExternalAccountingSystem accSystem;
    private final ExternalInventorySystem invSystem;
    private final Printer printer;
    private final Register register;

    /**
     * Constructs a Controller with refrencess to the specified systems.
     *
     * @param printer the printer to use for printing receipts
     * @param accSystem the external accounting system to use
     * @param invSystem the external inventory system to use
     * @param register the register to use for handling cash transactions
     * @throws IllegalArgumentException if any of the parameters are null
     */
    public Controller(Printer printer, ExternalAccountingSystem accSystem, ExternalInventorySystem invSystem, Register register)
            throws IllegalArgumentException {
        if (printer == null || accSystem == null || invSystem == null || register == null) {
            throw new IllegalArgumentException("None of the parameters can be null");
        }
        this.printer = printer;
        this.accSystem = accSystem;
        this.invSystem = invSystem;
        this.register = register;
        this.sale = null;
        this.receipt = null;
    }

    /**
     * Starts a new sale.
     */
    public void startSale() {
        sale = new Sale();
    }

    /**
     * Ends the current sale and returns the total price including VAT.
     *
     * @return the total price including VAT of the current sale
     * @throws IllegalStateException if no sale is in progress
     */
    public Amount endSale() throws IllegalStateException{
        if (sale == null) {
            throw new IllegalStateException("No sale in progress. Call startSale() first.");
        }
        return sale.getRunningTotalIncludingVAT();
    }



    /**
     * Scans an item with specified item ID and quantity, records it in the sale, and
     * returns item cost and the current running total.
     *
     * @param itemID the ID of the item to scan
     * @param quantity the quantity of the item to scan
     * @return a SaleSummaryDTO containing information about 
     * the scanned item and the running total of the updated sale
     */
    public SaleSummaryDTO scanItem(String itemID, int quantity){
        ItemDTO item = invSystem.searchItem(itemID);
        return sale.registerItem(item, quantity);
    }



    /**
     * Scans an item with specified item ID and no quantity specified (program assumes a quantity of 1).
     *
     * @param itemID the ID of the item to scan
     * @return a SaleSummaryDTO containing information about 
     * the scanned item and the running total of the updated sale
     */
    public SaleSummaryDTO scanItem(String itemID){
        ItemDTO item = invSystem.searchItem(itemID);
        return sale.registerItem(item, 1);
    }


    /**
    * Records a payment by completing the sale, and returns the sale state information 
    *+ updates all the external systems as well as the balance of the register.
    *Ends by Printing the receipt and returns the change amount.
    *
    * @param amount the amount paid by the customer
    * @return change amount
    * @throws IllegalStateException if no sale is in progress
    */
    public Amount recordPayment (Amount amount)throws IllegalStateException{
        if (sale == null) {
            throw new IllegalStateException("No sale in progress. Call startSale() first.");
        }
        
        SaleStateDTO saleState = sale.recordPayment(amount);

        // Send sale information to external systems
        accSystem.recordSale(saleState);
        invSystem.updateInventory(saleState.getItemList());
        register.updateBalance(amount);
        receipt = new Receipt(saleState);
        printer.print(receipt);
        

        return saleState.getChange();
        
    }
}

