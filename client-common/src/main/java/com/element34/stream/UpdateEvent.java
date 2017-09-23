package com.element34.stream;

import static com.element34.test.Run.GSON;

import com.google.gson.JsonElement;
import java.util.Map;


public class UpdateEvent implements Event {

  private final Map<String, Object> data;

  public UpdateEvent(Map<String, Object> data) {
    this.data = data;
  }

  @Override
  public JsonElement toJSON() {
    return GSON.toJsonTree(data);
  }

  @Override
  public String toString() {
    return GSON.toJson(toJSON());
  }
}
