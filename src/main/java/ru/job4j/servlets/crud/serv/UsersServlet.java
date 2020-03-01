package ru.job4j.servlets.crud.serv;

import ru.job4j.servlets.crud.logic.ValidateService;
import ru.job4j.servlets.crud.model.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class UsersServlet extends HttpServlet {

    private final ValidateService logic = ValidateService.getInstance();
    private final Dispatcher dispatcher = Dispatcher.getInstance().init();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");
        String strId = req.getParameter("id");
        Integer id = Objects.nonNull(strId) ? Integer.parseInt(strId) : null;
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        User user = new User(id, name, login, email);
        this.dispatcher.send(action, this.logic, user);
        resp.sendRedirect("index.jsp");
    }
}
