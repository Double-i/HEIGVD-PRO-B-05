package ch.heigvd.easytoolz.components;

import ch.heigvd.easytoolz.models.User;
import ch.heigvd.easytoolz.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class EasyAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    /**
     * @param authentication the authentication request
     * @return a new authentification token if the authentication is correct
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<User> userOptional = userRepository.findById(userName);

        if(userOptional.isPresent()) {
            User concreteUser = userOptional.get();
            if(passwordEncoder.matches(password, concreteUser.getPassword()))
                return new UsernamePasswordAuthenticationToken(userName, password, new ArrayList<>());
        }

        throw new BadCredentialsException("The password or the username is incorrect");
    }

    /**
     * @param authentication
     * @return does the class support the authentication ?
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
