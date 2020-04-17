package ch.heigvd.easytoolz.specifications;

import ch.heigvd.easytoolz.models.*;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.*;
import java.util.Date;

public class LoanSpecs {

    public static Specification<Loan> getState(String state) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Loan_.state), State.valueOf(state));
    }

    public static Specification<Loan> getDateStart(Date dateStart) {
        return (root, query, criteriaBuilder) ->
        {
            Join<Loan, Period> periodJoin = root.join(Loan_.periods);
            return criteriaBuilder.greaterThanOrEqualTo(periodJoin.get(Period_.DATE_START), dateStart);
        };
    }

    public static Specification<Loan> getDateEnd(Date dateEnd) {
        return (root, query, criteriaBuilder) ->
        {
            Join<Loan, Period> periodJoin = root.join(Loan_.periods);
            return criteriaBuilder.greaterThanOrEqualTo(periodJoin.get(Period_.DATE_END), dateEnd);
        };
    }

    public static Specification<Loan> getCity(String city) {
        return (root, query, criteriaBuilder) -> {
            Join<Loan, EZObject> EZObjectJoin = root.join(Loan_.EZObject);
            Join<EZObject, User> userJoin = EZObjectJoin.join(EZObject_.owner);
            Join<User, Address> addressJoin = userJoin.join(User_.address);
            Join<Address, City> cityJoin = addressJoin.join(Address_.city);
            return criteriaBuilder.equal(cityJoin.get(City_.city),city);
        };
    }

    public static Specification<Loan> getLoanByBorrower(String name) {
        return (root, query, criteriaBuilder) -> {
            Join<Loan, User> UserJoin = root.join(Loan_.borrower);
            return criteriaBuilder.equal(UserJoin.get(User_.userName), name);
        };
    }

    public static Specification<Loan> getLoanByOwner(String name) {
        return (root, query, criteriaBuilder) -> {
            Join<Loan, EZObject> EZObjectJoin = root.join(Loan_.EZObject);
            Join<EZObject, User> UserJoin = EZObjectJoin.join(EZObject_.owner);
            return criteriaBuilder.equal(UserJoin.get(User_.userName), name);
        };
    }

}
