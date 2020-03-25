package ru.job4j.servlets.crud.persistent;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConnectionPool {

    private static final BasicDataSource SOURCE = new BasicDataSource();
    private static final ConnectionPool POOL = new ConnectionPool();
    private static final String NAME_FILE_PROPERTIES = "app.properties";

    private ConnectionPool() {
        Properties config = loadProperties();
        SOURCE.setDriverClassName(config.getProperty("driver-class-name"));
        SOURCE.setUrl(config.getProperty("url"));
        SOURCE.setUsername(config.getProperty("username"));
        SOURCE.setPassword(config.getProperty("password"));
        SOURCE.setMinIdle(Integer.parseInt(config.getProperty("min-idle")));
        SOURCE.setMaxIdle(Integer.parseInt(config.getProperty("max-idle")));
        SOURCE.setMaxOpenPreparedStatements(Integer.parseInt(config.getProperty("max-prepared-statements")));
    }

    private Properties loadProperties() {
        Properties config = new Properties();
        try (InputStream is = ConnectionPool.class.getClassLoader().getResourceAsStream(NAME_FILE_PROPERTIES)) {
            config.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }

    public static ConnectionPool getInstance() {
        return POOL;
    }

    public BasicDataSource pool() {
        return SOURCE;
    }
}
