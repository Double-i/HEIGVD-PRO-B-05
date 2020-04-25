package ch.heigvd.easytoolz.specifications;

import ch.heigvd.easytoolz.models.User_;
import ch.heigvd.easytoolz.models.User;
import ch.heigvd.easytoolz.util.ServiceUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.function.Predicate;

public class UserSpecs extends DefaultSpecs{
    public static Specification<User> getFirstname(String firstname){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get(User_.FIRST_NAME), ServiceUtils.transformLike(firstname));
    }

    public static Specification<User> getLastname(String lastname){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get(User_.LAST_NAME), ServiceUtils.transformLike(lastname));
    }

    public static Specification<User> getUsername(String username){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get(User_.USER_NAME), ServiceUtils.transformLike(username));
    }

    public static Specification<User> getEmail(String email){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get(User_.EMAIL), ServiceUtils.transformLike(email));
    }
}
