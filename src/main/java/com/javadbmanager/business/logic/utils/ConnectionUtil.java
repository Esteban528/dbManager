package com.javadbmanager.business.logic.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.function.Function;

import com.javadbmanager.business.logic.ConnectionBeanBuilder;
import com.javadbmanager.data.ConnectionBean;
import com.javadbmanager.data.ConnectionHandler;
import com.javadbmanager.data.ConnectionHandlerImpl;
import com.javadbmanager.data.utils.DataUtilsImpl;

/**
 * The {@code ConnectionUtil} class provides utility methods for working with database connections,
 * such as testing connection validity.
 */
public class ConnectionUtil {

    /**
     * Tests the database connection using the provided {@code ConnectionBeanBuilder}.
     * @param connectionBeanBuilder The builder containing the connection parameters. Should not be null.
     * @return {@code true} if the connection is valid, {@code false} otherwise.
     */
    public boolean test(ConnectionBeanBuilder connectionBeanBuilder) {
        if (connectionBeanBuilder == null) {
            return false; 
        }

        ConnectionBean connectionBean = connectionBeanBuilder.build();
        boolean result = false;
        try {
            ConnectionHandler connectionHandler = new ConnectionHandlerImpl(connectionBean, new DataUtilsImpl());
            Connection con = connectionHandler.getConnection();
            result = con.isValid(5); 
            con.close();  
        } catch (SQLException e) {
            result = false;
        }

        return result;
    }

    /**
     * Establishes a simple database connection using a {@link ConnectionBeanBuilder} 
     * and executes a given function with the resulting {@link Connection}.
     *
     * <p>This method is a utility for executing operations that require a JDBC connection. 
     * It builds the connection configuration, opens the connection, passes it to the given function,
     * and ensures the connection is properly closed afterward.</p>
     *
     * @param connectionBeanBuilder the builder that provides the necessary connection parameters; must not be {@code null}
     * @param fun a {@link Function} that receives an open {@link Connection} and returns a result; must not be {@code null}
     * @return the result of applying the given function to the open connection, or {@code null} if the builder is {@code null} or an exception occurs
     */
    public Object run(ConnectionBeanBuilder connectionBeanBuilder, Function<Connection, Object> fun) {
        if (connectionBeanBuilder == null) {
            return null; 
        }

        ConnectionBean connectionBean = connectionBeanBuilder.build();

        ConnectionHandler connectionHandler = new ConnectionHandlerImpl(connectionBean, new DataUtilsImpl());
        try(Connection con = connectionHandler.getConnection()) {
            return fun.apply(con);
        } catch (SQLException e) {
        }

        return null;
    }
}
