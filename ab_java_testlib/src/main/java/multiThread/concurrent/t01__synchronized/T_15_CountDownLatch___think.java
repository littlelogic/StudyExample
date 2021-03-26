package multiThread.concurrent.t01__synchronized;

import com.study.wjw.z_utils.Log;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


/*


门闩 - CountDownLatch
上几把锁，解几把锁
public void await() throws InterruptedException { };
public boolean await(long timeout, TimeUnit unit)
					throws InterruptedException { };        				 */
public class T_15_CountDownLatch___think {
	CountDownLatch latch = new CountDownLatch(5);
	void m1(){
		try {
			latch.await();// 等待门闩开放。
		} catch (InterruptedException e) {}
		Log.i("m1() method");
	}
	void m2(){
		for(int i = 0; i < 10; i++){
			if(latch.getCount() != 0){
				Log.i("latch count"+latch.getCount());
				latch.countDown(); //减门闩上的锁。
			}
			try {
				TimeUnit.MILLISECONDS.sleep(500);
			} catch (InterruptedException e) {}
			Log.i("m2() method : " + i);
		}
	}
	public static void main(String[] args) {
		final T_15_CountDownLatch___think t =
				new T_15_CountDownLatch___think();
		new Thread(new Runnable() {
			@Override
			public void run() {
				t.m1();
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				t.m2();
			}
		}).start();
	}
}
