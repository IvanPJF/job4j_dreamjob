package ru.job4j.servlets.crud.serv;

import ru.job4j.servlets.crud.logic.ValidateService;
import ru.job4j.servlets.crud.model.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.StringJoiner;

public class UsersServlet extends HttpServlet {

    private final ValidateService logic = ValidateService.getInstance();
    private final Dispatcher dispatcher = Dispatcher.getInstance().init();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        StringJoiner jTable = new StringJoiner("", "<table>", "</table>");
        jTable.add("<tr>")
                .add("<th>Name</th>")
                .add("<th>Login</th>")
                .add("<th>Email</th>")
                .add("<th>Date create</th>")
                .add("</tr>");
        for (User user : this.logic.findAll()) {
            jTable.add("<tr>")
                    .add(String.format("<td>%s</td>", user.getName()))
                    .add(String.format("<td>%s</td>", user.getLogin()))
                    .add(String.format("<td>%s</td>", user.getEmail()))
                    .add(String.format("<td>%s</td>", user.getCreateDate()))
                    .add("<td><form>")
                    .add(String.format("<input type='hidden' name='id' value='%s'/>", user.getId()))
                    .add("<button formaction='edit?id={id}' name='action' value='update' type='submit'>edit</button>")
                    .add("<button formaction='list' formmethod='post' name='action' value='delete' type='submit'>delete</button>")
                    .add("</form></td>")
                    .add("</tr>");
        }
        jTable.add("<tr><td align='left'><form action='create'><button type='submit'>add</button></form></td></tr>");
        try (PrintWriter writer = resp.getWriter()) {
            String html = "<html><head><meta charset='utf-8'></head>"
                    + "<body>" + jTable + "</body>"
                    + "</html>";
            writer.println(html);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        String action = req.getParameter("action");
        String strId = req.getParameter("id");
        Integer id = Objects.nonNull(strId) ? Integer.parseInt(strId) : null;
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        User user = new User(id, name, login, email);
        this.dispatcher.send(action, this.logic, user);
        doGet(req, resp);
    }
}
