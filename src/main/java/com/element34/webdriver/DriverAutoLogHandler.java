package com.element34.webdriver;

import com.element34.report.EventSink;
import com.element34.report.Log;
import com.element34.report.ScreenshotLog;
import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by freynaud on 31/08/2017.
 */
public class DriverAutoLogHandler implements InvocationHandler {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final WebDriver base;
  private final String sessionId;
  private final File screenshots;


  public DriverAutoLogHandler(WebDriver base) {
    this.base = base;
    sessionId = ((RemoteWebDriver) base).getSessionId().toString();
    URL url = ((HttpCommandExecutor) ((RemoteWebDriver) base).getCommandExecutor()).getAddressOfRemoteServer();
    logger.info("augmenting session " + sessionId + " on " + url.toExternalForm());
    screenshots = new File("screenshots");
    screenshots.mkdirs();
    logger.info("screenshots : " + screenshots.getAbsolutePath());
  }

  String getSessionId() {
    return sessionId;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

    EventSink.add(new Log("INFO", "before " + method.getName()));
    Object res = null;
    try {
      res = method.invoke(base, args);
    } catch (Throwable t) {
      throw t.getCause();
    }

    switch (method.getName()) {
      case "findElement":
        logger.info("augmenting element");
        WebElement el = (WebElement) res;
        EventSink.add(new Log("INFO", "after " + method.getName() + (res == null ? "" : res.toString())));
        return new WebElementAutoLogAugmenter().augment(el);
      case "getScreenshotAs":
        if (res instanceof File) {
          File f = (File) res;
          File dest = new File(screenshots, sessionId + "_screen.png");
          Files.copy(Paths.get(f.getAbsolutePath()), Paths.get(dest.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
          EventSink.add(new ScreenshotLog("INFO", "Screenshot", dest.getPath()));
        } else {
          logger.warn("Only OutputType.File is supported.");
        }
        return res;
      default:
        EventSink.add(new Log("INFO", "after " + method.getName() + (res == null ? "" : res.toString())));
        return res;
    }
  }
}
