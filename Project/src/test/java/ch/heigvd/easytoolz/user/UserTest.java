package ch.heigvd.easytoolz.user;

import ch.heigvd.easytoolz.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserTest {
    @Autowired
    private UserRepository userRepository;

    // CURL Request for creating a new user
    // curl -X POST localhost:8080/api/users/ -H "Content-type:application/json;Accept:application/json" -d "{\"firstName\":\"Bastien\", \"lastName\":\"Potet\",\"password\":\"1234\",\"isAdmin\":\"true\",\"userName\":\"vanlong\"}"
    // CURL Request for updating the user 'vanlong'
    // curl -X PUT localhost:8080/api/users/vanlong -H "Content-type:application/json;Accept:application/json" -d "{\"userName\":\"Babouste\"}"

    @Test
    public void iShouldFindAUser(){
    }
}
