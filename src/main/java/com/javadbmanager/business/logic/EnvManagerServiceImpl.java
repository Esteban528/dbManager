package com.javadbmanager.business.logic;

import java.util.HashMap;

/**
 * EnvManagerServiceImpl is an implementation of the environment variable
 * management service within the application. This class follows the Singleton
 * pattern to ensure that only one instance is present throughout the
 * application.
 */
public class EnvManagerServiceImpl implements EnvManagerService {
  // Map that stores the environment variables.
  private HashMap<String, Object> environmentVariables;

  // The single instance of the class (Singleton).
  private static EnvManagerService instance;

  /**
   * Retrieves the single instance of EnvManagerService.
   * 
   * @return the single instance of EnvManagerService.
   */
  public static synchronized EnvManagerService getInstance() {
    if (instance == null) {
      instance = new EnvManagerServiceImpl();
    }
    return instance;
  }

  /**
   * Private constructor to prevent instantiation outside of the class.
   * Initializes the environment variables map.
   */
  private EnvManagerServiceImpl() {
    this.environmentVariables = new HashMap<>();
  }

  /**
   * Retrieves the value of a specific environment variable.
   * 
   * @param envName the name of the environment variable.
   * @return the value of the environment variable, or null if not found.
   */
  @Override
  public Object get(String envName) {
    return environmentVariables.get(envName);
  }

  /**
   * Sets or updates an environment variable with the specified value.
   * 
   * @param envName the name of the environment variable.
   * @param value   the value to be set for the environment variable.
   */
  @Override
  public void set(String envName, Object value) {
    environmentVariables.put(envName, value);
  }

  /**
   * Removes an environment variable from the map.
   * 
   * @param envName the name of the environment variable to be removed.
   */
  @Override
  public void remove(String envName) {
    environmentVariables.remove(envName);
  }
}
