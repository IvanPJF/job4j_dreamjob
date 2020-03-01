package ru.job4j.servlets.crud.serv;

import ru.job4j.servlets.crud.logic.ValidateService;
import ru.job4j.servlets.crud.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserUpdateServlet extends HttpServlet {

    private final ValidateService logic = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String strId = req.getParameter("id");
        Integer id = Integer.parseInt(strId);
        User user = this.logic.findById(new User(id, null, null, null));
        req.setAttribute("id", user);
        getServletContext().getRequestDispatcher("/edit.jsp").forward(req, resp);
    }
}
