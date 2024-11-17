package com.javadbmanager.business.logic;

import com.javadbmanager.data.AnyRepository;
import com.javadbmanager.data.ConnectionHandler;
import com.javadbmanager.data.DataAccessObject;
import com.javadbmanager.data.TableHandler;
import com.javadbmanager.data.utils.DataUtils;
import com.javadbmanager.data.utils.QueryBuilder;

/**
 * The {@code DataLayerProvider} interface acts as a factory for providing
 * access to
 * various data access and manipulation components. It offers methods to obtain
 * instances of
 * utility classes, repositories, and data handlers, abstracting the underlying
 * data layer implementation.
 */
public interface DataLayerProvider {

    /**
     * Retrieves a {@code DataUtils} instance, which provides utility methods for
     * working with data.
     * 
     * @return The {@code DataUtils} instance.
     */
    DataUtils getDataUtils();

    /**
     * Retrieves a {@code QueryBuilder} instance, used for constructing database
     * queries.
     * 
     * @return The {@code QueryBuilder} instance.
     */
    QueryBuilder getQueryBuilder();

    /**
     * Retrieves a {@code DataAccessObject} instance, which provides methods for
     * accessing and manipulating data.
     * 
     * @return The {@code DataAccessObject} instance.
     */
    DataAccessObject getDataAccessObject();

    /**
     * Retrieves a generic {@code AnyRepository} instance, offering common data
     * access operations.
     * 
     * @return The {@code AnyRepository} instance.
     */
    AnyRepository getAnyRepository();

    /**
     * Retrieves a {@code TableHandler} instance, used for managing database tables.
     * 
     * @return The {@code TableHandler} instance.
     */
    TableHandler getTableHandler();

    /**
     * Retrieves a {@code ConnectionHandler} instance, responsible for managing
     * database connections.
     * 
     * @return The {@code ConnectionHandler} instance.
     */
    ConnectionHandler getConnectionHandler();

    /**
     * Initializes the data layer. This method might perform setup operations like
     * establishing
     * database connections, configuring connection pools, or loading necessary
     * resources.
     */
    void initDataLayer();
}
