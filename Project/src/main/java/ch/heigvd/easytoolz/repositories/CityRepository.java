package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Integer> {
    /**
     * @param city the name of the city
     * @return sort like the cities by the name
     */
    List<City> findByCityLike(String city);

    /**
     * @param city the name of the city
     * @return sort the cities by name (must be exacty like "city")
     */
    List<City> findByCity(String city);

    /**
     * @param country the name of the country
     * @return sort the cities by the name country
     */
    List<City> findByCountry_CountryLike(String country);

    /**
     * @param country the name of the country
     * @param city the name of the city
     * @return sort the cities by the name of the country and the name of the city
     */
    List<City> findByCountry_CountryLikeAndCityLike(String country, String city);
}
