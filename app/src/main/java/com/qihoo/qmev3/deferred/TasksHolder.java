package com.qihoo.qmev3.deferred;

import com.qihoo.qmev3.deferred.read.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

abstract class TasksHolder<T> {
  // 注意！搬砖代码，目前每个list仅支持一个task，以实现链式调用！
  private List<Task<T>> tasks = new ArrayList<>();
  private List<Task<T>> doneTasks = new ArrayList<>();
  private List<Task<Throwable>> failTasks = new ArrayList<>();
  private List<Task<Exception>> exceptionTasks = new ArrayList<>();
  private final ReentrantLock lock = new ReentrantLock();
  protected int resolved = 0;

  private boolean successful;
  private T data;
  private Throwable error;
  private Exception exception_;

  private Deferred<T> dfd_;

  //==============================================




  protected void emit(T data) {
    lock.lock();
    try {
      this.resolved = 0;
      this.successful = false;
      this.data = data;
      for (Task<T> task : tasks) {
        emit(dfd_, task);
      }
    } finally {
      lock.unlock();
    }
  }

  protected void notify(T data) {
    lock.lock();
    try {
      this.data = data;
      for (Task<T> task : tasks) {
        emitNotify(dfd_, task);
      }
    } finally {
      lock.unlock();
    }
  }

  protected void notifyOnce(T data) {
    lock.lock();
    try {
      this.data = data;
      for (Task<T> task : tasks) {
        emitNotifyOnce(dfd_, task);
      }
      tasks.clear();
    } finally {
      lock.unlock();
    }
  }

  public void resolve(T data) {
    lock.lock();
    try {
      markAsResolved();
      this.successful = true;
      this.data = data;
      for (Task<T> task : doneTasks) {
        emitDone(dfd_, task);
      }
    } finally {
      lock.unlock();
    }
  }

  public void reject(Throwable throwable) {
    lock.lock();
    try {
      markAsResolved();
      resolved = -1;
      this.successful = false;
      this.error = throwable;
      for (Task<Throwable> task : failTasks) {
        emitFail(dfd_, task);
      }
    } finally {
      lock.unlock();
    }
  }

  protected void do_throw(Exception ex) {
    lock.lock();
    try {
      markAsResolved();
      resolved = -2;
      this.successful = false;
      this.error = ex;
      this.exception_ = ex;
      for (Task<Exception> task : exceptionTasks) {
        emitException(dfd_, task);
      }
    } finally {
      lock.unlock();
    }
  }

  protected boolean isDone() {
    boolean b = true;

    if (isSuccessful() && !doneTasks.isEmpty()) {
      b = true;
      for (Task<?> task : doneTasks) {
        if (!((InnerTask)task).isDone()) {
          b = false;
          break;
        }
      }
    }
    else if (resolved == -1 && !failTasks.isEmpty()) {
      b = true;
      for (Task<Throwable> task : failTasks) {
        if (!((InnerTask)task).isDone()) {
          b = false;
          break;
        }
      }
    }
    else if (resolved == -2 && !exceptionTasks.isEmpty()) {
      b = true;
      for (Task<Exception> task : exceptionTasks) {
        if (!((InnerTask)task).isDone()) {
          b = false;
          break;
        }
      }
    }
    else if (doneTasks.isEmpty() && failTasks.isEmpty() && exceptionTasks.isEmpty()) {
      b = true;
      for (Task<?> task : tasks) {
        if (!((InnerTask)task).isDone()) {
          b = false;
          break;
        }
      }
    }

    return b;
  }



  //==============================================

  public TasksHolder(Deferred<T> dfd) {
    dfd_ = dfd;
  }

  // 内部task，记录完成标志
  private class InnerTask<T> extends Task<T> {
    public InnerTask(Task<T> t) {
      task_ = t;
      done_ = false;
    }
    public void run(T data) {
      if (done_)
        return;

      try {
        done_ = false;
        task_.run(data);
      } catch (Exception ex) {
        throw ex;
      } finally {
        done_ = true;
      }
    }

    public boolean isDone() {
      return done_;
    }

    private Task<T> task_;
    private volatile boolean done_;
  }

  // 支持异常保护
  private class NotifyTask<T> extends Task<T> {
    public NotifyTask(Task<T> t) {
      task_ = t;
    }
    public void run(T data) {
      try {
        task_.run(data);
      } catch (Exception ex) {
        ex.printStackTrace();
      } finally {
      }
    }

    private Task<T> task_;
  }

  //------------------------

  protected void clear() {
    lock.lock();
    try {
      tasks.clear();
      doneTasks.clear();
      failTasks.clear();
      exceptionTasks.clear();
    } finally {
      lock.unlock();
    }
  }

  void add(Task<T> task) {
    lock.lock();
    try {
      tasks.add(new InnerTask(task));
    } finally {
      lock.unlock();
    }
  }

  void addNotify(Task<T> task) {
    lock.lock();
    try {
      tasks.add(new NotifyTask(task));
    } finally {
      lock.unlock();
    }
  }

  void addDone(Task<T> task) {
    lock.lock();
    try {
      if (resolved == 1) {
        throw new IllegalStateException("Deferred already resolved!");
        //emitDone(dfd_, new InnerTask(task));
      } else {
        doneTasks.add(new InnerTask(task));
      }
    } finally {
      lock.unlock();
    }
  }

  void addFail(Task<Throwable> task) {
    lock.lock();
    try {
      if (resolved == -1) {
        throw new IllegalStateException("Deferred already rejected!");
        //emitFail(dfd_, new InnerTask(task));
      } else {
        failTasks.add(new InnerTask(task));
      }
    } finally {
      lock.unlock();
    }
  }

  protected void addException(Task<Exception> task) {
    lock.lock();
    try {
      if (resolved == -2) {
        throw new IllegalStateException("Deferred already fatal!");
        //emitException(dfd_, new InnerTask(task));
      } else {
        exceptionTasks.add(new InnerTask(task));
      }
    } finally {
      lock.unlock();
    }
  }

  //----------------

  protected boolean isSuccessful() {
    return successful;
  }

  protected Throwable getError() {
    return error;
  }

  protected Exception getException() {
    return exception_;
  }

  protected T getData() {
    return data;
  }

  //----------------

  protected List<Task<T>> getTasks() {
    return tasks;
  }

  protected List<Task<T>> getDoneTasks() {
    return doneTasks;
  }

  protected List<Task<Throwable>> getFailTasks() {
    return failTasks;
  }

  protected List<Task<Exception>> getExceptionTasks() {
    return exceptionTasks;
  }

  private void markAsResolved() {
    if (resolved == 1) {
      throw new IllegalStateException("Deferred already resolved");
    }
    resolved = 1;
  }

  //---------------

  protected abstract void emit(Deferred<T> dfd, Task<T> task);

  protected abstract void emitDone(Deferred<T> dfd, Task<T> task);

  protected abstract void emitFail(Deferred<T> dfd, Task<Throwable> task);

  protected abstract void emitException(Deferred<T> dfd, Task<Exception> task);

  protected abstract void emitNotify(Deferred<T> dfd, Task<T> task);

  protected abstract void emitNotifyOnce(Deferred<T> dfd, Task<T> task);

  //---------------






















}
