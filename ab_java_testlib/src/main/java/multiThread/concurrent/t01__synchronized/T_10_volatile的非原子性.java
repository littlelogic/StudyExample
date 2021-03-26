package multiThread.concurrent.t01__synchronized;

import java.util.ArrayList;
import java.util.List;

/**
 * volatile关键字
 * volatile的非原子性问题
 * volatile， 只能保证可见性，不能保证原子性。
 * 不是枷锁问题，只是内存数据可见。
 */
public class T_10_volatile的非原子性 {

	Num mNum = new Num();
	volatile int count = 0;
	/*synchronized*/ void m(){
		for(int i = 0; i < 10000; i++){
			count++;
			synchronized (mNum){
				mNum.value++;
			}
		}
	}
	
	public static void main(String[] args) {
		final T_10_volatile的非原子性 t = new T_10_volatile的非原子性();
		List<Thread> threads = new ArrayList<>();
		for(int i = 0; i < 10; i++){
			threads.add(new Thread(new Runnable() {
				@Override
				public void run() {
					t.m();
				}
			}));
		}
		for(Thread thread : threads){
			thread.start();
		}
		for(Thread thread : threads){
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(t.count);
		System.out.println(t.mNum.value);
	}

	class Num {
		public int value = 0;
	}
}
