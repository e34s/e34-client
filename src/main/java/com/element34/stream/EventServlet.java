package com.element34.stream;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by freynaud on 06/09/2017.
 */
public class EventServlet extends WebSocketServlet {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Override
  public void configure(WebSocketServletFactory factory) {
    //factory.register(EventSocket.class);
    factory.getPolicy().setIdleTimeout(10 * 60 * 1000);

    //final WebSocketCreator creator = factory.getCreator();

    // Set your custom Creator
    factory.setCreator(new WebSocketCreator() {
      @Override
      public Object createWebSocket(ServletUpgradeRequest servletUpgradeRequest, ServletUpgradeResponse servletUpgradeResponse) {
        String path = servletUpgradeRequest.getRequestURI().toASCIIString();
        return new EventSocket();
      }
    });
  }
}
