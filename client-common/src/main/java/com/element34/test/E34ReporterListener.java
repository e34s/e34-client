package com.element34.test;


import com.element34.Hardcoded;
import com.element34.report.ReportSink;
import java.io.File;

public class E34ReporterListener {

  public void generate() {
    File dest = new File(Hardcoded.DEST);
    ReportSink.generateReport(dest);
  }
}
