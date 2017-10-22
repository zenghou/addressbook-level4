package seedu.address.logic;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class AutoCompleteTest {

    private static final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

}
