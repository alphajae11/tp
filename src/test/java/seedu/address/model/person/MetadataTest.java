// Part of the code is adpatated from original AB3 Code. All credits and thanks to the original
// CS2103T teaching team for this.

package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.Assert;

public class MetadataTest {
    @Test
    public void isValidMetadata() {
        // null metadata
        Assert.assertThrows(NullPointerException.class, () -> Metadata.isValidMetadata(null));

        // invalid metadata
        assertFalse(Metadata.isValidMetadata("/")); // contains slash
        assertFalse(Metadata.isValidMetadata("peter/a")); // contains slash

        // valid metadata
        assertTrue(Metadata.isValidMetadata("I love to get drink every day"));
        assertTrue(Metadata.isValidMetadata("I sleep on the floor"));
        assertTrue(Metadata.isValidMetadata("I like to play soccer 2 times a week")); // alphanumeric characters
    }

    @Test
    public void equals() {
        Metadata metadata = new Metadata("I love to eat pear");

        // same values -> returns true
        assertTrue(metadata.equals(new Metadata("I love to eat pear")));

        // same object -> returns true
        assertTrue(metadata.equals(metadata));

        // null -> returns false
        assertFalse(metadata.equals(null));

        // different types -> returns false
        assertFalse(metadata.equals(5.0f));

        // different values -> returns false
        assertFalse(metadata.equals(new Metadata("I love to eat grape")));
    }
}
