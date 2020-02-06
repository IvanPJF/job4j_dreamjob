package ru.job4j.servlets.crud.logic;

import org.junit.After;
import org.junit.Test;
import ru.job4j.servlets.crud.model.User;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ValidateServiceTest {

    @After
    public void clearValidateService() {
        ValidateService logic = ValidateService.getInstance();
        Collection<User> users = logic.findAll();
        if (!users.isEmpty()) {
            users.clear();
        }
    }

    @Test
    public void whenActionIsAddThenTrue() {
        ValidateService logic = ValidateService.getInstance();
        User user = new User(0, "Oracle", "Java", "o@gmail.com");
        boolean result = logic.add(user);
        assertThat(result, is(true));
        assertThat(logic.findById(user), is(user));
    }

    @Test
    public void whenActionIsUpdateThenTrue() {
        ValidateService logic = ValidateService.getInstance();
        User addUser = new User(0, "PSF", "Python", "psf@gmail.com");
        logic.add(addUser);
        Integer id = logic.findAll().iterator().next().getId();
        User updateUser = new User(id, "Oracle", "Java", "o@gmail.com");
        boolean result = logic.update(updateUser);
        assertThat(result, is(true));
        assertThat(logic.findAll().size(), is(1));
        assertThat(logic.findById(updateUser), is(updateUser));
    }

    @Test
    public void whenActionIsUpdateAndOlderUserNotExistThenFalse() {
        ValidateService logic = ValidateService.getInstance();
        User updateUser = new User(0, "Oracle", "Java", "o@gmail.com");
        boolean result = logic.update(updateUser);
        assertThat(result, is(false));
        assertThat(logic.findAll().isEmpty(), is(true));
    }

    @Test
    public void whenActionIsUpdateAndOlderUserExistAndIdUserForUpdateNotMatchThenFalse() {
        ValidateService logic = ValidateService.getInstance();
        User addUser = new User(null, "PSF", "Python", "psf@gmail.com");
        User updateUser = new User(1000, "Oracle", "Java", "o@gmail.com");
        logic.add(addUser);
        boolean result = logic.update(updateUser);
        assertThat(result, is(false));
        assertThat(logic.findAll().size(), is(1));
    }

    @Test
    public void whenActionIsDeleteAndUserExistAndIdMatchThenTrue() {
        ValidateService logic = ValidateService.getInstance();
        User addUser = new User(null, "PSF", "Python", "psf@gmail.com");
        logic.add(addUser);
        Integer id = logic.findAll().iterator().next().getId();
        User deleteUser = new User(id, null, null, null);
        boolean result = logic.delete(deleteUser);
        assertThat(result, is(true));
    }

    @Test
    public void whenActionIsDeleteAndUserExistAndIdNotMatchThenFalse() {
        ValidateService logic = ValidateService.getInstance();
        User addUser = new User(null, "PSF", "Python", "psf@gmail.com");
        User deleteUser = new User(1000, null, null, null);
        logic.add(addUser);
        boolean result = logic.delete(deleteUser);
        assertThat(result, is(false));
    }

    @Test
    public void whenActionIsDeleteAndUserNotExistThenFalse() {
        ValidateService logic = ValidateService.getInstance();
        User deleteUser = new User(1000, null, null, null);
        boolean result = logic.delete(deleteUser);
        assertThat(result, is(false));
    }
}