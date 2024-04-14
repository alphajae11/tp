// Part of the code is adapted from original AB3 Code. All credits and thanks to the original
// CS2103T teaching team for this.
package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.amount.Amount;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.attendance.Sessions;
import seedu.address.model.cca.Cca;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Metadata;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.roles.Role;


/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * OweCommand.
 */
public class OweCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Amount amount = new Amount("10.00");
        OweCommand oweCommand = new OweCommand(outOfBoundIndex, amount);

        assertCommandFailure(oweCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void testGetIndex() {
        Index expectedIndex = Index.fromOneBased(1);
        OweCommand oweCommand = new OweCommand(expectedIndex, new Amount("10.00"));
        assertEquals(expectedIndex, oweCommand.getIndex());
    }

    // The testGetAmount() is adapted from ChatGPT. (Reason: Quick add a simple test)
    @Test
    public void testGetAmount() {
        Amount expectedAmount = new Amount("100.0");
        OweCommand oweCommand = new OweCommand(INDEX_FIRST_PERSON, expectedAmount);
        assertEquals(expectedAmount, oweCommand.getAmount());
    }
    @Test
    public void testCreateOwedPerson() {
        // Arrange

        Person personToOwe = new Person(new Name("temp"), new Phone("94351252"),
                new Email("ewe@gmail.com"), new Address("secret"), Role.createRoleSet("member"),
                Cca.createCcaSet(), new Amount("100.0"), new Attendance("3"), new Sessions("5"),
                new Metadata("eating"));
        Amount amount = new Amount("100.0");

        // Act
        Person owedPerson = OweCommand.createOwedPerson(personToOwe, amount);

        // Assert
        assertEquals(personToOwe.getName(), owedPerson.getName());
        assertEquals(personToOwe.getPhone(), owedPerson.getPhone());
        assertEquals(personToOwe.getEmail(), owedPerson.getEmail());
        assertEquals(personToOwe.getAddress(), owedPerson.getAddress());
        assertEquals(personToOwe.getRoles(), owedPerson.getRoles());
        assertEquals(personToOwe.getCcas(), owedPerson.getCcas());
        assertEquals(amount, owedPerson.getAmount());
        assertEquals(personToOwe.getAtt(), owedPerson.getAtt());
        assertEquals(personToOwe.getSess(), owedPerson.getSess());
        assertEquals(personToOwe.getMetadata(), owedPerson.getMetadata());
    }

    @Test
    public void testToString() {
        // Arrange
        Index index = Index.fromOneBased(5);
        Amount amount = new Amount("100.00");
        OweCommand oweCommand = new OweCommand(index, amount);

        // Act
        String result = oweCommand.toString();

        // Assert
        String expected = "OweCommand{ index: " + index + ", amount: " + amount + "}";
        assertEquals(expected, result);
    }
    @Test
    public void equals() {
        Amount amount = new Amount("10.00");
        final OweCommand standardCommand = new OweCommand(INDEX_FIRST_PERSON, amount);

        // same values -> returns true
        OweCommand commandWithSameValues = new OweCommand(INDEX_FIRST_PERSON, amount);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertTrue(!standardCommand.equals(null));

        // different index -> returns false
        assertTrue(!standardCommand.equals(new OweCommand(INDEX_SECOND_PERSON, amount)));
    }
}
