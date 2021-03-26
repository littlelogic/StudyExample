package multiThread.concurrent.t06__Concurren;

import com.study.wjw.z_utils.Log;
import com.study.wjw.z_utils.Threader;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;


/**
 * 并发容器 - SynchronousQueue
 */
public class T08_SynchronusQueue_think {
	


	/*




	SynchronousQueue,并发容器,是一个非常特殊的BlockingQueue，
	它的模式是在offer的时候，如果没有另外一个线程正在take或poll的话，
	那么offer就会失败；在take的时候，如果没有另外的线程正好并发在offer，
	也会失败.
	是一个没有数据缓冲的阻塞队列，生产者线程对其的插入操作put()
	必须等待消费者的移除操作take()，反过来也一样。
	take() & put() //这是阻塞的，会阻塞操作线程
	poll() & offer() //非阻塞的,不设置超时时间,立马返回boolean
	在ThreadPoolExecutor中,通过Executors创建的cachedThreadPool
	就是使用此类型队列.已确保,如果现有线程无法接收任务
	(offer失败),将会创建新的线程来执行															*/
	public static void main(String[] args) {
		final SynchronousQueue<String> queue=new SynchronousQueue<>();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Log.i(Threader.getName()+" take begin " );
					try {
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e) { }
					Log.i(Threader.getName()+" - "+queue.take());

					queue.add("");
					queue.put(null);


				} catch (InterruptedException e) { }
			}
		}, "OutputThread").start();
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) { }
		try {
			queue.put("test put");
		} catch (InterruptedException e) { }
		Log.i(Threader.getName()+" queue size : "+queue.size());
	}

}
