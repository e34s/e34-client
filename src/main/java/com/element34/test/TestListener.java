package com.element34.test;


import com.element34.report.ReportSink;

public class TestListener {


  public void onTestStarts(String className, String packageName, String methodName, Object... args) {
    TestResult result = TestResult.create();
    result.setPackage(packageName);
    result.setClazz(className);
    result.setMethod(methodName);

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
    ReportSink.addResult(TestResult.getCurrentTestResult());
    TestResult.close();
  }
}
