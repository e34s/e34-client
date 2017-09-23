package testng.listeners;

import com.element34.test.E34TestListener;
import java.lang.reflect.Method;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener2;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.internal.ConstructorOrMethod;


public class TestListener implements IInvokedMethodListener2 {

  E34TestListener listener = new E34TestListener();

  @Override
  public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
    ConstructorOrMethod cm = method.getTestMethod().getConstructorOrMethod();
    if (cm.getMethod() != null && method.isTestMethod()) {
      Method m = cm.getMethod();
      String clazz = m.getDeclaringClass().getCanonicalName();
      String pack = m.getDeclaringClass().getPackage().getName();
      listener.onTestStarts(clazz, pack, m.getName(), testResult.getParameters());
    }
  }

  @Override
  public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
    if (method.isTestMethod()) {
      switch (testResult.getStatus()) {
        case ITestResult.SUCCESS:
          listener.onTestPassed();
          break;
        case ITestResult.FAILURE:
          listener.onTestFailed(testResult.getThrowable());
          break;
        case ITestResult.SKIP:
          listener.onTestSkipped(testResult.getThrowable());
          break;
      }
      listener.onTestFinishes();
    }

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
