package ch.heigvd.easytoolz.services.implementation;

import ch.heigvd.easytoolz.models.Country;
import ch.heigvd.easytoolz.repositories.CountryRepository;
import ch.heigvd.easytoolz.services.interfaces.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {
    @Autowired
    private CountryRepository countryRepository;

    @Override
    public Country storeCountry(Country country) {
        Country currentCountry = loadByName(country.getCountry());
        if(currentCountry != null)
            return currentCountry;
        return countryRepository.save(country);
    }

    @Override
    public Country loadByName(String name) {
        List<Country> countries = countryRepository.findByCountry(name);
        if(countries.size() == 1)
            return countries.get(0);
        else
            return null;
    }
}
