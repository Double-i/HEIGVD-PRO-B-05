package ch.heigvd.easytoolz.services.interfaces;

import ch.heigvd.easytoolz.models.City;

public interface CityService {
    /**
     * Stores a new city
     * @param city The new city
     * @return the City stored else the city already stored
     */
    City storeCity(City city);

    /**
     * Finds the city by name
     * @param name the name of the city
     * @return the object City else null if the name isn't found
     */
    City loadByName(String name);
}
