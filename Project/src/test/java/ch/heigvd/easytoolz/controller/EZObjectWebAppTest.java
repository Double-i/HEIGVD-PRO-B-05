package ch.heigvd.easytoolz.controller;

import ch.heigvd.easytoolz.models.EZObject;
import ch.heigvd.easytoolz.services.interfaces.EZObjectService;
import ch.heigvd.easytoolz.views.EZObjectView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EZObjectWebAppTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    EZObjectService service;

    List<EZObjectView> objects;
    /*@Before
    public void Init()
    {
        objects = service.getAll();
    }
    @Test
    public void shouldReturnIndex() throws Exception
    {

        this.mockMvc.perform(get("http://localhost:8080/api/objects/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(objects.size())));

    }

    @Test
    public void shouldReturnObjectView() throws Exception
    {
        for(int i = 0; i < objects.size(); i++)
        {
            this.mockMvc.perform(get("http://localhost:8080/api/objects/"+(i+1))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$",hasEntry("objectName",objects.get(i).getObjectName())))
                    .andExpect(jsonPath("$",hasEntry("objectDescription",objects.get(i).getObjectDescription())))
                    .andExpect(jsonPath("$",hasEntry("objectOwner",objects.get(i).getObjectOwner())))
                    .andExpect(jsonPath("$",hasEntry("ownerAddress",objects.get(i).getOwnerAddress())))
                    .andExpect(jsonPath("$",hasEntry("ownerDistrict",objects.get(i).getOwnerDistrict())))
                    .andExpect(jsonPath("$",hasEntry("ownerPostalCode",objects.get(i).getOwnerPostalCode())));
        }
    }

    @Test
    public void filterByNameTest() throws Exception
    {
        List<EZObjectView> filtered = service.getObjectByName("pelle");

        this.mockMvc.perform(get("http://localhost:8080/api/objects/find/name/pelle")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]..objectName",hasItem(containsString("pelle"))));
    }

    @Test
    public void filterByDescription() throws Exception
    {
        List<EZObjectView> filtered = service.getObjectByDescription("super");

        this.mockMvc.perform(get("http://localhost:8080/api/objects/find/description/super")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]..objectDescription",hasItem(containsString("super"))));
    }

    @Test
    public void filterByOwner() throws Exception
    {
        List<EZObjectView> filtered = service.getObjectByOwner("fukuchimiste");

        this.mockMvc.perform(get("http://localhost:8080/api/objects/owner/fukuchimiste")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]..objectOwner",hasItem("fukuchimiste")));
    }*/

}
