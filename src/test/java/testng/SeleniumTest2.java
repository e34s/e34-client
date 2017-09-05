package testng;

import com.element34.webdriver.DriverAutoLogAugmenter;
import java.net.MalformedURLException;
import java.net.URL;
import junit.Settings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by freynaud on 05/09/2017.
 */
public class SeleniumTest2 {

  private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
  private final Logger logger = LoggerFactory.getLogger(this.getClass());


  @BeforeMethod
  public void setup() throws MalformedURLException {
    WebDriver d = new RemoteWebDriver(new URL(Settings.getHub()), DesiredCapabilities.chrome());
    d = new DriverAutoLogAugmenter().augment(d);
    driver.set(d);
  }

  @AfterMethod
  public void teardown() {
    driver.get().quit();
    driver.remove();
  }


  @Test
  public void chrome2() throws MalformedURLException, InterruptedException {
    WebDriver d = driver.get();
    d.get("about:policy");
    d.findElement(By.id("reload-policies"));
    //Thread.sleep(20000);

  }


  @Test
  public void chrome3() throws MalformedURLException, InterruptedException {
    WebDriver d = driver.get();
    d.get("about:policy");
    d.findElement(By.id("reload-policies"));
    //Thread.sleep(20000);
  }
  @Test
  public void chromeFail() throws MalformedURLException, InterruptedException {
    WebDriver d = driver.get();
    d.get("about:policy");
    d.findElement(By.id("reload-policies2"));
    //Thread.sleep(20000);
  }
}
