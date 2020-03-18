package ch.heigvd.easytoolz;

import ch.heigvd.easytoolz.controllers.UserController;
import ch.heigvd.easytoolz.repositories.UserRepository;
import ch.heigvd.easytoolz.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@SpringBootTest
class EasyToolzApplicationTests {

    @Test
    void contextLoads() {
    }

}
