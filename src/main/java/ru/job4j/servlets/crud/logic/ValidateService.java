package ru.job4j.servlets.crud.logic;

import ru.job4j.servlets.crud.model.User;
import ru.job4j.servlets.crud.persistent.DBStore;
import ru.job4j.servlets.crud.persistent.IStore;

import java.util.Collection;
import java.util.Objects;

/**
 * The class of the main logic of the program.
 *
 * @author IvanPJF (teaching-light@yandex.ru)
 * @version 0.1
 * @since 06.02.2020
 */
public class ValidateService implements IValidate {

    private final IStore persistence = DBStore.getInstance();
    private static final ValidateService INSTANCE = new ValidateService();

    private ValidateService() {
    }

    public static IValidate getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean add(User user) {
        boolean result = false;
        if (Objects.isNull(findById(user))) {
            this.persistence.add(user);
            result = true;
        }
        return result;
    }

    @Override
    public boolean update(User user) {
        boolean result = false;
        if (Objects.nonNull(findById(user))) {
            this.persistence.update(user);
            result = true;
        }
        return result;
    }

    @Override
    public boolean delete(User user) {
        boolean result = false;
        if (Objects.nonNull(findById(user))) {
            this.persistence.delete(user);
            result = true;
        }
        return result;
    }

    @Override
    public Collection<User> findAll() {
        return this.persistence.findAll();
    }

    @Override
    public User findById(User user) {
        return Objects.nonNull(user.getId()) ? this.persistence.findById(user) : null;
    }

    @Override
    public User findByLogin(User user) {
        return Objects.nonNull(user) ? this.persistence.findByLogin(user.getLogin()) : null;
    }

    @Override
    public boolean isCredential(String login, String password) {
        return this.persistence.isCredential(login, password);
    }
}
