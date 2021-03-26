package multiThread.concurrent.t06__Concurren;

import com.study.wjw.z_utils.Log;
import com.study.wjw.z_utils.Threader;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;

public class T07_LinkedTransferQueue_think {




	/*

	LinkedTransferQueue,转移队列,并发容器
	add - 队列会保存数据，不做阻塞等待。
	transfer(E)：特有方法。必须有消费者（take()（有阻塞）方法的调用者）
 						如果没有任意线程消费数据，transfer方法阻塞
	tryTransfer(E)：准确的说就是，立刻给一个等待接收元素的线程，
					如果没有消费者就会返回false，而不将元素放入队列。
	tryTransfer(E,long,TimeUnit)：将元素立刻给消费者，
							若没有就等待指定时间。失败返回false。
	hasWaitingConsumer()：返回当前是否有消费者在等待元素。
	getWaitingConsumerCount()：返回等待元素的消费者个数。												*/
	public static void main(String[] args) throws Exception{
		final TransferQueue queue=new LinkedTransferQueue<>();
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Log.i("take_start" );
					Log.i(Threader.getName()+":"+queue.take());
					Log.i("take_end add_start" );
					queue.add("TestStrB");
					Log.i("add_end transfer_start" );
					queue.transfer("TestStrC");
					Log.i("transfer_end" );
				} catch (Exception e) {  }
			}
		}).start();
		TimeUnit.SECONDS.sleep(1);
		queue.add("TestStrA");
		TimeUnit.SECONDS.sleep(1);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Log.i(Threader.getName() + ":takeA_start");
					Log.i(Threader.getName()+":"+queue.take());
					Log.i(Threader.getName() + ":takeA_end");
					TimeUnit.SECONDS.sleep(1);
					Log.i(Threader.getName() + ":takeB_start");
					Log.i(Threader.getName()+":"+queue.take());
					Log.i(Threader.getName() + ":takeB_end");
				} catch (InterruptedException e) { }
			}
		}, "outPutThread").start();          /*
		todo 大概执行输出  take_start
						 Thread-0:TestStrA
						 take_end add_start
						 add_end transfer_start
						 outPutThread:takeA_start
						 outPutThread:TestStrB
						 outPutThread:takeA_end
						 outPutThread:takeB_start
						 outPutThread:TestStrC
						 transfer_end
						 outPutThread:takeB_end

		 */
	}





}
