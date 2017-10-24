package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

public class LoginCommand extends Command{
    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD  + ": Authenticates a user of the address book."
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD ";

    public static final String MESSAGE_SUCCESS = "Successfully logged in!";

    private final String username;
    private final String password;

    //TODO: Implement Credentials class so that username and password not stored in open
    public LoginCommand(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public CommandResult execute() {
        // TODO: replace stub method
        return new CommandResult("stub");
    }
}
