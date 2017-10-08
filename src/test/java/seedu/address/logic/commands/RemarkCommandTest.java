package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.commands.exceptions.CommandException;

public class RemarkCommandTest {

    @Test (expected = CommandException.class)
    public void execute_executeUndoableCommand_throwsCommandException() throws CommandException {
        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON);
        remarkCommand.executeUndoableCommand();
    }

}
