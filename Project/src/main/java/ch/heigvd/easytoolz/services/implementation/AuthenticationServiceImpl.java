package ch.heigvd.easytoolz.services.implementation;

import ch.heigvd.easytoolz.components.EasyAuthenticationProvider;
import ch.heigvd.easytoolz.models.User;
import ch.heigvd.easytoolz.repositories.UserRepository;
import ch.heigvd.easytoolz.services.interfaces.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.HttpCookie;
import java.util.Date;

@Transactional
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
        } catch (BadCredentialsException e) {
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
        if (currentAuthentication != null) {
            if(currentAuthentication instanceof AnonymousAuthenticationToken)
                return null;
            else
                return (User) currentAuthentication.getPrincipal();
        }
        else
            return null;
    }

    @Override
    public User loadByUsername(String username) {
        return (User) userRepository.findById(username).get();
    }


}
