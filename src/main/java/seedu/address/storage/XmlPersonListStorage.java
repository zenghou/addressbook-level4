package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

import seedu.address.commons.util.FileUtil;
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

    public void savePersonList(List<ReadOnlyPerson> persons) throws IOException {
        savePersonList(persons, this.filePath);
    }

    public void savePersonList(List<ReadOnlyPerson> persons, String filePath) throws IOException{
        requireNonNull(persons);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializablePersonList(persons));
    }
}
