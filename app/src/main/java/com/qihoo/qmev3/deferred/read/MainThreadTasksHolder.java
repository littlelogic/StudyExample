package com.qihoo.qmev3.deferred.read;

import android.os.Handler;
import android.os.Looper;

import com.qihoo.qmev3.deferred.Deferred;
import com.qihoo.qmev3.deferred.SameThreadTasksHolder;

public class MainThreadTasksHolder<T> extends SameThreadTasksHolder<T> {
  private static final Handler HANDLER = new Handler(Looper.getMainLooper());

  public MainThreadTasksHolder(Deferred<T> dfd) {
    super(dfd);
  }

  @Override
  protected void emit(final Deferred<T> dfd, final Task<T> task) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
      super.emit(dfd, task);
    } else {
      HANDLER.post(new Runnable() {
        @Override
        public void run() {
          MainThreadTasksHolder.super.emit(dfd, task);
        }
      });
    }
  }

  @Override
  protected void emitNotify(final Deferred<T> dfd, final Task<T> task) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
      super.emitNotify(dfd, task);
    } else {
      HANDLER.post(new Runnable() {
        @Override
        public void run() {
          MainThreadTasksHolder.super.emitNotify(dfd, task);
        }
      });
    }
  }

  @Override
  protected void emitNotifyOnce(final Deferred<T> dfd, final Task<T> task) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
      super.emitNotifyOnce(dfd, task);
    } else {
      HANDLER.post(new Runnable() {
        @Override
        public void run() {
          MainThreadTasksHolder.super.emitNotifyOnce(dfd, task);
        }
      });
    }
  }

  @Override
  protected void emitDone(final Deferred<T> dfd, final Task<T> task) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
      super.emitDone(dfd, task);
    } else {
      HANDLER.post(new Runnable() {
        @Override
        public void run() {
          MainThreadTasksHolder.super.emitDone(dfd, task);
        }
      });
    }
  }

  @Override
  protected void emitFail(final Deferred<T> dfd, final Task<Throwable> task) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
      super.emitFail(dfd, task);
    } else {
      HANDLER.post(new Runnable() {
        @Override
        public void run() {
          MainThreadTasksHolder.super.emitFail(dfd, task);
        }
      });
    }
  }
}
