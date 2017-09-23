package com.element34.stream;

import com.element34.report.ReportSink;
import java.io.IOException;
import java.util.function.Consumer;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

/**
 * Created by freynaud on 06/09/2017.
 */
public class EventSocket extends WebSocketAdapter {


  private Consumer<Event> consumer = create();


  private Consumer<Event> create() {
    return (event -> {
      send(event.toString());
    });
  }


  private synchronized void send(String text) {
    try {
      getRemote().sendString(text);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onWebSocketConnect(Session sess) {
    super.onWebSocketConnect(sess);
  }

  @Override
  public void onWebSocketText(String message) {
    super.onWebSocketText(message);
    ReportSink.addListener(consumer);
  }

  @Override
  public void onWebSocketClose(int statusCode, String reason) {
    super.onWebSocketClose(statusCode, reason);
    ReportSink.removeListener(consumer);
  }


  @Override
  public void onWebSocketError(Throwable cause) {
    super.onWebSocketError(cause);
    ReportSink.removeListener(consumer);
  }
}
