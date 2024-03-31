// Part of the code is adpatated from original AB3 Code. All credits and thanks to the original
// CS2103T teaching team for this.
package seedu.address.logic.commands;

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
