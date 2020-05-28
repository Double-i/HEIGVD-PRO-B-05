package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, Integer> {
    /**
     * @param country the name of the country
     * @return sort by the name of the country (must exactly the value of the parameter)
     */
    List<Country> findByCountry(String country);
}
