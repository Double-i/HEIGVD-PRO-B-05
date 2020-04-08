package ch.heigvd.easytoolz.controller;

import ch.heigvd.easytoolz.controllers.UserController;
import ch.heigvd.easytoolz.models.Address;
import ch.heigvd.easytoolz.models.City;
import ch.heigvd.easytoolz.models.Country;
import ch.heigvd.easytoolz.models.User;
import ch.heigvd.easytoolz.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// https://developer.okta.com/blog/2019/03/28/test-java-spring-boot-junit5

// CURL Request for creating a new user
// curl -X POST localhost:8080/api/users/ -H "Content-type:application/json;Accept:application/json" -d "{\"firstName\":\"Bastien\", \"lastName\":\"Potet\",\"password\":\"1234\",\"isAdmin\":\"true\",\"userName\":\"vanlong\",\"email\":\"bastien.potet@gmail.com\"}"
// CURL Request for updating the user 'vanlong'
// curl -X PUT localhost:8080/api/users/vanlong -H "Content-type:application/json;Accept:application/json" -d "{\"userName\":\"Babouste\"}"
// CURL Request for deleting the user 'vanlong'

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
class UserTestController {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserController userController;

    private List<User> allUsers;

    private User user;
    private User user1;
    private User user2;
    private User user3;

    private Address address;

    @BeforeEach
    public void setUp(){
        allUsers = new ArrayList<>();

        Country country = new Country("Suisse");
        City city = new City("Orsières", country);
        address = new Address("Somlaproz 48", "Entremont", "1937", 2,4, city);

        user = new User("vanlong","Bastien","Potet","1234", "bastien.potet@gmail.com", address, false);
        user1 = new User("patoche","Patrick","Paul","abcd", "patoche@gmail.com" , address, true);
        user2 = new User("praymond","Raymond","Paien","1234", "paienraymond@dayrep.com", address, false);
        user3 = new User("vcliche","Cliche","Vick","abcd1234", "vcliche@gmail.com" , address, true);
    }

    @AfterEach
    public void setDown(){
        allUsers.clear();
    }

    @Test
    public void appShouldDisplayAllUsers() throws Exception{
        mvc.perform(get("/api/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(allUsers.size())))
                .andExpect(jsonPath("$[0].firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$[0].firstName",not("")))
                .andExpect(jsonPath("$[0].lastName", is(user.getLastName())))
                .andExpect(jsonPath("$[0].lastName",not("")))
                .andExpect(jsonPath("$[0].userName", is(user.getUserName())))
                .andExpect(jsonPath("$[0].userName",not("")))
                .andExpect(jsonPath("$[0].password", is(user.getPassword())))
                .andExpect(jsonPath("$[0].password",not("")))
                .andExpect(jsonPath("$[0].admin", is(user.isAdmin())))
                .andExpect(jsonPath("$[0].email", is(user.getEmail())))
                .andExpect(jsonPath("$[0].email",not("")));
    }

    @Test
    public void appShouldThrowExceptionIfNotNull(){
        throw new RuntimeException("Test not implemented");
    }

    @Test
    public void appShouldDisplayTheInformationsOfUser() throws Exception {

        given(userController.show(user.getUserName())).willReturn(user);

        mvc.perform(get(
                "/api/users/" + user.getUserName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
                .andExpect(jsonPath("$.userName", is(user.getUserName())))
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.password", is(user.getPassword())))
                .andExpect(jsonPath("$.admin", is(user.isAdmin())));
    }

    @Test
    public void appShouldStoreUser() throws Exception {
        JSONObject userJSonObject = new JSONObject();

        userJSonObject.put("userName","vanlong");
        userJSonObject.put("firstName","Bastien");
        userJSonObject.put("lastName","Potet");
        userJSonObject.put("password","1234");
        userJSonObject.put("email","bastien.potet@gmail.com");
        userJSonObject.put("isAdmin","true");



        User user = new User("vanlong","Bastien","Potet","1234", "bastien.potet@gmail.com", address, true);

        when(userController.store(any(User.class))).thenReturn(user);

        mvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJSonObject.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Bastien")))
                .andExpect(jsonPath("$.lastName", is("Potet")))
                .andExpect(jsonPath("$.password",is("1234")))
                .andExpect(jsonPath("$.userName",is("vanlong")))
                .andExpect(jsonPath("$.admin",is(true)))
                .andExpect(jsonPath("$.email",is("bastien.potet@gmail.com")));
    }

    @Test
    public void appShouldUpdateAUser() throws Exception {
        JSONObject userJSonObject = new JSONObject();

        userJSonObject.put("firstName","Harry");
        userJSonObject.put("lastName","Potter");
        userJSonObject.put("password","1080");
        userJSonObject.put("email","harry1.potter@gmail.com");
        userJSonObject.put("isAdmin","false");

        User harry = new User("vanlong","Harry","Potter","1080","harry1.potter@gmail.com",address, false);

        when(userController.show("vanlong")).thenReturn(user);

        mvc.perform(put("/api/users/vanlong")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJSonObject.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Harry")))
                .andExpect(jsonPath("$.lastName", is("Potter")))
                .andExpect(jsonPath("$.password",is("1234")))
                .andExpect(jsonPath("$.userName",is("vanlong")))
                .andExpect(jsonPath("$.admin",is(true)))
                .andExpect(jsonPath("$.email",is("bastien.potet@gmail.com")));
    }

    @Test
    public void appShouldSearchWithLikeMode(){
        throw new RuntimeException("Test not implemented");
    }

    @Test
    public void appShouldDeleteAUser(){
        throw new RuntimeException("Test not implemented");
    }

    @Test
    public void appShouldSearchAUser(){
        throw new RuntimeException("Test not implemented");
    }

    @Test
    public void appShouldHashThePassword(){
        throw new RuntimeException("Test not implemented");
    }

    @Test
    public void appShouldPaginateTheData(){
        throw new RuntimeException("Test not implemented");
    }


}
