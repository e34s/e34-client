package testng;


import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SeleniumTest3 {

  @DataProvider(name = "params")
  public Object[][] data() {
    return new Object[][]{
        {0, 0}, {1, 1}, {2, 1}, {3, 2}, {4, 3}, {5, 5}, {6, 8}
    };
  }

  @Test(dataProvider = "params")
  public void test(int input, int expected) {
    assertEquals(input, expected);
  }
}
