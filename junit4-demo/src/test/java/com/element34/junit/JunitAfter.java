package com.element34.junit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by freynaud on 31/08/2017.
 */
public class JunitAfter {


  @Before
  public void setup() {
  }


  @Test
  public void testok() throws Exception {
  }

  @Test
  public void testfail() throws Exception {
    throw new Exception("Error");
  }

  @After
  public void teardown() {
  }
}
