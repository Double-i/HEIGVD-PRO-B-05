package ch.heigvd.easytoolz.services;

import ch.heigvd.easytoolz.controllers.exceptions.user.UserNotFoundException;
import ch.heigvd.easytoolz.models.User;
import ch.heigvd.easytoolz.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User loadByUsername(String username) throws UserNotFoundException {
        return userRepository.findById(username).get();
    }
}
