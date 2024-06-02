package palew.integration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import palew.DTO.ItemAndRunningTotalDTO;
import palew.DTO.ItemDTO;

/**
 * Implementation of the external inventory system.
 */
public class ExternalInventorySystem {
    private Map<String, ItemDTO> inventory;

    /**
     * Creates an instance of ExternalInventorySystem. Simulates the external inventory system
     * by initializing a collection of ItemDTOs that can be used in the sale instance.
     */
    public ExternalInventorySystem() {
        this.inventory = createInventory();
    }

    /**
     * Initializes the inventory with some predefined items.
     *
     * @return a map representing the inventory with item identifiers as keys and ItemDTOs as values
     */
    private Map<String, ItemDTO> createInventory() {
        Map<String, ItemDTO> inventory = new HashMap<>();
        inventory.put("abc123", new ItemDTO("abc123", 30.0, 0.2, "A chair"));
        inventory.put("def456", new ItemDTO("def456", 20.0, 0.2, "A table"));
        inventory.put("ghi789", new ItemDTO("ghi789", 15.0, 0.2, "A lamp"));
        return inventory;
    }

    /**
     * Searches inventory for the item with the given item identifier.
     *
     * @param itemID the itemID to search for
     * @return the found item as ItemDTO, or null if not found
     */
    public ItemDTO searchItem(String itemID) {
        return inventory.get(itemID);
    }

    /**
     * Updates the external inventory system with the quantities sold during a sale.
     *
     * @param itemList the list of items and their quantities
     */
    public void updateInventory(List<ItemAndRunningTotalDTO> itemList) {
        
    }
}

