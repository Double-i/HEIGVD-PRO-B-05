package ch.heigvd.easytoolz.user;

import ch.heigvd.easytoolz.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void iShouldFindAUser(){
        User user = userRepository.save(new User());
    }
}
