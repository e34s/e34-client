package com.element34.testng.listeners;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener2;
import org.testng.ITestContext;
import org.testng.ITestResult;

/**
 * Created by freynaud on 05/09/2017.
 */
public class TestListener implements IInvokedMethodListener2 {



  @Override
  public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {

  }

  @Override
  public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {

  }

  @Override
  public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
    // no op
  }

  @Override
  public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
    // no op
  }
}
