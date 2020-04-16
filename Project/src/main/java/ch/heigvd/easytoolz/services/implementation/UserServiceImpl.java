package ch.heigvd.easytoolz.services.implementation;

import ch.heigvd.easytoolz.exceptions.authentication.AccessDeniedNotAdminException;
import ch.heigvd.easytoolz.exceptions.user.UserAlreadyPresent;
import ch.heigvd.easytoolz.exceptions.user.UserFailedDeleteException;
import ch.heigvd.easytoolz.exceptions.user.UserFailedStoreException;
import ch.heigvd.easytoolz.exceptions.user.UserNotFoundException;
import ch.heigvd.easytoolz.models.User;
import ch.heigvd.easytoolz.repositories.UserRepository;
import ch.heigvd.easytoolz.services.interfaces.AddressService;
import ch.heigvd.easytoolz.services.interfaces.AuthenticationService;
import ch.heigvd.easytoolz.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static ch.heigvd.easytoolz.util.ServiceUtils.transformLike;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    AddressService addressService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    UserRepository userRepository;

    @Override
    public User getUser(String username) throws UserNotFoundException {
        if (!authenticationService.isTheCurrentUserAdmin()) {
            if (!authenticationService.getTheDetailsOfCurrentUser().getUserName().equals(username))
                throw new AccessDeniedNotAdminException();
        }
        return userRepository
                .findById(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    @Override
    public List<User> filters(String firstName, String lastName, String userName, String email) {

        if (!authenticationService.isTheCurrentUserAdmin())
            throw new AccessDeniedNotAdminException();

        firstName = transformLike(firstName);
        lastName = transformLike(lastName);
        userName = transformLike(userName);
        email = transformLike(email);

        if (firstName != null) {
            if (lastName != null) {
                return userRepository.findByFirstNameLikeAndLastNameLike(firstName, lastName);
            } else {
                return userRepository.findByFirstNameLike(firstName);
            }
        } else {
            if (lastName != null) {
                return userRepository.findByLastNameLike(lastName);
            } else if (userName != null) {
                return userRepository.findByUserNameLike(userName);
            } else if (email != null) {
                return userRepository.findByEmailLike(email);
            } else {
                return userRepository.findAll();
            }
        }
    }

    @Override
    public void storeUser(User user) throws UserAlreadyPresent, UserFailedStoreException {
        if (userRepository.findById(user.getUserName()).isPresent()) {
            throw new UserAlreadyPresent(user.getUserName());
        } else {
            addressService.storeAddress(user.getAddress());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            if (userRepository.findById(user.getUserName()).isEmpty()) {
                throw new UserFailedStoreException(user.getUserName());
            }
        }
    }

    @Override
    public User updateUser(User newUser, String username) throws UserNotFoundException {
        if (!authenticationService.isTheCurrentUserAdmin()) {
            if (!authenticationService.getTheDetailsOfCurrentUser().getUserName().equals(username))
                throw new AccessDeniedNotAdminException();
        }

        return userRepository.findById(username)
                .map(oldUser -> {
                    if (newUser.getFirstName() != null) oldUser.setFirstName(newUser.getFirstName());
                    if (newUser.getLastName() != null) oldUser.setLastName(newUser.getLastName());
                    if (oldUser.isAdmin() != newUser.isAdmin()) oldUser.setAdmin(newUser.isAdmin());
                    if (newUser.getEmail() != null) oldUser.setEmail(newUser.getEmail());
                    if (newUser.getAddress() != null)
                        addressService.updateAddress(newUser.getAddress(), newUser.getAddress().getId());
                    return userRepository.save(oldUser);
                })
                .orElseThrow(
                        () -> new UserNotFoundException(username)
                );
    }

    @Override
    public void deleteUser(String username) throws UserFailedDeleteException {
        if (!authenticationService.isTheCurrentUserAdmin()) {
            if (!authenticationService.getTheDetailsOfCurrentUser().getUserName().equals(username))
                throw new AccessDeniedNotAdminException();
        }

        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            if (userRepository.findById(username).isPresent()) {
                throw new UserFailedDeleteException(username);
            }
        } else {
            throw new UserNotFoundException(username);
        }
    }
}
