/**
 * 门闩 - CountDownLatch
 * 可以和锁混合使用，或替代锁的功能。
 * 在门闩未完全开放之前等待。当门闩完全开放后执行。
 * 避免锁的效率低下问题。
 */
package multiThread.concurrent.t01__synchronized;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class T_15_CountDownLatch_DieLock {

	class Locker extends Thread {
		CountDownLatch latch;
		Object obj1;
		Object obj2;

		Locker(Object obj1, Object obj2, CountDownLatch latch) {
			this.obj1 = obj1;
			this.obj2 = obj2;
			this.latch = latch;
		}

		@Override
		public void run() {
			synchronized (obj1) {
				latch.countDown();
				try {
					//todo 没有释放锁
					latch.await();
				} catch (Exception e) {
					e.printStackTrace();
				}
				synchronized (obj2) {
					System.out.println("Thread finished");
				}
			}
		}
	}

	public static void main(String[] args) {
		T_15_CountDownLatch_DieLock mClass = new T_15_CountDownLatch_DieLock();
		Object obj1 = new Object();
		Object obj2 = new Object();
		CountDownLatch latch = new CountDownLatch(2);

		mClass.new Locker(obj1, obj2, latch).start();
		mClass.new Locker(obj2, obj1, latch).start();
	}
	
}
