package ru.job4j.servlets.crud.controller;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        if (!request.getRequestURI().contains("/signin")) {
            HttpSession session = request.getSession();
            if (Objects.isNull(session.getAttribute("s_user"))) {
                ((HttpServletResponse) resp).sendRedirect(String.format("%s/signin", request.getContextPath()));
                return;
            }
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
