package ch.heigvd.easytoolz.specifications;

import ch.heigvd.easytoolz.models.*;
import ch.heigvd.easytoolz.util.ServiceUtils;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.*;
import java.util.Date;

public class LoanSpecs {

    public static Specification<Loan> getState(String state) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Loan_.STATE), State.valueOf(state));
    }

    public static Specification<Loan> getDateStartLess(Date dateStart) {
        return (root, query, criteriaBuilder) ->
        {

            Join<Loan, Period> periodJoin = root.join(Loan_.periods);

            Predicate date =  criteriaBuilder.lessThanOrEqualTo(periodJoin.get(Period_.dateStart), dateStart);
            Predicate accepted =  criteriaBuilder.equal(periodJoin.get(Period_.STATE), State.accepted);

            return criteriaBuilder.and(date,accepted);
        };

    }

    public static Specification<Loan> getObject(int objectId) {
        return (root, query, criteriaBuilder) -> {
            Join<Loan, EZObject> EZObjectJoin = root.join(Loan_.EZObject);
            return criteriaBuilder.equal(EZObjectJoin.get(EZObject_.I_D),objectId);
        };
    }

    public static Specification<Loan> getDateEndLess(Date dateEnd) {
        return (root, query, criteriaBuilder) ->
        {
            Join<Loan, Period> periodJoin = root.join(Loan_.periods);

            Predicate date =  criteriaBuilder.lessThanOrEqualTo(periodJoin.get(Period_.dateEnd), dateEnd);
            Predicate accepted =  criteriaBuilder.equal(periodJoin.get(Period_.STATE), State.accepted);

            return criteriaBuilder.and(date,accepted);
        };
    }

    public static Specification<Loan> getDateStartGreater(Date dateStart) {
        return (root, query, criteriaBuilder) ->
        {
            Join<Loan, Period> periodJoin = root.join(Loan_.periods);

            Predicate date =  criteriaBuilder.greaterThanOrEqualTo(periodJoin.get(Period_.dateStart), dateStart);
            Predicate accepted =  criteriaBuilder.equal(periodJoin.get(Period_.STATE), State.accepted);

            return criteriaBuilder.and(date,accepted);
        };
    }

    public static Specification<Loan> getDateEndGreater(Date dateEnd) {
        return (root, query, criteriaBuilder) ->
        {
            Join<Loan, Period> periodJoin = root.join(Loan_.periods);

            Predicate date =  criteriaBuilder.greaterThanOrEqualTo(periodJoin.get(Period_.dateEnd), dateEnd);
            Predicate accepted =  criteriaBuilder.equal(periodJoin.get(Period_.STATE), State.accepted);

            return criteriaBuilder.and(date,accepted);
        };
    }

    public static Specification<Loan> getCity(String city) {
        return (root, query, criteriaBuilder) -> {
            Join<Loan, EZObject> EZObjectJoin = root.join(Loan_.EZObject);
            Join<EZObject, User> userJoin = EZObjectJoin.join(EZObject_.owner);
            Join<User, Address> addressJoin = userJoin.join(User_.address);
            Join<Address, City> cityJoin = addressJoin.join(Address_.city);
            return criteriaBuilder.like(cityJoin.get(City_.city), ServiceUtils.transformLike(city));
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
