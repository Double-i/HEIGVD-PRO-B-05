package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    User userNameIs(String userName);
    List<User> findByFirstNameLike(String firstName);
    List<User> findByLastNameLike(String lastName);
    List<User> findByFirstNameLikeAndLastNameLike(String firstName, String lastName);
    List<User> findByUserNameLike(String userName);
    List<User> findByEmailLike(String email);
}
