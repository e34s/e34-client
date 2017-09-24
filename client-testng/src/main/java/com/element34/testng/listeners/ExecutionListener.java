package com.element34.testng.listeners;

import com.element34.test.E34ExecutionListener;
import java.io.File;
import org.testng.IExecutionListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;


public class ExecutionListener implements IExecutionListener, ISuiteListener {


  E34ExecutionListener e34ExecutionListener = new E34ExecutionListener();

  @Override
  public void onExecutionStart() {

  }

  @Override
  public void onExecutionFinish() {
    e34ExecutionListener.stop();
  }

  @Override
  public void onStart(ISuite suite) {
    String out = suite.getOutputDirectory();
    File parent = new File(out).getParentFile();
    e34ExecutionListener.unzipReport(parent.getAbsoluteFile());
    e34ExecutionListener.onStart();
  }

  @Override
  public void onFinish(ISuite suite) {

  }
}
