package palew.view;

import palew.DTO.SaleSummaryDTO;
import palew.controller.Controller;
import palew.model.Amount;

public class View{
    private final Controller contr;
    
    /**
     * Creates a new instance of View.
     * 
     * @param contr Controller that is used to call on all operations.
     */
    public View(Controller contr){
        this.contr = contr;
    }
    
    /**
     * Example of performing a sale, 
     */
    public void execution(){
        contr.startSale();
        SaleSummaryDTO saleSummary;

        // Adding the first item with item id "abc123"
        System.out.println("Add 1 item with item id abc123:");
        saleSummary = contr.scanItem("abc123");
        System.out.println(saleSummaryDTOString(saleSummary));

        // Adding the second item with item id "abc123"
        System.out.println("Add 1 item with item id abc123:");
        saleSummary = contr.scanItem("abc123");
        System.out.println(saleSummaryDTOString(saleSummary));

        // Adding the item with item id "def456"
        System.out.println("Add 1 item with item id def456:");
        saleSummary = contr.scanItem("def456");
        System.out.println(saleSummaryDTOString(saleSummary));

        // Ending the sale
        double totalPriceIncludingVAT = contr.endSale().getAmount();
        System.out.println("End sale:");
        System.out.println("Total cost (incl VAT): " + totalPriceIncludingVAT + " SEK\n");

        // Customer pays
        Amount paidAmount = new Amount(200);
        System.out.println("Customer pays " + paidAmount + " SEK:");
        Amount change = contr.recordPayment(paidAmount);
        System.out.println("Change: " + change + " SEK");


        //Inform that systems have been updated after payment was successful
        System.out.println("External inventory system has been updated");
        System.out.println("External accounting system has been updated");
        System.out.println("Register balance has been updated");

    }

    private String saleSummaryDTOString(SaleSummaryDTO saleSummary) {
        StringBuilder builder = new StringBuilder();
        builder.append("Item ID: ").append(saleSummary.getItemAndRunningTotal().getItem().getItemID()).append("\n");
        builder.append("Item name: ").append(saleSummary.getItemAndRunningTotal().getItem().getItemDescription()).append("\n");
        builder.append("Item cost: ").append(saleSummary.getItemAndRunningTotal().getItem().getPrice()).append(" SEK\n");
        builder.append("VAT: ").append(saleSummary.getItemAndRunningTotal().getItem().getVatRate() * 100).append("%\n");
        builder.append("Item description: ").append(saleSummary.getItemAndRunningTotal().getItem().getItemDescription()).append("\n\n");
        builder.append("Total cost (incl VAT): ").append(saleSummary.getTotalPriceIncludingVAT()).append(" SEK\n");
        double totalVAT = saleSummary.getTotalPriceIncludingVAT().getAmount() - saleSummary.getTotalPrice().getAmount();
        builder.append("Total VAT: ").append(String.format("%.2f", totalVAT)).append(" SEK\n");
        return builder.toString();
    }
    
}