package systemtests;

import static seedu.address.testutil.TestUtil.getLastIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.io.File;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;

public class ExportCommandTest extends AddressBookSystemTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void export() throws Exception {
        String testFile = testFolder.getRoot().getPath() + "TestFile.xml";
        Model model = getModel();

        /* ----------------- Performing export operation while an unfiltered list is being shown -------------------- */

        /* Case: export the first person in the list, command with leading and trailing whitespaces
         * -> first person exported
         */
        Index firstPersonIndex = INDEX_FIRST_PERSON;
        String command  =
            "   " + ExportCommand.COMMAND_WORD + "   " + firstPersonIndex.getOneBased() + "   ;   " + testFile + "   ";
        String expectedMessage = String.format(ExportCommand.MESSAGE_EXPORT_PERSON_SUCCESS,
            getNameListString(model, firstPersonIndex), testFile);
        assertCommandSuccess(command, model, expectedMessage);

        /* Case: export the last person in the list -> last person exported */
        Index lastPersonIndex = getLastIndex(model);
        command = ExportCommand.COMMAND_WORD + " " + lastPersonIndex.getOneBased() + ";" + testFile;
        expectedMessage = String.format(ExportCommand.MESSAGE_EXPORT_PERSON_SUCCESS,
            getNameListString(model, lastPersonIndex), testFile);
        assertCommandSuccess(command, model, expectedMessage);

        /* Case: export first and second person in the list with index separated by "," -> 2 persons exported */
        command = ExportCommand.COMMAND_WORD + " 1,2 ; " + testFile;
        expectedMessage = String.format(ExportCommand.MESSAGE_EXPORT_PERSON_SUCCESS,
            getNameListString(model, Index.fromOneBased(1), Index.fromOneBased(2)), testFile);
        assertCommandSuccess(command, model, expectedMessage);

        /* Case: repeat the previous command with index separated by whitespaces -> 2 persons exported */
        command = ExportCommand.COMMAND_WORD + " 1 2 ; " + testFile;
        expectedMessage = String.format(ExportCommand.MESSAGE_EXPORT_PERSON_SUCCESS,
            getNameListString(model, Index.fromOneBased(1), Index.fromOneBased(2)), testFile);
        assertCommandSuccess(command, model, expectedMessage);

        /* Case: export first three persons in the list with index separated by a mix of "," and whitespaces
         * -> 3 persons exported
         */
        command = ExportCommand.COMMAND_WORD + " 1,, 2  3 ; " + testFile;
        expectedMessage = String.format(ExportCommand.MESSAGE_EXPORT_PERSON_SUCCESS,
            getNameListString(model, Index.fromOneBased(1), Index.fromOneBased(2), Index.fromOneBased(3)), testFile);
        assertCommandSuccess(command, model, expectedMessage);

        /* Case: export first person to non-existing file -> exported and create new file */
        String nonExistingFile = testFolder.getRoot().getPath() + "NonExistingFile.xml";
        command = ExportCommand.COMMAND_WORD + " " + firstPersonIndex.getOneBased() + "; " + nonExistingFile;
        expectedMessage = String.format(ExportCommand.MESSAGE_EXPORT_PERSON_SUCCESS,
            getNameListString(model, firstPersonIndex), nonExistingFile);
        assertCommandSuccess(command, model, expectedMessage);

        /* Case: export last person to existed file -> exported to overwrite file */
        command = ExportCommand.COMMAND_WORD + " " + lastPersonIndex.getOneBased() + "; " + nonExistingFile;
        expectedMessage = String.format(ExportCommand.MESSAGE_EXPORT_PERSON_SUCCESS,
            getNameListString(model, lastPersonIndex), nonExistingFile);
        assertCommandSuccess(command, model, expectedMessage);

        /* Case: export first person with file path without extension -> exported and add .xml extension */
        String noExtensionFile = testFolder.getRoot().getPath() + "NoExtensionFile";
        command = ExportCommand.COMMAND_WORD + " " + firstPersonIndex.getOneBased() + "; " + noExtensionFile;
        expectedMessage = String.format(ExportCommand.MESSAGE_EXPORT_PERSON_SUCCESS,
            getNameListString(model, firstPersonIndex), noExtensionFile + ".xml");
        assertCommandSuccess(command, model, expectedMessage);

        /* Case: export first person with file path with wrong extension -> exported and add .xml extension */
        String wrongExtensionFile = testFolder.getRoot().getPath() + "WrongExtensionFile.txt";
        command = ExportCommand.COMMAND_WORD + " " + firstPersonIndex.getOneBased() + "; " + wrongExtensionFile;
        expectedMessage = String.format(ExportCommand.MESSAGE_EXPORT_PERSON_SUCCESS,
            getNameListString(model, firstPersonIndex), wrongExtensionFile + ".xml");
        assertCommandSuccess(command, model, expectedMessage);

        /* Case: export first person with file path with no file name -> exported and add .xml extension */
        String noNameFile = testFolder.getRoot().getPath() + File.separator + ".xml";
        command = ExportCommand.COMMAND_WORD + " " + firstPersonIndex.getOneBased() + "; " + noNameFile;
        expectedMessage = String.format(ExportCommand.MESSAGE_EXPORT_PERSON_SUCCESS,
            getNameListString(model, firstPersonIndex), noNameFile + ".xml");
        assertCommandSuccess(command, model, expectedMessage);

        /* Case: export first person with file path with extension in upper case -> exported to given file name */
        String upperCaseExtensionFile = testFolder.getRoot().getPath() + "UpperCaseFile.XML";
        command = ExportCommand.COMMAND_WORD + " " + firstPersonIndex.getOneBased() + "; " + upperCaseExtensionFile;
        expectedMessage = String.format(ExportCommand.MESSAGE_EXPORT_PERSON_SUCCESS,
            getNameListString(model, firstPersonIndex), upperCaseExtensionFile);
        assertCommandSuccess(command, model, expectedMessage);

        /* ------------------ Performing export operation while a filtered list is being shown ---------------------- */

        /* Case: filtered person list, export within boundary of address book and person list -> exported */

        /* Case: filtered person list, export within boundary of address book but out of bounds of person list
         * -> rejected
         */

        /* --------------------- Performing export operation while a person card is selected ------------------------ */

        /* Case: export the selected person -> exported and selection remains */

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */

        /* Case: invalid index (-1) -> rejected */

        /* Case: invalid index (size + 1)  */

        /* Case: invalid arguments (alphabets in index part) -> rejected */

        /* Case: invalid arguments (indexes separated by invalid char) -> rejected */

        /* Case: missing index list -> rejected */

        /* Case: missing semicolon ";" -> rejected */

        /* Case: missing file path -> rejected */

        /* Case: invalid file (missing file) -> rejected */

        /* Case: invalid file (not xml format) -> rejected */

        /* Case: invalid file () -> rejected */

        /* Case: mixed case command word -> rejected */

    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card remains unchanged.<br>
     * 5. Asserts that the status bar remains unchanged.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see ExportCommandTest#assertCommandSuccess(String, Model, String)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchanged();
    }

    private String getNameListString(Model model, Index... indexes) {
        List<ReadOnlyPerson> filteredList = model.getFilteredPersonList();
        StringBuilder personNameBuilder = new StringBuilder();
        for (Index index : indexes) {
            personNameBuilder.append(filteredList.get(index.getZeroBased()).getName().fullName).append(", ");
        }
        return personNameBuilder.deleteCharAt(personNameBuilder.lastIndexOf(",")).toString();
    }

}
