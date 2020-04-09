package ch.heigvd.easytoolz.services;

import ch.heigvd.easytoolz.controllers.exceptions.user.UserNotFoundException;
import ch.heigvd.easytoolz.models.User;

public interface UserService {
    public User loadByUsername(String username) throws UserNotFoundException;
}
