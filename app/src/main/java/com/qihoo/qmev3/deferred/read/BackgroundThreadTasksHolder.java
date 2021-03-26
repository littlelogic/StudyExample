package com.qihoo.qmev3.deferred.read;

import android.os.AsyncTask;
import android.os.Looper;

import com.qihoo.qmev3.deferred.Deferred;
import com.qihoo.qmev3.deferred.SameThreadTasksHolder;

public class BackgroundThreadTasksHolder<T> extends SameThreadTasksHolder<T> {

  public BackgroundThreadTasksHolder(Deferred<T> dfd) {
    super(dfd);
  }

  @Override
  protected void emit(final Deferred<T> dfd, final Task<T> task) {
    execute(new Runnable() {
      @Override
      public void run() {
        BackgroundThreadTasksHolder.super.emit(dfd, task);
      }
    });
  }

  @Override
  protected void emitNotify(final Deferred<T> dfd, final Task<T> task) {
    throw new RuntimeException("notify only be called in UI thread");
  }

  protected void emitNotifyOnce(Deferred<T> dfd, Task<T> task) {
    throw new RuntimeException("notify must be called in UI thread");
  }

  @Override
  protected void emitDone(final Deferred<T> dfd, final Task<T> task) {
    execute(new Runnable() {
      @Override
      public void run() {
        BackgroundThreadTasksHolder.super.emitDone(dfd, task);
      }
    });
  }

  @Override
  protected void emitFail(final Deferred<T> dfd, final Task<Throwable> task) {
    execute(new Runnable() {
      @Override
      public void run() {
        BackgroundThreadTasksHolder.super.emitFail(dfd, task);
      }
    });
  }

  private void execute(Runnable runnable) {
    if (isMainLoop()) {
      executeOnAsyncTask(runnable);
    } else {
      runnable.run();
    }
  }

  private boolean isMainLoop() {
    return Looper.getMainLooper() == Looper.myLooper();
  }

  private void executeOnAsyncTask(final Runnable runnable) {
    new AsyncTask<Void, Void, Void>() {

      @Override
      protected Void doInBackground(Void... voids) {
        runnable.run();
        return null;
      }
    }.execute();
  }
}
