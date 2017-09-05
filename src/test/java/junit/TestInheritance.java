package junit;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by freynaud on 31/08/2017.
 */
public class TestInheritance extends Inheritance {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Test
  public void test() {
    logger.info("the test");
  }
}
