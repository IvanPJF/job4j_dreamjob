package ru.job4j.servlets.crud.logic;

import ru.job4j.servlets.crud.model.User;
import ru.job4j.servlets.crud.persistent.IStore;
import ru.job4j.servlets.crud.persistent.MemoryStore;

import java.util.Collection;
import java.util.Objects;

/**
 * The class of the main logic of the program.
 *
 * @author IvanPJF (teaching-light@yandex.ru)
 * @version 0.1
 * @since 06.02.2020
 */
public class ValidateService {

    private static final ValidateService INSTANCE = new ValidateService();
    private final IStore persistence = MemoryStore.getInstance();

    private ValidateService() {
    }

    public static ValidateService getInstance() {
        return INSTANCE;
    }

    public boolean add(User user) {
        boolean result = false;
        if (Objects.isNull(findById(user))) {
            this.persistence.add(new User(user.getName(), user.getLogin(), user.getEmail(), user.getCreateDate()));
            result = true;
        }
        return result;
    }

    public boolean update(User user) {
        boolean result = false;
        if (Objects.nonNull(findById(user))) {
            this.persistence.update(user);
            result = true;
        }
        return result;
    }

    public boolean delete(User user) {
        boolean result = false;
        if (Objects.nonNull(findById(user))) {
            this.persistence.delete(user);
            result = true;
        }
        return result;
    }

    public Collection<User> findAll() {
        return this.persistence.findAll();
    }

    public User findById(User user) {
        return Objects.nonNull(user.getId()) ? this.persistence.findById(user) : null;
    }
}
