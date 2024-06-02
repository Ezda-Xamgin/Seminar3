package integration;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import palew.DTO.ItemDTO;
import palew.integration.ExternalInventorySystem;

public class ExternalInventorySystemTest {

    private ExternalInventorySystem externalInventorySystem;

    @BeforeEach
    public void setUp() {
        externalInventorySystem = new ExternalInventorySystem();
    }

    @AfterEach
    public void tearDown() {
        externalInventorySystem = null;
    }

    @Test
    public void testSearchItemThatexists() {
        
        String existingItemID = "abc123";

        
        ItemDTO itemDTO = externalInventorySystem.searchItem(existingItemID);

        
        assertNotNull(itemDTO, "Item should exist in inventory.");
        assertEquals(existingItemID, itemDTO.getItemID(), "Item ID should match.");
    }

    @Test
    public void testSearchItemThatDoesNotExist() {

        String nonExistingItemID = "xyz999";
        ItemDTO itemDTO = externalInventorySystem.searchItem(nonExistingItemID);
        assertNull(itemDTO, "Item should not exist in inventory.");
    }

    
}

