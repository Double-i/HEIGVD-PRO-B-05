package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan,Integer> , JpaSpecificationExecutor<Loan> {
}
