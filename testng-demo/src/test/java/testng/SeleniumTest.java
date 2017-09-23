package testng;

import static com.element34.test.Run.sleepTight;

import com.element34.webdriver.DriverAutoLogAugmenter;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(ParallelMethods.class)
public class SeleniumTest {


  @Test
  public void chrome() throws MalformedURLException, InterruptedException {
    DesiredCapabilities chrome = DesiredCapabilities.chrome();
    chrome.setCapability("video", true);
    WebDriver driver = new RemoteWebDriver(new URL(Settings.getHub()), chrome);

    driver = new DriverAutoLogAugmenter().augment(driver);

    driver.get("about:policy");
    WebElement reload = driver.findElement(By.id("reload-policies"));
    TakesScreenshot ts = (TakesScreenshot) driver;
    File f = ts.getScreenshotAs(OutputType.FILE);
    System.out.println(reload.getText());
    driver.quit();
  }

//  @Test(invocationCount = 100)
//  public void lots(){
//
//  }

  @DataProvider(name = "browsers", parallel = true)
  public Object[][] browsers() {
    return new Object[][]{
        {DesiredCapabilities.firefox()},
        {DesiredCapabilities.chrome()},
        {DesiredCapabilities.edge()},
        {DesiredCapabilities.internetExplorer()}
    };
  }

  @Test(dataProvider = "browsers")
  public void dataProvider(DesiredCapabilities cap) throws MalformedURLException {
    WebDriver driver = new RemoteWebDriver(new URL(Settings.getHub()), cap);
    driver = new DriverAutoLogAugmenter().augment(driver);
    driver.get("https://www.google.com/ncr");
    driver.quit();
  }


  @Test(dataProvider = "browsers")
  public void selectionChMultiVideo(DesiredCapabilities cap) throws MalformedURLException {
    cap.setCapability("video", true);
    WebDriver driver = new RemoteWebDriver(new URL(Settings.getHub()), cap);
    driver.manage().window().setSize(new Dimension(1900, 1000));

    driver = new DriverAutoLogAugmenter().augment(driver);

//    driver.get("http://static.element34.net/e34/");
    driver.get("http://static.element34.net/the-internet/");
    high("A/B Testing", driver);
    high("Basic Auth", driver);
    high("Broken Images", driver);
    high("Challenging DOM", driver);
    high("Checkboxes", driver);
    high("Context Menu", driver);
    high("Disappearing Elements", driver);
    high("Drag and Drop", driver);
    high("Dropdown", driver);
    high("Dynamic Content", driver);
    high("Dynamic Controls", driver);
    driver.quit();

  }

  @Test
  public void selectionCh() throws MalformedURLException {
    DesiredCapabilities cap = DesiredCapabilities.chrome();
    cap.setCapability("video", true);
    WebDriver driver = new RemoteWebDriver(new URL(Settings.getHub()), cap);
    driver.manage().window().setSize(new Dimension(1900, 1000));

    driver = new DriverAutoLogAugmenter().augment(driver);

//    driver.get("http://static.element34.net/e34/");
    driver.get("http://static.element34.net/the-internet/");
    high("A/B Testing", driver);
    high("Basic Auth", driver);
    high("Broken Images", driver);
    high("Challenging DOM", driver);
    high("Checkboxes", driver);
    high("Context Menu", driver);
    high("Disappearing Elements", driver);
    high("Drag and Drop", driver);
    high("Dropdown", driver);
    high("Dynamic Content", driver);
    high("Dynamic Controls", driver);
    driver.quit();

  }

  public static void highlight(JavascriptExecutor d, WebElement el) {
    /*
    function highlight(obj){
   var orig = obj.style.color;
   obj.style.color = '#f00';
   setTimeout(function(){
        obj.style.color = orig;
   }, 1000);
}
    */
    d.executeScript("arguments[0].style.border='3px solid red'", el);
  }


  private void high(String text, WebDriver driver) {
    WebElement element = driver.findElement(By.linkText(text));
    highlight((JavascriptExecutor) driver, element);
    element.getText();
    element.isDisplayed();
    sleepTight(250);
  }


  @Test
  public void error() {
    Assert.assertEquals("houlala", "Houlala");
  }


  @Test
  public void someExceptions() throws MalformedURLException {
    DesiredCapabilities cap = DesiredCapabilities.chrome();
    cap.setCapability("video", true);
    WebDriver driver = new RemoteWebDriver(new URL(Settings.getHub()), cap);

    driver = new DriverAutoLogAugmenter().augment(driver);

    try {
      driver.get("http://static.element34.net/the-internet/");
      high("A/B Testing", driver);

      try {
        high("Basic Auth2", driver);
      } catch (Exception e) {
        // ignore;
      }
      high("Basic Auth3", driver);
    } finally {
      driver.quit();
    }

  }

}
