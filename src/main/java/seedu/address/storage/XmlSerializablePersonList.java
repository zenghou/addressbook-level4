package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;

@XmlRootElement(name = "personList")
public class XmlSerializablePersonList {

    @XmlElement
    private List<XmlAdaptedPerson> persons;

    /**
     * Creates an empty XmlSerializablePersonList.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializablePersonList() {
        persons = new ArrayList<>();
    }

    /**
     *
     * @param persons
     */
    public XmlSerializablePersonList(List<Person> persons) {
        this();
        this.persons.addAll(persons.stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
    }

    public ObservableList<Person> getPersons() {
        final ObservableList<Person> persons = this.persons.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(persons);
    }
}
