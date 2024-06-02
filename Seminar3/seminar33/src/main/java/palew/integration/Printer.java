package palew.integration;

import palew.model.Receipt;

/*
 * This class represents the printer in the sale flow
 */
public class Printer {

    /**
    * Prints the details of the receipt to the console.
    * 
    * @param receipt the receipt to be printed
    */
    public void print(Receipt receipt) {
        System.out.println(receipt.toString());
    }

}
