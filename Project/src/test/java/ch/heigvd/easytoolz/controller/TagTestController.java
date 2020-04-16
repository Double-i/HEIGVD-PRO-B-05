package ch.heigvd.easytoolz.controller;

import ch.heigvd.easytoolz.controllers.TagController;
import ch.heigvd.easytoolz.models.Tag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.test.context.ContextConfiguration;
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
@WebMvcTest(TagController.class)
@ContextConfiguration(classes = {
        WebSecurityConfigurerAdapter.class
})
public class TagTestController {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TagController tagController;

    @Test
    public void appShouldReturnAllTags() throws Exception {
        List<Tag> tags = new LinkedList<>();

        given(tagController.index(null)).willReturn(tags);

        mvc.perform(get("/api/tags/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(tags.size())))
                .andExpect(jsonPath("$[0].name", is(tags.get(0).getName())))
                .andExpect(jsonPath("$[1].name", is(tags.get(1).getName())));
    }


}
