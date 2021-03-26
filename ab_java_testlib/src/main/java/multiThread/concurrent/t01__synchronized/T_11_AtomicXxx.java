package multiThread.concurrent.t01__synchronized;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * AtomicXxx
 * 同步类型
 * 原子操作类型。 其中的每个方法都是原子操作。可以保证线程安全。
 */
public class T_11_AtomicXxx {
	AtomicInteger count = new AtomicInteger(0);
	void m(){
		for(int i = 0; i < 10000; i++){
			///if(count.get() < 1000)///结果不是1000
				count.incrementAndGet();
		}
	}
	
	public static void main(String[] args) {
		final T_11_AtomicXxx t = new T_11_AtomicXxx();
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(t.count.intValue());
	}
}
