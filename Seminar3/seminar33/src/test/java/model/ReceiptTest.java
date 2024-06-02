package model;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import palew.DTO.ItemAndRunningTotalDTO;
import palew.DTO.ItemDTO;
import palew.DTO.SaleStateDTO;
import palew.model.Amount;
import palew.model.Receipt;
import palew.model.Sale;

public class ReceiptTest {
    private Sale sale;
    private SaleStateDTO saleState;
    private ItemDTO item1;
    private ItemDTO item2;
    private List<ItemAndRunningTotalDTO> itemList;
    private Amount paidAmount;
    private Amount change;

    @BeforeEach
    public void setUp() {
        item1 = new ItemDTO("abc123", 30.0, 0.2, "A chair");
        item2 = new ItemDTO("def456", 20.0, 0.2, "A table");

        itemList = new ArrayList<>();
        itemList.add(new ItemAndRunningTotalDTO(item1, 1));
        itemList.add(new ItemAndRunningTotalDTO(item2, 2));

        sale = new Sale();
        for (ItemAndRunningTotalDTO itemAndRunningTotalDTO : itemList) {
            sale.registerItem(itemAndRunningTotalDTO.getItem(), itemAndRunningTotalDTO.getQuantity());
        }

        paidAmount = new Amount(100.0);
        change = new Amount(30.0);
        saleState = new SaleStateDTO(sale, paidAmount, change);
    }

    @AfterEach
    public void tearDown() {
        sale = null;
        saleState = null;
        item1 = null;
        item2 = null;
        itemList = null;
        paidAmount = null;
        change = null;
    }

    @Test
    public void testReceiptConstructor() {
        assertDoesNotThrow(() -> new Receipt(saleState), "Receipt constructor should not throw an exception.");
    }

    @Test
    public void testReceiptConstructorNullSaleState() {
        assertThrows(IllegalArgumentException.class, () -> {new Receipt(null);
        }, "Creating a Receipt with null SaleStateDTO should throw IllegalArgumentException.");
    }

    @Test
    public void testReceiptContainsAllSaleItems() {
        Receipt receipt = new Receipt(saleState);
        String receiptString = receipt.toString();

        for (ItemAndRunningTotalDTO itemAndRunningTotalDTO : itemList) {
            assertTrue(receiptString.contains(itemAndRunningTotalDTO.getItem().getItemDescription()),
                "Receipt should contain item description: " + itemAndRunningTotalDTO.getItem().getItemDescription());
            assertTrue(receiptString.contains(String.valueOf(itemAndRunningTotalDTO.getQuantity())),
                "Receipt should contain item quantity: " + itemAndRunningTotalDTO.getQuantity());
        }
    }

    @Test
    public void testReceiptContainsTotalPriceIncludingVAT() {
        Receipt receipt = new Receipt(saleState);
        String receiptString = receipt.toString();

        assertTrue(receiptString.contains(String.valueOf(saleState.getTotalPriceIncludingVAT().getAmount())),
            "Receipt should contain total price including VAT: " + saleState.getTotalPriceIncludingVAT().getAmount());
    }

    @Test
    public void testReceiptContainsPaidAmountAndChange() {
        Receipt receipt = new Receipt(saleState);
        String receiptString = receipt.toString();

        assertTrue(receiptString.contains(String.valueOf(saleState.getPaidAmount().getAmount())),
            "Receipt should contain paid amount: " + saleState.getPaidAmount().getAmount());
        assertTrue(receiptString.contains(String.valueOf(saleState.getChange().getAmount())),
            "Receipt should contain change amount: " + saleState.getChange().getAmount());
    }
}
