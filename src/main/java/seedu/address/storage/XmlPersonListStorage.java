package seedu.address.storage;

import java.util.List;

import seedu.address.model.person.ReadOnlyPerson;

public class XmlPersonListStorage implements PersonListStorage{

    private String filePath;

    public XmlPersonListStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getPersonListFilePath() {
        return this.filePath;
    }

    public List<ReadOnlyPerson> readPersonList() {
        return readPersonList(this.filePath);
    }

    public List<ReadOnlyPerson> readPersonList(String filePath) {
        return null;
    }

    public void savePersonList(List<ReadOnlyPerson> persons) {
        savePersonList(persons, this.filePath);
    }

    public void savePersonList(List<ReadOnlyPerson> persons, String filePath) {

    }
}
