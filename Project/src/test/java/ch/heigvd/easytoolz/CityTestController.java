package ch.heigvd.easytoolz;

import ch.heigvd.easytoolz.config.WebSecurityConfig;
import ch.heigvd.easytoolz.controllers.CityController;
import ch.heigvd.easytoolz.models.City;
import ch.heigvd.easytoolz.models.Country;
import ch.heigvd.easytoolz.models.User;
import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.print.attribute.standard.Media;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        WebSecurityConfig.class
})
@WebAppConfiguration
public class CityTestController {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private CityController cityController;

    @Before
    public void setUp(){
        mvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
    }

    @Test
    public void AppShouldReturnListCities() throws Exception {
        List<City> cities = new LinkedList<>();

        Country country  = new Country("Suisse");
        Country country1 = new Country("France");
        cities.add(new City("Zürich", country));
        cities.add(new City("Bern", country));
        cities.add(new City("Paris", country1));

        given(cityController.index(null, null)).willReturn(cities);

        mvc.perform(get("/api/cities/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(user("Henri")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(cities.size())))
                .andExpect(jsonPath("$[0].city", is(cities.get(0).getCity())))
                .andExpect(jsonPath("$[0].country.id", is(cities.get(0).getCountry().getId())))
                .andExpect(jsonPath("$[0].country.country", is(cities.get(0).getCountry().getCountry())));
    }

    @Test
    public void AppShouldFilterByCountry() throws Exception {
        List<City> cities = new LinkedList<>();

        Country country  = new Country("Suisse");

        cities.add(new City("Zürich", country));
        cities.add(new City("Bern", country));

        mvc.perform(get("/api/cities?country=Sui")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(cities.size())))
                .andExpect(jsonPath("$[1].city", is(cities.get(1).getCity())))
                .andExpect(jsonPath("$[1].country.id", is(cities.get(1).getCountry().getId())))
                .andExpect(jsonPath("$[1].country.country", is(cities.get(1).getCountry().getCountry())));
    }

    @Test
    public void AppShouldFilterByNameAndCountry() throws Exception {

        List<City> cities = new LinkedList<>();

        Country country  = new Country("Suisse");

        cities.add(new City("Zurich", country));
        cities.add(new City("Bern", country));
        cities.add(new City("Zurichhorn", country));

        mvc.perform(get("/api/cities?name=zu&country=Sui")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(cities.size())))
                .andExpect(jsonPath("$[0].city", is(cities.get(0).getCity())))
                .andExpect(jsonPath("$[0].country.id", is(cities.get(0).getCountry().getId())))
                .andExpect(jsonPath("$[0].country.country", is(cities.get(0).getCountry().getCountry())));
    }

    @Test
    public void AppShouldFilterByName() throws Exception {
        List<City> cities = new LinkedList<>();

        Country country  = new Country("Suisse");

        cities.add(new City("Zurich", country));
        cities.add(new City("Zug", country));

        mvc.perform(get("/api/cities?name=zu")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(cities.size())))
                .andExpect(jsonPath("$[1].city", is(cities.get(1).getCity())))
                .andExpect(jsonPath("$[1].country.id", is(cities.get(1).getCountry().getId())))
                .andExpect(jsonPath("$[1].country.country", is(cities.get(1).getCountry().getCountry())));

    }
}
