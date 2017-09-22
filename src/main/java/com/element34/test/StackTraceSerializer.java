package com.element34.test;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class StackTraceSerializer {


  public JsonElement serialize(Throwable t) {
    JsonObject ex = new JsonObject();
    ex.addProperty("class", t.getClass().getCanonicalName());
    ex.addProperty("message", t.getMessage());
    JsonArray elements = new JsonArray();
    ex.add("stackTraceElements", elements);
    for (StackTraceElement st : t.getStackTrace()) {
      elements.add(serialize(st));
    }
    if (t.getCause() != null) {
      ex.add("cause", serialize(t.getCause()));
    }
    return ex;
  }

  private JsonElement serialize(StackTraceElement el) {
    JsonObject res = new JsonObject();
    res.addProperty("declaringClass", el.getClassName());
    res.addProperty("methodName", el.getMethodName());
    res.addProperty("fileName", el.getFileName());
    res.addProperty("lineNumber", el.getLineNumber());
    return res;
  }
}
