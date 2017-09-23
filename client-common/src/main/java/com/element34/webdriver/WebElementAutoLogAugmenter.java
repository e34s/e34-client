package com.element34.webdriver;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class WebElementAutoLogAugmenter {

  public WebElement augment(WebElement base,WebDriver driver) {
    InvocationHandler handler = new WebElementAutoLogHandler(base,driver);
    return (WebElement) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{WebElement.class,WebElementWrapper.class}, handler);
  }
}
