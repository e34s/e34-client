package com.element34.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by freynaud on 05/09/2017.
 */
public class Run {

  private String name;
  private List<TestResult> results = new ArrayList<>();
  public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

  public Run() {
    name = "Test run";
  }

  public void add(TestResult result) {
    results.add(result);
  }

  @Override
  public String toString() {
    return GSON.toJson(toJSON());
  }

  public JsonObject toJSON() {
    JsonObject run = new JsonObject();
    run.addProperty("name", name);
    JsonArray results = new JsonArray();
    for (TestResult result : this.results) {
      results.add(result.toJSON());
    }
    run.add("results", results);
    return run;
  }
}
