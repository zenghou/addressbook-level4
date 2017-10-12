package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.FileUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ExportCommandTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/ExportCommandTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        List<Index> indexes = Arrays.asList(Index.fromOneBased(1), Index.fromOneBased(2));
        String filePath = "TestFile.xml";
        ExportCommand exportCommand = new ExportCommand(indexes, filePath);

        // same object -> true
        assertTrue(exportCommand.equals(exportCommand));

        // same value -> true
        ExportCommand exportCommandCopy = new ExportCommand(indexes, filePath);
        assertTrue(exportCommand.equals(exportCommandCopy));

        // different type -> false
        assertFalse(exportCommand.equals(1));

        // null -> false
        assertFalse(exportCommand.equals(null));

        // different index -> false
        List<Index> newIndexes = Collections.singletonList(Index.fromOneBased(1));
        ExportCommand exportCommandDifferentIndex = new ExportCommand(newIndexes, filePath);
        assertFalse(exportCommand.equals(exportCommandDifferentIndex));

        //different filePath -> false
        ExportCommand exportCommandDifferentFilePath = new ExportCommand(indexes, "OtherFile.xml");
        assertFalse(exportCommand.equals(exportCommandDifferentFilePath));
    }

    @Test
    public void execute_validIndexesAndFilePath_success() {

    }

    /**
     * @return an {@code ExportCommand} with parameters {@code indexes} and {@code filePath}
     */
    private ExportCommand prepareCommand(Integer[] indexesInt, String filePath) {
        List<Index> indexes = Arrays.stream(indexesInt).map(Index::fromZeroBased).collect(Collectors.toList());
        ExportCommand export = new ExportCommand(indexes, filePath);
        export.setData(this.model, new CommandHistory(), new UndoRedoStack());
        return export;
    }
}
