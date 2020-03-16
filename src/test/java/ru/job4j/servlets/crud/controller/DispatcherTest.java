package ru.job4j.servlets.crud.controller;

import org.junit.After;
import org.junit.Test;
import ru.job4j.servlets.crud.logic.ValidateService;
import ru.job4j.servlets.crud.model.User;

import java.util.Collection;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DispatcherTest {

    private final ValidateService logic = ValidateService.getInstance();

    @After
    public void clearDB() {
        Collection<User> users = logic.findAll();
        for (User user : users) {
            logic.delete(user);
        }
    }

    @Test
    public void whenSendAfterInitThenTrue() {
        Dispatcher dispatcher = Dispatcher.getInstance();
        String add = "add";
        User addUser = new User(null, "PSF", "Python",
                "psf@gmail.com", "python", () -> "admin");
        boolean result = dispatcher.init().send(add, logic, addUser);
        assertThat(result, is(true));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenSendWithoutInitThenException() {
        Dispatcher dispatcher = Dispatcher.getInstance();
        String add = "add";
        User addUser = new User(null, "PSF", "Python",
                "psf@gmail.com", "python", () -> "admin");
        dispatcher.send(add, logic, addUser);
    }
}