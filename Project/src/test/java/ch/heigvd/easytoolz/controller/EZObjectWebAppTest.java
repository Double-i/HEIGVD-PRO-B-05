package ch.heigvd.easytoolz.controller;

import ch.heigvd.easytoolz.config.WebSecurityConfig;
import ch.heigvd.easytoolz.services.interfaces.EZObjectService;
import ch.heigvd.easytoolz.views.EZObjectView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebSecurityConfig.class)
@WebAppConfiguration
public class EZObjectWebAppTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private EZObjectService service;

    @Before
    public void setUp()
    {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void filterByNameTest() throws Exception
    {

        List<EZObjectView> objects = service.getObjectByName("pelle");

        ResultActions action = this.mockMvc
                .perform(get("http://localhost:8080/api/objects/find/name/pelle").with(user("KEVIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        for( int i = 0; i < objects.size(); i++)
        {
            action.andExpect(status().isOk())
                    .andExpect(jsonPath("$["+i+"]..name",hasItem(containsString("pelle"))));
        }

    }

    @Test
    public void filterByDescription() throws Exception {

        List<EZObjectView> objects = service.getObjectByDescription("super");
        ResultActions action = this.mockMvc.perform(get("http://localhost:8080/api/objects/find/description/super").with(user("JEAN"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        for (int i = 0; i < objects.size(); i++) {
            action
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$["+i+"]..description", hasItem(containsString("super"))));
        }

    }

    @Test
    public void filterByOwner() throws Exception
    {
        List<EZObjectView> objects = service.getObjectByOwner("fukuchimiste");

        ResultActions action = this.mockMvc.perform(get("http://localhost:8080/api/objects/owner/fukuchimiste").with(user("JEAN"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        for (int i = 0; i < objects.size(); i++) {
            action
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$["+i+"].owner.userName",is("fukuchimiste")));
        }
    }

}
