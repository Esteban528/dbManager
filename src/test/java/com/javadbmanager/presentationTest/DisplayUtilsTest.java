package com.javadbmanager.presentationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.javadbmanager.presentation.DisplayUtils;

/**
 * DisplayUtilsTest
 */
public class DisplayUtilsTest {

  @Test
  void parseMessageToMapTest() {
    Map<String, String> result = DisplayUtils.parseMessageToMap("id=9");
    assertEquals(result, Map.of("id", "9"));

    result = DisplayUtils.parseMessageToMap("id=90000");
    assertEquals(result, Map.of("id", "90000"));

    result = DisplayUtils.parseMessageToMap("!1231test=90000");
    assertEquals(result, Map.of("!1231test", "90000"));
  }
}
