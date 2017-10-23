package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class AutoCompleteTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void autoCompleteAdd_missingOneField_addMissingPrefix() {
        // missing phone
        String command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + TAG_DESC_FRIEND;
        String expected = AddCommand.COMMAND_WORD + NAME_DESC_AMY
            + " " + PREFIX_PHONE + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + TAG_DESC_FRIEND;
        assertAutoComplete(command, expected);

        // missing address
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND;
        expected = AddCommand.COMMAND_WORD + NAME_DESC_AMY
            + PHONE_DESC_AMY + EMAIL_DESC_AMY + " " + PREFIX_ADDRESS + TAG_DESC_FRIEND;
        assertAutoComplete(command, expected);
    }

    @Test
    public void autoCompleteAdd_missingMultipleFields_addMissingPrefixes() {
        // missing name and email
        String command = AddCommand.COMMAND_WORD + PHONE_DESC_AMY + ADDRESS_DESC_AMY + TAG_DESC_FRIEND;
        String expected = AddCommand.COMMAND_WORD + " " + PREFIX_NAME + PHONE_DESC_AMY
            + " " + PREFIX_EMAIL + ADDRESS_DESC_AMY + TAG_DESC_FRIEND;
        assertAutoComplete(command, expected);

        // all fields are missing
        command = AddCommand.COMMAND_WORD;
        expected = AddCommand.COMMAND_WORD + " " + PREFIX_NAME + " " + PREFIX_PHONE + " " + PREFIX_EMAIL
            + " " + PREFIX_ADDRESS + " " + PREFIX_TAG;
        assertAutoComplete(command, expected);
    }

    @Test
    public void autoCompleteAdd_invalidFields_addMissingPrefixes() {
        // invalid phone
        String command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + " " + PREFIX_PHONE + "abc"
            + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + TAG_DESC_FRIEND;
        String expected = AddCommand.COMMAND_WORD + NAME_DESC_AMY
            + " " + PREFIX_PHONE + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + TAG_DESC_FRIEND;
        assertAutoComplete(command, expected);

        // two name field -> choose the first one
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_AMY
            + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + TAG_DESC_FRIEND;
        expected= AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
            + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + TAG_DESC_FRIEND;
        assertAutoComplete(command, expected);
    }

    @Test
    public void autoCompleteAdd_allValidFields_noChange() {
        String command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
            + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + TAG_DESC_FRIEND;
        assertAutoComplete(command, command);
    }

    @Test
    public void autoCompleteDelete_invalidIndex_trimNonDigitChar() {
        String command = "delete 1a";
        String expected = "delete 1";
        assertAutoComplete(command, expected);

        command = "delete aa1bb   ";
        assertAutoComplete(command, expected);

        command = "delete a  1  b";
        assertAutoComplete(command, expected);
    }

    @Test
    public void autoCompleteDelete_validIndex_noChange() {
        String command = "delete 1";
        assertAutoComplete(command, command);
    }

    /**
     * Asserts if the auto-complete of {@code command} equals to {@code expectedResult}.
     */
    private void assertAutoComplete(String command, String expectedResult) {
        String result = AutoComplete.autoComplete(command, model);
        assertEquals(result, expectedResult);
    }
}
