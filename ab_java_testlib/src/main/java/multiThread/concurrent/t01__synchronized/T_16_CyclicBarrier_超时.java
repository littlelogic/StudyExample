package multiThread.concurrent.t01__synchronized;

import com.study.wjw.z_utils.Log;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/*


public CyclicBarrier(int parties)

参数parties指让多少个线程或者任务等待至barrier状态；参数barrierAction为当这些线程都达到barrier状态时会执行的内容
随机选择一个线程来执行。
public CyclicBarrier(int parties, Runnable barrierAction)


public int await() throws InterruptedException, BrokenBarrierException { };

让这些线程等待至一定的时间，如果还有线程没有到达barrier状态就直接让到达barrier的线程执行后续任务。
超时会抛出TimeoutException异常，此时如果设置了barrierAction，并不会执行，后续的报错是BrokenBarrierException
public int await(long timeout, TimeUnit unit)throws InterruptedException,BrokenBarrierException,TimeoutException { };

返回当前在屏障处等待的参与者数目。
getNumberWaiting()

返回要求启动此 barrier 的参与者数目。
getParties()

查询此屏障是否处于损坏状态。
isBroken()


将屏障重置为其初始状态。
如果在线程处于等待状态时 barrier 被 reset()，
或者在调用 await 时 barrier 被损坏，抑或任意一个线程正处于等待状态，
则抛出 BrokenBarrierException 异常。
reset()


 */
public class T_16_CyclicBarrier_超时 {

	static class WriterOutTime extends Thread{
		private CyclicBarrier cyclicBarrier;
		public WriterOutTime(CyclicBarrier cyclicBarrier) {
			this.cyclicBarrier = cyclicBarrier;
		}

		@Override
		public void run() {
			System.out.println("线程"+Thread.currentThread().getName()+"正在写入数据...");
			try {
				Thread.sleep(1000);      //以睡眠来模拟写入数据操作
				Log.i("线程"+Thread.currentThread().getName()+"写入数据完毕，等待其他线程写入完毕");
				try {
					/*String Strnull = null;
					Strnull.trim();*/
					cyclicBarrier.await(2000, TimeUnit.MILLISECONDS);
				} catch (TimeoutException e) {
					Log.i("线程------------"+Thread.currentThread().getName()+" 等待超时继续执行");
					e.printStackTrace();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}catch(BrokenBarrierException e){
				Log.i("线程------------"+Thread.currentThread().getName()+" BrokenBarrierException");
				e.printStackTrace();
			}catch(Exception e){
				Log.i("线程------------"+Thread.currentThread().getName()+" Exception");
				e.printStackTrace();
			}
			Log.i(Thread.currentThread().getName()+"所有线程写入完毕，继续处理其他任务...");
		}
	}

	public static void dealWriterOutTime() {
		int N = 4;
		CyclicBarrier barrier  = new CyclicBarrier(N,new Runnable(){
			@Override
			public void run() {
				Log.i("线程"+Thread.currentThread().getName()+"执行最后操作");
			}
		});

		for(int i=0;i<N;i++) {
			if(i<N-1)
				new WriterOutTime(barrier).start();
			else {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				new WriterOutTime(barrier).start();
			}
		}
	}

	//=====================================

	static class Writer extends Thread{
		private CyclicBarrier cyclicBarrier;
		public Writer(CyclicBarrier cyclicBarrier) {
			this.cyclicBarrier = cyclicBarrier;
		}

		@Override
		public void run() {
			System.out.println("线程"+Thread.currentThread().getName()+"正在写入数据...");
			try {
				Thread.sleep(1000);      //以睡眠来模拟写入数据操作
				System.out.println("线程"+Thread.currentThread().getName()+"写入数据完毕，等待其他线程写入完毕");

				cyclicBarrier.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}catch(BrokenBarrierException e){
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()+"所有线程写入完毕，继续处理其他任务...");
		}
	}

	public static void dealWriter() {
		int N = 4;
		CyclicBarrier barrier  = new CyclicBarrier(N);

		for(int i=0;i<N;i++) {
			new T_16_CyclicBarrier.Writer(barrier).start();
		}

		try {
			//todo 确保上面的都执行完了，不然会混到一起
			Thread.sleep(3500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		barrier.reset();

		System.out.println("CyclicBarrier重用");
		for(int i=0;i<N;i++) {
			new T_16_CyclicBarrier.Writer(barrier).start();
		}
	}

	//=====================================

	public static void main(String[] args) {
		dealWriter();
		///dealWriterOutTime();
	}

}
