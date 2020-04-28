package ch.heigvd.easytoolz;

import ch.heigvd.easytoolz.controllers.EZObjectController;
import ch.heigvd.easytoolz.models.EZObject;
import ch.heigvd.easytoolz.repositories.EZObjectRepository;
import ch.heigvd.easytoolz.services.implementation.EZObjectServiceImpl;
import ch.heigvd.easytoolz.services.interfaces.EZObjectService;
import ch.heigvd.easytoolz.views.EZObjectView;
import org.assertj.core.api.Assertions;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;
import java.util.List;
import org.assertj.core.api.Assertions.*;

@SpringBootTest
public class EZObjectTestController {


    @Autowired
    private EZObjectService service;

    @DisplayName("The controller must exist in the current context")
    @Test
    public void contextLoads() throws Exception
    {
        Assertions.assertThat(service).isNotNull();
    }


}

