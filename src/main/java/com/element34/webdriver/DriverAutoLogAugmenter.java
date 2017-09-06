package com.element34.webdriver;

import com.element34.test.TestResult;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by freynaud on 31/08/2017.
 */
public class DriverAutoLogAugmenter {

  private final static Logger logger = LoggerFactory.getLogger(DriverAutoLogAugmenter.class);


  public WebDriver augment(WebDriver base) {
    base = new Augmenter().augment(base);

    InvocationHandler handler = new DriverAutoLogHandler(base);
    List<Class> classes = new ArrayList<>();
    classes.add(WebDriver.class);

    if (base instanceof TakesScreenshot) {
      classes.add(TakesScreenshot.class);
    }
    TestResult result = TestResult.getCurrentTestResult();
    if (base instanceof RemoteWebDriver) {
      RemoteWebDriver rd = (RemoteWebDriver) base;
      result.setSessionId(rd.getSessionId().toString());
      result.addTag("browser", rd.getCapabilities().getBrowserName());
    }
    return (WebDriver) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), classes.toArray(new Class[0]), handler);
  }

//  private Method findTest() {
//    for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
//      String clazz = element.getClassName();
//      String method = element.getMethodName();
//      try {
//        Class c = Class.forName(clazz);
//        for (Method m : c.getDeclaredMethods()) {
//          if (m.getName().equals(method)) {
//            if (looksLikeATest(m)) {
//              return m;
//            }
//          }
//        }
//      } catch (Exception e) {
//        logger.error("can't find " + e.getMessage(), e);
//
//      }
//    }
//    throw new Element34Exception("Cannot find test method.");
//  }
//
//  private boolean looksLikeATest(Method m) {
//    // TODO freynaud : use reflection to not need a dependency here.
//    return m.getAnnotation(Test.class) != null /*|| m.getAnnotation(org.testng.annotations.Test.class) != null*///  }*/
}
