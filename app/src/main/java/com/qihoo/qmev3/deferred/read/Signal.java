package com.qihoo.qmev3.deferred.read;

public class Signal {
  public void hangup() {
    synchronized (object) {
      try {
        object.wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public void raise() {
    synchronized (object) {
      object.notify();
    }
  }

  public void raiseAll() {
    synchronized (object) {
      object.notifyAll();
    }
  }

  // Don't call wait() on constant String's or global objects
  private final Object object = new Object();
}
