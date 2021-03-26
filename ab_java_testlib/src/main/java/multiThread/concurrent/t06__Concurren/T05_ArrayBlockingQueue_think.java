package multiThread.concurrent.t06__Concurren;

import com.study.wjw.z_utils.Log;
import com.study.wjw.z_utils.Threader;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;


public class T05_ArrayBlockingQueue_think {
	


	/*




	ArrayBlockingQueue和LinkedBlockingQueue都是并发阻塞容器
	1. 队列中锁的实现不同
	ArrayBlockingQueue生产和消费用的是同一个锁；
	Linkedxxx 的锁是分离的，即生产用的是putLock，消费是takeLock
	2. 在生产或消费时操作不同
	ArrayBlockingQueue是直接将枚举对象插入或移除的；
	Linkedxxx 要把元素转为Node<E>进行插入或移除，会影响性能
	3. 队列大小初始化方式不同
	ArrayBlockingQueue实现的队列中必须指定队列的大小；
	Linkedxxx可以不指定队列的大小，默认是Integer.MAX_VALUE										*/
	public static void main(String[] args) {
		final BlockingQueue<String> queue =
			new ArrayBlockingQueue<>(3,false);
		for(int i = 0; i < 5; i++)
			try {
				Log.i("offer method : " +
					queue.offer("value" + i,1,
							TimeUnit.SECONDS));
			} catch (InterruptedException e) {  }
		//todo 后来两个插入不成功，输出三个
		System.out.println(queue);
	}
	void dealLinkedBlockingQueue(String[] args){
		final BlockingQueue<String> queue =
				new LinkedBlockingQueue<>(6);
		final Random r = new Random();
		new Thread(new Runnable() {
			public void run() {
				while(true)
					try {
						queue.put("V"+r.nextInt(10));
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) { }
			}
		}, "producer").start();
		for(int i = 0; i < 3; i++)
			new Thread(new Runnable() {
				public void run() {
					while(true)
						try {
							Log.i(Threader.getName() +
									" - " + queue.take());
						} catch (InterruptedException e){}
				}
			}, "consumer"+i).start();
	}






}
