package ch.heigvd.easytoolz.controller;

import ch.heigvd.easytoolz.models.EZObject;
import ch.heigvd.easytoolz.repositories.EZObjectRepository;
import ch.heigvd.easytoolz.services.implementation.EZObjectServiceImpl;
import ch.heigvd.easytoolz.services.interfaces.EZObjectService;
import ch.heigvd.easytoolz.views.EZObjectView;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.WebApplicationContext;
import java.util.List;
import org.junit.Assert;

@SpringBootTest
public class EZObjectTestController {

    @MockBean
    EZObjectRepository repository;

    @Autowired
    private EZObjectService service;


    @Test
    public void findCorrectNumberOfObject()
    {
        List<EZObject> found = service.get();
        Assert.assertEquals(13,found.size());
    }

    @Test
    public void shouldFindObjectPelle()
    {
        List<EZObjectView> found = service.getObjectByName("pelle");

        for(int i = 0; i < found.size(); i++)
            Assert.assertThat(found.get(i).getObjectName(), CoreMatchers.containsString("pelle"));
    }

    @Test
    public void shouldOnlyReturnObjectFromOwner()
    {
        List<EZObjectView> found = service.getObjectByOwner("fukuchimiste");

        for(int i = 0; i < found.size(); i++)
            Assert.assertEquals("fukuchimiste",found.get(i).getObjectOwner());
    }
}

