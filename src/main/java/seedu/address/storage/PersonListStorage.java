package seedu.address.storage;

import java.io.IOException;
import java.util.List;

import seedu.address.model.person.ReadOnlyPerson;

public interface PersonListStorage {

    public String getPersonListFilePath();

    public List<ReadOnlyPerson> readPersonList();

    public List<ReadOnlyPerson> readPersonList(String filePath);

    public void savePersonList(List<ReadOnlyPerson> persons) throws IOException;

    public void savePersonList(List<ReadOnlyPerson> persons, String filePath) throws IOException;

}
