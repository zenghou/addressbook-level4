package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.DetailsContainKeywordsPredicate;

public class SearchCommandTest {

    ExpectedException expectedException = ExpectedException.none();

    /** Test should throw Exception since execute method is not implemented */
    @Test
    public void execute_SearchCommand_throwsException() throws Exception {
        expectedException.expect(Exception.class);
        // TODO: change constructor for DetailsContainKeywordsPredicte after implementation
        SearchCommand searchCommand = new SearchCommand(new DetailsContainKeywordsPredicate());
    }

    @Test
    public void equals() {
        // TODO: implement after DetailsContainKeywordsPredicte, otherwise equals will test for reference
    }
}
