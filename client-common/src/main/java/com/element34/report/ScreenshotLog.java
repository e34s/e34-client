package com.element34.report;

import com.element34.E34Settings;
import java.io.File;
import java.util.Map;

/**
 * Created by freynaud on 03/09/2017.
 */
public class ScreenshotLog extends Log {

  private final String command;
  private final String path;
  private final long duration;
  private final String result;

  public ScreenshotLog(String level, String command, long duration, String message, String result, File path) {
    super(level, message);
    this.path = (path == null ? null : E34Settings.SCREENSHOTS_FOLDER + "/" + path.getName());
    this.command = command;
    this.duration = duration;
    this.result = result;
  }

  public Map<String, Object> asMap() {
    Map<String, Object> res = super.asMap();
    res.put("path", path);
    res.put("duration", duration);
    res.put("command", command);
    res.put("result", result);
    return res;
  }
}
