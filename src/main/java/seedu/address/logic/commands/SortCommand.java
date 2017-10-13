package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;

public class SortCommand extends UndoableCommand{
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "list sorted in alphabetical order!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": sort the person in alphabetical order.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        model.sortFilteredPersonList((o1, o2) -> o1.getName().toString().compareToIgnoreCase(o2.getName().toString()));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
