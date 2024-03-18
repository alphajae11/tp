package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

import java.util.*;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

public class AssignCommand extends Command{

    public static final String COMMAND_WORD = "assign";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_ROLE + "ROLE]...\n"
            + "Example: " + COMMAND_WORD + " 2 "
            + PREFIX_ROLE + "Head ";

    public static final String MESSAGE_ASSIGN_PERSON_SUCCESS = "Assigned Person: %1$s";
    public static final String MESSAGE_NOT_ASSIGNED = "Role should be provided here.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final AssignCommand.AssignPersonDescriptor assignPersonDescriptor;

    public AssignCommand(Index index, AssignCommand.AssignPersonDescriptor assignPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(assignPersonDescriptor);

        this.index = index;
        this.assignPersonDescriptor = new AssignCommand.AssignPersonDescriptor(assignPersonDescriptor);
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToAssign = lastShownList.get(index.getZeroBased());
        Person AssignedPerson = createAssignedPerson(personToAssign, assignPersonDescriptor);

        if (!personToAssign.isSamePerson(AssignedPerson) && model.hasPerson(AssignedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToAssign, AssignedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ASSIGN_PERSON_SUCCESS, Messages.format(AssignedPerson)));
    }

    private static Person createAssignedPerson(Person personToAssign, AssignCommand.AssignPersonDescriptor assignPersonDescriptor) {
        assert personToAssign != null;

        Name updatedName = personToAssign.getName();
        Phone updatedPhone = personToAssign.getPhone();
        Email updatedEmail = personToAssign.getEmail();
        Address updatedAddress = personToAssign.getAddress();
        Set<Tag> updatedTags = assignPersonDescriptor.getRole().orElse(personToAssign.getTags());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags);
    }

    public static class AssignPersonDescriptor {
        private Set<Tag> role;

        public AssignPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public AssignPersonDescriptor(AssignCommand.AssignPersonDescriptor toCopy) {
            setRole(toCopy.role);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldNotEdited() {
            return CollectionUtil.isNotNull(role);
        }

        public void setRole(Set<Tag> role) {
            this.role = role;
        }

        public Optional<Set<Tag>> getRole() {
            return (role != null) ? Optional.of(Collections.unmodifiableSet(role)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof AssignCommand.AssignPersonDescriptor)) {
                return false;
            }

            AssignCommand.AssignPersonDescriptor otherAssignDescriptor = (AssignCommand.AssignPersonDescriptor) other;
            return Objects.equals(role, otherAssignDescriptor.role);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("role", role)
                    .toString();
        }
    }
}
