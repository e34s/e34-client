package helper;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.xml.XmlSuite;

public class ParallelMethods implements ISuiteListener {


  @Override
  public void onStart(ISuite suite) {
    suite.getXmlSuite().setParallel(XmlSuite.ParallelMode.METHODS);
  }

  @Override
  public void onFinish(ISuite suite) {

  }
}
