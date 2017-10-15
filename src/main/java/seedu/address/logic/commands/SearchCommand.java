package seedu.address.logic.commands;

import seedu.address.model.person.DetailsContainKeywordsPredicate;

public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";
    public static final String COMMAND_ALIAS = "sh";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches for all persons whose details (i.e. name, "
            + "phone, email, address, remark or tags) exactly matches the "
            + "the specified keyword/phrase (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final DetailsContainKeywordsPredicate predicate;

    public SearchCommand(DetailsContainKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        try {
            throw new Exception("stub exception");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CommandResult("stub");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchCommand // instanceof handles nulls
                && this.predicate.equals(((SearchCommand) other).predicate)); // state check
    }
}
