package com.element34.testng.listeners;

import com.element34.test.E34ExecutionListener;
import org.testng.IExecutionListener;


public class ExecutionListener implements IExecutionListener {


  E34ExecutionListener e34ExecutionListener = new E34ExecutionListener();

  @Override
  public void onExecutionStart() {
    e34ExecutionListener.onStart();
  }

  @Override
  public void onExecutionFinish() {
    e34ExecutionListener.stop();
  }
}
