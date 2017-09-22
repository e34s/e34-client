package com.element34.report;

import com.element34.stream.Event;
import com.element34.test.Run;
import com.element34.test.TestResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ReportSink {

  private final static Logger logger = LoggerFactory.getLogger(ReportSink.class);

  private final Run run = new Run();
  private static ReportSink SINK = new ReportSink();


  public static synchronized void addResult(TestResult result) {
    SINK.run.add(result);
  }

  public static Run currentRun(){
    return SINK.run;
  }


  public static void addListener(Consumer<Event> consumer) {
    SINK.run.addListener(consumer);
  }

  public static synchronized void generateReport(File outputFolder) {
    String content = SINK.run.toString();
    outputFolder.mkdirs();
    logger.info(content);
    File data = new File(outputFolder, "data.js");
    try {
      FileWriter writer = new FileWriter(data);
      writer.write("var result_new=" + content);
      writer.close();
    } catch (IOException e) {
      logger.warn(e.getMessage(), e);
    }
  }

  public static void removeListener(Consumer<Event> consumer) {
    SINK.run.removeListener(consumer);
  }
}
