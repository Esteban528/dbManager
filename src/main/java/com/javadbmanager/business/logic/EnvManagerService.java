package com.javadbmanager.business.logic;

/**
 * The {@code EnvManagerService} interface defines methods for managing and accessing environment variables.
 * It acts as an orchestrator for storing and retrieving various application settings and dependencies.
 */
public interface EnvManagerService {

    /**
     * Retrieves the value associated with the specified environment variable name.
     *
     * @param envName The name of the environment variable.
     * @return The value associated with the environment variable. The return type is {@code Object},
     *         so casting to the appropriate type is required when using the retrieved value.  For example:
     *         {@code (MyType) envManagerService.get("myVariable");}
     * @throws EnvVariableNotFoundException If no environment variable with the given name exists. (Add this exception for better error handling)
     */
    Object get(String envName);

    /**
     * Sets the value of the specified environment variable.  If the variable already exists,
     * its value will be overwritten.
     * @param envName The name of the environment variable to set.
     * @param value The new value for the environment variable.  Can be of any type.
     */
    void set(String envName, Object value);


    /**
     * Removes the specified environment variable.
     * @param envName The name of the environment variable to remove.
     */
    void remove(String envName);
}
