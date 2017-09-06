package com.element34.test;

import com.element34.stream.Event;
import com.element34.stream.StreamableList;
import com.element34.stream.UpdateEvent;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

/**
 * Created by freynaud on 05/09/2017.
 */
public class Run implements Event {

  private String name;
  private TestStatus status = TestStatus.STARTED;
  private StreamableList<TestResult> results = new StreamableList<>();
  public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
  private final List<Consumer<Event>> listeners = new CopyOnWriteArrayList<>();

  public Run() {
    name = "Test run";
  }

  public synchronized void addListener(Consumer<Event> consumer) {
    consumer.accept(new UpdateEvent(ImmutableMap.of("reset", true)));
    consumer.accept(this);
    listeners.add(consumer);
    results.addListener(consumer);
  }


  public synchronized void add(TestResult result) {
    results.add(result);

  }

  @Override
  public String toString() {
    return GSON.toJson(toJSON());
  }

  public JsonObject toJSON() {
    JsonObject run = new JsonObject();
    run.addProperty("type", "run");
    run.addProperty("name", name);
    run.addProperty("status", status.toString());
    JsonArray results = new JsonArray();
    for (TestResult result : this.results) {
      results.add(result.toJSON());
    }
    run.add("results", results);
    return run;
  }

  public List<Consumer<Event>> getListeners() {
    return listeners;
  }

  public synchronized void removeListener(Consumer<Event> consumer) {
    listeners.remove(consumer);
    results.removeListener(consumer);
  }


  public synchronized void setStatus(TestStatus status) {
    this.status = status;
    update("status", status);
  }

  private void update(String key, Object value) {
    Event event = new UpdateEvent(ImmutableMap.of("type", "run", key, value));
    for (Consumer<Event> listener : listeners) {
      listener.accept(event);
    }
  }

  public void awaitConsumers(long timeoutSeconds) throws TimeoutException {

    long deadline = System.currentTimeMillis() + timeoutSeconds * 1000;
    while (true) {
      sleepTight(500);
      if (listeners.size() > 0) {
        return;
      }
      if (System.currentTimeMillis() > deadline) {
        throw new TimeoutException("Couldn't connect to websocket after " + timeoutSeconds + " sec.");
      }
    }
  }

  public static void sleepTight(int ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) {
    }
  }
}
