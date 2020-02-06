package ru.job4j.servlets.crud.serv;

import ru.job4j.servlets.crud.logic.ValidateService;
import ru.job4j.servlets.crud.model.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * User servlet class.
 *
 * @author IvanPJF (teaching-light@yandex.ru)
 * @version 0.1
 * @since 06.02.2020
 */
public class UserServlet extends HttpServlet {

    private final ValidateService logic = ValidateService.getInstance();
    private final Dispatcher dispatcher = Dispatcher.getInstance().init();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (PrintWriter writer = resp.getWriter()) {
            for (User user : this.logic.findAll()) {
                writer.println(user.toString());
            }
            writer.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");
        String strId = req.getParameter("id");
        Integer id = Objects.nonNull(strId) ? Integer.parseInt(strId) : null;
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        User user = new User(id, name, login, email);
        boolean result = this.dispatcher.send(action, this.logic, user);
        try (PrintWriter writer = resp.getWriter()) {
            writer.println(String.format("Action[%s] is %b", action, result));
            writer.flush();
        }
    }
}
