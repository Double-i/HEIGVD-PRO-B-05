package ch.heigvd.easytoolz;

import ch.heigvd.easytoolz.controllers.CityController;
import ch.heigvd.easytoolz.services.interfaces.AuthenticationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(CityController.class)
public class AuthenticationController {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthenticationService authenticationService;



}
