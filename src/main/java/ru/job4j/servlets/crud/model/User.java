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

    private final static AtomicInteger NEXT_ID = new AtomicInteger();
    private final Integer id;
    private String name;
    private String login;
    private String email;
    private Date createDate;

    public User(Integer id, String name, String login, String email, Date createDate) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.email = email;
        this.createDate = createDate;
    }

    public User(Integer id, String name, String login, String email) {
        this(id, name, login, email, new Date());
    }

    public User(String name, String login, String email, Date createDate) {
        this(NEXT_ID.getAndIncrement(), name, login, email, createDate);
    }

    public Integer getId() {
        return id;
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
                && Objects.equals(name, user.name)
                && Objects.equals(login, user.login)
                && Objects.equals(email, user.email)
                && Objects.equals(createDate, user.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, login, email, createDate);
    }

    @Override
    public String toString() {
        return new StringJoiner(" | ", "[ ", " ]")
                .add(String.format("%-5.5s", this.id))
                .add(String.format("%-20.20s", this.name))
                .add(String.format("%-20.20s", this.login))
                .add(String.format("%-20.20s", this.email))
                .add(String.format("%-30.30s", this.createDate.toString()))
                .toString();
    }
}
