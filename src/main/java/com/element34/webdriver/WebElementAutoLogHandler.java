package com.element34.webdriver;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by freynaud on 31/08/2017.
 */
public class WebElementAutoLogHandler implements InvocationHandler {

  private final WebElement base;
  private final Logger logger = LoggerFactory.getLogger(this.getClass());


  public WebElementAutoLogHandler(WebElement base) {
    this.base = base;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    logger.info("WE:before invoking " + method.getName() + "(" + args + ")");
    Object res = method.invoke(base, args);
    logger.info("WE:will return " + res);
    return res;
  }
}
