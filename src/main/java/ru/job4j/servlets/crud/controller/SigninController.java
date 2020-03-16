package ru.job4j.servlets.crud.controller;

import ru.job4j.servlets.crud.model.StoreRoleMemory;
import ru.job4j.servlets.crud.persistent.DBStore;
import ru.job4j.servlets.crud.persistent.IStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

public class SigninController extends HttpServlet {

    private final IStore store = DBStore.getInstance();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/SigninView.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (!store.isCredential(login, password)) {
            req.setAttribute("error", "Credential invalid");
            doGet(req, resp);
            return;
        }
        HttpSession session = req.getSession();
        session.setAttribute("s_user", this.store.findByLogin(login));
        session.setAttribute("s_roles", StoreRoleMemory.getInstance().getAllRoles());
        resp.sendRedirect(String.format("%s/", req.getContextPath()));
    }
}
