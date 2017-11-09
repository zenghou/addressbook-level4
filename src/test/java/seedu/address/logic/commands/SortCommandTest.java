//@@author sunarjo-denny
package seedu.address.logic.commands;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.user.UserCreds;
import seedu.address.model.user.UserPrefs;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

public class SortCommandTest {
    private Model model;
    private Model expectedModel;
    private SortCommand sortCommand;
    private SortCommand reverseSortCommand;
    private String DEFAULT_SORT_ATTRIBUTE = "name";
    private String DEFAULT_SORT_SEQUENCE = "ascending";
    private String REVERSE_SORT_SEQUENCE = "descending";
    private boolean DEFAULT_SORT_ORDER = false;
    private boolean REVERSE_SORT_ORDER = true;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new UserCreds());
        model.getUserCreds().validateCurrentSession(); // validate user
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new UserCreds());
        expectedModel.getUserCreds().validateCurrentSession(); // validate user

        sortCommand = new SortCommand(DEFAULT_SORT_ATTRIBUTE, DEFAULT_SORT_ORDER);
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_defaultSortCommand_unfilteredList() {
        assertCommandSuccess(sortCommand, model, String.format(sortCommand.MESSAGE_SUCCESS, DEFAULT_SORT_ATTRIBUTE,
                DEFAULT_SORT_SEQUENCE), expectedModel);
    }

    @Test
    public void execute_defaultSortCommand_filteredList() {
        showFirstPersonOnly(model);
        assertCommandSuccess(sortCommand, model, String.format(sortCommand.MESSAGE_SUCCESS, DEFAULT_SORT_ATTRIBUTE,
                DEFAULT_SORT_SEQUENCE), expectedModel);
    }
}
