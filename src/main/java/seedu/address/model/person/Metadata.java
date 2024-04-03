package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's meta-data in the CCA Manager
 * Guarantees: immutable; is valid as declared in {@link #isValidMetadata(String)}
 */

public class Metadata {
    public static final String MESSAGE_CONSTRAINTS =
            "Meta-data should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * A valid metadata is any string that does not contain a `/`.
     */
    public static final String VALIDATION_REGEX = "[^/]+";

    public final String metadata;

    /**
     * Constructs a {@code Metadata}.
     *
     * @param metadata A valid metadata.
     */
    public Metadata(String metadata) {
        requireNonNull(metadata);
        checkArgument(isValidMetadata(metadata), MESSAGE_CONSTRAINTS);
        this.metadata = metadata;
    }

    public static boolean isValidMetadata(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return metadata;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Metadata)) {
            return false;
        }

        Metadata otherMetadata = (Metadata) other;
        return metadata.equals(otherMetadata.metadata);
    }

    @Override
    public int hashCode() {
        return metadata.hashCode();
    }
}
