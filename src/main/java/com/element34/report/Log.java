package com.element34.report;

import static com.element34.test.Run.GSON;

import com.element34.stream.Event;
import com.google.gson.JsonElement;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by freynaud on 03/09/2017.
 */
public class Log implements Event {

  private String testId;
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
    return GSON.toJson(toJSON());
  }

  public Map<String, Object> asMap() {
    Map<String, Object> res = new HashMap<>();
    res.put("type", "log");
    res.put("resultId", testId);
    res.put("time", time);
    res.put("level", level);
    res.put("message", message);
    return res;
  }

  @Override
  public JsonElement toJSON() {
    return GSON.toJsonTree(asMap());
  }

  public void setTestId(String testId) {
    this.testId = testId;
  }
}
