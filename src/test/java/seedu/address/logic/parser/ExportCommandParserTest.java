package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.ExportCommand;

public class ExportCommandParserTest {

    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parser_validArgs_success() {
        String filePath = "SomeFile.xml ";
        List<Index> indexes = new ArrayList<>();
        indexes.add(Index.fromOneBased(1));
        indexes.add(Index.fromOneBased(2));
        indexes.add(Index.fromOneBased(3));
        String input = "1, 2 3 ; " + filePath;
        ExportCommand exportCommand = new ExportCommand(indexes, filePath.trim());
        assertParseSuccess(parser, input, exportCommand);
    }
}
