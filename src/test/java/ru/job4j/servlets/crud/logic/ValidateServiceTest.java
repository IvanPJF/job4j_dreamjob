package ru.job4j.servlets.crud.logic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.servlets.crud.model.City;
import ru.job4j.servlets.crud.model.Country;
import ru.job4j.servlets.crud.model.IRole;
import ru.job4j.servlets.crud.model.User;
import ru.job4j.servlets.crud.persistent.DBStore;
import ru.job4j.servlets.crud.persistent.IStore;
import ru.job4j.servlets.crud.persistent.MemoryStore;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DBStore.class})
public class ValidateServiceTest {

    private static final IRole ADMIN = () -> "admin";
    private final IStore store = MemoryStore.getInstance();

    @Before
    public void prepareMockStore() {
        PowerMockito.mockStatic(DBStore.class);
        when(DBStore.getInstance()).thenReturn(store);
    }

    @Test
    public void whenActionIsAddThenTrue() {
        User user = new User(null, "Oracle", "Java",
                "o@gmail.com", "java", ADMIN,
                new Country(1, "Russia"), new City(1, "Moscow"));
        IValidate logic = ValidateService.getInstance();
        boolean result = logic.add(user);
        assertThat(result, is(true));
        assertThat(store.findById(user), is(user));
    }

    @Test
    public void whenActionIsUpdateThenTrue() {
        User addUser = new User(null, "PSF", "Python",
                "psf@gmail.com", "python", ADMIN,
                new Country(1, "Russia"), new City(1, "Moscow"));
        IValidate logic = ValidateService.getInstance();
        logic.add(addUser);
        User updateUser = new User(addUser.getId(), "Oracle", "Java",
                "o@gmail.com", "java", ADMIN,
                new Country(1, "Russia"), new City(1, "Moscow"));
        boolean result = logic.update(updateUser);
        assertThat(result, is(true));
        assertThat(store.findById(updateUser), is(updateUser));
    }

    @Test
    public void whenActionIsUpdateAndOlderUserNotExistThenFalse() {
        User updateUser = new User(Integer.MAX_VALUE, "Oracle", "Java",
                "o@gmail.com", "java", ADMIN,
                new Country(1, "Russia"), new City(1, "Moscow"));
        IValidate logic = ValidateService.getInstance();
        boolean result = logic.update(updateUser);
        assertThat(result, is(false));
        assertThat(logic.findById(updateUser), is((User) null));
    }

    @Test
    public void whenActionIsUpdateAndOlderUserExistAndIdUserForUpdateNotMatchThenFalse() {
        User addUser = new User(null, "PSF", "Python",
                "psf@gmail.com", "python", ADMIN,
                new Country(1, "Russia"), new City(1, "Moscow"));
        User updateUser = new User(Integer.MAX_VALUE, "Oracle", "Java",
                "o@gmail.com", "java", ADMIN,
                new Country(1, "Russia"), new City(1, "Moscow"));
        IValidate logic = ValidateService.getInstance();
        logic.add(addUser);
        boolean result = logic.update(updateUser);
        assertThat(result, is(false));
        assertThat(store.findById(updateUser), is((User) null));
    }

    @Test
    public void whenActionIsDeleteAndUserExistAndIdMatchThenTrue() {
        User user = new User(null, "PSF", "Python",
                "psf@gmail.com", "python", ADMIN,
                new Country(1, "Russia"), new City(1, "Moscow"));
        IValidate logic = ValidateService.getInstance();
        logic.add(user);
        boolean result = logic.delete(user);
        assertThat(result, is(true));
        assertThat(store.findById(user), is((User) null));
    }

    @Test
    public void whenActionIsDeleteAndUserExistAndIdNotMatchThenFalse() {
        User addUser = new User(null, "PSF", "Python",
                "psf@gmail.com", "python", ADMIN,
                new Country(1, "Russia"), new City(1, "Moscow"));
        IValidate logic = ValidateService.getInstance();
        logic.add(addUser);
        User deleteUser = new User(Integer.MAX_VALUE);
        boolean result = logic.delete(deleteUser);
        assertThat(result, is(false));
        assertThat(store.findById(addUser), is(addUser));
    }

    @Test
    public void whenActionIsDeleteAndUserNotExistThenFalse() {
        User deleteUser = new User(Integer.MAX_VALUE);
        IValidate logic = ValidateService.getInstance();
        boolean result = logic.delete(deleteUser);
        assertThat(result, is(false));
    }
}