package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan,String> {
    //Loan findByID(int id);

    List<Loan> findByBorrower(String borrower);


}
