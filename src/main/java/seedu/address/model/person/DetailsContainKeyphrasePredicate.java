package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that either one of {@code ReadOnlyPerson}'s {@code Name, Phone, Address, Email, Tag, Remarks} matches,
 * partially or in full, any of the keywords given.
 */
public class DetailsContainKeyphrasePredicate implements Predicate<ReadOnlyPerson>{
    private final String keyphrase;

    public DetailsContainKeyphrasePredicate(String keyphrase)  {
        this.keyphrase = keyphrase;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return personDetailsContainsKeyphrase(person, keyphrase);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && this.keyphrase.equals(((DetailsContainKeyphrasePredicate) other).keyphrase)); // state check
    }

    /**
     * Tests if the {@code keyphrase} entered matches, partially or in full, any attribute of a {@code person}
     * @param person
     * @param keyphrase
     * @return true if any attribute of the {@code person}'s details match the keyphrase
     */
    public boolean personDetailsContainsKeyphrase(ReadOnlyPerson person, String keyphrase) {
        boolean nameContainsKeyphrase = person.getName().fullName.contains(keyphrase);
        boolean emailContainsKeyphrase = person.getEmail().value.contains(keyphrase);
        boolean phoneContainsKeyphrase = person.getPhone().value.contains(keyphrase);
        boolean addressContainsKeyphrase = person.getAddress().value.contains(keyphrase);
        boolean remarkContainsKeyphrase = person.getRemark().value.contains(keyphrase);
        boolean tagContainsKeyphrase = person.getTags().toString().contains(keyphrase);

        return nameContainsKeyphrase || emailContainsKeyphrase || phoneContainsKeyphrase || addressContainsKeyphrase
                || remarkContainsKeyphrase || tagContainsKeyphrase;
    }
}
