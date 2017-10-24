package seedu.address.logic.commands;

public class LoginCommand extends Command{
    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_SUCCESS = "Successfully logged in!";
    
    @Override
    public CommandResult execute() {
        // TODO: replace stub method
        return new CommandResult("stub");
    }
}
