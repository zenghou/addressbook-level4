package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 *
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    private static final Pattern INDEXES_AND_FILEPATH =
        Pattern.compile("(?<indexesString>.(((\\d)*(,)*(\\s)*)+));(?<filePath>.*)");

    @Override
    public ExportCommand parse(String args) throws ParseException {
        final Matcher matcher = INDEXES_AND_FILEPATH.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }
        final String indexesString = matcher.group("indexesString");
        final String filePath = matcher.group("filePath").trim();

        // parse indexes
        String[] indexStrings = indexesString.split("(,)*(\\s)*");
        List<Index> indexes = Arrays.stream(indexStrings)
            .map(String::trim)
            .filter(((Predicate<String>) String::isEmpty).negate())
            .map(Integer::parseInt)
            .map(Index::fromOneBased)
            .collect(Collectors.toList());

        return new ExportCommand(indexes, filePath);
    }
}
