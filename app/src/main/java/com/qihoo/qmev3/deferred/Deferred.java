package com.qihoo.qmev3.deferred;

import com.qihoo.qmev3.deferred.out.ApiBase;
import com.qihoo.qmev3.deferred.read.AsyncTasksHolder;
import com.qihoo.qmev3.deferred.read.BackgroundThreadTasksHolder;
import com.qihoo.qmev3.deferred.read.MainThreadTasksHolder;
import com.qihoo.qmev3.deferred.read.Promise;
import com.qihoo.qmev3.deferred.read.Schedule;
import com.qihoo.qmev3.deferred.read.Task;
import com.qihoo.qmev3.deferred.read.TaskQueue;
import com.qihoo.qmev3.deferred.read.TaskQueueHolder;

import java.util.List;

/**
 * Modified by davidwongiiss
 * supports promise chain
 * @param <T>
 */
public class Deferred<T> implements Promise<T> {
  private Deferred() { }

  public static void init() {
    TaskQueue.init(new String[]{"IO_THREAD", "COMPUTE_THREAD", "NETWORK_THREAD", "SYNC_THREAD"});
  }

  public static void uninit() {
    TaskQueue.uninit();
  }

  public static <T> Deferred<T> create(final T data) {
    //Assert.isTrue(TaskQueue.good(), "U must call init method when app launched!");
    Deferred<T> dfd = create(Schedule.CURRENT, new Task<T>() {
      @Override
      public void run(T data) {
        Deferred.self().next(data);
      }
    });
    return dfd;
  }

  public static <T> Deferred<T> create(Schedule schedule, Task<T> task) {
    //Assert.isTrue(TaskQueue.good(), "U must call init method when app launched!");
    Deferred<T> dfd = new Deferred<>();
    TasksHolder th = dfd.get_task_holder(schedule);
    th.add(task);
    return dfd;
  }

  @Override
  public Deferred<T> root() {
    Deferred<T> r = this;
    Deferred<T> prev = prev_;
    while (prev != null) {
      r = prev;
      prev = prev.prev_;
    }

    return r;
  }

  private Promise<T> do_submit(T data) {
    for (int i = 0; i < tasks_.length; ++i) {
      if (!tasks_[i].getTasks().isEmpty()) {
        tasks_[i].emit(data);
        break;
      }
    }

    return this;
  }

  @Override
  public Promise<T> submit(T data) {
    Deferred dfd = root();
    if (dfd != null) {
      dfd.do_submit(data);
    }
    return dfd;
  }

