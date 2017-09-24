package com.element34.webdriver;

import com.element34.E34Settings;
import com.element34.report.EventSink;
import com.element34.report.ScreenshotLog;
import com.element34.test.E34TestListener;
import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;


public class DriverAutoLogHandler implements InvocationHandler {


  private final WebDriver base;
  private final String sessionId;
  private final File screenshots;


  public DriverAutoLogHandler(WebDriver base) {
    this.base = base;
    sessionId = ((RemoteWebDriver) base).getSessionId().toString();
    screenshots = new File(E34TestListener.DEST, E34Settings.SCREENSHOTS_FOLDER);
    screenshots.mkdirs();
  }

  String getSessionId() {
    return sessionId;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

    Object res = null;

    if (args != null) {
      for (int i = 0; i < args.length; i++) {
        Object o = args[i];
        args[i] = extractElementFromProxy(o);
      }
    }

    long start = System.currentTimeMillis();
    long duration;
    try {
      res = method.invoke(base, args);
      duration = System.currentTimeMillis() - start;
    } catch (Throwable t) {
      Throwable cause = t.getCause();
      duration = System.currentTimeMillis() - start;
      File ss = null;
      try {
        String name = UUID.randomUUID().toString().substring(0, 10);
        ss = new File(screenshots, sessionId + "_" + name + "_screen.png");
        if (base instanceof TakesScreenshot) {
          File tmp = ((TakesScreenshot) base).getScreenshotAs(OutputType.FILE);
          tmp.renameTo(ss);
        }
      } catch (Exception ignore) {

      }
      EventSink.add(new ScreenshotLog("WARN", method.getName(), duration, "threw : " + cause.getMessage(), null, ss));
      throw t.getCause();
    }
    File screenshot = null;
    switch (method.getName()) {
      case "findElement":
        WebElement el = (WebElement) res;
        screenshot = takeScreenhotForCommand();
        EventSink.add(new ScreenshotLog("INFO", method.getName(), duration, "success", res.toString(), screenshot));
        return new WebElementAutoLogAugmenter().augment(el, base);
      case "findElements":
        List<WebElement> els = (List<WebElement>) res;
        screenshot = takeScreenhotForCommand();
        EventSink.add(new ScreenshotLog("INFO", method.getName(), duration, "success", res.toString(), screenshot));
        List<WebElement> augmenteds = new ArrayList<>();
        for (WebElement e : els) {
          augmenteds.add(new WebElementAutoLogAugmenter().augment(e, base));
        }
        EventSink.add(new ScreenshotLog("INFO", method.getName(), duration, "success", res.toString(), screenshot));
        return augmenteds;
      case "get":
        screenshot = takeScreenhotForCommand();
        EventSink.add(new ScreenshotLog("INFO", method.getName(), duration, "success", null, screenshot));
        return res;
      case "getScreenshotAs":
        if (res instanceof File) {
          File f = (File) res;
          File dest = new File(screenshots, sessionId + "_screen.png");
          Files.copy(Paths.get(f.getAbsolutePath()), Paths.get(dest.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
          EventSink.add(new ScreenshotLog("INFO", method.getName(), duration, "success", null, dest));
        } else {
          System.err.println("Only OutputType.File is supported.");
        }
        return res;
      default:
        EventSink.add(new ScreenshotLog("INFO", method.getName(), duration, "some message", (res == null ? "void" : res.toString()), null));
        return res;
    }
  }


  private File takeScreenhotForCommand() {
    String name = UUID.randomUUID().toString().substring(0, 10);
    File ss = new File(screenshots, sessionId + "_" + name + "_screen.png");
    File tmp = ((TakesScreenshot) base).getScreenshotAs(OutputType.FILE);
    tmp.renameTo(ss);
    return ss;
  }

  private Object extractElementFromProxy(Object o) {
    if (o instanceof WebElement) {
      if (o instanceof WebElementWrapper) {
        return ((WebElementWrapper) o).getWrappedElement();
      }
    }

    if (o instanceof Object[]) {
      Object[] objects = (Object[]) o;
      for (int i = 0; i < objects.length; i++) {
        objects[i] = extractElementFromProxy(objects[i]);
      }
    }
    return o;
  }
}
