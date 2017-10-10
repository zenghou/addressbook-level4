package seedu.address.logic.commands;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.util.FileUtil;

public class ExportCommandTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/ExportCommandTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

}
