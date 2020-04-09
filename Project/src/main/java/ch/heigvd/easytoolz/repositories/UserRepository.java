package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    User findByUserName(String userName);
    List<User> findByFirstName(String firstName);
    List<User> findByLastName(String lastName);
    List<User> findByFirstNameAndLastName(String firstName, String lastName);
}
