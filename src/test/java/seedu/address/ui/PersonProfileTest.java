package seedu.address.ui;

import static seedu.address.ui.testutil.GuiTestAssert.assertProfileEquals;

import org.junit.Test;

import guitests.guihandles.PersonProfileHandle;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.testutil.PersonBuilder;

public class PersonProfileTest extends GuiUnitTest {
    @Test
    public void display() {
        // with tags
        Person personWithTags = new PersonBuilder().withTags().build();
        // set remarks
        personWithTags.setRemark(new Remark("Sample remark"));
        PersonProfile personProfile = new PersonProfile();
        EventsCenter.getInstance().post(new PersonPanelSelectionChangedEvent(
                new PersonCard(personWithTags, 1)));
        uiPartRule.setUiPart(personProfile);
        assertCardDisplay(personProfile, personWithTags);
    }

    /**
     * Asserts that {@code personProfile} displays the details of {@code expectedPerson} correctly.
     */
    private void assertCardDisplay(PersonProfile personProfile, ReadOnlyPerson expectedPerson) {
        guiRobot.pauseForHuman();

        PersonProfileHandle personProfileHandle = new PersonProfileHandle(personProfile.getRoot());

        // verify person details are displayed correctly
        assertProfileEquals(expectedPerson, personProfileHandle);
    }
}
