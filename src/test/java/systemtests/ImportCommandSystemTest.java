package systemtests;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.util.FileUtil;
import seedu.address.model.Model;

public class ImportCommandSystemTest extends AddressBookSystemTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/ImportCommandSystemTest/");

    @Test
    public void importCommand() {
        Model model = getModel();

        /* Case: import one unique person into address book -> imported */

        /* Case: import multiple unique persons into address book -> imported */

        /* Case: undo import -> persons deleted */

        /* Case: redo import -> persons imported again */

        /* Case: import multiple persons containing the same person in address book
         * -> imported with duplicated persons ignored
         */

        /* Case: import valid file containing no person -> imported no person */

        /* Case: import to a filtered address book -> imported */

        /* Case: import to an empty address book -> imported */

        /* Case: select first person card, and then import -> imported without card selection changing */

        /* Case: missing arguments -> rejected */

        /* Case: import file containing duplicated persons -> rejected */

        /* Case: import missing file -> rejected */

        /* Case: import file not in xml format -> rejected */

        /* Case: import file with invalid person -> rejected */

        /* Case: mix case command word -> rejected */

    }

    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();
        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }


}
