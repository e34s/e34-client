package junit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by freynaud on 31/08/2017.
 */
public class JunitAfter {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Before
  public void setup() {
    logger.info("start");
  }


  @Test
  public void testok() throws Exception {
    logger.info("the test");
  }

  @Test
  public void testfail() throws Exception {
    logger.info("the failed test");
    throw new Exception("Error");
  }

  @After
  public void teardown() {
    logger.info("stop");
  }
}
