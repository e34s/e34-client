package com.element34.webdriver;

import com.element34.test.TestResult;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Created by freynaud on 31/08/2017.
 */
public class DriverAutoLogAugmenter {


  public WebDriver augment(WebDriver base) {
    base = new Augmenter().augment(base);

    InvocationHandler handler = new DriverAutoLogHandler(base);
    List<Class> classes = new ArrayList<>();
    classes.add(WebDriver.class);

    if (base instanceof TakesScreenshot) {
      classes.add(TakesScreenshot.class);
    }
    if (base instanceof JavascriptExecutor) {
      classes.add(JavascriptExecutor.class);

    }
    TestResult result = TestResult.getCurrentTestResult();
    if (result == null) {
      result = TestResult.create();
    }

    if (base instanceof RemoteWebDriver) {
      RemoteWebDriver rd = (RemoteWebDriver) base;
      result.setSessionId(rd.getSessionId().toString());
      result.addTag("browser", rd.getCapabilities().getBrowserName());
      String version = rd.getCapabilities().getVersion();
      if (version == null || version.isEmpty()) {
        Object o = rd.getCapabilities().getCapability("browserVersion");
        if (o != null) {
          version = o.toString();
        }
      }
      result.addTag("version", version);

      boolean e34 = (boolean) (rd.getCapabilities().getCapability("e34"));
      if (e34) {
        result.addTag("selenium-box", true);
        String hubBase = null;
        CommandExecutor exec = rd.getCommandExecutor();
        if (exec instanceof HttpCommandExecutor) {
          URL url = ((HttpCommandExecutor) exec).getAddressOfRemoteServer();
          hubBase = url.toExternalForm().replace("/wd/hub", "");
          result.addTag("e34_hub", hubBase);
          Object video = (rd.getCapabilities().getCapability("videoEnabled"));

          if (video != null && (boolean) video) {
            result.setVideo(hubBase + "/videos/" + rd.getSessionId() + ".mp4");
          }
        }
      }
    }
    result.setStart(System.currentTimeMillis());
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
