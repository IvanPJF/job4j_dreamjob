package ru.job4j.servlets.crud.persistent;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.servlets.crud.model.IRole;
import ru.job4j.servlets.crud.model.IStoreRole;
import ru.job4j.servlets.crud.model.StoreRoleMemory;
import ru.job4j.servlets.crud.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

public class DBStore implements IStore {

    private static final IStoreRole STORE_ROLE = StoreRoleMemory.getInstance();
    private static final BasicDataSource SOURCE = new BasicDataSource();
    private static final DBStore INSTANCE = new DBStore();
    private static final String NAME_FILE_PROPERTIES = "app.properties";

    private Properties loadProperties() {
        Properties config = new Properties();
        try (InputStream is = DBStore.class.getClassLoader().getResourceAsStream(NAME_FILE_PROPERTIES)) {
            config.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }

    private DBStore() {
        Properties config = loadProperties();
        SOURCE.setDriverClassName(config.getProperty("driver-class-name"));
        SOURCE.setUrl(config.getProperty("url"));
        SOURCE.setUsername(config.getProperty("username"));
        SOURCE.setPassword(config.getProperty("password"));
        SOURCE.setMinIdle(Integer.parseInt(config.getProperty("min-idle")));
        SOURCE.setMaxIdle(Integer.parseInt(config.getProperty("max-idle")));
        SOURCE.setMaxOpenPreparedStatements(Integer.parseInt(config.getProperty("max-prepared-statements")));
        createTable();
        createUser();
    }

    public static DBStore getInstance() {
        return INSTANCE;
    }

    @Override
    public User add(User user) {
        try (Connection con = SOURCE.getConnection();
             PreparedStatement prst = con
                     .prepareStatement("INSERT INTO users(name, login, email, create_date, password, role) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            inputToPrepare(user, prst);
            prst.execute();
            try (ResultSet genKey = prst.getGeneratedKeys()) {
                if (genKey.next()) {
                    user.setId(genKey.getInt("id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User update(User user) {
        try (Connection con = SOURCE.getConnection();
             PreparedStatement prst = con
                     .prepareStatement("UPDATE users SET name = ?, login = ?, email = ?, create_date = ?, password = ?, role = ? WHERE id = ?")) {
            User oldUser = findById(user);
            inputToPrepare(user, prst);
            prst.setInt(7, user.getId());
            if (prst.executeUpdate() == 1) {
                return oldUser;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User delete(User user) {
        try (Connection con = SOURCE.getConnection();
             PreparedStatement prst = con
                     .prepareStatement("DELETE FROM users WHERE id = ?")) {
            User oldUser = findById(user);
            prst.setInt(1, user.getId());
            if (prst.executeUpdate() == 1) {
                return oldUser;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Collection<User> findAll() {
        Collection<User> result = new ArrayList<>();
        try (Connection con = SOURCE.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM users ORDER BY id")) {
            while (rs.next()) {
                result.add(new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("login"),
                        rs.getString("email"),
                        new Date(rs.getTimestamp("create_date").getTime()),
                        rs.getString("password"),
                        STORE_ROLE.getRole(rs.getString("role")))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public User findById(User user) {
        try (Connection con = SOURCE.getConnection();
             PreparedStatement prst = con
                     .prepareStatement("SELECT * FROM users WHERE id = ?")) {
            prst.setInt(1, user.getId());
            return executePrepStatement(prst);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findByLogin(String login) {
        try (Connection con = SOURCE.getConnection();
             PreparedStatement prst = con
                     .prepareStatement("SELECT * FROM users WHERE login = ?")) {
            prst.setString(1, login);
            return executePrepStatement(prst);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isCredential(String login, String password) {
        try (Connection con = SOURCE.getConnection();
             PreparedStatement prst = con
                     .prepareStatement("SELECT * FROM users WHERE login = ? and password = ?")) {
            prst.setString(1, login);
            prst.setString(2, password);
            try (ResultSet rs = prst.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void createTable() {
        try (Connection con = SOURCE.getConnection();
             Statement st = con.createStatement()) {
            st.execute("CREATE TABLE IF NOT EXISTS users("
                    + "id SERIAL PRIMARY KEY,"
                    + "name TEXT NOT NULL,"
                    + "login TEXT UNIQUE NOT NULL,"
                    + "email TEXT NOT NULL,"
                    + "create_date TIMESTAMP NOT NULL,"
                    + "password TEXT NOT NULL,"
                    + "role TEXT NOT NULL)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createUser() {
        if (findAll().isEmpty()) {
            add(new User(null, "root", "root", "root@root", "root", STORE_ROLE.getRole(IRole.ADMIN)));
        }
    }

    private User executePrepStatement(PreparedStatement prst) throws SQLException {
        try (ResultSet rs = prst.executeQuery()) {
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("login"),
                        rs.getString("email"),
                        new Date(rs.getTimestamp("create_date").getTime()),
                        rs.getString("password"),
                        STORE_ROLE.getRole(rs.getString("role"))
                );
            }
        }
        return null;
    }

    private void inputToPrepare(User user, PreparedStatement prst) throws SQLException {
        prst.setString(1, user.getName());
        prst.setString(2, user.getLogin());
        prst.setString(3, user.getEmail());
        prst.setTimestamp(4, new Timestamp(user.getCreateDate().getTime()));
        prst.setString(5, user.getPassword());
        prst.setString(6, user.getRole().getName());
    }
}
