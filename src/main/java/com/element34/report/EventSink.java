package com.element34.report;

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
    result.add(log);
  }
}
