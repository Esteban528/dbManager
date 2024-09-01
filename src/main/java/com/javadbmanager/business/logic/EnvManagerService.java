package com.javadbmanager.business.logic;

public interface EnvManagerService {

  Object get(String envManager);

  void set(String envName, Object value);

  void remove(String envName);
}