  @Override
  public Promise<T> when(final Promise[] promises) {
    final Deferred<T> that = this;
    // 死等
    then(Schedule.NEW_THREAD, new Task<T>() {
      @Override
      public void run(T v) {
        boolean b = !(promises != null && promises.length > 0);
        while (!b) {
          b = true;
          for (int i = 0; i < promises.length && b; ++i) {
            b = ((Deferred)promises[i]).isDone();
          }

          // sleep
          try {
            Thread.sleep(50);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }

        that.next(null);
      }
    });
    return this;
  }

  @Override
  public Promise<T> then(Schedule schedule, Task<T> task) {
    next_ = create(schedule, task);
    next_.prev_ = this;
    next_.next_ = null;
    return next_;
  }

  @Override
  public Promise<T> done(Schedule schedule, Task<T> task) {
    TasksHolder th = get_task_holder(schedule);
    th.addDone(task);
    return this;
  }

  @Override
  public Promise<T> fail(Schedule schedule, Task<Throwable> task) {
    TasksHolder th = get_task_holder(schedule);
    th.addFail(task);
    return this;
  }

  @Override
  public Promise<T> exception(Schedule schedule, Task<Exception> task) {
    TasksHolder th = get_task_holder(schedule);
    th.addException(task);
    return this;
  }

  @Override
  public Promise<T> progress(Task<T> task) {
    ApiBase.check_on_ui();
    progress_task_.addNotify(task);
    return this;
  }

  @Override
  public Promise<T> progressOnce(Task<T> task) {
    // 必须在UI中调用
    ApiBase.check_on_ui();

    // 先删除已经通知过的
    List<Task> tasks = progress_once_task_.getTasks();
    int cnt = tasks.size();
    for (int i = cnt - 1; i >= 0; --i) {
      Task<T> t = tasks.get(i);
    }

    progress_once_task_.addNotify(task);
    return this;
  }

  public Promise<T> notify(T data) {
    progress_task_.notify(data);
    progress_once_task_.notifyOnce(data);
    return this;
  }

  public void next(T data) {
    do_next(data);
  }

  public void resolve(T data) {
    boolean b = false;
    for (int i = 0; i < tasks_.length; ++i) {
      if (!tasks_[i].getDoneTasks().isEmpty()) {
        tasks_[i].resolve(data);
        b = true;
        break;
      }
    }

    if (!b) {
      if (isResolved()) {
        do_next(data);
      }
    }
  }

  @Override
  public boolean isResolved() {
    boolean b = false;
    for (int i = 0; i < tasks_.length && !b; ++i) {
      if (!tasks_[i].getTasks().isEmpty()) {
        b = tasks_[i].isSuccessful();
        break;
      }
    }
    return b;
  }

  public void reject() {
    reject(new Throwable("Person cancelled!"));
  }

  public void reject(Throwable throwable) {
    for (int i = 0; i < tasks_.length; ++i) {
      if (!tasks_[i].getFailTasks().isEmpty()) {
        tasks_[i].reject(throwable);
        break;
      }
    }
  }

  @Override
  public boolean isRejected() {
    boolean b = false;
    for (int i = 0; i < tasks_.length && !b; ++i) {
      if (!tasks_[i].getTasks().isEmpty()) {
        b = (!tasks_[i].isSuccessful() && tasks_[i].resolved < 0);
        break;
      }
    }
    return b;
  }

  public static <T> Deferred<T> self() {
    return (Deferred<T>)meself_.get();
  }

  protected static <T> void _set_myself(Deferred<T> dfd) {
    meself_.set(dfd);
  }

  private static <T> boolean is_root(Deferred<T> dfd) {
    return (dfd.prev_ == null);
  }

  private TasksHolder get_task_holder(Schedule schedule) {
    TasksHolder t = tasks_[0];
    switch (schedule) {
    case CURRENT:
      t = tasks_[0];
      break;
    case UI:
      t = tasks_[1];
      break;
    case BACKGROUND:
      t = tasks_[2];
      break;
    case NEW_THREAD:
      t = tasks_[3];
      break;
    case IO:
      t = tasks_[4];
      break;
    case COMPUTE:
      t = tasks_[5];
      break;
    case NETWORK:
      t = tasks_[6];
      break;
    case SYNC_TASK:
      t = tasks_[7];
      break;
    case QME_TASK:
      t = tasks_[8];
      break;
    default:
      break;
    }

    return t;
  }

  private void do_next(T data) {
    if (next_ != null) {
      next_.do_submit(data);
    }
  }

  protected boolean isDone() {
    boolean b = true;
    for (int i = 0; i < tasks_.length && b; ++i) {
      b = tasks_[i].isDone();
    }

    if (b) {
      Deferred<T> next = next_;
      while (next != null && b) {
        b = next.isDone();
        next = next.next_;
      }
    }

    return b;
  }

  private static final ThreadLocal<Object> meself_ = new ThreadLocal<Object>() {
    @Override
    protected Object initialValue() {
      return null;
    }
  };

  protected TasksHolder[] tasks_ = {
    new SameThreadTasksHolder(this),
    new MainThreadTasksHolder(this),
    new BackgroundThreadTasksHolder(this),
    new AsyncTasksHolder(this),
    new TaskQueueHolder(this, TaskQueue.instance(0)),
    new TaskQueueHolder(this, TaskQueue.instance(1)),
    new TaskQueueHolder(this, TaskQueue.instance(2)),
    new TaskQueueHolder(this, TaskQueue.instance(3)),
    new QmeTasksHolder(this)
  };

  // always on main thread
  private TasksHolder progress_task_ = new MainThreadTasksHolder(this);
  private TasksHolder progress_once_task_ = new MainThreadTasksHolder(this);

  protected Deferred<T> prev_ = null;
  protected Deferred<T> next_ = null;
}
