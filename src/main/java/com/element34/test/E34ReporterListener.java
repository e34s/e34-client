package com.element34.test;


import com.element34.report.ReportSink;
import java.io.File;

public class E34ReporterListener {

  public void generate() {
    File dest = new File("/Users/freynaud/Documents/workspace/e34_report_fe/src/data/");
    ReportSink.generateReport(dest);
  }
}
