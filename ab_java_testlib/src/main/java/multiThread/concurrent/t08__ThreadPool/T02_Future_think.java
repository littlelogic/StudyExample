package multiThread.concurrent.t08__ThreadPool;

import com.study.wjw.z_utils.Log;
import com.study.wjw.z_utils.Threader;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;


/*
 线程池
 固定容量线程池
 */
public class T02_Future_think {
	
	public static void main(String[] args) throws
			InterruptedException, ExecutionException {

		ExecutorService service=Executors.newFixedThreadPool(3);
		Future<String> future=service.submit(new Callable<String>(){
			@Override
			public String call() {
				try {
					TimeUnit.MILLISECONDS.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("aaa");
				return Threader.getName() + " - test executor";
			}
		});
		System.out.println(future);

		// 查看线程是否结束， 任务是否完成。 call方法是否执行结束
		System.out.println(future.isDone());

		// 获取call方法的返回值。
		System.out.println(future.get());
		System.out.println(future.isDone());
	}


	public static void study() throws
			InterruptedException, ExecutionException {
		// FutureTask<V> implements Runnable,Future<V> {
		// todo 因为FutureTask 类是 Future 的一个实现
		FutureTask<String> futureTask = null;
		Runnable hRunnable = new Runnable() {
			@Override
			public void run() {
				Log.i("FirstFutureTask-run");
			}
		};
		futureTask=new FutureTask(hRunnable,"FirstFutureTask");
		new Thread(futureTask).start();
		Log.i("FirstFutureTask.get: "+futureTask.get());

		futureTask =new FutureTask<String>(new Callable<String>(){
			@Override
			public String call() throws Exception {
				Log.i("SecondFutureTask-run");
				return "SecondFutureTask";
			}
		});
		new Thread(futureTask).start();
		Log.i("SecondFutureTask.get: "+futureTask.get());
	}

	/*


	public interface Callable<V> {
		V call() throws Exception;
	}

	interface Future<V>
	是对于具体的Runnable或者Callable任务的执行结果
	进行取消、查询是否完成、获取结果。
	必要时可以通过get方法获取执行结果，
	该方法会阻塞直到任务返回结果
	判断任务是否完成；
	能够中断任务；
	能够获取任务执行结果。

	用来取消任务，取消成功返回true，失败返回false
	参数mayInterruptIfRunning，设置true，表示可以取消
	正在执行却没有执行完毕的任务。
	如果任务已经完成，此方法肯定返回false；
	如果任务还没有执行,肯定返回true。
	任务正在执行，"参数"设置为true，则返回true，
	若"参数"设置为false，则返回false；
	此方法返回后，对isDone()的后续调用将始终返回true。
	此方法返回true，则isCancelled()的后续调用返回true
	boolean cancel(boolean mayInterruptIfRunning);

	任务是否被取消成功，
	如果在任务正常完成前被取消成功，则返回 true。
	boolean isCancelled();

	isDone方法表示任务是否已经完成，若任务完成，则返回true；
	boolean isDone();

	获取执行结果，会产生阻塞，一直等到任务执行完毕才返回
	V get() throws InterruptedException,
								ExecutionException;

	获取执行结果，如果在指定时间内，还没获取到结果，
	就直接返回"null"，
	todo 也可能直接抛出异常没有赋值的过程
	V get(long var1, TimeUnit var3) throws
						InterruptedException,
						ExecutionException,
						 TimeoutException;

	 */

}
