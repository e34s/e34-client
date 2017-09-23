package testng;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by freynaud on 31/08/2017.
 */
public class Page {

  @FindBy(id = "reload-policies")
  private WebElement reload;


  public String getText() {
    return reload.getText();
  }
}
