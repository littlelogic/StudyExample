package multiThread.concurrent.t08__ThreadPool;

import com.study.wjw.z_utils.Log;
import com.study.wjw.z_utils.ThreadName;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;



public class T06_newScheduledThreadPool_think {

	public static void main(String[] args){
		if(true) main_a();
//		if(false) main_b();
//		if(true) main_c();
	}

	/*

	Timer计时器,内置一个线程，两个缺陷
	1.对调度的支持是基于绝对时间的，而不是相对时间，所以它对系统时间的改变非常敏感。
	再有多个任务执行时(任务的执行时间大于间隔时间)，执行时间隔间不能保证
	2.Timer线程是不会捕获异常的，如果TimerTask抛出的了未检查异常则会导致Timer线程终止，
	同时Timer也不会重新恢复线程的执行，他会错误的认为整个Timer线程都会取消。
	针对Timer的缺陷ScheduledExecutorService都有很好的处理

	 */
	static abstract class MRunnable implements Runnable{
		String name = "default";volatile long time ;
		MRunnable(String i){
			name = i;
		} }
	static abstract class MTimerTask extends TimerTask{
		String name = "default";
		volatile long time ;
		MTimerTask(String i){
			name = i;
		}
	}
	public static void main_a(){
		ScheduledExecutorService service =
				Executors.newScheduledThreadPool(31);
		service.schedule(new Runnable() {
			public void run() {
				Log.i(ThreadName.get()+": 延时任务end");
			}
		}, 1, TimeUnit.SECONDS);
		// 循环任务，按照上一次任务的发起时间计算下一次任务的开始时间
		// 线程池的数量大于 任务的数量 时 可以保证执行的时间间隔
		for (int i = 1; i <= 2; i++)
		service.scheduleAtFixedRate(new MRunnable("Rate_"+i) {
			public void run() {
				Log.i(ThreadName.get()+": 循环任务step "+name+
				" differTime "+(System.currentTimeMillis()-time));
				time = System.currentTimeMillis();
				try {Thread.sleep(3000);} catch (Exception e) {}
			}
		}, 1, 1, TimeUnit.SECONDS);
		// 循环任务，以上一次任务的结束时间计算下一次任务的开始时间
		service.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {Thread.sleep(3000);} catch (Exception e) {}
				Log.i(ThreadName.get()+": Delay循环任务step");
			}
		}, 1, 1, TimeUnit.SECONDS);

		try {Thread.sleep(23*1000);} catch (Exception e) {}
		service.shutdownNow();
		try {Thread.sleep(2*1000);} catch (Exception e) {}
		Log.i(service.isTerminated());

		Timer hTimer = new Timer();
		// todo TimerTask的执行间隔不能保证是 3000
		for (int i = 1; i <= 2; i++)
		hTimer.scheduleAtFixedRate(new MTimerTask("Timer_"+i) {
			public void run() {
				Log.i(ThreadName.get()+": step "+name+
				" differTime "+(System.currentTimeMillis()-time) );
				time = System.currentTimeMillis();
				try {Thread.sleep(3000);} catch (Exception e) {}
			}
		},0, 300);
	}

	//todo ScheduledExecutorService可以保证，task1出现异常时，不影响task2的运行：
	public static void main_b(){
		final TimerTask task1 = new TimerTask(){
			@Override
			public void run(){
				throw new RuntimeException();
			}
		};
		final TimerTask task2 = new TimerTask(){
			@Override
			public void run(){
				System.out.println("task2 invoked!");
			}
		};
		ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
		pool.schedule(task1, 100, TimeUnit.MILLISECONDS);
		pool.scheduleAtFixedRate(task2, 0 , 1000, TimeUnit.MILLISECONDS);
	}

	public static void main_c() {
		//todo 线程数是2，可以保证两个任务的执行间隔
		ScheduledExecutorService scheduExec =
				Executors.newScheduledThreadPool(2);
		final long start = System.currentTimeMillis();
		scheduExec.schedule(new Runnable() {
			public void run() {
				//todo 1000 左右开始
				System.out.println("timerOne,the time:" +
						(System.currentTimeMillis() - start));
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		},1000,TimeUnit.MILLISECONDS);
		scheduExec.schedule(new Runnable() {
			public void run() {
				//todo 2000 左右开始
				System.out.println("timerTwo,the time:" +
						(System.currentTimeMillis() - start));
			}
		},2000,TimeUnit.MILLISECONDS);
	}

}
