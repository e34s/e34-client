package com.element34.junit;

import com.element34.junit.SeleniumSuiteWatcher;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.ParallelComputer;
import org.junit.rules.TestRule;
import org.junit.runner.JUnitCore;

/**
 * Created by freynaud on 03/09/2017.
 */
public class ParallelRun {

  @ClassRule
  public static TestRule watcher = new SeleniumSuiteWatcher();

  @Test
  public void runSuite(){
    //JUnitCore junit = new JUnitCore();
    //junit.addListener();

    //JUnitCore.runClasses(ParallelComputer.classes(), new Class[]{SeleniumTest.class, SeleniumTest2.class});
    JUnitCore.runClasses(new ParallelComputer(true, true), new Class[]{SeleniumTest.class, SeleniumTest2.class, SeleniumTest3.class});
  }
}
