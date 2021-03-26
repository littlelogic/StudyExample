package multiThread.concurrent.t01__synchronized;

import com.study.wjw.z_utils.Log;

import java.util.concurrent.TimeUnit;

/**
 * synchronized关键字
 * 同步方法 - static
 * 静态同步方法，锁的是当前类型的类对象。在本代码中就是Test_02.class
 */
public class T_02_类锁 {
	private static int staticCount = 0;
	
	public static synchronized void testSync4(){
		System.out.println(Thread.currentThread().getName() 
				+ "testSync4-staticCount = " + staticCount++ +"-time"+System.currentTimeMillis());
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void testSync5(){
		synchronized(T_02_类锁.class){
			System.out.println(Thread.currentThread().getName() 
					+ "testSync5-staticCount = " + staticCount++ +"-time"+System.currentTimeMillis());
		}
	}

	static {
		Log.i(Thread.currentThread().getName()+ " static代码块");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	public static void  main(String ar1[]){

		final T_02_类锁 mTest_02 = new T_02_类锁();

		new Thread(new Runnable() {
			@Override
			public void run() {
				testSync4();
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				mTest_02.testSync5();
			}
		}).start();


	}
}
