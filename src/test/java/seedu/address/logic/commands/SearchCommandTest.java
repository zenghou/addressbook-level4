package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.DetailsContainKeyphrasePredicate;

public class SearchCommandTest {

    ExpectedException expectedException = ExpectedException.none();

    /** Test should throw Exception since execute method is not implemented */
    @Test
    public void execute_SearchCommand_throwsException() throws Exception {
        expectedException.expect(Exception.class);
        SearchCommand searchCommand = new SearchCommand(new DetailsContainKeyphrasePredicate(""));
        searchCommand.execute();
    }

    @Test
    public void equals() {
        SearchCommand searchCommandOne = new SearchCommand(new DetailsContainKeyphrasePredicate("testing one"));
        SearchCommand searchCommandTwo = new SearchCommand(new DetailsContainKeyphrasePredicate("testing two"));

        // same object -> returns true
        assertTrue(searchCommandOne.equals(searchCommandOne));

        // different object -> returns false
        assertFalse(searchCommandOne.equals(searchCommandTwo));

        // different type -> returns false
        assertFalse(searchCommandOne.equals(1));
    }
}
