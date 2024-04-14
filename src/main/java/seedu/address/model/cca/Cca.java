package seedu.address.model.cca;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.parser.CliSyntax.NIL_FIELD;

import java.util.HashSet;
import java.util.Set;
/**
 * Represents a CCA in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidCcaName(String)}
 */
public class Cca {

    public static final String MESSAGE_CONSTRAINTS = "CCA names should be non-empty and alphanumeric "
        + "(but can include whitespace).\n"
        + "CCA names cannot be \"" + NIL_FIELD + "\" as \"" + NIL_FIELD
        + "\" is reserved for denoting an empty CCA field.";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}\\s]+";

    public final String ccaName;

    /**
     * Constructs a {@code Cca}.
     *
     * @param ccaName A valid CCA name.
     */
    public Cca(String ccaName) {
        requireNonNull(ccaName);
        checkArgument(isValidCcaName(ccaName), MESSAGE_CONSTRAINTS);
        this.ccaName = ccaName;
    }

    /**
     * Creates a set of Cca objects from a set of Cca names.
     *
     * @param ccaNames
     * @return
     */
    public static Set<Cca> createCcaSet(String... ccaNames) {
        Set<Cca> ccaSet = new HashSet<>();
        for (String ccaName : ccaNames) {
            ccaSet.add(new Cca(ccaName));
        }
        return ccaSet;
    }

    /**
     * Returns true if a given string is a valid Cca name.
     */
    public static boolean isValidCcaName(String test) {
        return !test.equals(NIL_FIELD) && test.matches(VALIDATION_REGEX);
    }

    /**
     * Checks if other is the same Cca by checking only the name.(Test only)
     *
     * @param other
     * @return
     */
    public boolean isSameCcaName(Cca other) {
        requireNonNull(other);
        if (other == this) {
            return true;
        }

        return ccaName.equals(other.ccaName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Cca)) {
            return false;
        }

        Cca otherCca = (Cca) other;
        return ccaName.equals(otherCca.ccaName);
    }

    /**
     * hashCode() should ONLY hash ccaName!
     */
    @Override
    public int hashCode() {
        return ccaName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return "[CCA: " + ccaName + ']';
    }

}
