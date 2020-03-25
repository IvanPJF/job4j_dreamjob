package ru.job4j.servlets.crud.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.servlets.crud.logic.IValidate;
import ru.job4j.servlets.crud.logic.ValidateService;
import ru.job4j.servlets.crud.logic.ValidateStub;
import ru.job4j.servlets.crud.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ValidateService.class)
public class UsersControllerServletTest {

    @Test
    public void whenAddUserThenStoreIt() throws IOException {
        IValidate validate = new ValidateStub();
        PowerMockito.mockStatic(ValidateService.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(ValidateService.getInstance()).thenReturn(validate);
        when(req.getParameter("action")).thenReturn("add");
        new UsersControllerServlet().doPost(req, resp);
        assertThat(validate.findAll().iterator().next(), is(new User(0)));
    }

    @Test
    public void whenUpdateUserThenStoreUpdateIt() throws IOException {
        IValidate validate = new ValidateStub();
        User userAdd = new User(null);
        userAdd.setName("Ivan");
        validate.add(userAdd);
        User userUpdate = new User(userAdd.getId());
        userUpdate.setName("Pavel");
        PowerMockito.mockStatic(ValidateService.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(ValidateService.getInstance()).thenReturn(validate);
        when(req.getParameter("action")).thenReturn("update");
        when(req.getParameter("id")).thenReturn(String.valueOf(userUpdate.getId()));
        when(req.getParameter("name")).thenReturn(userUpdate.getName());
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("s_user")).thenReturn(userUpdate);
        new UsersControllerServlet().doPost(req, resp);
        assertThat(validate.findAll().iterator().next().getName(), is(userUpdate.getName()));
    }

    @Test
    public void whenDeleteUserThenStoreNotIt() throws IOException {
        IValidate validate = new ValidateStub();
        User userAdd = new User(null);
        userAdd.setName("Ivan");
        validate.add(userAdd);
        PowerMockito.mockStatic(ValidateService.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(ValidateService.getInstance()).thenReturn(validate);
        when(req.getParameter("action")).thenReturn("delete");
        when(req.getParameter("id")).thenReturn(String.valueOf(userAdd.getId()));
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("s_user")).thenReturn(userAdd);
        assertThat(validate.findAll().isEmpty(), is(false));
        new UsersControllerServlet().doPost(req, resp);
        assertThat(validate.findAll().isEmpty(), is(true));
    }
}