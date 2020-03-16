package ru.job4j.servlets.crud.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class StoreRoleMemory implements IStoreRole {

    private final Map<String, IRole> store = new HashMap<>();
    private static final StoreRoleMemory INSTANCE = new StoreRoleMemory();

    private StoreRoleMemory() {
        this.store.put(IRole.ADMIN, () -> IRole.ADMIN);
        this.store.put(IRole.USER, () -> IRole.USER);
    }

    public static IStoreRole getInstance() {
        return INSTANCE;
    }

    @Override
    public Collection<IRole> getAllRoles() {
        return this.store.values();
    }

    @Override
    public IRole getRole(String role) {
        return this.store.get(role);
    }
}
