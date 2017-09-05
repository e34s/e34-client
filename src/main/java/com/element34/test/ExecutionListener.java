package com.element34.test;

import java.util.logging.Level;
import java.util.logging.LogManager;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class ExecutionListener {

  public void onStart() {
    LogManager.getLogManager().reset();
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();
    java.util.logging.Logger.getLogger("global").setLevel(Level.FINEST);
  }
}
