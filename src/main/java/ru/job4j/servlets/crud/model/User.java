package ru.job4j.servlets.crud.model;

import java.util.Date;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class model.
 *
 * @author IvanPJF (teaching-light@yandex.ru)
 * @version 0.1
 * @since 06.02.2020
 */
public class User {

    private Integer id;
    private String name;
    private String login;
    private String email;
    private Date createDate;
    private String password;
    private IRole role;

    public User(Integer id, String name, String login, String email,
                Date createDate, String password, IRole role) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.email = email;
        this.createDate = createDate;
        this.password = password;
        this.role = role;
    }

    public User(Integer id, String name, String login, String email, String password, IRole role) {
        this(id, name, login, email, new Date(), password, role);
    }

    public User(Integer id) {
        this(id, null, null, null, new Date(), null, null);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getPassword() {
        return password;
    }

    public IRole getRole() {
        return role;
    }

    public void setRole(IRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id)
                && Objects.equals(login, user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "User{", "}")
                .add(String.format("id=%d", this.id))
                .add(String.format("name=%s", this.name))
                .add(String.format("login=%s", this.login))
                .add(String.format("email=%s", this.email))
                .add(String.format("createDate=%s", this.createDate.toString()))
                .add(String.format("password=%s", this.password))
                .add(String.format("role=%s", Objects.nonNull(this.role) ? this.role.getName() : null))
                .toString();
    }
}
