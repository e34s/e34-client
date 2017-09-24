package com.element34.junit;

import com.element34.webdriver.DriverAutoLogAugmenter;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


public class SeleniumTest extends TestBase {

  @ClassRule
  public static TestRule watcher = new SeleniumSuiteWatcher();

  @Test
  public void chrome() throws MalformedURLException, InterruptedException {
    DesiredCapabilities chrome = DesiredCapabilities.chrome();
    chrome.setCapability("video",true);
    WebDriver driver = new RemoteWebDriver(new URL(Settings.getHub()), chrome);

    driver = new DriverAutoLogAugmenter().augment(driver);

    driver.get("about:policy");
    List<WebElement> reloads = driver.findElements(By.id("reload-policies"));
    TakesScreenshot ts = (TakesScreenshot) driver;
    File f = ts.getScreenshotAs(OutputType.FILE);
    System.out.println(reloads.get(0).getText());
    Thread.sleep(10000);
    driver.quit();
  }

  @Test
  public void chrome2() throws MalformedURLException, InterruptedException {
    DesiredCapabilities chrome = DesiredCapabilities.chrome();
    chrome.setCapability("video",true);
    WebDriver driver = new RemoteWebDriver(new URL(Settings.getHub()), chrome);

    driver = new DriverAutoLogAugmenter().augment(driver);

    driver.get("about:policy");
    List<WebElement> reloads = driver.findElements(By.id("reload-policies"));
    TakesScreenshot ts = (TakesScreenshot) driver;
    File f = ts.getScreenshotAs(OutputType.FILE);
    System.out.println(reloads.get(0).getText());
//    Thread.sleep(10000);
    driver.quit();
  }
}
