/*
 公平锁
 synchronized能做的，reentrantlock都能做，
 synchronized不能做的，reentrantlock也能做，


 公平锁： 是指多个线程竞争同一资源时[等待同一个锁时]，获取资源的顺序是按照申请锁的先后顺序的；
 		 公平锁保障了多线程下各线程获取锁的顺序，先到的线程优先获取锁，有点像早年买火车票一样排队早的人先买到火车票；
 基本特点： 线程执行会严格按照顺序执行，等待锁的线程不会饿死，但 整体效率相对比较低；

 非公平锁： 是指多个线程竞争同一资源时，获取资源的顺序是不确定的，一般是抢占式的；
 		   非公平锁相对公平锁是增加了获取资源的不确定性，但是整体效率得以提升；
 基本特点： 整体效率高，线程等待时间片具有不确定性；

 */
package multiThread.concurrent.t03__ReentrantLock;

import java.util.concurrent.locks.ReentrantLock;

public class T_04_公平锁 {
	
	public static void main(String[] args) {
		TestReentrantlock t = new TestReentrantlock();
		//TestSync t = new TestSync();

		/*
		 线程以和线程二会交替打印
		 */
		Thread t1 = new Thread(t);
		Thread t2 = new Thread(t);
		t1.start();
		t2.start();
	}
}

class TestReentrantlock extends Thread{
	// 定义一个公平锁
	private static ReentrantLock lock = new ReentrantLock(true);
	public void run(){
		for(int i = 0; i < 5; i++){
			lock.lock();
			try{
				System.out.println(Thread.currentThread().getName() + " get lock");
			}finally{
				lock.unlock();
			}
		}
	}
	
}

class TestSync extends Thread{
	public void run(){
		for(int i = 0; i < 5; i++){
			synchronized (this) {
				System.out.println(Thread.currentThread().getName() + " get lock in TestSync");
			}
		}
	}
}
