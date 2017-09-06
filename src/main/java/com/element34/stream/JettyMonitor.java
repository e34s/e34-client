package com.element34.stream;

import com.element34.report.ReportSink;
import java.awt.Desktop;
import java.net.URI;
import java.util.concurrent.TimeoutException;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by freynaud on 06/09/2017.
 */
public class JettyMonitor {

  private final String SERVER_LIVE_REPORT = "http://localhost:4200";
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final Server server;

  public JettyMonitor()  {
    server = new Server(8080);
    ServletContextHandler root = new ServletContextHandler(ServletContextHandler.SESSIONS);
    server.setHandler(root);
    root.addServlet(EventServlet.class, "/e34/ws/*");
  }


  public static void main(String[] args) {
    new JettyMonitor().start();
  }
  public void start() {
    try {
      server.start();
    } catch (Exception e) {
      logger.error("Couldn't start the server publishing server " + e.getMessage());
      return;
    }

    try {
      ReportSink.currentRun().awaitConsumers(5);
    } catch (TimeoutException e) {
      logger.info("No live report up.Starting one.");
      spawnBrowser();
    }

    try {
      ReportSink.currentRun().awaitConsumers(5);
    } catch (TimeoutException e) {
      logger.error("Couldn't connect to live report page after 5 sec." + e.getMessage());
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
