package seedu.address.model.roles;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.parser.CliSyntax.NIL_FIELD;

import java.util.HashSet;
import java.util.Set;
/**
 * Represents a Role in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidRoleName(String)}
 */
public class Role {

    public static final String MESSAGE_CONSTRAINTS = "Role names should be non-empty and alphanumeric "
        + "(but can include whitespace).\n"
        + "Role names cannot be \"" + NIL_FIELD + "\" as \"" + NIL_FIELD
        + "\" is reserved for denoting an empty role field.";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}\\s]+";

    public final String roleName;

    /**
     * Constructs a {@code Role}.
     *
     * @param roleName A valid role name.
     */
    public Role(String roleName) {
        requireNonNull(roleName);
        checkArgument(isValidRoleName(roleName), MESSAGE_CONSTRAINTS);
        this.roleName = roleName;
    }

    /**
     * Creates a set of Role objects from a set of role names.(Test only)
     *
     * @param roleNames
     * @return Set of role Objects
     */
    public static Set<Role> createRoleSet(String... roleNames) {
        requireNonNull(roleNames);
        Set<Role> roles = new HashSet<>();
        for (String roleName : roleNames) {
            roles.add(new Role(roleName));
        }
        return roles;
    }

    /**
     * Returns true if a given string is a valid role name.
     */
    public static boolean isValidRoleName(String test) {
        return !test.equals(NIL_FIELD) && test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Role)) {
            return false;
        }

        Role otherRole = (Role) other;
        return roleName.equals(otherRole.roleName);
    }

    @Override
    public int hashCode() {
        return roleName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + roleName + ']';
    }

}
