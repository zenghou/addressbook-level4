package seedu.address.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;

/**
 * Represents a storage for a list of {@link seedu.address.model.person.Person}.
 */
public interface PersonListStorage {

    /**
     * Returns the file path of the data file.
     */
    public String getPersonListFilePath();

    /**
     * To be implemented
     */
    public Optional<UniquePersonList> readPersonList() throws DataConversionException, IOException;

    /**
     * To be implemented
     */
    public Optional<UniquePersonList> readPersonList(String filePath)
        throws DataConversionException, FileNotFoundException;

    /**
     * Saves the given {@link ReadOnlyPerson} to the storage.
     * @param persons cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    public void savePersonList(List<ReadOnlyPerson> persons) throws IOException;

    /**
     * @see #savePersonList(List)
     */
    public void savePersonList(List<ReadOnlyPerson> persons, String filePath) throws IOException;

}
