package com.element34.junit;

import com.element34.junit.SeleniumSuiteWatcher;
import org.junit.ClassRule;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by freynaud on 31/08/2017.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({/*SeleniumTest.class, SeleniumTest2.class, */SeleniumTest3.class})
public class JunitSuite {

  @ClassRule
  public static TestRule watcher = new SeleniumSuiteWatcher();
}

