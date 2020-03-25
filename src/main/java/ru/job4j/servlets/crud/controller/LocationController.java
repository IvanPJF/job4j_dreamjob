package ru.job4j.servlets.crud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.servlets.crud.model.City;
import ru.job4j.servlets.crud.persistent.DBLocation;
import ru.job4j.servlets.crud.persistent.Location;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

public class LocationController extends HttpServlet {

    private final Location location = DBLocation.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int idCountries = Integer.parseInt(req.getParameter("idCountry"));
        Collection<City> cities = this.location.findAllCitiesByIdCountry(idCountries);
        ObjectMapper mapperJSON = new ObjectMapper();
        try (PrintWriter writer = resp.getWriter()) {
            mapperJSON.writeValue(writer, cities);
        }
    }
}
