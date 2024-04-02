package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.cca.Cca;
import seedu.address.model.person.Person;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the cca list.
     * This list will not contain any duplicate cca.
     */
    ObservableList<Cca> getCcaList();

}
