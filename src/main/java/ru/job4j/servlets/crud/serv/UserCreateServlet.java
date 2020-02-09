package ru.job4j.servlets.crud.serv;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UserCreateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        String html = "<html>"
                + "<head><meta charset='utf-8'></head>"
                + "<body>"
                + "<form action='list' method='post'>"
                + "Name:  <input type='text' name='name'/><br>"
                + "Login: <input type='text' name='login'/><br>"
                + "Email: <input type='text' name='email'/><br>"
                + "<button name='action' value='add' type='submit'>save</button>"
                + "</form>"
                + "</body>"
                + "</html>";
        try (PrintWriter writer = resp.getWriter()) {
            writer.println(html);
        }
    }
}
