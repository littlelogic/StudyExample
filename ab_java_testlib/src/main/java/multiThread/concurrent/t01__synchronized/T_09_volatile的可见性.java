package multiThread.concurrent.t01__synchronized;

import java.util.concurrent.TimeUnit;

/**
 * volatile关键字
 * volatile的可见性
 * 通知OS操作系统底层，在CPU计算过程中，都要检查内存中数据的有效性。保证最新的内存数据被使用。
 *
 */
public class T_09_volatile的可见性 {
	
	volatile boolean b = true;
	
	void m(){
		System.out.println("start");
		while(b){}
		System.out.println("end");
	}
	
	public static void main(String[] args) {
		final T_09_volatile的可见性 t = new T_09_volatile的可见性();
		new Thread(new Runnable() {
			@Override
			public void run() {
				t.m();
			}
		}).start();
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		t.b = false;
	}
	
}
