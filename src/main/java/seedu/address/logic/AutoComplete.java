package seedu.address.logic;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_STRING;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

/**
 * Utilities for auto completion for command.
 */
public class AutoComplete {

    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Auto-completes the input String.
     */
    public static String autoComplete(String userInput, Model model) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return unknownCommandAutoComplete(userInput);
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
        case AddCommand.COMMAND_ALIAS:
            return addCommandAutoComplete(arguments);

        case EditCommand.COMMAND_WORD:
        case EditCommand.COMMAND_ALIAS:
            return editCommandAutoComplete(arguments, model);

        case SelectCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_ALIAS:
            return selectCommandAutoComplete(arguments);

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_ALIAS:
            return deleteCommandAutoComplete(arguments);

        case RemarkCommand.COMMAND_WORD:
        case RemarkCommand.COMMAND_ALIAS:
            return remarkCommandAutoComplete(arguments);

        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_ALIAS:
            return findCommandAutoComplete(arguments);

        case SortCommand.COMMAND_WORD:
            return sortCommandAutoComplete(arguments);

        case ExportCommand.COMMAND_WORD:
            return exportCommandAutoComplete(arguments);

        case ImportCommand.COMMAND_WORD:
            return importCommandAutoComplete(arguments);

        case SearchCommand.COMMAND_WORD:
        case SearchCommand.COMMAND_ALIAS:
            return searchCommandAutoComplete(arguments);

        default:
            return unknownCommandAutoComplete(commandWord, arguments);

        }
    }

    /**
     * Auto-completes the prefix field that is not entered.
     */
    public static String addCommandAutoComplete(String args) {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);
        return AddCommand.COMMAND_WORD + " "
            + formatPrefixWithArgs(argMultimap, PREFIX_NAME) + " "
            + formatPrefixWithArgs(argMultimap, PREFIX_PHONE) + " "
            + formatPrefixWithArgs(argMultimap, PREFIX_EMAIL) + " "
            + formatPrefixWithArgs(argMultimap, PREFIX_ADDRESS) + " "
            + formatPrefixWithArgs(argMultimap, PREFIX_TAG);
    }

    /**
     * Formats the arguments into " PREFIX/ARGS" form.
     */
    private static String formatPrefixWithArgs(ArgumentMultimap argMultimap, final Prefix prefix) {
        String arg = "";
        List<String> argList = argMultimap.getAllValues(prefix);
        if (!argList.isEmpty()) {
            arg = argList.stream().collect(Collectors.joining(" " + prefix.getPrefix()));
        }
        return prefix + arg;
    }

    /**
     * Auto-completes delete command.
     */
    public static String deleteCommandAutoComplete(String args) {
        return DeleteCommand.COMMAND_WORD +  " ";
    }

    /**
     * Auto-completes edit command.
     */
    public static String editCommandAutoComplete(String args, Model model) {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        String indexString = argMultimap.getPreamble().trim();
        Index index;
        try {
            index = ParserUtil.parseIndex(indexString);
        } catch (IllegalValueException ive) {
            indexString = "";
            index = null;
        }

        // if the index is invalid
        if (index == null) {
            return EditCommand.COMMAND_WORD + " ";
        }

        // auto fill all fields
        ReadOnlyPerson person = model.getFilteredPersonList().get(index.getZeroBased());
        String prefixWithArgs = formatPrefixWithArgs(argMultimap, PREFIX_NAME, person)
            + formatPrefixWithArgs(argMultimap, PREFIX_PHONE, person)
            + formatPrefixWithArgs(argMultimap, PREFIX_EMAIL, person)
            + formatPrefixWithArgs(argMultimap, PREFIX_ADDRESS, person)
            + formatPrefixWithArgs(argMultimap, PREFIX_TAG, person);

        return EditCommand.COMMAND_WORD + " " + indexString + prefixWithArgs;
    }

    /**
     * Formats the argument into " PREFIX/ARGS" form. If the {@code ArgumentMultimap} does not contain the field,
     * replace the argument with {@code ReadOnlyPerson}'s corresponding field.
     */
    private static String formatPrefixWithArgs(ArgumentMultimap argMultimap, Prefix prefix, ReadOnlyPerson person) {
        String prefixWithArgs = formatPrefixWithArgs(argMultimap, prefix);
        if (prefixWithArgs.equals(" " + prefix)) { // no input, read field info from person
            prefixWithArgs += getPersonFieldOfPrefix(person, prefix);
        }
        if (prefix.equals(PREFIX_TAG)) {
            // insert tag prefix into each tag
            prefixWithArgs = prefixWithArgs.replace(" ", " " + PREFIX_TAG_STRING);
        }
        return prefixWithArgs;
    }

    /**
     * @return the corresponding field of a {@code ReadOnlyPerson} based on a {@code Prefix}.
     */
    private static String getPersonFieldOfPrefix(ReadOnlyPerson person, Prefix prefix) {
        requireNonNull(person);
        requireNonNull(prefix);

        switch (prefix.getPrefix()) {
        case PREFIX_NAME_STRING:
            return person.getName().fullName;
        case PREFIX_PHONE_STRING:
            return person.getPhone().value;
        case PREFIX_EMAIL_STRING:
            return person.getEmail().value;
        case PREFIX_ADDRESS_STRING:
            return person.getAddress().value;
        case PREFIX_TAG_STRING:
            return person.getTags().stream().map(Tag::toString).collect(Collectors.joining(" "));
        default:
            return "";
        }
    }

    /**
     * Auto-completes export command.
     */
    public static String exportCommandAutoComplete(String args) {
        return " ";
    }

    /**
     * Auto-completes find command.
     */
    public static String findCommandAutoComplete(String args) {
        return FindCommand.COMMAND_WORD + " ";
    }

    /**
     * Auto-completes import command.
     */
    public static String importCommandAutoComplete(String args) {
        return " ";
    }

    /**
     * Auto-completes remark command.
     */
    public static String remarkCommandAutoComplete(String args) {
        return " ";
    }

    /**
     * Auto-completes search command.
     */
    public static String searchCommandAutoComplete(String args) {
        return " ";
    }

    /**
     * Auto-completes select command.
     */
    public static String selectCommandAutoComplete(String args) {
        return SelectCommand.COMMAND_WORD +  " ";
    }

    /**
     * Auto-completes sort command.
     */
    public static String sortCommandAutoComplete(String args) {
        return " ";
    }

    /**
     * Auto-completes unknown command without arguments.
     */
    public static String unknownCommandAutoComplete(String args) {
        return " ";
    }

    /**
     * Auto-completes unknown command.
     */
    public static String unknownCommandAutoComplete(String command, String args) {
        return " ";
    }

}
