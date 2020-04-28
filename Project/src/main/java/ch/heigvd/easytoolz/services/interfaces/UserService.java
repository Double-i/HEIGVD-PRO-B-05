package ch.heigvd.easytoolz.services.interfaces;

import ch.heigvd.easytoolz.exceptions.user.UserAlreadyPresent;
import ch.heigvd.easytoolz.exceptions.user.UserFailedDeleteException;
import ch.heigvd.easytoolz.exceptions.user.UserFailedStoreException;
import ch.heigvd.easytoolz.exceptions.user.UserNotFoundException;
import ch.heigvd.easytoolz.models.DTO.EditPasswordRequest;
import ch.heigvd.easytoolz.models.User;

import java.util.List;

public interface UserService {
    /**
     * get the details of the user
     *
     * @param username
     * @return the user which has the username passed in parameter
     * @throws UserNotFoundException
     */
    User getUser(String username) throws UserNotFoundException;



    /**
     * stores the user passed in parameter
     *
     * @param user the new user
     * @throws UserAlreadyPresent       if the username is already present
     * @throws UserFailedStoreException if the insertion of the user fails
     */
    User storeUser(User user) throws UserAlreadyPresent, UserFailedStoreException;

    /**
     * deletes the user which has the username passed in parameter
     *
     * @param username
     * @throws UserFailedDeleteException if the removal of the user fails
     */
    void deleteUser(String username) throws UserFailedDeleteException;

    /**
     * updates the user with the newUser
     *
     * @param newUser  the updated user
     * @param userName the current username of the user
     * @return the new user updated
     * @throws UserNotFoundException if the user isn't found
     */
    User updateUser(User newUser, String userName) throws UserNotFoundException;

    /**
     * filters the list of the users
     *
     * @param firstName
     * @param lastName
     * @param userName
     * @param email
     * @return
     */
    List<User> filters(String firstName, String lastName, String userName, String email);

    /**
     * Change the user's password. The current password should be send with the request and is check before changing the password
     *
     * @param username
     * @param editPasswordRequest
     */
    void editPassword(String username, EditPasswordRequest editPasswordRequest);
}
