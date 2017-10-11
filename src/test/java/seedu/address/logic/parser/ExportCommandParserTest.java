package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.ExportCommand;

public class ExportCommandParserTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/export/");
    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parser_validArgs_success() {
        String filePath = TEST_DATA_FOLDER + "TypicalExportFile.xml";
        List<Index> indexes = new ArrayList<>();
        indexes.add(Index.fromOneBased(1));
        indexes.add(Index.fromOneBased(2));
        String input = "1, 2; " + filePath;
        ExportCommand exportCommand = new ExportCommand(indexes, filePath);
        assertParseSuccess(parser, input, exportCommand);
    }
}
