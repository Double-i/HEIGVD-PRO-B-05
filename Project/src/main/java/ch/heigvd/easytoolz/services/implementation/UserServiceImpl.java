package ch.heigvd.easytoolz.services.implementation;


import ch.heigvd.easytoolz.exceptions.authentication.AccessDeniedException;
import ch.heigvd.easytoolz.exceptions.authentication.AccessDeniedNotAdminException;
import ch.heigvd.easytoolz.exceptions.user.UserAlreadyPresent;
import ch.heigvd.easytoolz.exceptions.user.UserFailedDeleteException;
import ch.heigvd.easytoolz.exceptions.user.UserFailedStoreException;
import ch.heigvd.easytoolz.exceptions.user.UserNotFoundException;
import ch.heigvd.easytoolz.models.Notification;
import ch.heigvd.easytoolz.models.DTO.EditPasswordRequest;
import ch.heigvd.easytoolz.models.User;
import ch.heigvd.easytoolz.repositories.UserRepository;
import ch.heigvd.easytoolz.services.interfaces.AddressService;
import ch.heigvd.easytoolz.services.interfaces.AuthenticationService;
import ch.heigvd.easytoolz.services.interfaces.UserService;
import ch.heigvd.easytoolz.specifications.UserSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

        Specification<User> specs = UserSpecs.getAll();

        if (firstName != null) {
            specs = specs.and(UserSpecs.getFirstname(firstName));
        }

        if(lastName != null) {
            specs = specs.and(UserSpecs.getLastname(lastName));
        }

        if(userName != null) {
            specs = specs.and(UserSpecs.getUsername(userName));
        }

        if(email != null) {
            specs = specs.and(UserSpecs.getEmail(email));
        }

        return userRepository.findAll(specs);
    }

    @Override
    public void editPassword(String username, EditPasswordRequest editPasswordRequest) {
        User connectedUser = authenticationService.getTheDetailsOfCurrentUser();
     ;
        // We check the user wants to edit his password and that his current password is correct
        if(!connectedUser.getUserName().equals(username) )
            throw new AccessDeniedException();


        if (! passwordEncoder.matches(editPasswordRequest.getCurrentPassword(), connectedUser.getPassword()))
            throw new AccessDeniedException();

        // We encode the new password and save it
        connectedUser.setPassword(passwordEncoder.encode(editPasswordRequest.getNewPassword()));
        userRepository.save(connectedUser);
    }

    @Override
    public List<Notification> getNotifications(String username, boolean alreadyRead) {
        return getUser(username).getNotifications(alreadyRead);
    }

    @Override
    public User storeUser(User user) throws UserAlreadyPresent, UserFailedStoreException {
        if (userRepository.findById(user.getUserName()).isPresent()) {
            throw new UserAlreadyPresent(user.getUserName());
        } else {
            addressService.storeAddress(user.getAddress());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User storedUser = userRepository.save(user);
            if (userRepository.findById(user.getUserName()).isEmpty()) {
                throw new UserFailedStoreException(user.getUserName());
            }
            return storedUser;
        }
    }

    @Override
    public User updateUser(User newUser, String username) throws UserNotFoundException {
        if (!authenticationService.isTheCurrentUserAdmin()) {
            if (!authenticationService.getTheDetailsOfCurrentUser().getUserName().equals(username))
                throw new AccessDeniedNotAdminException();
        }
        System.out.println(newUser);

        return userRepository.findById(username)
            .map(oldUser -> {
                if (newUser.getFirstName() != null) oldUser.setFirstName(newUser.getFirstName());
                if (newUser.getLastName() != null) oldUser.setLastName(newUser.getLastName());
                // if the admin flag is different we check the user is admin otherwise throw access denied exception
                if(oldUser.isAdmin() != newUser.isAdmin()){
                    if(authenticationService.isTheCurrentUserAdmin()) {
                        oldUser.setAdmin(newUser.isAdmin());
                    }else{
                        throw new AccessDeniedNotAdminException();
                    }
                }
                if (newUser.getEmail() != null) oldUser.setEmail(newUser.getEmail());
                if (newUser.getAddress() != null)
                    addressService.updateAddress(newUser.getAddress(), oldUser.getAddress().getId());

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
