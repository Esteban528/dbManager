package com.javadbmanager.data.exceptions;

/**
 * A custom runtime exception indicating that a specified column was not found in a given table.
 * This exception is typically thrown during database operations when attempting to access or
 * manipulate a non-existent column.
 */
public class ColumnNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code ColumnNotFoundException} with a message indicating the missing column and table.
     * @param columnName The name of the column that was not found.
     * @param tableName The name of the table where the column was expected.
     */
    public ColumnNotFoundException(String columnName, String tableName) {
        super(String.format("The column %s does not exist in table %s", columnName, tableName));
    }
}
