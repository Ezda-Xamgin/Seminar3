package model;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import palew.model.Amount;

public class AmountTest {

    private Amount amount;

    @BeforeEach
    public void setUp() {
        amount = new Amount(100.0);
    }

    @AfterEach
    public void tearDown() {
        amount = null;
    }

    @Test
    public void testGetAmount() {
        double value = amount.getAmount();
        assertEquals(100.0, value, "The amount value should be 100.0.");
    }

    @Test
    public void testMinus() {
        Amount amountToSubtract = new Amount(40.0);
        Amount result = amount.minus(amountToSubtract);
        assertEquals(60.0, result.getAmount(), "The result of subtraction should be 60.0.");
    }

    @Test
    public void testIsLessThanTrue() {
        Amount lesserAmount = new Amount(150.0);

        assertTrue(amount.isLessThan(lesserAmount), "Amount 100.0 should be less than 150.0.");
    }

    @Test
    public void testIsLessThanFalse() {
        Amount greaterAmount = new Amount(50.0);
        assertFalse(amount.isLessThan(greaterAmount), "Amount 100.0 should not be less than 50.0.");
    }
}
