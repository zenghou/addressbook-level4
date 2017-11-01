package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.PictureSize;
import facebook4j.auth.AccessToken;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

/** Contains the profile panel of a person */
public class PersonProfile extends UiPart<Region> {

    private static final String FXML = "PersonProfile.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private final Facebook facebook = new FacebookFactory().getInstance();

    private final String appId = "383233665430563";
    private final String appSecret = "c127fe47ff977d7da7be5f38b4662ba5";
    private final String appToken = "383233665430563|lAaXz9VGaRFJNVLTI9iTKKl1pWk";
    private final String defaultProfilePicture = "images/fail.png";

    @FXML
    private Label name;

    @FXML
    private Label remark;

    @FXML
    private ImageView profilePicture;

    public PersonProfile() {
        super(FXML);
        registerAsAnEventHandler(this);
        facebook.setOAuthAppId(appId, appSecret);
        facebook.setOAuthAccessToken(new AccessToken(appToken, null));
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        remark.textProperty().bind(Bindings.convert(person.remarkProperty()));
    }

    /**
     * Retrieves a profile picture from Facebook {@see Facebook#getPictureURL}and sets that image as the contact image
     * for the Person's profile. A default image would be assigned if no picture is retrieved from Facebook.
     */
    private void setProfilePicture() {
        Image profilePic;
        try {
            // TODO: Replace hardcoded userID with Dynamically get Facebook userId
            profilePic = new Image(facebook.getPictureURL("1253844668", PictureSize.large).toString());
        } catch (FacebookException fbe) {
            profilePic = new Image(defaultProfilePicture);
            fbe.printStackTrace();
        }
        profilePicture.setImage(profilePic);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        bindListeners(event.getNewSelection().person);
        setProfilePicture();
    }
}
