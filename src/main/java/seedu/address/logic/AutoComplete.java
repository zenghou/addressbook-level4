package seedu.address.logic;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Prefix;

/**
 * Utilities for auto completion for command.
 */
public class AutoComplete {

    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     *
     */
    public String autoComplete(String userInput) {
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
            return editCommandAutoComplete(arguments);

        case SelectCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_ALIAS:
            return selectCommandAutoComplete(arguments);

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_ALIAS:
            return deleteCommandAutoComplete(arguments);

        case RemarkCommand.COMMAND_WORD:
        case RemarkCommand.COMMAND_ALIAS:
            return remarkCommandAutoComplete(arguments);

        case ClearCommand.COMMAND_WORD:
        case ClearCommand.COMMAND_ALIAS:
            return "";

        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_ALIAS:
            return findCommandAutoComplete(arguments);

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_ALIAS:
            return "";

        case SortCommand.COMMAND_WORD:
            return sortCommandAutoComplete(arguments);

        case ExportCommand.COMMAND_WORD:
            return exportCommandAutoComplete(arguments);

        case ImportCommand.COMMAND_WORD:
            return importCommandAutoComplete(arguments);

        case SearchCommand.COMMAND_WORD:
        case SearchCommand.COMMAND_ALIAS:
            return searchCommandAutoComplete(arguments);

        case HistoryCommand.COMMAND_WORD:
            return "";

        case ExitCommand.COMMAND_WORD:
            return "";

        case HelpCommand.COMMAND_WORD:
            return "";

        case UndoCommand.COMMAND_WORD:
        case UndoCommand.COMMAND_ALIAS:
            return "";

        case RedoCommand.COMMAND_WORD:
        case RedoCommand.COMMAND_ALIAS:
            return "";

        default:
            return unknownCommandAutoComplete(commandWord, arguments);

        }
    }

    /**
     * Auto-completes the prefix field that is not entered.
     */
    public String addCommandAutoComplete(String args) {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);
        return AddCommand.COMMAND_WORD
            + formatPrefixWithArgs(argMultimap, PREFIX_NAME)
            + formatPrefixWithArgs(argMultimap, PREFIX_PHONE)
            + formatPrefixWithArgs(argMultimap, PREFIX_EMAIL)
            + formatPrefixWithArgs(argMultimap, PREFIX_ADDRESS)
            + formatPrefixWithArgs(argMultimap, PREFIX_TAG);
    }

    /**
     * Formats the arguments into " PREFIX/ARGS" form.
     */
    private String formatPrefixWithArgs(ArgumentMultimap argMultimap, final Prefix prefix) {
        String arg = "";
        List<String> argList = argMultimap.getAllValues(prefix);
        if (!argList.isEmpty()) {
            arg = argList.stream().collect(Collectors.joining(" " + prefix.getPrefix()));
        }
        return " " + prefix + arg;
    }

    /**
     * Formats the arguments into " PREFIX/ARGS" form.
     */
    public String deleteCommandAutoComplete(String args) {
        return " ";
    }

    /**
     * Formats the arguments into " PREFIX/ARGS" form.
     */
    public String editCommandAutoComplete(String args) {
        return " ";
    }

    /**
     * Formats the arguments into " PREFIX/ARGS" form.
     */
    public String exportCommandAutoComplete(String args) {
        return " ";
    }

    /**
     * Formats the arguments into " PREFIX/ARGS" form.
     */
    public String findCommandAutoComplete(String args) {
        return " ";
    }

    /**
     * Formats the arguments into " PREFIX/ARGS" form.
     */
    public String importCommandAutoComplete(String args) {
        return " ";
    }

    /**
     * Formats the arguments into " PREFIX/ARGS" form.
     */
    public String remarkCommandAutoComplete(String args) {
        return " ";
    }

    /**
     * Formats the arguments into " PREFIX/ARGS" form.
     */
    public String searchCommandAutoComplete(String args) {
        return " ";
    }

    /**
     * Formats the arguments into " PREFIX/ARGS" form.
     */
    public String selectCommandAutoComplete(String args) {
        return " ";
    }

    /**
     * Formats the arguments into " PREFIX/ARGS" form.
     */
    public String sortCommandAutoComplete(String args) {
        return " ";
    }

    /**
     * Formats the arguments into " PREFIX/ARGS" form.
     */
    public String unknownCommandAutoComplete(String args) {
        return " ";
    }

    /**
     * Formats the arguments into " PREFIX/ARGS" form.
     */
    public String unknownCommandAutoComplete(String command, String args) {
        return " ";
    }

}
