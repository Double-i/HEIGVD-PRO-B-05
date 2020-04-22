
package ch.heigvd.easytoolz.services.implementation;

import ch.heigvd.easytoolz.models.City;
import ch.heigvd.easytoolz.models.Country;
import ch.heigvd.easytoolz.repositories.CityRepository;
import ch.heigvd.easytoolz.services.interfaces.CityService;
import ch.heigvd.easytoolz.services.interfaces.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CountryService countryService;

    @Override
    public City storeCity(City city) {
        Country currentCountry = countryService.storeCountry(city.getCountry());
        city.setCountry(currentCountry);
        City currentCity = loadByName(city.getCity());
        if(currentCity != null)
            return currentCity;
        return cityRepository.save(city);
    }

    @Override
    public City loadByName(String name) {
        List<City> cityOptional = cityRepository.findByCity(name);
        if(cityOptional.size() == 1)
            return cityOptional.get(0);
        else
            return null;
    }
}
