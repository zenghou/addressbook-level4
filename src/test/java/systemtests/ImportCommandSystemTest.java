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
