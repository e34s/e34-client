package com.element34.testng.listeners;

import com.element34.test.E34ReporterListener;
import java.util.List;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.xml.XmlSuite;


public class JsonReporter implements IReporter {

  E34ReporterListener listener = new E34ReporterListener();

  @Override
  public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
    listener.generate();
  }
}
