package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class AutoCompleteTest {

    @Rule
    private static final ExpectedException thrown = ExpectedException.none();

    private static final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void autoCompleteAdd_missingOneField_addMissingPrefix() {

    }

    @Test
    public void autoCompleteAdd_missingMultipleFields_addMissingPrefixes() {

    }

    @Test
    public void autoCompleteAdd_invalidFields_addMissingPrefixes() {

    }

    @Test
    public void autoCompleteAdd_allValidFields_noChange() {

    }

    /**
     * Asserts if the auto-complete of {@code command} equals to {@code expectedResult}.
     */
    private void assertAutoComplete(String command, String expectedResult) {
        String result = AutoComplete.autoComplete(command, model);
        assertEquals(result, expectedResult);
    }
}
