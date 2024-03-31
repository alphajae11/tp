// Part of the code is adpatated from original AB3 Code. All credits and thanks to the original
// CS2103T teaching team for this.

package seedu.address.model.amount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.storage.JsonAdaptedAmount;

public class AmountTest {

    @Test
    public void isValidAmount() {
        // null amount
        assertThrows(NullPointerException.class, () -> Amount.isValidAmount(null));

        // invalid amount
        assertFalse(Amount.isValidAmount("")); // empty string
        assertFalse(Amount.isValidAmount(" ")); // spaces only
        assertFalse(Amount.isValidAmount("phone")); // non-numeric
        assertFalse(Amount.isValidAmount("9011p041")); // alphabets within digits
        assertFalse(Amount.isValidAmount("9312 1534")); // spaces within digits

        // valid amount
        assertTrue(Amount.isValidAmount("911")); // exactly 3 numbers
        assertTrue(Amount.isValidAmount("93121534"));
    }

    @Test
    public void testHashCode() {
        Amount amount = new Amount("30");
        int expectedHashCode = Double.hashCode(30);
        assertEquals(expectedHashCode, amount.hashCode());
    }

    @Test
    public void testToString() {
        Amount amount = new Amount("100");
        assertEquals(amount.toString(), amount.value);
    }

    @Test
    public void testEquals() {
        Amount amount = new Amount("100");
        Amount amount2 = new Amount("100");
        Amount amount3 = new Amount("200");
        assertTrue(amount.equals(amount2));
        assertFalse(amount.equals(amount3));
        assertFalse(amount.equals(null));
        assertFalse(amount.equals("100"));
    }

    @Test
    public void testCompareTo() {
        Amount amount = new Amount("100");
        Amount amount2 = new Amount("100");
        Amount amount3 = new Amount("200");
        assertEquals(amount.compareTo(amount2), 0);
        assertEquals(amount.compareTo(amount3), -1);
        assertEquals(amount3.compareTo(amount), 1);
    }

    @Test
    public void testJsonAdaptedAmount() {
        JsonAdaptedAmount jsonAdaptedAmount = new JsonAdaptedAmount(new Amount("100"));
        assertEquals(jsonAdaptedAmount.getValue(), "100");
        try {
            assertEquals(jsonAdaptedAmount.toModelType(), new Amount("100"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
