package ru.job4j.servlets.crud.persistent;

import ru.job4j.servlets.crud.model.User;

import java.util.Collection;

/**
 * Data storage interface.
 *
 * @author IvanPJF (teaching-light@yandex.ru)
 * @version 0.1
 * @since 06.02.2020
 */
public interface IStore {

    User add(User user);

    User update(User user);

    User delete(User user);

    Collection<User> findAll();

    User findById(User user);
}
