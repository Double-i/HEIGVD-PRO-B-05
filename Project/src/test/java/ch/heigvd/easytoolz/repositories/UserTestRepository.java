package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.Address;
import ch.heigvd.easytoolz.models.City;
import ch.heigvd.easytoolz.models.Country;
import ch.heigvd.easytoolz.models.User;
import org.junit.Before;
import org.junit.Test;
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

        Country country = new Country("Suisse");
        City city = new City("Orsières", country);
        Address address = new Address("Somlaproz 48", "Entremont", "1937", 2,4, city);

        user = new User("vanlong","Bastien","Potet","1234", "bastien.potet@gmail.com",address,  false);
        user1 = new User("patoche","Patrick","Paul","abcd", "patoche@gmail.com" , address, true);
        user2 = new User("praymond","Raymond","Paien","1234", "paienraymond@dayrep.com", address, false);
        user3 = new User("vcliche","Cliche","Vick","abcd1234", "vcliche@gmail.com" , address, true);

        entityManager.persistAndFlush(user1);
        entityManager.persistAndFlush(user2);
    }

    @Test
    public void itShouldreturnTheUser(){
        entityManager.persistAndFlush(user);
        assertThat(userRepository.findById(user.getUserName()).get()).isEqualTo(user);
    }

    @Test
    public void itShouldSaveUser(){
        User tmp = entityManager.persistAndFlush(user);
        assertThat(userRepository.findById(tmp.getUserName()).get()).isEqualTo(tmp);
    }

    @Test
    public void itShouldUpdateUser(){
        User newUser = user1;
        newUser.setFirstName("Patrick");
        User oldUser = userRepository.findById(user1.getUserName()).get();
        oldUser.setFirstName("Patrick");
        userRepository.save(oldUser);
        assertThat(userRepository.findById(user1.getUserName()).get()).isEqualTo(user1);
    }

    @Test
    public void itShouldDeleteUser(){
        entityManager.remove(user1);
        assertThat(userRepository.findById(user1.getUserName()).isEmpty());
    }

    @Test
    public void itShouldFilterTheUsers(){
        assertThat(userRepository.findByFirstNameLike("%as%").isEmpty());
        entityManager.persistAndFlush(user);
        assertThat(userRepository.findByFirstNameLike("%as%").size()).isEqualTo(1);
        entityManager.persistAndFlush(user);
        assertThat(userRepository.findByFirstNameLike("%as%").size()).isEqualTo(1);
        assertThat(userRepository.findByLastNameLike("%a%").size()).isEqualTo(2);
    }

    @Test
    public void itShouldFilterByAdress(){

    }
}
