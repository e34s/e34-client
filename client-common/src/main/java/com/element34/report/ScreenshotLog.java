package com.element34.report;

import com.element34.Hardcoded;
import java.io.File;

/**
 * Created by freynaud on 03/09/2017.
 */
public class ScreenshotLog extends Log {

  private final String command;
  private final String path;
  private final long duration;
  private final String result;

  public ScreenshotLog(String level, String command, long duration, String message, String result,File path) {
    super(level, message);
    this.path = ( path == null  ? null :  Hardcoded.SCREENSHOTS_FOLDER+"/"+path.getName());
    this.command = command;
    this.duration = duration;
    this.result = result;
  }
}
