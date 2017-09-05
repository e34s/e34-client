package com.element34.test;

import static com.element34.test.Run.GSON;

import com.element34.report.Log;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestResult {

  private final static Logger logger = LoggerFactory.getLogger(TestResult.class);

  private static final ThreadLocal<TestResult> results = new InheritableThreadLocal<>();
  private final List<String> events = new ArrayList<>();
  private String sessionId;
  private TestStatus status = TestStatus.STARTED;
  private Throwable throwable;
  private long start;
  private long end;
  private final String id = UUID.randomUUID().toString();
  private String aPackage;
  private String clazz;
  private String method;
  private List<SimpleEntry<String, String>> tags = new ArrayList<>();
  private List<Log> logs = new ArrayList<>();

  private Object[] params = new Object[0];

  public static TestResult getCurrentTestResult() {
    TestResult res = results.get();
    if (res == null) {
      // logger.warn("Cannot access TestResult of a non selenium augmented test.");
      res = create();
    }
    return res;
  }

  public static TestResult create() {
    TestResult result = null;
    if (results.get() != null) {
      logger.warn("Trying to ceate a result on a non empty thread.");
      result = results.get();
    } else {
      result = new TestResult();
    }
    result.setStart(System.currentTimeMillis());
    results.set(result);
    return result;
  }

  public static TestResult close() {
    TestResult result = getCurrentTestResult();
    result.setEnd(System.currentTimeMillis());
    results.remove();
    return result;
  }


  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public void setStatus(TestStatus status) {
    this.status = status;
  }

  public void setThrowable(Throwable throwable) {
    this.throwable = throwable;
  }

  public void setEnd(long end) {
    this.end = end;
  }

  public void setStart(long start) {
    this.start = start;
  }


  public JsonObject toJSON() {
    JsonObject result = new JsonObject();
    result.addProperty("id", id);
    result.addProperty("package", aPackage);
    result.addProperty("clazz", clazz);
    result.addProperty("method", method);
    result.addProperty("start", start);
    result.addProperty("end", end);
    result.addProperty("duration", end - start);
    result.addProperty("status", status.toString());
    if (throwable != null) {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      throwable.printStackTrace(pw);
      String trace = sw.toString();
      result.addProperty("error", throwable.getClass().getSimpleName() + ":" + throwable.getMessage());
      result.addProperty("throwable", trace);
    }
    result.addProperty("sessionId", sessionId);
    JsonArray array = new JsonArray();
    for (SimpleEntry<String, String> entry : tags) {
      JsonObject tag = new JsonObject();
      tag.addProperty("name", entry.getKey());
      tag.addProperty("value", entry.getValue());
      array.add(tag);
    }
    result.add("tags", array);

    JsonArray logs = new JsonArray();
    for (Log log : this.logs) {
      JsonElement l = GSON.toJsonTree(log);
      logs.add(l);
    }
    result.add("logs", logs);
    JsonArray parameterTypes = new JsonArray();
    for (Object p : params) {
      if (p != null) {
        parameterTypes.add("(" + p.getClass().getSimpleName() + ")" + p.toString());
      } else {
        parameterTypes.add("null");
      }
    }
    result.add("params", parameterTypes);
    return result;
  }

  @Override
  public String toString() {
    return GSON.toJson(toJSON());
  }

  public void setPackage(String aPackage) {
    this.aPackage = aPackage;
  }

  public void setClazz(String clazz) {
    this.clazz = clazz;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public void addTag(String key, String value) {
    tags.add(new SimpleEntry<>(key, value));
  }

  public void add(Log log) {
    logs.add(log);
  }

  public void setParams(Object[] params) {
    this.params = params;
  }

}
