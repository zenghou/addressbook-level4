package seedu.address.logic;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.StringUtil.getSystemCommandWords;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_STRING;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;

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

        final String commandWord = matcher.group("commandWord").toLowerCase();
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
            return remarkCommandAutoComplete(arguments, model);

        case SortCommand.COMMAND_WORD:
            return sortCommandAutoComplete(arguments);

        case ExportCommand.COMMAND_WORD:
            return exportCommandAutoComplete(arguments);

        case ImportCommand.COMMAND_WORD:
            return importCommandAutoComplete(arguments);

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
     * Auto-completes delete command.
     */
    public static String deleteCommandAutoComplete(String args) {
        return DeleteCommand.COMMAND_WORD +  " " + formatSingleIndexString(args);
    }

    /**
     * Auto-completes edit command.
     */
    public static String editCommandAutoComplete(String args, Model model) {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        String indexString = argMultimap.getPreamble().trim();
        Index index = null;
        try {
            index = ParserUtil.parseIndex(indexString);
        } catch (IllegalValueException ive) {
            indexString = formatSingleIndexString(indexString);
        }

        // if the index is invalid
        if (index == null) {
            return EditCommand.COMMAND_WORD + " " + indexString + " ";
        }

        // auto fill all fields
        ReadOnlyPerson person = model.getFilteredPersonList().get(index.getZeroBased());
        String prefixWithArgs = formatPrefixWithArgs(argMultimap, PREFIX_NAME, person) + " "
            + formatPrefixWithArgs(argMultimap, PREFIX_PHONE, person) + " "
            + formatPrefixWithArgs(argMultimap, PREFIX_EMAIL, person) + " "
            + formatPrefixWithArgs(argMultimap, PREFIX_ADDRESS, person) + " "
            + formatPrefixWithArgs(argMultimap, PREFIX_TAG, person);

        return EditCommand.COMMAND_WORD + " " + indexString +  " " + prefixWithArgs;
    }

    /**
     * Auto-completes export command.
     */
    public static String exportCommandAutoComplete(String args) {
        Pattern exportPattern = Pattern.compile("(?<possibleIndexList>.[^;]);(?<possibleFilePath>.*)");
        Matcher matcher = exportPattern.matcher(args);

        String possibleIndexListString = "";
        String possibleFilePath = "";
        if (matcher.matches()) { // contains delimiter ";"
            possibleIndexListString = matcher.group("possibleIndexList");
            possibleFilePath = matcher.group("possibleFilePath");
        } else { // try to find the index part and file part
            int possibleDelimiterIndex = Math.max(args.lastIndexOf(' '), args.lastIndexOf(','));
            possibleIndexListString = args.substring(0, possibleDelimiterIndex);
            possibleFilePath = args.substring(possibleDelimiterIndex + 1);
        }

        // format Index List
        possibleIndexListString = Arrays.stream(possibleIndexListString.split("(\\s)+|(,)+"))
            .map(AutoComplete::formatSingleIndexString).filter(p -> !p.isEmpty()).collect(Collectors.joining(", "));

        // format file path
        possibleFilePath = possibleFilePath.trim();

        return ExportCommand.COMMAND_WORD + " " + possibleIndexListString + "; " + possibleFilePath;
    }

    /**
     * Auto-completes import command.
     */
    public static String importCommandAutoComplete(String args) {
        return ImportCommand.COMMAND_WORD + " " + args.trim();
    }

    /**
     * Auto-completes remark command.
     */
    public static String remarkCommandAutoComplete(String args, Model model) {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_REMARK);

        String indexString = argMultimap.getPreamble().trim();
        Index index = null;
        try {
            index = ParserUtil.parseIndex(indexString);
        } catch (IllegalValueException ive) {
            indexString = formatSingleIndexString(indexString);
        }

        // if the index is invalid
        if (index == null) {
            return RemarkCommand.COMMAND_WORD + " " + indexString + " ";
        }

        // auto fill remark field
        ReadOnlyPerson person = model.getFilteredPersonList().get(index.getZeroBased());
        String prefixWithArgs = formatPrefixWithArgs(argMultimap, PREFIX_REMARK, person);

        return RemarkCommand.COMMAND_WORD + " " + indexString +  " " + prefixWithArgs;
    }

    /**
     * Auto-completes select command.
     */
    public static String selectCommandAutoComplete(String args) {
        return SelectCommand.COMMAND_WORD +  " " + formatSingleIndexString(args);
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
        List<String> prefixMatch = prefixMatch(getSystemCommandWords(), command);
        if (prefixMatch.size() == 1) { // only prefix-match
            return prefixMatch.get(0) + " " + args.trim();
        } else if (prefixMatch.size() > 1) { // multiple prefix-matches
            return promptForPossibleCommand(prefixMatch);
        } else { // no prefix-match
            List<String> fuzzyMatch = fuzzyMatches(getSystemCommandWords(), command);
            if (fuzzyMatch.isEmpty()) {
                return "";
            }
            return promptForPossibleCommand(fuzzyMatch);
        }
    }

    /**
     * Formats the arguments into " PREFIX/ARGS" form.
     */
    private static String formatPrefixWithArgs(ArgumentMultimap argMultimap, final Prefix prefix) {
        try {
            return parseArgOfPrefix(argMultimap.getAllValues(prefix), prefix);
        } catch (IllegalValueException ive) {
            return prefix.getPrefix();
        }
    }

    /**
     * Formats the argument into " PREFIX/ARGS" form. If the {@code ArgumentMultimap} does not contain the field,
     * replace the argument with {@code ReadOnlyPerson}'s corresponding field.
     */
    private static String formatPrefixWithArgs(ArgumentMultimap argMultimap, Prefix prefix, ReadOnlyPerson person) {
        String prefixWithArgs = formatPrefixWithArgs(argMultimap, prefix);
        if (prefixWithArgs.equals(prefix.getPrefix())) { // no input, read field info from person
            prefixWithArgs += getPersonFieldOfPrefix(person, prefix);
        }
        if (prefix.equals(PREFIX_TAG)) {
            // insert tag prefix into each tag
            prefixWithArgs = prefixWithArgs.replace(" ", " " + PREFIX_TAG_STRING);
        }
        return prefixWithArgs;
    }

    /**
     * Tests whether the argument is valid for certain {@code Prefix}.
     */
    private static String parseArgOfPrefix(List<String> argList, Prefix prefix) throws IllegalValueException {
        if (argList.isEmpty()) {
            return prefix.getPrefix();
        }
        Optional<String> firstArg = Optional.of(argList.get(0));
        switch (prefix.getPrefix()) {
        case PREFIX_ADDRESS_STRING:
            return prefix.getPrefix() + ParserUtil.parseAddress(firstArg).map(p -> p.value).orElse("");
        case PREFIX_EMAIL_STRING:
            return prefix.getPrefix() + ParserUtil.parseEmail(firstArg).map(p -> p.value).orElse("");
        case PREFIX_NAME_STRING:
            return prefix.getPrefix() + ParserUtil.parseName(firstArg).map(p -> p.fullName).orElse("");
        case PREFIX_PHONE_STRING:
            return prefix.getPrefix() + ParserUtil.parsePhone(firstArg).map(p -> p.value).orElse("");
        case PREFIX_TAG_STRING:
            return ParserUtil.parseTags(argList).stream().map(p -> (prefix + p.tagName))
                .collect(Collectors.joining(" "));
        case PREFIX_REMARK_STRING:
            return prefix.getPrefix() + firstArg.get().trim();
        default:
            return prefix.getPrefix();
        }
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
        case PREFIX_REMARK_STRING:
            return person.getRemark().value;
        case PREFIX_TAG_STRING:
            return person.getTags().stream().map(t -> t.tagName).collect(Collectors.joining(" "));
        default:
            return "";
        }
    }

    /**
     * Formats the argument into all-digit form.
     */
    private static String formatSingleIndexString(String arg) {
        return arg.replaceAll("\\D", "");
    }

    /**
     * Returns the suggested commands.
     */
    private static String promptForPossibleCommand(List<String> possibleCommands) {
        return "Do you mean: " + possibleCommands.stream().collect(Collectors.joining(" or ")) + " ?";
    }

    /**
     * Finds all test in array that have prefix of tester and returns the result as a List.
     */
    private static List<String> prefixMatch(String[] tests, String tester) {
        HashSet<String> prefixMatchesSet = new HashSet<>();
        Pattern prefixPattern = Pattern.compile("^" + tester + ".*$");
        prefixMatchesSet.addAll(Arrays.stream(tests).filter(p -> prefixPattern.matcher(p).matches())
            .collect(Collectors.toList()));
        List<String> prefixMatches = new ArrayList<>();
        prefixMatches.addAll(prefixMatchesSet);
        return prefixMatches;
    }

    /**
     * Finds the fuzzy match of tester in the Array.
     */
    private static List<String> fuzzyMatches(String[] tests, String tester) {
        HashSet<String> bestMatchesSet = new HashSet<>();
        bestMatchesSet.addAll(Arrays.stream(tests).filter(p -> levenshteinDistance(tester, p) > (p.length() / 2))
            .collect(Collectors.toList()));
        List<String> bestMatches = new ArrayList<>();
        bestMatches.addAll(bestMatchesSet);
        return bestMatches;
    }

    /**
     * Calculate the levenshtein Distance of two {@code String}s.
     */
    private static int levenshteinDistance(String s, String t) {
        if (s.length() == 0) {
            return t.length();
        }
        if (t.length() == 0) {
            return s.length();
        }
        if (s.charAt(0) == t.charAt(0)) {
            return levenshteinDistance(s.substring(1), t.substring(1));
        }
        int substitute = levenshteinDistance(s.substring(1), t.substring(1));
        int delete = levenshteinDistance(s, t.substring(1));
        int insert = levenshteinDistance(s.substring(1), t);
        return minimum(substitute, delete, insert) + 1;
    }

    /**
     * @return minimum number of Integers.
     */
    private static int minimum(Integer... integers) {
        return Arrays.stream(integers).reduce(Math::min).orElse(-1);
    }
}
