package ch.heigvd.easytoolz.controllers;

import ch.heigvd.easytoolz.models.City;
import ch.heigvd.easytoolz.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static ch.heigvd.easytoolz.util.ServiceUtils.transformLike;

@RestController
@RequestMapping("/cities")
public class CityController {
    @Autowired
    private CityRepository cityRepository;

    /**
     * @param name name of the city
     * @param country name of the country
     * @return the list of the city filtered by the parameter
     */
    @GetMapping
    public List<City> index(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "country", required = false) String country
    ) {
        name = transformLike(name);
        country = transformLike(country);

        if (name != null) {
            if (country != null) {
                return cityRepository.findByCountry_CountryLikeAndCityLike(country, name);
            } else {
                return cityRepository.findByCityLike(name);
            }
        } else {
            if (country != null) {
                return cityRepository.findByCountry_CountryLike(country);
            } else {
                return cityRepository.findAll();
            }
        }
    }
}
