package com.qihoo.qmev3.deferred.read;

public enum Schedule {
  CURRENT,
  UI,
  BACKGROUND,
  NEW_THREAD,
  COMPUTE,  // 计算线程
  IO,        // IO线程
  NETWORK,   // 网络线程
  QME_TASK,	// QETaskController
  SYNC_TASK	// 时序同步队列
}
