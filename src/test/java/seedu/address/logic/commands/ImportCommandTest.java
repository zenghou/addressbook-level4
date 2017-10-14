package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.exceptions.CommandException;

public class ImportCommandTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/ImportCommandTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void equals() {
        String filePath = "SomeFile.xml";
        ImportCommand importCommand = new ImportCommand(filePath);

        // the same object -> true
        assertTrue(importCommand.equals(importCommand));

        // different object same value -> true
        assertTrue(importCommand.equals(new ImportCommand(filePath)));

        // null -> false
        assertFalse(importCommand.equals(null));

        // different classes -> false
        assertFalse(importCommand.equals(1));

        // different values -> false
        assertFalse(importCommand.equals(new ImportCommand("OtherFile.xml")));
    }

    @Test
    public void execute_missingFile_throwsFileNotFoundException() throws Exception {
        String filePath = addToTestDataPathIfNotNull("MissingFile.xml");
        assertCommandException(new ImportCommand(filePath),
            String.format(ImportCommand.MESSAGE_MISSING_FILE, filePath));
    }

    @Test
    public void execute_notXmlFormat_throwsDataConversionException() throws Exception {
        String filePath = addToTestDataPathIfNotNull("NotXmlFormatExportFile.xml");
        assertCommandException(new ImportCommand(filePath),
            String.format(ImportCommand.MESSAGE_INVALID_XML_FILE, filePath));
    }

    @Test
    public void execute_emptyFile_failure() {
        String filePath = addToTestDataPathIfNotNull("EmptyFile.xml");
        //TODO: empty file should return Optional.empty()?
        assertCommandException(new ImportCommand(filePath),
            String.format(ImportCommand.MESSAGE_INVALID_XML_FILE, filePath));
    }

    @Test
    public void execute_validFilePathAndFile_success() {

    }

    /**
     * Asserts if executing the given command throws {@code CommandException} with {@code exceptionMessage}.
     */
    private void assertCommandException(ImportCommand command, String exceptionMessage) {
        try {
            command.executeUndoableCommand();
        } catch (CommandException ce) {
            assertEquals(ce.getMessage(), exceptionMessage);
        }
    }

    /**
     * Adds test folder path to {@code prefsFileInTestDataFolder}.
     */
    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
               ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
               : null;
    }
}
