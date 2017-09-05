package com.element34.report;

/**
 * Created by freynaud on 03/09/2017.
 */
public class ScreenshotLog extends Log {

  private final String path;

  public ScreenshotLog(String level, String message, String path) {
    super(level, message);
    this.path = path;
  }
}
