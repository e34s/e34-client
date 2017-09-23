package com.element34.junit;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Created by freynaud on 05/09/2017.
 */
@RunWith(Parameterized.class)
public class SeleniumTest3 extends TestBase{


  @Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][] {
        { 0, 0 }, { 1, 1 }, { 2, 1 },{ 2, 1 }, { 3, 2 }, { 4, 3 }, { 5, 5 }, { 6, 8 }
    });
  }

  private int fInput;

  private int fExpected;


  public SeleniumTest3(int input, int expected) {
    fInput= input;
    fExpected= expected;
  }

  @Test
  public void test() {
    assertEquals(fExpected,  fInput);
  }
}
