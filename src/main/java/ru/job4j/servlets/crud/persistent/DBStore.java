package ru.job4j.servlets.crud.persistent;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.servlets.crud.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

public class DBStore implements IStore {

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
    }

    private void createTable() {
        try (Connection connection = SOURCE.getConnection()) {
            Statement st = connection.createStatement();
            st.execute("CREATE TABLE IF NOT EXISTS users "
                    + "(id SERIAL PRIMARY KEY, name TEXT NOT NULL, login TEXT NOT NULL, email TEXT NOT NULL, create_date TIMESTAMP NOT NULL)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DBStore getInstance() {
        return INSTANCE;
    }

    @Override
    public User add(User user) {
        try (PreparedStatement pst = SOURCE.getConnection()
                .prepareStatement("INSERT INTO users(name, login, email, create_date) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, user.getName());
            pst.setString(2, user.getLogin());
            pst.setString(3, user.getEmail());
            pst.setTimestamp(4, new Timestamp(user.getCreateDate().getTime()));
            pst.execute();
            try (ResultSet genKey = pst.getGeneratedKeys()) {
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
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement pstm = connection
                     .prepareStatement("UPDATE users SET name = ?, login = ?, email = ?, create_date = ? WHERE id = ?")) {
            User oldUser = getUserById(connection, user.getId());
            pstm.setString(1, user.getName());
            pstm.setString(2, user.getLogin());
            pstm.setString(3, user.getEmail());
            pstm.setTimestamp(4, new Timestamp(user.getCreateDate().getTime()));
            pstm.setInt(5, user.getId());
            if (pstm.executeUpdate() == 1) {
                return oldUser;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User delete(User user) {
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement pstm = connection
                     .prepareStatement("DELETE FROM users WHERE id = ?")) {
            User oldUser = getUserById(connection, user.getId());
            pstm.setInt(1, user.getId());
            if (pstm.executeUpdate() == 1) {
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
        try (Statement st = SOURCE.getConnection().createStatement();
             ResultSet rset = st.executeQuery("SELECT * FROM users")) {
            while (rset.next()) {
                result.add(new User(
                        rset.getInt("id"),
                        rset.getString("name"),
                        rset.getString("login"),
                        rset.getString("email"),
                        new Date(rset.getTimestamp("create_date").getTime()))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public User findById(User user) {
        try (Connection connection = SOURCE.getConnection()) {
            return getUserById(connection, user.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private User getUserById(Connection connection, Integer id) {
        User userFromDB = null;
        try (PreparedStatement pstmt = connection
                .prepareStatement("SELECT * FROM users WHERE id = ?")) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    userFromDB = new User(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("login"),
                            rs.getString("email"),
                            new Date(rs.getTimestamp("create_date").getTime())
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userFromDB;
    }
}
