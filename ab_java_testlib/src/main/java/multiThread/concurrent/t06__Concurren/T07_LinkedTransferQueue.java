package multiThread.concurrent.t06__Concurren;

import com.study.wjw.z_utils.Log;
import com.study.wjw.z_utils.Threader;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;



/**
	并发容器 - LinkedTransferQueue
 转移队列
 add - 队列会保存数据，不做阻塞等待。
 transfer - 是TransferQueue的特有方法。必须有消费者（take()（有阻塞）方法的调用者）。
 如果没有任意线程消费数据，transfer方法阻塞。一般用于处理即时消息。

 1.tryTransfer(E)：将元素立刻给消费者。准确的说就是立刻给一个等待接收元素的线程，
   			如果没有消费者就会返回false，而不将元素放入队列。
 2.transfer(E)：将元素给消费者，如果没有消费者就会等待。
 3.tryTransfer(E,long,TimeUnit)：将元素立刻给消费者，如果没有就等待指定时间。给失败返回false。
 4.hasWaitingConsumer()：返回当前是否有消费者在等待元素。
 5.getWaitingConsumerCount()：返回等待元素的消费者个数。
 */
public class T07_LinkedTransferQueue {

	LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
	
	public static void main(String[] args) {
		if (true) test_a();
		if (false) test_b();
	}


	public static void test_a() {
		final T07_LinkedTransferQueue t = new T07_LinkedTransferQueue();
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Log.i("transfer_01" );
					t.queue.transfer("test string");
					//t.queue.add("test string");
					Log.i("transfer_99" );
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Log.i(Threader.getName() + " begin");
					Log.i(Threader.getName() + " - " + t.queue.take());
					Log.i(Threader.getName() + " end");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "output thread").start();
	}

	public static void test_b() {
		final T07_LinkedTransferQueue t = new T07_LinkedTransferQueue();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Log.i(Threader.getName() + " begin");
					Log.i(Threader.getName() + " - " + t.queue.take());
					Log.i(Threader.getName() + " end");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "output thread").start();
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			Log.i("transfer_01" );
			t.queue.transfer("test string");
			Log.i("transfer_99" );
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}




}
