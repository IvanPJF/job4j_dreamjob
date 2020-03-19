package ru.job4j.servlets.crud.logic;

import org.junit.After;
import org.junit.Test;
import ru.job4j.servlets.crud.model.IRole;
import ru.job4j.servlets.crud.model.User;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ValidateServiceTest {

    private static final IRole ADMIN = () -> "admin";
    private final IValidate logic = ValidateService.getInstance();

    @After
    public void clearDB() {
        Collection<User> users = logic.findAll();
        for (User user : users) {
            logic.delete(user);
        }
    }

    @Test
    public void whenActionIsAddThenTrue() {
        User user = new User(null, "Oracle", "Java",
                "o@gmail.com", "java", ADMIN);
        boolean result = logic.add(user);
        assertThat(result, is(true));
        assertThat(logic.findById(user), is(user));
    }

    @Test
    public void whenActionIsUpdateThenTrue() {
        User addUser = new User(null, "PSF", "Python",
                "psf@gmail.com", "python", ADMIN);
        logic.add(addUser);
        User updateUser = new User(addUser.getId(), "Oracle", "Java",
                "o@gmail.com", "java", ADMIN);
        boolean result = logic.update(updateUser);
        assertThat(result, is(true));
        assertThat(logic.findById(updateUser), is(updateUser));
    }

    @Test
    public void whenActionIsUpdateAndOlderUserNotExistThenFalse() {
        User updateUser = new User(Integer.MAX_VALUE, "Oracle", "Java",
                "o@gmail.com", "java", ADMIN);
        boolean result = logic.update(updateUser);
        assertThat(result, is(false));
        assertThat(logic.findById(updateUser), is((User) null));
    }

    @Test
    public void whenActionIsUpdateAndOlderUserExistAndIdUserForUpdateNotMatchThenFalse() {
        User addUser = new User(null, "PSF", "Python",
                "psf@gmail.com", "python", ADMIN);
        User updateUser = new User(Integer.MAX_VALUE, "Oracle", "Java",
                "o@gmail.com", "java", ADMIN);
        logic.add(addUser);
        boolean result = logic.update(updateUser);
        assertThat(result, is(false));
        assertThat(logic.findById(updateUser), is((User) null));
    }

    @Test
    public void whenActionIsDeleteAndUserExistAndIdMatchThenTrue() {
        User user = new User(null, "PSF", "Python",
                "psf@gmail.com", "python", ADMIN);
        logic.add(user);
        boolean result = logic.delete(user);
        assertThat(result, is(true));
    }

    @Test
    public void whenActionIsDeleteAndUserExistAndIdNotMatchThenFalse() {
        User addUser = new User(null, "PSF", "Python",
                "psf@gmail.com", "python", ADMIN);
        logic.add(addUser);
        User deleteUser = new User(Integer.MAX_VALUE);
        boolean result = logic.delete(deleteUser);
        assertThat(result, is(false));
        assertThat(logic.findById(addUser), is(addUser));
    }

    @Test
    public void whenActionIsDeleteAndUserNotExistThenFalse() {
        User deleteUser = new User(Integer.MAX_VALUE);
        boolean result = logic.delete(deleteUser);
        assertThat(result, is(false));
    }
}