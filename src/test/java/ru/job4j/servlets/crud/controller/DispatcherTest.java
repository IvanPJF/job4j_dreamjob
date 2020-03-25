package ru.job4j.servlets.crud.controller;

import org.junit.Test;
import ru.job4j.servlets.crud.logic.IValidate;
import ru.job4j.servlets.crud.logic.ValidateStub;
import ru.job4j.servlets.crud.model.User;

import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DispatcherTest {

    private final IValidate logic = new ValidateStub();

    @Test
    public void whenSendAfterInitThenTrue() {
        Dispatcher dispatcher = Dispatcher.getInstance();
        String add = "add";
        User addUser = new User(null, "PSF", "Python",
                "psf@gmail.com", "python", () -> "admin", null, null);
        boolean result = dispatcher.init().send(add, logic, addUser);
        assertThat(result, is(true));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenSendWithoutInitThenException() {
        Dispatcher dispatcher = Dispatcher.getInstance();
        String add = "add";
        User addUser = new User(null, "PSF", "Python",
                "psf@gmail.com", "python", () -> "admin", null, null);
        dispatcher.send(add, logic, addUser);
    }
}