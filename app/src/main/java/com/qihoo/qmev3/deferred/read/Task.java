package com.qihoo.qmev3.deferred.read;

public abstract class Task<T> {
  public abstract void run(T data);
}
