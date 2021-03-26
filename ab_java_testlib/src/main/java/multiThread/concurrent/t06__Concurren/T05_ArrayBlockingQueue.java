package multiThread.concurrent.t06__Concurren;

import com.study.wjw.z_utils.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;



public class T05_ArrayBlockingQueue {
	


	/*




	ArrayBlockingQueue是一个用数组实现的有界阻塞队列。并发容器 ，
	此队列按照先进先出（FIFO）的原则对元素进行排序。  															*/
	public static void main(String[] args) {
		final BlockingQueue<String> queue = new ArrayBlockingQueue<>(3,false);
		for(int i = 0; i < 5; i++){
			// System.out.println("add method : " + t.queue.add("value"+i));
			/*try {
				t.queue.put("put"+i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("put method : " + i);*/
			// System.out.println("offer method : " + t.queue.offer("value"+i));
			try {
				Log.i("offer method : " +
						queue.offer("value" + i, 1, TimeUnit.SECONDS));
			} catch (InterruptedException e) {  }
		}
		System.out.println(queue);
	}

}
