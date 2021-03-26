package multiThread.concurrent.t01__synchronized;

import com.study.wjw.z_utils.Log;
import com.study.wjw.z_utils.Threader;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/*

CyclicBarrier 循环屏障 ['saɪklɪk]['bæriə]
超时会抛出TimeoutException异常,CyclicBarrier机制解除，
后面会抛 BrokenBarrierException
await(long timeout, TimeUnit unit)throws InterruptedException,
						BrokenBarrierException,TimeoutException{}   									 */
public class T_16_CyclicBarrier___think {
	static class Writer extends Thread{
		private CyclicBarrier cyclicBarrier;
		private boolean outTime = false;
		public Writer(CyclicBarrier cyclicBarrier_,boolean outTime_) {
			cyclicBarrier = cyclicBarrier_;
			outTime = outTime_;
		}
		public void run() {
			Log.i("线程"+Thread.currentThread().getName()+"正在写入数据...");
			try {
				Thread.sleep(1000);//todo 以睡眠来模拟写入数据操作
				Log.i("线程"+Threader.getName()+"写入完毕，等其他线程写入完毕");
				if (outTime)
					cyclicBarrier.await(400, TimeUnit.MILLISECONDS);
				else
					cyclicBarrier.await();
			} catch (TimeoutException e) {
			} catch (InterruptedException e) {
			} catch(BrokenBarrierException e){}
			Log.i("线程"+Threader.getName()+"所有线程写入完毕，继续处理其他任务");
		}
	}
	public static void main(String[] args) throws InterruptedException{
		int number = 4;
		CyclicBarrier barrier  = new CyclicBarrier(number,new Runnable(){
			@Override
			public void run() {
				Log.i("随机选择线程"+Threader.getName()+"XXX执行最后操作XXX");
			}
		});
		for(int i = 0; i < number; i++)
			new Writer(barrier,false).start();
		Thread.sleep(3500);//todo 确保上面的都执行完了，不然会混到一起
		Log.i("CyclicBarrier重用");
		for(int i = 0; i < number; i++) {
			new Writer(barrier,i == 0 ).start();
			Thread.sleep(500);
		}
	}
}
