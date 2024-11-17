package com.javadbmanager;

import com.javadbmanager.business.delegate.BusinessDelegate;

/**
 * Main Class
 */
public class App {
  public static void main(String[] args) {
    BusinessDelegate bDelegate = new BusinessDelegate();
    bDelegate.init();
  }
}
