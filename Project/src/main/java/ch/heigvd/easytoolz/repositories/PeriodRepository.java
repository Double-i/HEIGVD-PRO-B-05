package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.Period;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodRepository extends JpaRepository<Period,Integer> {

}
