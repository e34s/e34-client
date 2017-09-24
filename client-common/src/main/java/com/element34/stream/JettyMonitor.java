package com.element34.stream;

import com.element34.E34Settings;
import com.element34.report.ReportSink;
import java.awt.Desktop;
import java.net.URI;
import java.util.concurrent.TimeoutException;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;


public class JettyMonitor {

  public static String SERVER_LIVE_REPORT = "unknown";
  private final Server server;

  public JettyMonitor() {
    server = new Server(E34Settings.E34_PORT);
    ServletContextHandler root = new ServletContextHandler(ServletContextHandler.SESSIONS);
    server.setHandler(root);
    root.addServlet(EventServlet.class, "/e34/ws/*");
  }


  public void start() {
    try {
      server.start();
    } catch (Exception e) {
      return;
    }

    try {
      ReportSink.currentRun().awaitConsumers(5);
    } catch (TimeoutException e) {
      spawnBrowser();
    }

    try {
      ReportSink.currentRun().awaitConsumers(5);
    } catch (TimeoutException e) {
      return;
    }
  }

  public void stop() {
    try {
      server.stop();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  private void spawnBrowser() {
    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
      try {
        desktop.browse(URI.create(SERVER_LIVE_REPORT));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
