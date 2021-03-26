package com.qihoo.qmev3.deferred.read;

public interface Promise<T> {
  Promise<T> root();
  Promise<T> submit(T data);
  Promise<T> when(Promise[] promises);
  Promise<T> then(Schedule schedule, Task<T> task);
  Promise<T> done(Schedule schedule, Task<T> task);
  Promise<T> fail(Schedule schedule, Task<Throwable> task);
  Promise<T> exception(Schedule schedule, Task<Exception> task);
  Promise<T> progress(Task<T> task);
  Promise<T> progressOnce(Task<T> task);

  boolean isResolved();
  boolean isRejected();
}
