package palew.main;

import palew.controller.Controller;
import palew.integration.ExternalAccountingSystem;
import palew.integration.ExternalInventorySystem;
import palew.integration.Printer;
import palew.model.Register;
import palew.view.View;

/**
 * The Main class contains the main-method which simulates a sale instance.
 * It creates the necessary system-objects and starts the execution
 * of the sale process.
 */
public class Main {

    /**
     * The main method is the entry point for the program.
     * It sets up the external systems, register, controller, and view, and then
     * starts the execution of the sale simulation.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {

        ExternalAccountingSystem accSystem = new ExternalAccountingSystem();
        ExternalInventorySystem invSsytem = new ExternalInventorySystem();
        Printer printer= new Printer();
        Register register = new Register();
        Controller contr = new Controller(printer, accSystem, invSsytem, register);
        View view = new View(contr);

        view.execution();
        

        
    }

}
