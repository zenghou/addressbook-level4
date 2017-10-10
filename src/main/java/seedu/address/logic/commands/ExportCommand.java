package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Export a person list to a specified save file.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Exports the persons identified by the index numbers used in the last person listing.\n"
        + "Parameters: INDEX; PATH (must be positive integers separated by commas or spaces)\n"
        + "Example: " + COMMAND_WORD + " 1, 2 3; \"~/Desktop/persons.xml\"";

    private final Index[] targetIndexes;
    private final String filePath;

    public ExportCommand(Index[] targetIndexes, String filePath) {
        this.targetIndexes = targetIndexes;
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute() throws CommandException {
        return null;
    }
}
