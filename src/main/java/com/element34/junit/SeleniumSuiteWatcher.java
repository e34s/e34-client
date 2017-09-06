package com.element34.junit;

import com.element34.test.E34ReporterListener;
import com.element34.test.E34ExecutionListener;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by freynaud on 03/09/2017.
 */
public class SeleniumSuiteWatcher extends TestWatcher {

  E34ExecutionListener executionListener = new E34ExecutionListener();
  E34ReporterListener reporterListener = new E34ReporterListener();

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  protected void starting(Description desc) {
    executionListener.onStart();
  }

  @Override
  protected void finished(Description desc) {
    executionListener.stop();
    reporterListener.generate();
  }
}
