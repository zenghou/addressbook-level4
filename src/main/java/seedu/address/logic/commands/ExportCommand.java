package seedu.address.logic.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.storage.XmlPersonListStorage;

/**
 * Export a person list to a specified save file.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Exports the persons identified by the index numbers used in the last person listing.\n"
        + "Parameters: INDEX; PATH (must be positive integers separated by commas or spaces)\n"
        + "Example: " + COMMAND_WORD + " 1, 2 3; \"~/Desktop/persons.xml\"";

    private final List<Index> targetIndexes;
    private final String filePath;

    public ExportCommand(List<Index> targetIndexes, String filePath) {
        this.targetIndexes = targetIndexes;
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShowList = model.getFilteredPersonList();
        List<ReadOnlyPerson> personsToSave = new ArrayList<>();
        for (Index index : this.targetIndexes) {
            if (index.getZeroBased() > lastShowList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            personsToSave.add(lastShowList.get(index.getZeroBased()));
        }
        XmlPersonListStorage xmlPersonListStorage = new XmlPersonListStorage(this.filePath);
        try {
            xmlPersonListStorage.savePersonList(personsToSave);
        } catch (IOException ioe) {
            throw new CommandException(ioe.getMessage());
            //TODO: better error handling
        }
        return new CommandResult("Persons have been exported to file: " + filePath);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ExportCommand // instanceof handles nulls
            && this.targetIndexes.equals(((ExportCommand) other).targetIndexes) // state check
            && this.filePath.equals(((ExportCommand) other).filePath));
    }
}
