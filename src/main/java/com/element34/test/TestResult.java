package com.element34.test;

import static com.element34.test.Run.GSON;

import com.element34.report.Log;
import com.element34.report.ReportSink;
import com.element34.stream.Event;
import com.element34.stream.StreamableList;
import com.element34.stream.UpdateEvent;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestResult implements Event {

  private static final Logger logger = LoggerFactory.getLogger(TestResult.class);
  private static final ThreadLocal<TestResult> results = new InheritableThreadLocal<>();
  private List<Consumer<Event>> listeners = new CopyOnWriteArrayList<>();


  private final Run parent;
  private String sessionId;
  private TestStatus status = TestStatus.STARTED;
  private Throwable throwable;
  private long start;
  private String video;
  private long end;
  private final String id = UUID.randomUUID().toString();
  private String aPackage;
  private String clazz;
  private String method;
  private List<SimpleEntry<String, Object>> tags = new ArrayList<>();
  private StreamableList<Log> logs = new StreamableList<>();

  private Object[] params = new Object[0];


  public TestResult() {
    parent = ReportSink.currentRun();
  }


  public synchronized static TestResult getCurrentTestResult() {
    TestResult res = results.get();
    if (res == null) {
      logger.warn("Cannot access TestResult of a non selenium augmented test.");
      return null;
    }
    return res;
  }


  public synchronized void addListener(Consumer<Event> consumer) {
    listeners.add(consumer);
    logs.addListener(consumer);
  }

  public synchronized void removeListener(Consumer<Event> consumer) {
    listeners.remove(consumer);
    logs.removeListener(consumer);
  }


  public synchronized static TestResult create() {
    TestResult result = results.get();
    if (result != null) {
      logger.warn("Trying to ceate a result on a non empty thread.");
      return result;
    } else {
      result = new TestResult();
      logger.warn("creating result");
      List<Consumer<Event>> consumers = ReportSink.currentRun().getListeners();
      for (Consumer<Event> consumer : consumers) {
        result.addListener(consumer);
      }
      ReportSink.currentRun().add(result);
      logger.warn("now " + ReportSink.currentRun().getResults().size());
      result.setStart(System.currentTimeMillis());
      results.set(result);
      return result;
    }
  }

  public static TestResult close() {
    TestResult result = getCurrentTestResult();
    result.setEnd(System.currentTimeMillis());
    results.remove();
    return result;
  }

  private String getError() {
    if (throwable == null) {
      return null;
    }
    return throwable.getClass().getSimpleName() + ":" + throwable.getMessage();
  }

  public synchronized void setSessionId(String sessionId) {
    this.sessionId = sessionId;
    update("sessionId", sessionId);
  }

  public synchronized void setStatus(TestStatus status) {
    this.status = status;
    update("status", status);
  }

  public synchronized void setThrowable(Throwable throwable) {
    this.throwable = throwable;
    update("throwable", new StackTraceSerializer().serialize(throwable));
    update("error", getError());
  }

  public synchronized void setEnd(long end) {
    this.end = end;
    update("end", end);
  }

  public synchronized void setStart(long start) {
    this.start = start;
    update("start", start);
  }

  public synchronized void setPackage(String aPackage) {
    this.aPackage = aPackage;
    update("package", aPackage);
  }

  public synchronized void setClazz(String clazz) {
    this.clazz = clazz;
    update("clazz", clazz);
  }

  public synchronized void setMethod(String method) {
    this.method = method;
    update("method", method);
  }

  public synchronized void addTag(String key, Object value) {
    tags.add(new SimpleEntry<>(key, value));
    update("tags", tags);
  }

  public synchronized void add(Log log) {
    log.setTestId(id);
    logs.add(log);
  }

  public synchronized void setParams(Object[] params) {
    this.params = params;
  }

  private synchronized void update(String key, Object value) {
    Event event = new UpdateEvent(ImmutableMap.of("type", "result", "id", id, key, value));
    for (Consumer<Event> listener : listeners) {
      listener.accept(event);
    }
  }


  public JsonObject toJSON() {
    JsonObject result = new JsonObject();
    result.addProperty("type", "result");
    result.addProperty("id", id);
    result.addProperty("video", video);
    result.addProperty("package", aPackage);
    result.addProperty("clazz", clazz);
    result.addProperty("method", method);
    result.addProperty("start", start);
    result.addProperty("end", end);
    result.addProperty("duration", end - start);
    result.addProperty("status", status.toString());
    if (throwable != null) {
      result.addProperty("error", getError());
      result.add("throwable", new StackTraceSerializer().serialize(throwable));
    }
    result.addProperty("sessionId", sessionId);
    JsonArray array = new JsonArray();
    for (SimpleEntry<String, Object> entry : tags) {
      JsonObject tag = new JsonObject();
      tag.addProperty("name", entry.getKey());
      if (entry.getValue() instanceof String) {
        tag.addProperty("value", (String) entry.getValue());
      } else if (entry.getValue() instanceof Boolean) {
        tag.addProperty("value", (Boolean) entry.getValue());
      } else {
        logger.warn("NI : tag of type " + entry.getValue());
      }
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
//        parameterTypes.add("(" + p.getClass().getSimpleName() + ")" + p.toString());
        parameterTypes.add(p.toString());
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


  public void setVideo(String video) {
    this.video = video;
  }

  public TestStatus getStatus() {
    return status;
  }
}
