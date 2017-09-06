package com.element34.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * Created by freynaud on 06/09/2017.
 */
public class StreamableList<T extends Event> extends ArrayList<T> {

  private final List<Consumer<Event>> listeners = new CopyOnWriteArrayList<>();

  public synchronized void addListener(Consumer<Event> listener) {
    for (T item : this) {
      listener.accept(item);
    }
    listeners.add(listener);
  }


  @Override
  public synchronized boolean add(T t) {
    boolean res = super.add(t);
    for (Consumer<Event> consumer : listeners) {
      consumer.accept(t);
    }
    return res;
  }

  public synchronized void removeListener(Consumer<Event> consumer) {
    listeners.remove(consumer);
  }
}
