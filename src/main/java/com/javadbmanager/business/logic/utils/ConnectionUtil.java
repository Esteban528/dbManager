package com.javadbmanager.business.logic.utils;

import java.sql.Connection;
import java.sql.SQLException;

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
}
