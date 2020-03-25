package ru.job4j.servlets.crud.persistent;

import ru.job4j.servlets.crud.model.City;
import ru.job4j.servlets.crud.model.Country;

import java.util.Collection;

public interface Location {

    Collection<Country> findAllCountries();

    Collection<City> findAllCitiesByIdCountry(int idCountry);
}
