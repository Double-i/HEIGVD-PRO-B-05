package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, Integer> {
    List<Country> findByCountryLike(String country);
}
