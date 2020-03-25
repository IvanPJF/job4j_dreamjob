package ru.job4j.servlets.crud.persistent;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.servlets.crud.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class DBStore implements IStore {

    private static final IStoreRole STORE_ROLE = StoreRoleMemory.getInstance();
    private static final BasicDataSource SOURCE = ConnectionPool.getInstance().pool();
    private static final DBStore INSTANCE = new DBStore();

    private DBStore() {
    }

    public static IStore getInstance() {
        return INSTANCE;
    }

    @Override
    public User add(User user) {
        try (Connection con = SOURCE.getConnection();
             PreparedStatement prst = con
                     .prepareStatement("INSERT INTO users"
                                     + " (name, login, email, create_date, password, role, cities_id)"
                                     + " VALUES (?, ?, ?, ?, ?, ?, ?)",
                             Statement.RETURN_GENERATED_KEYS)) {
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
                     .prepareStatement("UPDATE users"
                             + " SET name = ?, login = ?, email = ?, create_date = ?,"
                             + " password = ?, role = ?, cities_id = ?"
                             + " WHERE id = ?")) {
            User oldUser = findById(user);
            inputToPrepare(user, prst);
            prst.setInt(8, user.getId());
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
             ResultSet rs = st.executeQuery(
                     "SELECT u.id user_id, u.name, u.login, u.email, u.create_date,"
                             + " u.password, u.role, ci.id city_id, ci.name city, co.id country_id, co.name country"
                             + " FROM users u"
                             + " LEFT JOIN cities ci ON u.cities_id = ci.id"
                             + " LEFT JOIN countries co ON ci.countries_id = co.id"
                             + " ORDER BY u.id")) {
            while (rs.next()) {
                result.add(buildUser(rs));
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
                     .prepareStatement(
                             "SELECT u.id user_id, u.name, u.login, u.email, u.create_date,"
                                     + " u.password, u.role, ci.id city_id, ci.name city, co.id country_id, co.name country"
                                     + " FROM users u"
                                     + " LEFT JOIN cities ci ON u.cities_id = ci.id"
                                     + " LEFT JOIN countries co ON ci.countries_id = co.id"
                                     + " WHERE u.id = ?")) {
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
             PreparedStatement prst = con.prepareStatement(
                     "SELECT u.id user_id, u.name, u.login, u.email, u.create_date,"
                             + " u.password, u.role, ci.id city_id, ci.name city, co.id country_id, co.name country"
                             + " FROM users u"
                             + " LEFT JOIN cities ci ON u.cities_id = ci.id"
                             + " LEFT JOIN countries co ON ci.countries_id = co.id"
                             + " WHERE u.login = ?")) {
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

    private User buildUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("user_id"),
                rs.getString("name"),
                rs.getString("login"),
                rs.getString("email"),
                new Date(rs.getTimestamp("create_date").getTime()),
                rs.getString("password"),
                STORE_ROLE.getRole(rs.getString("role")),
                new Country(rs.getInt("country_id"), rs.getString("country")),
                new City(rs.getInt("city_id"), rs.getString("city")));

    }

    private User executePrepStatement(PreparedStatement prst) throws SQLException {
        try (ResultSet rs = prst.executeQuery()) {
            if (rs.next()) {
                return buildUser(rs);
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
        prst.setInt(7, user.getCity().getId());
    }
}
