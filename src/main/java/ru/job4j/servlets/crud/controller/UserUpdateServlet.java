package ru.job4j.servlets.crud.controller;

import ru.job4j.servlets.crud.logic.IValidate;
import ru.job4j.servlets.crud.logic.ValidateService;
import ru.job4j.servlets.crud.model.User;
import ru.job4j.servlets.crud.persistent.DBLocation;
import ru.job4j.servlets.crud.persistent.Location;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserUpdateServlet extends HttpServlet {

    private final IValidate logic = ValidateService.getInstance();
    private final Location location = DBLocation.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String strId = req.getParameter("id");
        Integer id = Integer.parseInt(strId);
        User user = this.logic.findById(new User(id));
        req.setAttribute("user", user);
        req.setAttribute("countries", this.location.findAllCountries());
        req.setAttribute("cities", this.location.findAllCitiesByIdCountry(user.getCountry().getId()));
        HttpSession session = req.getSession();
        String nameAttribute = "s_user";
        User sessionUser = (User) session.getAttribute(nameAttribute);
        if (sessionUser.getId().equals(user.getId())) {
            session.setAttribute(nameAttribute, user);
        }
        req.getRequestDispatcher("/WEB-INF/views/UserEdit.jsp").forward(req, resp);
    }
}
