package multiThread.concurrent.t08__ThreadPool;

import com.study.wjw.z_utils.Log;
import com.study.wjw.z_utils.Threader;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/*
 线程池
 固定容量线程池
 newFixedThreadPool - 固定容量线程池。创建线程池的时候，容量固定。
 构造的时候，提供线程池最大容量
 new xxxxx ->


 */
public class T03_newFixedThreadPool {

	/*

	ExecutorService线程池服务类型。所有的线程池类型都实现这个接口。
 	实现这个接口，代表可以提供线程池能力。
 	Executors - Executor的工具类。类似Collection和Collections的关系
 	可以更简单的创建若干种线程池。

	void execute(Runnable var1)
	Future<?> submit(Runnable task);
	调用get方法的时候，如果线程执行成功会直接返回null，
	如果线程执行异常会返回异常的信息
	<T> Future<T> submit(Runnable task, T result);
	当线程正常结束,用Future的get方法会返回result对象，
	当线程抛出异常的时候会获取到对应的异常的信息

	<T> Future<T> submit(Callable<T> task);
	当线程正常结束,Future的get方法返回Callable执行结果，
	当线程抛出异常的时候会获取到对应的异常的信息												*/
	public static void main(String[] args) {
		ExecutorService service = 
				Executors.newFixedThreadPool(5);
		for(int i = 0; i < 6; i++)
			service.execute(new Runnable() {
				@Override
				public void run() {
					try { Thread.sleep(500);
					} catch (InterruptedException e) { }
					Log.i(Threader.getName()+" executorEnd");
				}
			});
		//启动一次顺序关闭，执行以前提交的任务，但不接受新任务。
		// 如果已经关闭，则调用没有其他作用。
		service.shutdown();

		//如果关闭后所有任务都已完成，则返回true。
		Log.i(service.isTerminated());
		//是否已经关闭，是否调用过shutdown方法
		Log.i(service.isShutdown());

		//试图停止所有正在执行的活动任务，暂停处理正在等待的任务，
		// 并返回等待执行的任务列表。
		//无法保证能够停止正在处理的活动执行任务，但是会尽力尝试。
		// 例如，通过 Thread.interrupt() 来取消典型的实现，
		// 所以任何任务无法响应中断都可能永远无法终止。
		service.shutdownNow();
		try {
			//监测ExecutorService是否已经关闭，若关闭则返回true，
			//若超过给定时间，还没关闭，返回false。
			// 一般情况下会和shutdown方法组合使用
			boolean b = service.awaitTermination(2000,
									 TimeUnit.MILLISECONDS);
			Log.i("awaitTermination-end>"+b);//true
		} catch (InterruptedException e) { }
		Log.i("last->"+service.isTerminated());//true
		Log.i(service.isShutdown());//true
	}


/*




<T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)
					  throws InterruptedException
执行给定的任务，当所有任务完成时，返回保持任务状态和结果的 Future 列表。
返回列表的所有元素的 Future.isDone() 为 true。
注意，可以正常地或通过抛出异常来终止已完成 任务。如果正在进行此操作时修改了给定的 collection，则此方法的结果是不确定的。


<T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks,
                              long timeout,
                              TimeUnit unit)
                          throws InterruptedException
执行给定的任务，当所有任务完成或超时期满时（无论哪个首先发生），返回保持任务状态和结果的 Future 列表。
返回列表的所有元素的 Future.isDone() 为 true。一旦返回后，即取消尚未完成的任务。
注意，可以正常地或通过抛出异常来终止已完成 任务。如果此操作正在进行时修改了给定的 collection，则此方法的结果是不确定的。


<T> T invokeAny(Collection<? extends Callable<T>> tasks)
            throws InterruptedException,
                   ExecutionException
执行给定的任务，如果某个任务已成功完成（也就是未抛出异常），则返回其结果。
一旦正常或异常返回后，则取消尚未完成的任务。如果此操作正在进行时修改了给定的 collection，则此方法的结果是不确定的。


<T> T invokeAny(Collection<? extends Callable<T>> tasks,
                long timeout,
                TimeUnit unit)
            throws InterruptedException,
                   ExecutionException,
                   TimeoutException
执行给定的任务，如果在给定的超时期满前某个任务已成功完成（也就是未抛出异常），则返回其结果。
一旦正常或异常返回后，则取消尚未完成的任务。如果此操作正在进行时修改了给定的 collection，则此方法的结果是不确定的。





 */

}
