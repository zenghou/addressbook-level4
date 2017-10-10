package seedu.address.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 *
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    private static final Pattern INDEXES_AND_FILEPATH = Pattern.compile("(?<indexesString>[^;]);(?<filePath>.*)");

    @Override
    public ExportCommand parse(String args) throws ParseException {
        final Matcher matcher = INDEXES_AND_FILEPATH.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException("Invalid format!");
        }

        final String indexesString = matcher.group("indexesString");
        final String filePath = matcher.group("filePath");

        // parse indexes
        String[] indexStrings = indexesString.split(",\\s");
        Index[] indexes = new Index[indexesString.length()];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = Index.fromOneBased(Integer.parseInt(indexStrings[i]));
        }

        return new ExportCommand(indexes, filePath);
    }
}
