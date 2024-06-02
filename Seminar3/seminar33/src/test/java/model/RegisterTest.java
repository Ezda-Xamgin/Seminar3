package model;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import palew.model.Amount;
import palew.model.Register;

public class RegisterTest {
    private Register register;

    @BeforeEach
    public void setUp() {
        register = new Register();
    }

    @AfterEach
    public void tearDown() {
        register = null;
    }

    @Test
    public void testUpdateBalance() {
        Amount initialBalance = register.getBalance();
        Amount amountToAdd = new Amount(100);
        register.updateBalance(amountToAdd);
        Amount newBalance = register.getBalance();
        assertEquals(initialBalance.getAmount() + amountToAdd.getAmount(), newBalance.getAmount(),
                     "Balance should be updated correctly with a positive amount.");
    }

    @Test
    public void testGetBalance() {
        Amount balance = register.getBalance();
        assertNotNull(balance, "Initial balance should not be null.");
        assertEquals(0, balance.getAmount(), "Initial balance should be 0.");
    }
}
