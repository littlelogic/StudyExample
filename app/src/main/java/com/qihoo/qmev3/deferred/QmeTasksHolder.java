package com.qihoo.qmev3.deferred;

import com.qihoo.qmev3.deferred.out.QETaskController;
import com.qihoo.qmev3.deferred.read.Task;

public class QmeTasksHolder<T> extends SameThreadTasksHolder<T> {
  public QmeTasksHolder(Deferred<T> dfd) {
    super(dfd);
  }

  @Override
  protected void emit(final Deferred<T> dfd, final Task<T> task) {
    execute(new Runnable() {
      @Override
      public void run() {
        QmeTasksHolder.super.emit(dfd, task);
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
        QmeTasksHolder.super.emitDone(dfd, task);
      }
    });
  }

  @Override
  protected void emitFail(final Deferred<T> dfd, final Task<Throwable> task) {
    execute(new Runnable() {
      @Override
      public void run() {
        QmeTasksHolder.super.emitFail(dfd, task);
      }
    });
  }

  private void execute(Runnable runnable) {
    QETaskController.getInstance().runOnly(runnable);
  }
}

