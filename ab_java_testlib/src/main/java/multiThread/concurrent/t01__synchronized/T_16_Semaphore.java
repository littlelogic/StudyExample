package multiThread.concurrent.t01__synchronized;

import com.study.wjw.z_utils.Log;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/*

Semaphore ['seməfɔː] 信号量
类似厕所的坑位

public Semaphore(int permits, boolean fair) {
//这个多了一个参数fair表示是否是公平的，即等待时间越久的越先获取许可

public void acquire() throws InterruptedException {  }     //获取一个许可
public void acquire(int permits) throws InterruptedException { }    //获取permits个许可
public void release() { }          //释放一个许可
public void release(int permits) { }    //释放permits个许可


//尝试获取一个许可，若获取成功，则立即返回true，若获取失败，则立即返回false
boolean tryAcquire() { };

//尝试获取一个许可，若在指定的时间内获取成功，则立即返回true，否则则立即返回false
boolean tryAcquire(long timeout, TimeUnit unit) throws InterruptedException { };

//尝试获取permits个许可，若获取成功，则立即返回true，若获取失败，则立即返回false
boolean tryAcquire(int permits) { };

//尝试获取permits个许可，若在指定的时间内获取成功，则立即返回true，否则则立即返回false
boolean tryAcquire(int permits, long timeout, TimeUnit unit) throws InterruptedException { };

另外还可以通过availablePermits()方法得到可用的许可数目。

 */
public class T_16_Semaphore {

	static class Worker extends Thread{
		private int num;
		private Semaphore semaphore;
		public Worker(int num,Semaphore semaphore){
			this.num = num;
			this.semaphore = semaphore;
		}

		@Override
		public void run() {
			try {
				semaphore.acquire();
				Log.i("工人" + this.num + "占用一个机器在生产...");
				Thread.sleep(1000);
				Log.i("工人"+this.num+"释放出机器");
				semaphore.release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		int N = 12;            //工人数
		Semaphore semaphore = new Semaphore(5); //机器数目
		for(int i=0;i<N;i++) {
			new Worker(i,semaphore).start();
		}

	}
	
}
