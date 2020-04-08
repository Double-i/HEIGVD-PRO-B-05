package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Integer> {
    List<City> findByCityLike(String city);
    List<City> findByCountry_CountryLike(String country);
    List<City> findByCountry_CountryLikeAndCityLike(String country, String city);
}
