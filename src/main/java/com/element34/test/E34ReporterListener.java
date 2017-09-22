package com.element34.test;


import static com.element34.Hardcoded.DEST;

import com.element34.report.ReportSink;
import java.io.File;

public class E34ReporterListener {

  public void generate() {
    File dest = new File(DEST);
    ReportSink.generateReport(dest);
  }
}
