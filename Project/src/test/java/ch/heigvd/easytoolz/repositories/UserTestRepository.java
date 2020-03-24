package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserTestRepository {
    @Autowired
    TestEntityManager entityManager;
    @Autowired
    UserRepository userRepository;

    ArrayList<User> allUsers;

    User user;
    User user1;
    User user2;
    User user3;

    @Before
    public void setUp(){
        allUsers = new ArrayList<>();

        user = new User("vanlong","Bastien","Potet","1234", "bastien.potet@gmail.com", false);
        user1 = new User("patoche","Patrick","Paul","abcd", "patoche@gmail.com" , true);
        user2 = new User("praymond","Raymond","Paien","1234", "paienraymond@dayrep.com", false);
        user3 = new User("vcliche","Cliche","Vick","abcd1234", "vcliche@gmail.com" , true);
    }

    @Test
    public void itShouldSaveUser(){
        User tmp = entityManager.persistAndFlush(user);
        assertThat(userRepository.findById(tmp.getUserName()).get()).isEqualTo(tmp);
    }
}
