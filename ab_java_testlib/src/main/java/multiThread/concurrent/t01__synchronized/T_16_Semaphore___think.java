package multiThread.concurrent.t01__synchronized;

import com.study.wjw.z_utils.Log;

import java.util.concurrent.Semaphore;

/*

Semaphore ['seməfɔː] 信号量
类似 厕所的坑位 的限流

//释放permits个许可
public void release(int permits) { }

//获取permits个许可
public void acquire(int permits) throws InterruptedException{}

//尝试获取permits个许可，若获取成功，则立即返回true
boolean tryAcquire(int permits) { };

//尝试获取permits个许可，若在指定的时间内获取成功，则立即返回true
boolean tryAcquire(int permits, long timeout, TimeUnit unit)
								throws InterruptedException{};                                          */
public class T_16_Semaphore___think {
	static class Worker extends Thread{
		private int num;
		private Semaphore semaphore;
		public Worker(int num,Semaphore semaphore){
			this.num = num;
			this.semaphore = semaphore;
		}
		public void run() {
			try {
				semaphore.acquire();
				Log.i("工人"+this.num+"占用一个机器在生产");
				Thread.sleep(1000);
				Log.i("工人"+this.num+"释放出机器");
				semaphore.release();
			} catch (InterruptedException e) { }
		}
	}
	public static void main(String[] args) {
		Semaphore semaphore = new Semaphore(5,false);
		for(int i=0;i < 12;i++)
			new Worker(i,semaphore).start();
	}
}
