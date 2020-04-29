package ch.heigvd.easytoolz.specifications;

import ch.heigvd.easytoolz.models.User;
import org.springframework.data.jpa.domain.Specification;

public abstract class DefaultSpecs {
    public static Specification<User> getAll(){
        return (root, criteriaQuery, criteriaBuilder) -> null;
    }
}
