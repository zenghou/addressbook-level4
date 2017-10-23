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
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.PersonUtil.getPersonDetails;
import static seedu.address.testutil.TestUtil.getPerson;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;

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

    @Test
    public void autoCompleteEdit_invalidIndex_trimNonDigitChar() {
        String command = "edit 11a";
        String expected = "edit 11 ";
        assertAutoComplete(command, expected);

        command = "edit a1,1a ";
        assertAutoComplete(command, expected);

        command = "edit a1a1a n/Some Name";
        //TODO: this command should be corrected to "edit 11 n/Some Name"
        assertAutoComplete(command, expected);
    }

    @Test
    public void autoCompleteEdit_firstIndexInvalidField_autoFillFields() {
        ReadOnlyPerson firstPerson = getPerson(model, INDEX_FIRST_PERSON);
        String expected = EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
            + " " + getPersonDetails(firstPerson).trim();

        // empty field
        String command = "edit 1 n/";
        assertAutoComplete(command, expected);

        // invalid field
        command = "edit  1  p/a";
        assertAutoComplete(command, expected);

        // multiple invalid fields
        command = "edit  1 p/a e/aa";
        assertAutoComplete(command, expected);
    }

    @Test
    public void autoCompleteEdit_firstIndexValidFields_autoFillTheRestFields() throws Exception {
        ReadOnlyPerson firstPerson = getPerson(model, INDEX_FIRST_PERSON);

        String validName = "Valid Name";
        String command = "edit 1 " + PREFIX_NAME_STRING + validName;
        Person expectedPerson = new Person(firstPerson);
        expectedPerson.setName(new Name(validName));
        String expected = EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
            + " " + getPersonDetails(expectedPerson).trim();
        assertAutoComplete(command, expected);

        String validPhone = "1234567";
        command = "edit 1 " + PREFIX_PHONE_STRING + validPhone;
        expectedPerson = new Person(firstPerson);
        expectedPerson.setPhone(new Phone(validPhone));
        expected = EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
            + " " + getPersonDetails(expectedPerson).trim();
        assertAutoComplete(command, expected);
    }


    @Test
    public void autoCompleteFind_anyArgument_trimAllArgs() {
        String command = "find some args";
        String expected = "find ";
        assertAutoComplete(command, expected);

        command = "find";
        assertAutoComplete(command, expected);

        command = "find arg with whitespaces  ";
        assertAutoComplete(command, expected);
    }

    /**
     * Asserts if the auto-complete of {@code command} equals to {@code expectedResult}.
     */
    private void assertAutoComplete(String command, String expectedResult) {
        String result = AutoComplete.autoComplete(command, model);
        assertEquals(result, expectedResult);
    }
}
