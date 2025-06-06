package com.javadbmanager.presentation;

import java.io.IOException;
import java.util.Map;

public class DisplayUtils {
  public static final String os = System.getProperty("os.name").toLowerCase();;

  public static int getWidth() {
    int width = 100;
    try {
      if (os.contains("win")) {
        // Windows
        Process process = Runtime.getRuntime().exec("mode con");
        process.waitFor();
        java.io.BufferedReader reader = new java.io.BufferedReader(
            new java.io.InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
          if (line.startsWith("Columns:")) {
            width = Integer.parseInt(line.split(":")[1].trim());
          }
        }
      } else {
        // Unix-like (Linux, macOS)
        Process process = Runtime.getRuntime().exec("tput cols");
        process.waitFor();
        java.io.BufferedReader reader = new java.io.BufferedReader(
            new java.io.InputStreamReader(process.getInputStream()));
        int cols = Integer.parseInt(reader.readLine());
        width = cols;
      }
    } catch (IOException | InterruptedException e) {
      // e.printStackTrace();
    }
    return width;
  }

  public static Map<String, String> parseMessageToMap(String message) {
    message.trim();
    String[] values = message.split("=");
    if (message.isBlank() || message.isEmpty() || values.length <= 1) {
      return Map.of();
    }

    return Map.of(values[0], values[1]);
  }
}
