package ru.job4j.servlets.crud.controller;

import ru.job4j.servlets.crud.logic.ValidateService;
import ru.job4j.servlets.crud.model.IRole;
import ru.job4j.servlets.crud.model.StoreRoleMemory;
import ru.job4j.servlets.crud.model.User;
import ru.job4j.servlets.crud.persistent.DBStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

public class UsersControllerServlet extends HttpServlet {

    private final ValidateService logic = ValidateService.getInstance();
    private final Dispatcher dispatcher = Dispatcher.getInstance().init();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("users", this.logic.findAll());
        req.getRequestDispatcher("/WEB-INF/views/UsersView.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");
        String strId = req.getParameter("id");
        Integer id = Objects.nonNull(strId) ? Integer.parseInt(strId) : null;
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        IRole role = StoreRoleMemory.getInstance().getRole(req.getParameter("role"));
        User user = new User(id, name, login, email, password, role);
        this.dispatcher.send(action, this.logic, user);
        addChangedUserToSession(req, action, login);
        resp.sendRedirect(String.format("%s/", req.getContextPath()));
    }

    private void addChangedUserToSession(HttpServletRequest req, String action, String login) {
        if (action.equals("update")) {
            HttpSession session = req.getSession();
            User sUser = (User) session.getAttribute("s_user");
            if (sUser.getLogin().equals(login)) {
                session.setAttribute("s_user", DBStore.getInstance().findByLogin(login));
            }
        }
    }
}
