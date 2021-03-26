package multiThread.concurrent.t03__ReentrantLock;

import com.study.wjw.z_utils.Log;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/*

方法condition.awaitUninterruptibly()的使用
线程在调用condition.await()后处于await状态，此时调用thread.interrupt()会报错
但是使用condition.awaitUninterruptibly()后，调用thread.interrupt()则不会报错,实际是不响应interrupt方法，线程没有中断


 */

public class T_04_await<E> {

	private Lock lock = new ReentrantLock();
	private Condition condition_await = lock.newCondition();
	private Condition condition_await_un = lock.newCondition();
	private Condition condition_await_during = lock.newCondition();

	public void await(){
		lock.lock();
		try {
			Log.i("await-await-");
			condition_await.await();
			Log.i("await-next-");
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void awaitUninterruptibly(){
		lock.lock();
		try {
			Log.i("awaitUninterruptibly-await-");
			condition_await_un.awaitUninterruptibly();
			Log.i("awaitUninterruptibly-next-");
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void awaitUninterruptibly_unlock(){
		lock.lock();
		try {
			Log.i("awaitUninterruptibly_unlock-01-");
			condition_await_un.signalAll();
			Log.i("awaitUninterruptibly_unlock-02-");
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			lock.unlock();
			Log.i("awaitUninterruptibly_unlock-99-");
		}
	}

	public void awaitDuring(){
		lock.lock();
		try {
			Log.i("awaitDuring-await-");
			boolean mark = condition_await_during.await(2000,TimeUnit.MILLISECONDS);
			if (mark) {
				Log.i("awaitDuring-及时唤醒-");
			} else {
				Log.i("awaitDuring-超时唤醒-");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void awaitDuring_unlock(){
		lock.lock();
		try {
			Log.i("awaitDuring_unlock-await-");
			//todo 唤醒的时候还没有超时
			condition_await_during.signalAll();
			Thread.sleep(3200);
			Log.i("awaitDuring_unlock-sleep-1200-");
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		final T_04_await<String> mDeal = new T_04_await<>();
		Thread 	awaitThread = new Thread(new Runnable() {
				@Override
				public void run() {
					mDeal.await();
				}
			});
		awaitThread.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		awaitThread.interrupt();


		//todo============================================
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Log.i("--------------------------------------------");
		Thread 	bThread = new Thread(new Runnable() {
			@Override
			public void run() {
				mDeal.awaitUninterruptibly();
			}
		});
		bThread.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		bThread.interrupt();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		mDeal.awaitUninterruptibly_unlock();


		//todo============================================
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Log.i("--------------------------------------------");
		Thread 	cThread = new Thread(new Runnable() {
			@Override
			public void run() {
				mDeal.awaitDuring();
			}
		});
		cThread.start();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		mDeal.awaitDuring_unlock();

	}
}
