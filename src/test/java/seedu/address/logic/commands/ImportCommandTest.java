package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.util.FileUtil;

public class ImportCommandTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlPersonListStorageTest/");

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

    }

    @Test
    public void execute_notXmlFormat_throwsDataConversionException() throws Exception {

    }

    @Test
    public void execute_emptyFile_failure() {

    }

    @Test
    public void execute_validFilePathAndFile_success() {

    }
}
