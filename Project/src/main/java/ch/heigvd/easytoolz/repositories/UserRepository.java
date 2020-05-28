package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    /**
     * @param userName username of the user
     * @return sort by the username
     */
    User userNameIs(String userName);
}
