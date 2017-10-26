package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;

public class UserCredsChangedEvent extends BaseEvent {

    @Override
    public String toString() {
        return UserCredsChangedEvent.class.getSimpleName();
    }
}
