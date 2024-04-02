/**
 * This file is copied and modified from UniquePersonList.java
 */

package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.cca.Cca;
import seedu.address.model.person.exceptions.CcaNotFoundException;
import seedu.address.model.person.exceptions.DuplicateCcaException;

/**
 * A list of ccas that enforces uniqueness between its elements and does not allow nulls.
 * A cca is considered unique by comparing using {@code Cca#isSameCca(Cca)}. As such, adding and updating of
 * ccas uses Cca#isSameCca(Cca) for equality so as to ensure that the cca being added or updated is
 * unique in terms of identity in the UniqueCcaList. However, the removal of a cca uses Person#equals(Object) so
 * as to ensure that the cca with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Cca#isSameCcaName(Cca)
 */
public class UniqueCcaList implements Iterable<Cca> {

    private final ObservableList<Cca> internalList = FXCollections.observableArrayList();
    private final ObservableList<Cca> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Gets the corresponding Cca in this.
     * @param ccaName
     * @return cca in list
     */
    public Cca getUniqueCca(String ccaName) {
        requireNonNull(ccaName);
        return internalList
            .stream()
            // Find cca in list.
            .filter(c -> c.ccaName.equals(ccaName))
            .findFirst()
            // If it doesn't exist, create a new one.
            .orElseGet(() -> {
                Cca newCca = new Cca(ccaName);
                internalList.add(newCca);
                return newCca;
            });
    }

    public Set<Cca> getUniqueCcas(Set<Cca> currentCcas) {
        return currentCcas
            .stream()
            .map(c -> this.getUniqueCca(c.ccaName))
            .collect(Collectors.toCollection(HashSet::new));
    }

    /**
     * Returns true if the list contains an equivalent cca as the given argument.
     */
    public boolean contains(Cca toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameCcaName);
    }

    /**
     * Adds a cca to the list.
     * The cca must not already exist in the list.
     */
    public void add(Cca toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateCcaException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the cca {@code target} in the list with {@code editedCca}.
     * {@code target} must exist in the list.
     * The cca name of {@code editedCca} must not be the same as another existing Cca in the list.
     */
    public void setCca(Cca target, Cca editedCca) {
        requireAllNonNull(target, editedCca);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new CcaNotFoundException();
        }

        if (!target.isSameCcaName(editedCca) && contains(editedCca)) {
            throw new DuplicateCcaException();
        }

        internalList.set(index, editedCca);
    }

    /**
     * Removes the equivalent cca from the list.
     * The cca must exist in the list.
     */
    public void remove(Cca toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new CcaNotFoundException();
        }
    }

    public void setCcas(UniqueCcaList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code ccas}.
     * {@code ccas} must not contain duplicate ccas.
     */
    public void setCcas(List<Cca> ccas) {
        requireAllNonNull(ccas);
        if (!ccasAreUnique(ccas)) {
            throw new DuplicateCcaException();
        }

        internalList.setAll(ccas);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Cca> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Cca> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueCcaList)) {
            return false;
        }

        UniqueCcaList otherUniqueCcaList = (UniqueCcaList) other;
        return internalList.equals(otherUniqueCcaList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code ccas} contains only unique ccas.
     */
    private boolean ccasAreUnique(List<Cca> ccas) {
        for (int i = 0; i < ccas.size() - 1; i++) {
            for (int j = i + 1; j < ccas.size(); j++) {
                if (ccas.get(i).isSameCcaName(ccas.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
