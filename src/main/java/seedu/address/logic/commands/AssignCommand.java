// Part of the code is adapted from original AB3 Code. All credits and thanks to the original
// CS2103T teaching team for this.
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
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
 * Assigns role to the existing person in the CCA Manager
 */
public class AssignCommand extends Command {

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
    public static final String MESSAGE_NO_CCA = "Cannot assign role to person without a CCA.";

    private final Index index;
    private final AssignCommand.AssignPersonDescriptor assignPersonDescriptor;

    /**
     * @param index                  of the person to assign
     * @param assignPersonDescriptor details of the role to assign the person with
     */
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

        if (personToAssign.getCcas().isEmpty()) {
            throw new CommandException(MESSAGE_NO_CCA);
        }

        Person assignedPerson = createAssignedPerson(personToAssign, assignPersonDescriptor);

        if (!personToAssign.isSamePerson(assignedPerson) && model.hasPerson(assignedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToAssign, assignedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ASSIGN_PERSON_SUCCESS, Messages.format(assignedPerson)));
    }

    /**
     * Creates and returns an assigned person with details of the role
     *
     * @param personToAssign         person who will be assigned
     * @param assignPersonDescriptor details of the role to assign the person with
     * @return Person who is assigned with a role
     */
    private static Person createAssignedPerson(Person personToAssign,
                                               AssignCommand.AssignPersonDescriptor assignPersonDescriptor) {
        assert personToAssign != null;

        Set<Role> rolesToAppend = assignPersonDescriptor.getRole().orElse(personToAssign.getRoles());

        Name updatedName = personToAssign.getName();
        Phone updatedPhone = personToAssign.getPhone();
        Email updatedEmail = personToAssign.getEmail();
        Address updatedAddress = personToAssign.getAddress();
        Set<Cca> updatedCcas = personToAssign.getCcas();
        Set<Role> updatedRoles = merge(personToAssign.getRoles(), rolesToAppend);
        Amount updatedAmount = personToAssign.getAmount();
        Attendance updatedAttendance = personToAssign.getAtt();
        Sessions updatedSessions = personToAssign.getSess();
        Optional<Metadata> updatedMetadata = personToAssign.getMetadata();

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress,
                updatedRoles, updatedCcas, updatedAmount, updatedAttendance, updatedSessions, updatedMetadata);
    }

    private static <T> Set<T> merge(Collection<? extends T>... collections) {
        Set<T> newSet = new HashSet<T>();
        for (Collection<? extends T> collection : collections) {
            newSet.addAll(collection);
        }
        return newSet;
    }

    /**
     * Stores the details of the role to assign the person with.
     */
    public static class AssignPersonDescriptor {
        private Set<Role> role;

        public AssignPersonDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code roles} is used internally.
         */
        public AssignPersonDescriptor(AssignCommand.AssignPersonDescriptor toCopy) {
            setRole(toCopy.role);
        }

        /**
         * Returns true if at least all fields are edited.
         */
        public boolean isAnyFieldNotEdited() {
            return CollectionUtil.isNotNull(role);
        }

        public void setRole(Set<Role> role) {
            this.role = role;
        }

        public Optional<Set<Role>> getRole() {
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
