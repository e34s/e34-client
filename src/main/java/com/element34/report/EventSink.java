package com.element34.report;

import static com.element34.test.Run.sleepTight;

import com.element34.test.TestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by freynaud on 03/09/2017.
 */
public class EventSink {

  private final static Logger logger = LoggerFactory.getLogger(ReportSink.class);


  public static void add(Log log) {
    TestResult result = TestResult.getCurrentTestResult();
    if (result == null) {
      logger.warn("result is null. Cannot sink event " + log);
      return;
    } else {
      // waiting to avoid having 2 events at the exact same timestamp
      sleepTight(1);
      result.add(log);
    }
  }
}
