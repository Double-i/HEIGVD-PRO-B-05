package ch.heigvd.easytoolz.services.interfaces;

import ch.heigvd.easytoolz.models.User;
import org.springframework.security.authentication.BadCredentialsException;

public interface AuthenticationService {
    /**
     * Try to verify if the username and the password are correct
     *
     * @param username Username of the user
     * @param password password of the user
     * @return true if the user and the password are correct else false
     * @throws BadCredentialsException if the username or the password is incorrect
     */
    boolean authenticateUser(String username, String password) throws BadCredentialsException;

    /**
     * @return true if the current session is admin
     */
    boolean isTheCurrentUserAdmin();

    /**
     * @return the details of the current user
     */
    User getTheDetailsOfCurrentUser();

    /**
     * @param username the username which will be connected
     * @return the current details of the user
     */
    User loadByUsername(String username);
}
