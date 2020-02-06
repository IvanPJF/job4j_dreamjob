package ru.job4j.servlets.crud.persistent;

import ru.job4j.servlets.crud.model.User;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A class for storing data in memory.
 *
 * @author IvanPJF (teaching-light@yandex.ru)
 * @version 0.1
 * @since 06.02.2020
 */
public class MemoryStore implements IStore {

    private static final MemoryStore PERSISTENCE = new MemoryStore();
    private final Map<Integer, User> storeMap = new ConcurrentHashMap<>();

    private MemoryStore() {
    }

    public static MemoryStore getInstance() {
        return PERSISTENCE;
    }

    @Override
    public User add(User user) {
        return this.storeMap.put(user.getId(), user);
    }

    @Override
    public User update(User user) {
        return this.storeMap.compute(user.getId(), (key, value) -> {
            value.setName(user.getName());
            value.setLogin(user.getLogin());
            value.setEmail(user.getEmail());
            return value;
        });
    }

    @Override
    public User delete(User user) {
        return this.storeMap.remove(user.getId());
    }

    @Override
    public Collection<User> findAll() {
        return this.storeMap.values();
    }

    @Override
    public User findById(User user) {
        return this.storeMap.get(user.getId());
    }
}
