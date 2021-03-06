package com.element34.junit;

import com.element34.E34Settings;
import com.element34.test.E34ExecutionListener;
import com.element34.test.E34ReporterListener;
import java.io.File;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class SeleniumSuiteWatcher extends TestWatcher {

  E34ExecutionListener executionListener = new E34ExecutionListener();
  E34ReporterListener reporterListener = new E34ReporterListener();


  @Override
  protected void starting(Description desc) {
    String output = System.getProperty("reportsDirectory");
    if (output ==null){
      output = "e34report";
    }
    executionListener.unzipReport(new File(output));
    executionListener.onStart();
  }

  @Override
  protected void finished(Description desc) {
    executionListener.stop();
    reporterListener.generate();
  }
}
