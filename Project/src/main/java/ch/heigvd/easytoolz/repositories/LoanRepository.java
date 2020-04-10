package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan,String> {
    //Loan findByID(int id);
}
