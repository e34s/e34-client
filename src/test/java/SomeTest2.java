import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * Created by freynaud on 31/08/2017.
 */
public class SomeTest2 {

  private static String watchedLog;


  @Rule
  public TestWatcher watchman= new TestWatcher() {
    @Override
    protected void failed(Throwable e, Description description) {
      watchedLog+= description + "\n";
    }

    @Override
    protected void succeeded(Description description) {
      watchedLog+= description + " " + "success!\n";
    }
  };

  @Test
  public void test(){

  }

  @AfterClass
  public static void teardown(){
    System.err.println(watchedLog);
  }
}
