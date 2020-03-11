package ru.job4j.servlets.crud.logic;

import org.junit.AfterClass;
import org.junit.Test;
import ru.job4j.servlets.crud.model.User;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ValidateServiceTest {

    @AfterClass
    public static void clearValidateService() {
        ValidateService logic = ValidateService.getInstance();
        Collection<User> users = logic.findAll();
        for (User user : users) {
            logic.delete(user);
        }
    }

    @Test
    public void whenActionIsAddThenTrue() {
        ValidateService logic = ValidateService.getInstance();
        User user = new User("Oracle", "Java", "o@gmail.com");
        boolean result = logic.add(user);
        assertThat(result, is(true));
        assertThat(logic.findById(user), is(user));
    }

    @Test
    public void whenActionIsUpdateThenTrue() {
        ValidateService logic = ValidateService.getInstance();
        User addUser = new User("PSF", "Python", "psf@gmail.com");
        logic.add(addUser);
        User updateUser = new User(addUser.getId(), "Oracle", "Java", "o@gmail.com");
        boolean result = logic.update(updateUser);
        assertThat(result, is(true));
        assertThat(logic.findById(updateUser), is(updateUser));
    }

    @Test
    public void whenActionIsUpdateAndOlderUserNotExistThenFalse() {
        ValidateService logic = ValidateService.getInstance();
        User updateUser = new User(Integer.MAX_VALUE, "Oracle", "Java", "o@gmail.com");
        boolean result = logic.update(updateUser);
        assertThat(result, is(false));
        assertThat(logic.findById(updateUser), is((User) null));
    }

    @Test
    public void whenActionIsUpdateAndOlderUserExistAndIdUserForUpdateNotMatchThenFalse() {
        ValidateService logic = ValidateService.getInstance();
        User addUser = new User("PSF", "Python", "psf@gmail.com");
        User updateUser = new User(Integer.MAX_VALUE, "Oracle", "Java", "o@gmail.com");
        logic.add(addUser);
        boolean result = logic.update(updateUser);
        assertThat(result, is(false));
        assertThat(logic.findById(updateUser), is((User) null));
    }

    @Test
    public void whenActionIsDeleteAndUserExistAndIdMatchThenTrue() {
        ValidateService logic = ValidateService.getInstance();
        User user = new User("PSF", "Python", "psf@gmail.com");
        logic.add(user);
        boolean result = logic.delete(user);
        assertThat(result, is(true));
    }

    @Test
    public void whenActionIsDeleteAndUserExistAndIdNotMatchThenFalse() {
        ValidateService logic = ValidateService.getInstance();
        User addUser = new User("PSF", "Python", "psf@gmail.com");
        logic.add(addUser);
        User deleteUser = new User(addUser.getId() + 1);
        boolean result = logic.delete(deleteUser);
        assertThat(result, is(false));
        assertThat(logic.findById(addUser), is(addUser));
    }

    @Test
    public void whenActionIsDeleteAndUserNotExistThenFalse() {
        ValidateService logic = ValidateService.getInstance();
        User deleteUser = new User(Integer.MAX_VALUE);
        boolean result = logic.delete(deleteUser);
        assertThat(result, is(false));
    }
}