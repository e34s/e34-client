package com.element34.junit;

import com.element34.test.E34TestListener;
import java.lang.reflect.Parameter;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeleniumTestWatcher extends TestWatcher {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final E34TestListener listener = new E34TestListener();

  public SeleniumTestWatcher() {
  }


  @Override
  protected void starting(Description description) {

    String[] pieces = description.getClassName().split("\\.");
    String clazz = pieces[pieces.length - 1];
    String packageName = description.getClassName().replace("." + clazz, "");
    listener.onTestStarts(clazz, packageName, description.getMethodName(),new Object[0]);
  }


  @Override
  protected void succeeded(Description description) {
    logger.warn(description.getDisplayName() + " " + "success!");
   listener.onTestPassed();
  }

  @Override
  protected void failed(Throwable e, Description description) {
    logger.warn(description.getDisplayName() + " " + e.getClass().getSimpleName());
    listener.onTestFailed(e);

  }

  @Override
  protected void finished(Description description) {
    listener.onTestFinishes();
  }

}
