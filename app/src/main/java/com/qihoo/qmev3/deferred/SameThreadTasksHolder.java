package com.qihoo.qmev3.deferred;

import com.qihoo.qmev3.deferred.out.ApiBase;
import com.qihoo.qmev3.deferred.read.Task;

public class SameThreadTasksHolder<T> extends TasksHolder<T> {
  public SameThreadTasksHolder(Deferred<T> dfd) {
    super(dfd);
  }

  @Override
  protected void emit(Deferred<T> dfd, Task<T> task) {
    try {
      dfd._set_myself(dfd);
      task.run(getData());
    }
    catch (Exception ex) {
      do_throw(ex);
    }
  }

  @Override
  protected void emitDone(Deferred<T> dfd, Task<T> task) {
    if (isSuccessful()) {
      try {
        dfd._set_myself(dfd);
        task.run(getData());
      }
      catch (Exception ex) {
        do_throw(ex);
      }
    }
  }

  @Override
  protected void emitFail(Deferred<T> dfd, Task<Throwable> task) {
    if (!isSuccessful()) {
      dfd._set_myself(dfd);
      task.run(getError());
    }
  }

  @Override
  protected void emitException(Deferred<T> dfd, Task<Exception> task) {
    if (!isSuccessful()) {
      dfd._set_myself(dfd);
      task.run(getException());
    }
  }

  @Override
  protected void emitNotify(Deferred<T> dfd, Task<T> task) {
    ApiBase.check_on_ui();

    try {
      dfd._set_myself(dfd);
      task.run(getData());
    }
    catch (Exception ex) {
      do_throw(ex);
    }
  }

  @Override    
  protected void emitNotifyOnce(Deferred<T> dfd, Task<T> task) {
    ApiBase.check_on_ui();

    try {
      dfd._set_myself(dfd);
      task.run(getData());
    }
    catch (Exception ex) {
      do_throw(ex);
    }
  }
}
