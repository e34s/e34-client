package com.element34.webdriver;

import com.element34.E34Settings;
import com.element34.report.EventSink;
import com.element34.report.ScreenshotLog;
import com.element34.test.E34TestListener;
import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;


public class WebElementAutoLogHandler implements InvocationHandler {

  private final WebElement base;
  private final WebDriver driver;
  private final File screenshots;
  private final String sessionId;

  public WebElementAutoLogHandler(WebElement base, WebDriver driver) {
    sessionId = ((RemoteWebDriver) driver).getSessionId().toString();
    this.base = base;
    this.driver = driver;
    screenshots = new File(E34TestListener.DEST, E34Settings.SCREENSHOTS_FOLDER);
    screenshots.mkdirs();
    //logger.info("screenshots : " + screenshots.getAbsolutePath());
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

    if (method.getName().equals("getWrappedElement")) {
      return base;
    }
    long start = System.currentTimeMillis();
    long duration;

    Object res;
    try {
      res = method.invoke(base, args);
      duration = System.currentTimeMillis() - start;
    } catch (Throwable t) {
      Throwable cause = t.getCause();
      duration = System.currentTimeMillis() - start;
      try {
        String name = UUID.randomUUID().toString().substring(0, 10);
        File ss = new File(screenshots, sessionId + "_" + name + "_screen.png");
        if (base instanceof TakesScreenshot) {
          File tmp = ((TakesScreenshot) base).getScreenshotAs(OutputType.FILE);
          tmp.renameTo(ss);
        }
        EventSink.add(new ScreenshotLog("WARN", method.getName(), duration, "threw : " + cause.getMessage(), null, ss));
      } catch (Exception ignore) {

      }
      EventSink.add(new ScreenshotLog("INFO", method.getName(), duration, " threw " + cause.getMessage(), null, null));
      throw cause;
    }
    EventSink.add(new ScreenshotLog("INFO", method.getName(), duration, "some message", (res == null ? "void" : res.toString()), null));
    return res;
  }
}
