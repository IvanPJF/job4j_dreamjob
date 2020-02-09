package ru.job4j.servlets.crud.serv;

import ru.job4j.servlets.crud.logic.ValidateService;
import ru.job4j.servlets.crud.model.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UserUpdateServlet extends HttpServlet {

    private final ValidateService logic = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        String strId = req.getParameter("id");
        Integer id = Integer.parseInt(strId);
        User user = this.logic.findById(new User(id, null, null, null));
        String html = "<html>"
                + "<head><meta charset='utf-8'></head>"
                + "<body>"
                + "<form action='list' method='post'>"
                + "<input type='hidden' name='id' value='" + user.getId() + "'/>"
                + "Name:  <input type='text' name='name' value='" + user.getName() + "'/><br>"
                + "Login: <input type='text' name='login' value='" + user.getLogin() + "'/><br>"
                + "Email: <input type='text' name='email' value='" + user.getEmail() + "'/><br>"
                + "<button name='action' value='update' type='submit'>save</button>"
                + "</form>"
                + "</body>"
                + "</html>";
        try (PrintWriter writer = resp.getWriter()) {
            writer.println(html);
        }
    }
}
