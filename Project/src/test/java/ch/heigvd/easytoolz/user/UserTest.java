package ch.heigvd.easytoolz.user;

import ch.heigvd.easytoolz.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserTest {
    @Autowired
    private UserRepository userRepository;

    // CURL Request for creating a new user
    // curl -X POST localhost:8080/api/users/ -H "Content-type:application/json;Accept:application/json" -d "{\"firstName\":\"Bastien\", \"lastName\":\"Potet\",\"password\":\"1234\",\"isAdmin\":\"true\",\"userName\":\"vanlong\"}"

    @Test
    public void iShouldFindAUser(){
    }
}
