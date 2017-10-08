package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.exceptions.CommandException;

public class RemarkCommandTest {

    public static final String TEST_REMARK = "This is a test remark";

    @Test (expected = CommandException.class)
    public void execute_executeUndoableCommand_throwsCommandException() throws CommandException {
        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON, TEST_REMARK);
        remarkCommand.executeUndoableCommand();
    }

    @Test
    public void equals() {
        RemarkCommand firstRemarkCommand = new RemarkCommand(INDEX_FIRST_PERSON, TEST_REMARK);
        RemarkCommand secondRemarkCommand = new RemarkCommand(INDEX_SECOND_PERSON, TEST_REMARK);

        // same object therefore same content -> returns true
        assertTrue(firstRemarkCommand.equals(firstRemarkCommand));

        // same values -> return true
        assertTrue(firstRemarkCommand.equals(new RemarkCommand(INDEX_FIRST_PERSON, "This is a test remark")));

        // different Indices -> return False
        assertFalse(firstRemarkCommand.equals(secondRemarkCommand));

        // different types -> return False
        assertFalse(firstRemarkCommand.equals("test"));

        // null -> return false
        assertFalse(firstRemarkCommand.equals(null));

    }

}
