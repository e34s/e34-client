package com.element34.junit;

import com.element34.webdriver.DriverAutoLogAugmenter;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Created by freynaud on 31/08/2017.
 */
public class SeleniumTest extends TestBase {

  @Test
  public void chrome() throws MalformedURLException, InterruptedException {
    WebDriver driver = new RemoteWebDriver(new URL(Settings.getHub()), DesiredCapabilities.chrome());

    driver = new DriverAutoLogAugmenter().augment(driver);

    driver.get("about:policy");
    WebElement reload = driver.findElement(By.id("reload-policies"));
    TakesScreenshot ts = (TakesScreenshot) driver;
    File f = ts.getScreenshotAs(OutputType.FILE);
    System.out.println(reload.getText());
    //Thread.sleep(20000);
    driver.quit();
  }
}
