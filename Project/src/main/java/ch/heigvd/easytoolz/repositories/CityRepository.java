package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Integer> {
    List<City> findByCity(String city);
}
