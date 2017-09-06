package com.element34.test;


import com.element34.report.ReportSink;

public class E34TestListener {


  public void onTestStarts(String className, String packageName, String methodName, Object[] params) {
    TestResult result = TestResult.create();
    // done in constructor.
    //result.setStatus(TestStatus.STARTED);
    result.setPackage(packageName);
    result.setClazz(className);
    result.setMethod(methodName);
    result.setParams(params);
  }

  public void onTestPassed() {
    TestResult result = TestResult.getCurrentTestResult();
    result.setStatus(TestStatus.PASSED);
  }


  public void onTestFailed(Throwable throwable) {
    TestResult result = TestResult.getCurrentTestResult();
    result.setStatus(TestStatus.FAILED);
    result.setThrowable(throwable);
  }


  public void onTestFinishes() {
    TestResult.close();
  }

  public void onTestSkipped(Throwable throwable) {
    TestResult result = TestResult.getCurrentTestResult();
    result.setStatus(TestStatus.SKIPPED);
    result.setThrowable(throwable);
  }
}
