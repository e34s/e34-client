package junit;

import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by freynaud on 31/08/2017.
 */
public class Inheritance {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Before
  public void setup() {
    logger.info("start");
  }


  @After
  public void teardown() {
    logger.info("stop");
  }
}
