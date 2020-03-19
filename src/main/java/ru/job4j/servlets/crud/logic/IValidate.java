package ru.job4j.servlets.crud.logic;

import ru.job4j.servlets.crud.model.User;

import java.util.Collection;

public interface IValidate {

    boolean add(User user);

    boolean update(User user);

    boolean delete(User user);

    Collection<User> findAll();

    User findById(User user);

    User findByLogin(User user);

    boolean isCredential(String login, String password);
}
