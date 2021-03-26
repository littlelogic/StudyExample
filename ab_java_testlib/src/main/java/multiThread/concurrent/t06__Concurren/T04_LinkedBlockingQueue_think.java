package multiThread.concurrent.t06__Concurren;

import com.study.wjw.z_utils.Log;
import com.study.wjw.z_utils.Threader;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class T04_LinkedBlockingQueue_think {
	



	/**
	 *  并发容器 - LinkedBlockingQueue
	 *  阻塞容器。
	 *  put & take - 自动阻塞。
	 *  put自动阻塞， 队列容量满后，自动阻塞
	 *  take自动阻塞方法， 队列容量为0后，自动阻塞。
	 */
	public static void main(String[] args) {
		final BlockingQueue<String> queue = new LinkedBlockingQueue<>(6);
		final Random r = new Random();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true)
					try {
						queue.put("value"+r.nextInt(1000));
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}
		}, "producer").start();
		for(int i = 0; i < 3; i++){
			new Thread(new Runnable() {
				@Override
				public void run() {
					while(true)
						try {
							Log.i(Threader.getName() +
									" - " + queue.take());
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
				}
			}, "consumer"+i).start();
		}
	}

}
