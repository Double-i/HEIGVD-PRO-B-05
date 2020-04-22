package ch.heigvd.easytoolz.services.interfaces;

import ch.heigvd.easytoolz.models.Country;

public interface CountryService {
    /**
     * Stores the new country
     * @param country The new country
     * @return the object Country else if the country exists already
     */
    Country storeCountry(Country country);

    /**
     * Finds the country by the name
     * @param name the name of the country
     * @return the object Country else null if the name isn't found
     */
    Country loadByName(String name);
}
