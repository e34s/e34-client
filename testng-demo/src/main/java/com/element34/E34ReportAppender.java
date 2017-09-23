package com.element34;

import com.element34.report.EventSink;
import com.element34.report.Log;
import java.io.Serializable;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

/**
 * Created by freynaud on 24/09/2017.
 */
@Plugin(name = "MyCustomAppender", category = "Core", elementType = "appender", printObject = true)
public class E34ReportAppender extends AbstractAppender {

  @PluginFactory
  public static E34ReportAppender createAppender(@PluginAttribute("name") String name,
      @PluginAttribute("ignoreExceptions") boolean ignoreExceptions,
      @PluginElement("Layout") Layout layout,
      @PluginElement("Filters") Filter filter) {
    return new E34ReportAppender(name, filter, layout);
  }

  protected E34ReportAppender(String name, Filter filter, Layout<? extends Serializable> layout) {
    super(name, filter, layout);
  }

  protected E34ReportAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions) {
    super(name, filter, layout, ignoreExceptions);
  }

  @Override
  public void append(LogEvent logEvent) {
    String level = "INFO";
    switch (logEvent.getLevel().name()) {
      case "FATAL":
      case "ERROR":
      case "WARN":
        level = "WARN";
        break;

      case "INFO":
        level = "INFO";
        break;
      case "DEBUG":
      case "TRACE":
        level = "DEBUG";
        break;
    }
    EventSink.add(new Log(level, logEvent.getMessage().getFormattedMessage()));


  }
}
