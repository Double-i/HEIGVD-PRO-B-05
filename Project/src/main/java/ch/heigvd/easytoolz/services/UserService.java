package ch.heigvd.easytoolz.services;

import ch.heigvd.easytoolz.controllers.exceptions.user.UserAlreadyPresent;
import ch.heigvd.easytoolz.controllers.exceptions.user.UserFailedDeleteException;
import ch.heigvd.easytoolz.controllers.exceptions.user.UserFailedStoreException;
import ch.heigvd.easytoolz.controllers.exceptions.user.UserNotFoundException;
import ch.heigvd.easytoolz.models.User;

import java.util.List;

public interface UserService {
    User getUser(String username) throws UserNotFoundException;
    void storeUser(User user) throws UserAlreadyPresent, UserFailedStoreException;
    void deleteUser(String username) throws UserFailedDeleteException;
    User updateUser(User newUser, String userName) throws UserNotFoundException;
    List<User> filters(String firstName, String lastName, String userName, String email);
}
