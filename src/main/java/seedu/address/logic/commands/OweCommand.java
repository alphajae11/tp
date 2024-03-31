// Part of the code is adpatated from original AB3 Code. All credits and thanks to the original
// CS2103T teaching team for this.
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.amount.Amount;
import seedu.address.model.cca.Cca;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.roles.Role;

/**
 * Owes the details of the person identified using the displayed index from the address book.
 */
public class OweCommand extends Command {
    public static final String COMMAND_WORD = "owe";

    // MESSAGE_USAGE below is modified from my Teammate's (AlphaJae) code
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Owes the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_AMOUNT + "AMOUNT]...\n"
            + "Example: " + COMMAND_WORD + " 2 "
            + PREFIX_AMOUNT + "10.00 ";

    public static final String MESSAGE_OWE_PERSON_SUCCESS = "Owed Person: %1$s";
    public static final String MESSAGE_NOT_OWE = "Amount should be provided here.";
    private final Index index;
    private final Amount amount;

    /**
     * @param index  of the person to assign
     * @param amount details of the role to assign the person with
     */
    public OweCommand(Index index, Amount amount) {
        requireNonNull(index);
        requireNonNull(amount);
        this.index = index;
        this.amount = amount;
    }

    public Index getIndex() {
        return index;
    }

    public Amount getAmount() {
        return amount;
    }

    // Solution below (equals method) is adapted from original AB3 author lzq.
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OweCommand // instanceof handles nulls
                && index.equals(((OweCommand) other).index)
                && amount.equals(((OweCommand) other).amount));
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToOwe = lastShownList.get(index.getZeroBased());
        Person owedPerson = createOwedPerson(personToOwe, amount);

        model.setPerson(personToOwe, owedPerson);
        return new CommandResult(String.format("Owed Person: $%s", owedPerson.getAmount().toString()));
    }

    private static Person createOwedPerson(Person personToOwe, Amount amount) {
        assert personToOwe != null;
        Name updatedName = personToOwe.getName();
        Phone updatedPhone = personToOwe.getPhone();
        Email updatedEmail = personToOwe.getEmail();
        Address updatedAddress = personToOwe.getAddress();
        Set<Role> updatedRoles = personToOwe.getRoles();
        Set<Cca> updatedCcas = personToOwe.getCcas();
        Amount updatedAmount = new Amount(amount.toString());
        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedRoles, updatedCcas,
                updatedAmount);
    }

    @Override
    public String toString() {
        return new StringBuilder().append("OweCommand{ index: ").append(index).append(", amount: ").append(amount)
                .append("}").toString();
    }
}
