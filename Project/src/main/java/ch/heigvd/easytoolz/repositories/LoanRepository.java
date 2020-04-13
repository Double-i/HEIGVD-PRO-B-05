package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.EZObject;
import ch.heigvd.easytoolz.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan,String> {

    List<Loan> findByBorrower_UserName(String username);

    List<Loan> findByObject_Owner_UserName(String username);

}
