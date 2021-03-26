package multiThread.concurrent.t01__synchronized;

import java.util.concurrent.TimeUnit;

/**

 synchronized关键字
 对象锁，
 不影响方法的覆盖继承
 轻度锁和重度锁

 *
 * 锁对象。synchronized(this)和synchronized实例方法都是锁当前对象。
 */
public class T_01_对象锁 {
	private int count = 0;
	private Object o = new Object();
	
	public void testSync1(){
		synchronized(o){
			System.out.println(Thread.currentThread().getName() 
					+ " count = " + count++);
		}
	}
	
	public void testSync2(){
		synchronized(this){
			System.out.println(Thread.currentThread().getName() 
					+ " count = " + count++);
		}
	}
	
	public synchronized void testSync3(){
		System.out.println(Thread.currentThread().getName() 
				+ " count = " + count++);
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception{
		final T_01_对象锁 t = new T_01_对象锁();
		Thread aThread = new Thread(new Runnable() {
			@Override
			public void run() {
				t.testSync3();
			}
		});
		aThread.start();
		///aThread.join();
		new Thread(new Runnable() {
			@Override
			public void run() {
				t.testSync3();
			}
		}).start();

		aThread.join();
		System.out.println(Thread.currentThread().getName()
				+ " main last ");
	}
	
}
