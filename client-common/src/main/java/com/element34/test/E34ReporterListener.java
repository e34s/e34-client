package com.element34.test;


import com.element34.E34Settings;
import com.element34.report.ReportSink;
import java.io.File;

public class E34ReporterListener {

  public void generate() {
    File dest = new File(E34TestListener.DEST);
    ReportSink.generateReport(dest);
  }
}
