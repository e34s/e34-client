package com.element34.test;

import static com.element34.test.Run.sleepTight;

import com.element34.report.ReportSink;
import com.element34.stream.JettyMonitor;
import java.util.logging.Level;
import java.util.logging.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class E34ExecutionListener {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final JettyMonitor monitor = new JettyMonitor();

  public void onStart() {
    LogManager.getLogManager().reset();
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();
    java.util.logging.Logger.getLogger("global").setLevel(Level.FINEST);

    // ReportSink.addListener((event) -> logger.info(event.toString()));

    monitor.start();
  }

  public void stop() {
    sleepTight(5000);
    monitor.stop();
  }
}
