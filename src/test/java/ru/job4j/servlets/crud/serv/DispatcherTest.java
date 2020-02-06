package ru.job4j.servlets.crud.serv;

import org.junit.Test;
import ru.job4j.servlets.crud.logic.ValidateService;
import ru.job4j.servlets.crud.model.User;

import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DispatcherTest {

    @Test
    public void whenSendAfterInitThenTrue() {
        Dispatcher dispatcher = Dispatcher.getInstance();
        ValidateService logic = ValidateService.getInstance();
        String add = "add";
        User addUser = new User(null, "PSF", "Python", "psf@gmail.com");
        boolean result = dispatcher.init().send(add, logic, addUser);
        assertThat(result, is(true));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenSendWithoutInitThenException() {
        Dispatcher dispatcher = Dispatcher.getInstance();
        ValidateService logic = ValidateService.getInstance();
        String add = "add";
        User addUser = new User(null, "PSF", "Python", "psf@gmail.com");
        boolean result = dispatcher.send(add, logic, addUser);
    }
}