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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
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



}
