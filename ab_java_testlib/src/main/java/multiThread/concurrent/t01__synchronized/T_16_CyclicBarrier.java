package multiThread.concurrent.t01__synchronized;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/*


public CyclicBarrier(int parties)

参数parties指让多少个线程或者任务等待至barrier状态；参数barrierAction为当这些线程都达到barrier状态时会执行的内容
随机选择一个线程来执行。
public CyclicBarrier(int parties, Runnable barrierAction)



public int await() throws InterruptedException, BrokenBarrierException { };

让这些线程等待至一定的时间，如果还有线程没有到达barrier状态就直接让到达barrier的线程执行后续任务。
超时会抛出TimeoutException异常
public int await(long timeout, TimeUnit unit)throws InterruptedException,BrokenBarrierException,TimeoutException { };

 */
public class T_16_CyclicBarrier {

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

	public static void main(String[] args) {
		int N = 4;
		CyclicBarrier barrier  = new CyclicBarrier(N);

		for(int i=0;i<N;i++) {
			new Writer(barrier).start();
		}

		try {
			//todo 确保上面的都执行完了，不然会混到一起
			Thread.sleep(3500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("CyclicBarrier重用");
		for(int i=0;i<N;i++) {
			new Writer(barrier).start();
		}
	}
	
}
