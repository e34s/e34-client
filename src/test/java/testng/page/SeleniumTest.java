package testng.page;

import com.element34.webdriver.DriverAutoLogAugmenter;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import junit.Settings;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

/**
 * Created by freynaud on 05/09/2017.
 */
public class SeleniumTest {


  @Test
  public void chrome() throws MalformedURLException {
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
