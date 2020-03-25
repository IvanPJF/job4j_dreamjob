package ru.job4j.servlets.crud.persistent;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.servlets.crud.model.City;
import ru.job4j.servlets.crud.model.Country;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class DBLocation implements Location {

    private static final DBLocation LOCATION = new DBLocation();
    private static final BasicDataSource SOURCE = ConnectionPool.getInstance().pool();

    private DBLocation() {
    }

    public static Location getInstance() {
        return LOCATION;
    }

    @Override
    public Collection<Country> findAllCountries() {
        Collection<Country> countries = new ArrayList<>();
        try (Connection con = SOURCE.getConnection();
             Statement st = con.createStatement()) {
            try (ResultSet rset = st.executeQuery("SELECT id, name FROM countries")) {
                while (rset.next()) {
                    countries.add(new Country(
                            rset.getInt("id"),
                            rset.getString("name")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countries;
    }

    @Override
    public Collection<City> findAllCitiesByIdCountry(int idCountry) {
        Collection<City> cities = new ArrayList<>();
        try (Connection con = SOURCE.getConnection();
             PreparedStatement prst = con.prepareStatement(
                     "SELECT id, name FROM cities WHERE countries_id = ? ORDER BY id"
             )) {
            prst.setInt(1, idCountry);
            try (ResultSet rset = prst.executeQuery()) {
                while (rset.next()) {
                    cities.add(new City(
                            rset.getInt("id"),
                            rset.getString("name")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }
}
