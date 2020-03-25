package ru.job4j.servlets.crud.controller;

import ru.job4j.servlets.crud.logic.IValidate;
import ru.job4j.servlets.crud.logic.ValidateService;
import ru.job4j.servlets.crud.model.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;

public class UsersControllerServlet extends HttpServlet {

    private final IValidate logic = ValidateService.getInstance();
    private final Dispatcher dispatcher = Dispatcher.getInstance().init();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("users", this.logic.findAll());
        req.getRequestDispatcher("/WEB-INF/views/UsersView.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");
        Integer id = idStringToInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        IRole role = StoreRoleMemory.getInstance().getRole(req.getParameter("role"));
        Integer idCountry = idStringToInt(req.getParameter("idCountry"));
        Integer idCity = idStringToInt(req.getParameter("idCity"));
        User user = new User(id, name, login, email, password, role, new Country(idCountry), new City(idCity));
        this.dispatcher.send(action, this.logic, user);
        changeSessionUser(req, action, id);
        resp.sendRedirect(String.format("%s/", req.getContextPath()));
    }

    private void changeSessionUser(HttpServletRequest req, String action, Integer id) {
        String actionUpdate = "update";
        String actionDelete = "delete";
        if (Set.of(actionUpdate, actionDelete).contains(action)) {
            HttpSession session = req.getSession();
            String nameAttribute = "s_user";
            User sUser = (User) session.getAttribute(nameAttribute);
            if (sUser.getId().equals(id)) {
                if (action.equals(actionUpdate)) {
                    session.setAttribute(nameAttribute, this.logic.findById(new User(id)));
                } else if (action.equals(actionDelete)) {
                    session.invalidate();
                }
            }
        }
    }

    private Integer idStringToInt(String string) {
        return Objects.nonNull(string) ? Integer.parseInt(string) : null;
    }
}
