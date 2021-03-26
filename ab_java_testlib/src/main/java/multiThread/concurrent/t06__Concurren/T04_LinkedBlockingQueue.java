package multiThread.concurrent.t06__Concurren;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class T04_LinkedBlockingQueue {
	
	final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
	final Random r = new Random();


	/**
	 *  并发容器 - LinkedBlockingQueue
	 *  阻塞容器。
	 *  put & take - 自动阻塞。
	 *  put自动阻塞， 队列容量满后，自动阻塞
	 *  take自动阻塞方法， 队列容量为0后，自动阻塞。
	 */
	public static void main(String[] args) {
		final T04_LinkedBlockingQueue t = new T04_LinkedBlockingQueue();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					try {
						t.queue.put("value"+t.r.nextInt(1000));
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}, "producer").start();
		
		for(int i = 0; i < 3; i++){
			new Thread(new Runnable() {
				@Override
				public void run() {
					while(true){
						try {
							System.out.println(Thread.currentThread().getName() + 
									" - " + t.queue.take());
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}, "consumer"+i).start();
		}
	}

}
