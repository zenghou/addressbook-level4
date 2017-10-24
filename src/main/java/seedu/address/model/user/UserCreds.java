package seedu.address.model.user;

import java.util.Objects;

/**
 * Represents a user's credentials
 */
public class UserCreds {
    private String username;
    private int passwordHash;

    public UserCreds() {
        username = "admin";
        passwordHash = "password".hashCode();
    }

    public UserCreds(String username, String password) {
        this.username = username;
        this.passwordHash = password.hashCode();
    }

    public String getUsername() {
        return username;
    }

    /**
     * Takes in a new String {@code password} and updates the passwordHash
     */
    public void updatePassword(String newPassword){
        passwordHash = newPassword.hashCode();
    }

    public void updateUsername(String newUsername) {
        username = newUsername;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UserCreds // instanceof handles nulls
                && this.username.equals(((UserCreds) other).username)
                && this.passwordHash == ((UserCreds) other).passwordHash);
    }

    @Override
    public String toString() {
        return "User Credentials should not be printed out";
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, passwordHash);
    }

}
