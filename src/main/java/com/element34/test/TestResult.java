package com.element34.test;

import com.element34.report.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
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
  private String aPackage;
  private String clazz;
  private String method;
  private List<SimpleEntry<String, String>> tags = new ArrayList<>();
  private List<Log> logs = new ArrayList<>();
  private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

  public static TestResult getCurrentTestResult() {
    TestResult res = results.get();
    if (res == null) {
      logger.warn("Cannot access TestResult of a non selenium augmented test.");
    }
    return res;
  }

  public static TestResult create() {
    if (results.get() != null) {
      logger.warn("Trying to ceate a result on a non empty thread.");
    }
    TestResult result = new TestResult();
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


  @Override
  public String toString() {
    JsonObject result = new JsonObject();
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
      result.addProperty("throwable", trace);
    }
    result.addProperty("sessionId", sessionId);
    JsonArray array = new JsonArray();
    for (SimpleEntry<String, String> entry : tags) {
      JsonObject tag = new JsonObject();
      tag.addProperty(entry.getKey(), entry.getValue());
      array.add(tag);
    }
    result.add("tags", array);

    JsonArray logs = new JsonArray();
    for (Log log : this.logs) {
      JsonElement l = gson.toJsonTree(log);
      logs.add(l);
    }
    result.add("logs", logs);
    return gson.toJson(result);
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
}
