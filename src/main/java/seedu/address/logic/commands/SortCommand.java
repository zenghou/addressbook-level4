package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.exceptions.CommandException;

public class SortCommand extends UndoableCommand{
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "list sorted in alphabetical order!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": sort the person in alphabetical order.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
