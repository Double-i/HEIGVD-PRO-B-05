package ch.heigvd.easytoolz.services;

import ch.heigvd.easytoolz.EasyAuthenticationProvider;
import ch.heigvd.easytoolz.models.User;
import ch.heigvd.easytoolz.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private EasyAuthenticationProvider authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean authenticateUser(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return true;
        }
        catch (BadCredentialsException e) {
            return false;
        }
    }

    @Override
    public boolean isTheCurrentUserAdmin() {
        return getTheDetailsOfCurrentUser().isAdmin();
    }

    @Override
    public User getTheDetailsOfCurrentUser() {
        Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
        if(currentAuthentication != null)
            return (User) currentAuthentication.getPrincipal();
        else
            return null;
    }

    @Override
    public User loadByUsername(String username) {
        return (User) userRepository.findById(username).get();
    }

}