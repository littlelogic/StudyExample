package com.qihoo.qmev3.deferred.read;

import android.util.Log;

import com.qihoo.qmev3.deferred.out.ApiBase;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Source: http://google-ukdev.blogspot.com/2009/01/crimes-against-code-and-using-threads.html
 */
public class TaskQueue {

  private static List<TaskQueue> task_queues_ = new ArrayList<>();
  //------------
  private final String ME = "TaskQueue";

  private LinkedList<Runnable> tasks;
  private Thread thread;
  private boolean running;
  private Runnable internalRunnable;
  private String name_;

  //======================================================

  public static TaskQueue instance(int index) {
    TaskQueue q = null;
    if (index >= 0 && index < task_queues_.size()) {
      q = task_queues_.get(index);
    }
    return q;
  }

  public static void init(String[] names) {
    ApiBase.check_on_ui();

    for (int i = 0; i < names.length; ++i) {
      TaskQueue q = new TaskQueue(names[i]);
      if (q != null) {
        task_queues_.add(q);
        q.start();
      }
    }
  }

  public static void uninit() {
    ApiBase.check_on_ui();

    for (int i = 0; i < task_queues_.size(); ++i) {
      TaskQueue q = task_queues_.get(i);
      if (q != null) {
        q.stop();
        q.join();
      }
    }
  }

  public static boolean good() {
    return !task_queues_.isEmpty();
  }

  //------------

  private TaskQueue(String name) {
    tasks = new LinkedList<>();
    name_ = name;
    internalRunnable = new InternalRunnable();
  }

  private class InternalRunnable implements Runnable {
    public void run() {
      internalRun();
    }
  }

  private void internalRun() {
    while (running) {
      Runnable task = getNextTask();
      try {
        task.run();
        Thread.yield();//---
      } catch (Throwable t) {
        Log.e(ME, "Task threw an exception", t);
      }
    }
  }

  public void start() {
    if (!running) {
      thread = new Thread(internalRunnable);
      thread.setDaemon(true);
      thread.setName("Deferred TaskQueue: " + name_);
      running = true;
      thread.start();
    }
  }

  public void join() {
    if (thread != null) {
      try {
        thread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private Runnable getNextTask() {
    Log.d(ME, "getNextTask");
    synchronized (tasks) {
      if (tasks.isEmpty()) {
        try {
          tasks.wait();
        } catch (InterruptedException e) {
          Log.e(ME, "Task interrupted", e);
          stop();
        }
      }
      return tasks.removeFirst();
    }
  }

  public void addTask(Runnable task) {
    Log.d(ME, "addTask");
    synchronized (tasks) {
      tasks.addLast(task);
      tasks.notify(); // notify any waiting threads
    }
  }

  public void stop() {
    running = false;
  }

  public int count() {
    synchronized (tasks) {
      return tasks.size();
    }
  }

  public boolean empty() {
    synchronized (tasks) {
      return tasks.isEmpty();
    }
  }

}
