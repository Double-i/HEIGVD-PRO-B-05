package ch.heigvd.easytoolz;

import ch.heigvd.easytoolz.controllers.UserController;
import ch.heigvd.easytoolz.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;

// CURL Request for creating a new user
// curl -X POST localhost:8080/api/users/ -H "Content-type:application/json;Accept:application/json" -d "{\"firstName\":\"Bastien\", \"lastName\":\"Potet\",\"password\":\"1234\",\"isAdmin\":\"true\",\"userName\":\"vanlong\"}"
// CURL Request for updating the user 'vanlong'
// curl -X PUT localhost:8080/api/users/vanlong -H "Content-type:application/json;Accept:application/json" -d "{\"userName\":\"Babouste\"}"
// CURL Request for deleting the user 'vanlong'

@SpringBootTest(properties = "test-application.properties")
class UserTest {

    @Autowired
    private UserController userController;

    @Test
    public void contextLoads(){
        assertThat(userController).isNotNull();
    }

    /*@Test
    public void storeUser(){
        User user = new User("vanlong","Bastien","Potet","1234",false,1);
        userController.store(user);
        user = userController.show("vanlong");
        assertEquals(user.getFirstName(),"Bastien");
        assertEquals(user.getLastName(), "Potet");
        assertEquals(user.getUserName(), "vanlong");
        assertEquals(user.getPassword(), "1234");
        assertFalse(user.isAdmin());
    }*/
}
