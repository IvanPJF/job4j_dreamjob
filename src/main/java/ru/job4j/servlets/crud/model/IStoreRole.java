package ru.job4j.servlets.crud.model;

import java.util.Collection;

public interface IStoreRole {

    Collection<IRole> getAllRoles();

    IRole getRole(String role);
}
