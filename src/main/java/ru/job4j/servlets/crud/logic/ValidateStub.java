package ru.job4j.servlets.crud.logic;

import ru.job4j.servlets.crud.model.User;

import java.util.*;
import java.util.function.Supplier;

public class ValidateStub implements IValidate {

    private final Map<Integer, User> store = new HashMap<>();
    private int ids;

    @Override
    public boolean add(User user) {
        boolean isExistUser = false;
        if (Objects.nonNull(user)) {
            user.setId(this.ids++);
            this.store.put(user.getId(), user);
            isExistUser = true;
        }
        return isExistUser;
    }

    @Override
    public boolean update(User user) {
        return actionStore(user, () -> this.store.replace(user.getId(), user));
    }

    @Override
    public boolean delete(User user) {
        return actionStore(user, () -> this.store.remove(user.getId()));
    }

    @Override
    public Collection<User> findAll() {
        return new ArrayList<>(this.store.values());
    }

    @Override
    public User findById(User user) {
        return Objects.nonNull(user) ? this.store.get(user.getId()) : null;
    }

    @Override
    public User findByLogin(User user) {
        User result = null;
        if (Objects.nonNull(user)) {
            for (User value : this.store.values()) {
                if (value.getLogin().equals(user.getLogin())) {
                    result = user;
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public boolean isCredential(String login, String password) {
        boolean result = false;
        for (User user : this.store.values()) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                result = true;
                break;
            }
        }
        return result;
    }

    private boolean actionStore(User user, Supplier<User> supplier) {
        boolean isExistKey = false;
        if (Objects.nonNull(user) && this.store.containsKey(user.getId())) {
            supplier.get();
            isExistKey = true;
        }
        return isExistKey;
    }
}
