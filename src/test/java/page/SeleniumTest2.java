package page;

import com.element34.webdriver.DriverAutoLogAugmenter;
import java.net.MalformedURLException;
import java.net.URL;
import junit.Settings;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Created by freynaud on 31/08/2017.
 */
public class SeleniumTest2 extends TestBase{

  private WebDriver driver;
  //@Rule
  //public TestWatcher watcher = new SeleniumTestWatcher();

  @Before
  public void setup() throws MalformedURLException {
    driver = new RemoteWebDriver(new URL(Settings.getHub()), DesiredCapabilities.chrome());
  }

  @After
  public void teardown() {
    driver.quit();
  }


  @Test
  public void chrome2() throws MalformedURLException, InterruptedException {
    driver = new DriverAutoLogAugmenter().augment(driver);
    //Thread.sleep(20000);

  }


  @Test
  public void chrome3() throws MalformedURLException, InterruptedException {
    driver = new DriverAutoLogAugmenter().augment(driver);
    //Thread.sleep(20000);
  }
}
