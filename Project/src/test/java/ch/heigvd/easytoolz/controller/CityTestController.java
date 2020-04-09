package ch.heigvd.easytoolz.controller;

import ch.heigvd.easytoolz.controllers.CityController;
import ch.heigvd.easytoolz.controllers.UserController;
import ch.heigvd.easytoolz.models.City;
import ch.heigvd.easytoolz.models.Country;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CityController.class)
public class CityTestController {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CityController cityController;

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
                .accept(MediaType.APPLICATION_JSON))
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

        given(cityController.index(null, "Sui")).willReturn(cities);

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

        given(cityController.index("zu", "Sui")).willReturn(cities);

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

        given(cityController.index("zu", null)).willReturn(cities);

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
