package page;

import com.element34.junit.SeleniumTestWatcher;
import org.junit.Rule;
import org.junit.rules.TestWatcher;

/**
 * Created by freynaud on 03/09/2017.
 */
public class TestBase {

  @Rule
  public TestWatcher watcher = new SeleniumTestWatcher();

}
