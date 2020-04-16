package ch.heigvd.easytoolz.specifications;

import ch.heigvd.easytoolz.models.*;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.*;
public class LoanSpecs {


    public static Specification<Loan> getPendingLoan() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Loan_.state), State.pending);
    }

    public static Specification<Loan> getRefusedLoan() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Loan_.state), State.refused);
    }

    public static Specification<Loan> getAcceptedLoan() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Loan_.state), State.accepted);
    }

    public static Specification<Loan> getCancelLoan() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Loan_.state), State.cancel);
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

    // Exemple de jointure
    public static Specification<Loan> getPendingLoanByBorrower(String name) {
        return Specification.where(getLoanByBorrower(name))
                .and(getPendingLoan());
    }

}
