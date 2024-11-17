package com.javadbmanager.business.logic;

import com.javadbmanager.data.ConnectionBean;

/**
 * A builder class for creating {@code ConnectionBean} objects.  This class uses the builder pattern
 * to provide a flexible and readable way to construct connection beans with various configurations.
 */
public class ConnectionBeanBuilder {

    private String host = "localhost";
    private String port = "3306";
    private String username = "root";
    private String password = "";
    private String dbType = "mysql";
    private double dbVersion = 8.1;
    private String database = "users";

    /**
     * Sets the hostname or IP address of the database server.
     * @param host The database host. Default is "localhost".
     * @return This builder instance for method chaining.
     */
    public ConnectionBeanBuilder setHost(String host) {
        this.host = host;
        return this;
    }

    /**
     * Sets the port number for the database connection.
     * @param port The database port. Default is "3306".
     * @return This builder instance for method chaining.
     */
    public ConnectionBeanBuilder setPort(String port) {
        this.port = port;
        return this;
    }

    /**
     * Sets the username for database authentication.
     * @param username The database username. Default is "root".
     * @return This builder instance for method chaining.
     */
    public ConnectionBeanBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * Sets the password for database authentication.
     * @param password The database password. Default is an empty string.
     * @return This builder instance for method chaining.
     */
    public ConnectionBeanBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    /**
     * Sets the database type (e.g., "mysql", "postgresql").  The input is case-insensitive
     * and will be normalized to "mysql" or "postgresql".
     * @param dbType The database type.  Default is "mysql".
     * @return This builder instance for method chaining.
     */
    public ConnectionBeanBuilder setDBType(String dbType) {
        String result = this.dbType;
        dbType = dbType.toLowerCase();

        if (dbType.contains("postgres")) {
            result = "postgresql";
        } else if (dbType.contains("mysq")) {  // Improved to handle variations like "mysql5"
            result = "mysql";
        }

        this.dbType = result;
        return this;
    }

    /**
     * Sets the database version.
     * @param dbVersion The database version. Default is 8.1.
     * @return This builder instance for method chaining.
     */
    public ConnectionBeanBuilder setDBVersion(double dbVersion) {
        this.dbVersion = dbVersion;
        return this;
    }

    /**
     * Sets the name of the database to connect to.
     * @param database The database name. Default is "users".
     * @return This builder instance for method chaining.
     */
    public ConnectionBeanBuilder setDatabase(String database) {
        this.database = database;
        return this;
    }


    /**
     * Creates a new {@code ConnectionBean} object using the configured parameters.
     * @return The newly constructed {@code ConnectionBean}.
     */
    public ConnectionBean build() {
        return new ConnectionBean(host, port, username, password, dbType, dbVersion, database);
    }
}
