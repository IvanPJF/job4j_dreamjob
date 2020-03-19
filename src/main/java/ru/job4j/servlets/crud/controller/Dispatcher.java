package ru.job4j.servlets.crud.controller;

import ru.job4j.servlets.crud.logic.IValidate;
import ru.job4j.servlets.crud.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * Dispatcher template.
 * Using logic by action name.
 *
 * @author IvanPJF (teaching-light@yandex.ru)
 * @version 0.1
 * @since 06.02.2020
 */
public class Dispatcher {

    private final Map<String, BiFunction<IValidate, User, Boolean>> map = new HashMap<>();
    private static final Dispatcher DISPATCHER = new Dispatcher();

    private Dispatcher() {
    }

    public static Dispatcher getInstance() {
        return DISPATCHER;
    }

    public Dispatcher init() {
        load("add", IValidate::add);
        load("update", IValidate::update);
        load("delete", IValidate::delete);
        return this;
    }

    public void load(String action, BiFunction<IValidate, User, Boolean> service) {
        this.map.put(action, service);
    }

    public boolean send(String action, IValidate logic, User user) {
        BiFunction<IValidate, User, Boolean> biFunction = this.map.get(action);
        if (Objects.isNull(biFunction)) {
            throw new NoSuchElementException("No action found.");
        }
        return biFunction.apply(logic, user);
    }
}
