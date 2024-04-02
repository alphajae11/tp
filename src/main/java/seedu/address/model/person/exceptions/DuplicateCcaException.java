package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in duplicate Cca (Cca are considered duplicates if they have the same
 * ccaName).
 */
public class DuplicateCcaException extends RuntimeException {
    public DuplicateCcaException() {
        super("Multiple CCAs of the same name.");
    }
}
