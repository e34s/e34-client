package com.element34.report;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by freynaud on 03/09/2017.
 */
public class Log {

  private final String level;
  private final String message;
  private final long time;

  public Log(String level, String message) {
    this.time = System.currentTimeMillis();
    this.level = level;
    this.message = message;
  }

  @Override
  public String toString() {
    return "[" + level + "] " + time + " \t" + message;
  }

  public Map<String, Object> asMap() {
    Map<String, Object> res = new HashMap<>();
    res.put("time", time);
    res.put("level", level);
    res.put("message", message);
    return res;
  }
}
