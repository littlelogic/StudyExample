package com.qihoo.qmev3.deferred.read;

import com.qihoo.qmev3.deferred.Deferred;
import com.qihoo.qmev3.deferred.SameThreadTasksHolder;

public class AsyncTasksHolder<T> extends SameThreadTasksHolder<T> {
  public AsyncTasksHolder(Deferred<T> dfd) {
    super(dfd);
  }

  @Override
  protected void emit(final Deferred<T> dfd, final Task<T> task) {
    execute(new Runnable() {
      @Override
      public void run() {
        AsyncTasksHolder.super.emit(dfd, task);
      }
    });
  }

  @Override
  protected void emitNotify(final Deferred<T> dfd, final Task<T> task) {
    throw new RuntimeException("notify must be called in UI thread");
  }

  protected void emitNotifyOnce(Deferred<T> dfd, Task<T> task) {
    throw new RuntimeException("notify must be called in UI thread");
  }

  @Override
  protected void emitDone(final Deferred<T> dfd, final Task<T> task) {
    execute(new Runnable() {
      @Override
      public void run() {
        AsyncTasksHolder.super.emitDone(dfd, task);
      }
    });
  }

  @Override
  protected void emitFail(final Deferred<T> dfd, final Task<Throwable> task) {
    execute(new Runnable() {
      @Override
      public void run() {
        AsyncTasksHolder.super.emitFail(dfd, task);
      }
    });
  }

  private void execute(Runnable runnable) {
    new Thread(runnable).start();
  }
}
