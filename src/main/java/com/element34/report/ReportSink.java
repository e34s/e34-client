package com.element34.report;

import com.element34.test.TestResult;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ReportSink {

  private final static Logger logger = LoggerFactory.getLogger(ReportSink.class);

  private List<TestResult> results = new ArrayList();
  private static ReportSink SINK = new ReportSink();


  public static synchronized void addResult(TestResult result) {
    SINK.results.add(result);
  }

  public static synchronized void generateReport() {
    logger.warn(SINK.results.toString());
  }

}
