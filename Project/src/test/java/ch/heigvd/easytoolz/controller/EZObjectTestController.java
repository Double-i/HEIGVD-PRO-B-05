package ch.heigvd.easytoolz.controller;

import ch.heigvd.easytoolz.controllers.EZObjectController;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(EZObjectController.class)
public class EZObjectTestController {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EZObjectController controller;


}
