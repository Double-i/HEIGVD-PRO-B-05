package ch.heigvd.easytoolz.JPATEST;

import ch.heigvd.easytoolz.models.*;
import ch.heigvd.easytoolz.repositories.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;


/**
 * Used for testing EZObject repositories queries
 */
@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.datasource.url=jdbc:mysql://localhost:3306/easytoolz?serverTimezone=UTC",
        "spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver",
        "spring.datasource.username=root",
        "spring.datasource.password=password",
        "file.upload-dir=./images"
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EZToolzTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    protected EZObjectRepository ezObjectRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected AddressRepository addressRepository;

    @Autowired
    protected CityRepository cityRepository;

    @Autowired
    protected CountryRepository countryRepository;



    @BeforeAll
    void Inituser()
    {
        ArrayList<Country> countries = new ArrayList<>()
        {
            {
                add(new Country("Suisse"));
                add(new Country("Belgique"));
                add(new Country("Allemagne"));
                add(new Country("France"));
                add(new Country("Roumanie"));

            }
        };
        for(Country c :countries)
        {
            countryRepository.save(c);
        }
        Assert.isTrue(countryRepository.findAll().size() == countries.size(), "");

        ArrayList<City> cities = new ArrayList<>()
        {
            {
                add(new City("Lausanne",countries.get(0)));
                add(new City("Bruxelle",countries.get(1)));
                add(new City("Yverdon-les-bains",countries.get(1)));
                add(new City("Berlin",countries.get(2)));
                add(new City("Paris",countries.get(3)));
                add(new City("Budapest",countries.get(4)));

            }
        };
        for(City c : cities)
        {
            cityRepository.save(c);
        }
        Assert.isTrue(cityRepository.findAll().size() == cities.size(), "");

        ArrayList<Address> addresses = new ArrayList<>();
        for(int i  = 0; i < 5; i++)
        {
            addresses.add(new Address("street"+i,"district"+i,"code"+i,new BigDecimal(new Random().nextDouble()), new BigDecimal(new Random().nextDouble()),cities.get(new Random().nextInt(5))));
        }
        for(Address a : addresses)
        {
            addressRepository.save(a);
        }
        Assert.isTrue(addressRepository.findAll().size() == addresses.size(), "");

        ArrayList<User> users = new ArrayList<>();
        for(int i  = 0; i < 5; i++)
        {
            users.add(new User("user"+i,"first"+i,"last"+i,"password"+i,"mail"+i+"@mail.com", addresses.get(i),false));
        }
        for(User u : users)
        {
            userRepository.save(u);
        }
        Assert.isTrue(userRepository.findAll().size() == users.size(), "");

    }
    @Test
    public void injectedComponentsAreNotNull()
    {
        Assert.notNull(dataSource,"");
        Assert.notNull(jdbcTemplate,"");
        Assert.notNull(entityManager,"");
        Assert.notNull(ezObjectRepository,"");
    }



}
