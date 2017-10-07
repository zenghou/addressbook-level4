package seedu.address.storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.testutil.TypicalPersons;

public class XmlPersonListStorageTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlPersonListStorageTest/");
    private static final List<ReadOnlyPerson> typicalPersonList = new ArrayList<>();
    private static final UniquePersonList typicalUniquePersonList = new UniquePersonList();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Before
    public void init() {
        typicalPersonList.add(TypicalPersons.ALICE);
        typicalPersonList.add(TypicalPersons.BENSON);
        typicalPersonList.add(TypicalPersons.CARL);
        typicalPersonList.add(TypicalPersons.DANIEL);
        typicalPersonList.add(TypicalPersons.ELLE);
        try {
            typicalUniquePersonList.setPersons(typicalPersonList);
        } catch (IllegalValueException e) {
            assert false : "not possible";
        }
    }

    @Test
    public void savePersonList_nullPersonList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        savePersonListAsList(null, "SomeFile.xml");
    }

    @Test
    public void savePersonList_nullUniquePersonList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        savePersonListAsUniquePersonList(null, "SomeFile.xml");
    }

    @Test
    public void savePersonList_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        savePersonListAsList(new ArrayList<>(), null);
    }

    /**
     * Saves {@code persons} at specified {@code filePath}
     */
    private void savePersonListAsList(List<ReadOnlyPerson> persons, String filePath) {
        try {
            new XmlPersonListStorage(filePath).savePersonList(persons, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    private void savePersonListAsUniquePersonList(UniquePersonList persons, String filePath) {
        try {
            new XmlPersonListStorage(filePath).savePersonList(persons, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
               ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
               : null;
    }

}
