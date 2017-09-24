package com.element34.report;

import static com.element34.test.Run.sleepTight;

import com.element34.test.TestResult;

public class EventSink {


  public static void add(Log log) {
    TestResult result = TestResult.getCurrentTestResult();
    if (result == null) {
      return;
    } else {
      // waiting to avoid having 2 events at the exact same timestamp
      sleepTight(1);
      result.add(log);
    }
  }
}
