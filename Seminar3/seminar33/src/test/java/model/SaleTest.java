package model;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import palew.DTO.ItemAndRunningTotalDTO;
import palew.DTO.ItemDTO;
import palew.DTO.SaleStateDTO;
import palew.DTO.SaleSummaryDTO;
import palew.model.Amount;
import palew.model.Sale;

public class SaleTest {
    private Sale sale;

    @BeforeEach
    public void setUp() {
        sale = new Sale();
    }

    @AfterEach
    public void tearDown() {
        sale = null;
    }

    @Test
    public void testConstructor() {
        // Test the constructor to ensure it initializes the sale correctly
        sale = new Sale();
        assertNotNull(sale.getTimeOfSale());
        assertEquals(0, sale.getRunningTotal().getAmount());
        assertEquals(0, sale.getRunningTotalIncludingVAT().getAmount());
        assertFalse(sale.isCompleted());
        assertEquals(0, sale.getItems().size());
    }

    @Test
    public void testRegisterItem() {
        ItemDTO item = new ItemDTO("abc123", 30.0, 0.2, "A chair");
        int quantity = 2;
        SaleSummaryDTO saleSummaryDTO = sale.registerItem(item, quantity);
        assertNotNull(saleSummaryDTO, "SaleSummaryDTO should not be null.");
        assertEquals(item, saleSummaryDTO.getItemAndRunningTotal().getItem(),
                     "Registered item in SaleSummaryDTO should match the registered item.");
        assertEquals(quantity, saleSummaryDTO.getItemAndRunningTotal().getQuantity(),
                     "Quantity in SaleSummaryDTO should match the registered quantity.");
    }

    @Test
    public void testRecordPaymentInsufficientPayment() {
        sale.registerItem(new ItemDTO("abc123", 30.0, 0.2, "A chair"), 2);
        assertThrows(IllegalArgumentException.class, () -> {
            sale.recordPayment(new Amount(50));
        });
    }
    

    @Test
    public void testGetItems() {

        ItemDTO item1 = new ItemDTO("abc123", 30.0, 0.2, "A chair");
        ItemDTO item2 = new ItemDTO("def456", 20.0, 0.2, "A table");
        sale.registerItem(item1, 1);
        sale.registerItem(item2, 2);
        List<ItemAndRunningTotalDTO> items = sale.getItems();
        assertNotNull(items, "List of items should not be null.");
        assertEquals(2, items.size(), "Number of items should match the registered items.");
        assertEquals(item1, items.get(0).getItem(), "First item in list should match the first registered item.");
        assertEquals(item2, items.get(1).getItem(), "Second item in list should match the second registered item.");
    }

    @Test
    public void testRecordPaymentSufficientPayment() {

        sale = new Sale();
        sale.registerItem(new ItemDTO("abc123", 30.0, 0.2, "A chair"), 2);
        SaleStateDTO saleState = sale.recordPayment(new Amount(100));
        assertNotNull(saleState);
        assertTrue(sale.isCompleted());
        assertEquals(100.0, saleState.getPaidAmount().getAmount());
        assertEquals(28.0, saleState.getChange().getAmount());
    }
}
