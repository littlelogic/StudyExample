package multiThread.concurrent.t08__ThreadPool;

import com.study.wjw.z_utils.Log;
import com.study.wjw.z_utils.Threader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;


/*

 Cup
 硬件核心，主板上面插了几个cup，一般的主板cup插槽是1，2，4，8的插槽
 逻辑核心，4核心8核心，可以同时让今个线程一起运行
 Cup 4核心8线程，一个核心在超频的时候“几乎”相当于同时跑了两个线程，实际还是一个线程。

 线程池
 无容量限制的线程池（最大容量默认为Integer.MAX_VALUE）
 默认线程空闲 60 秒，自动销毁
 */
public class T05_newCachedThreadPool_think {


	/*


	newCachedThreadPool,缓存型池子,无容量限制的线程池,最大容量默认为
	Integer.MAX_VALUE）使用时先查看池中有没有以前建立的线程，如果有，
	就reuse，如果没有，就建一个新的线程加入池中，
	缓存型池子通常用于执行一些生存期很短的异步型任务,
	因此在一些面向连接的 daemon 型 SERVER 中用得不多。
	但对于生存期短的异步任务，它是 Executor 的首选。
	能reuse的线程，必须是timeout IDLE 内的池中线程，缺省timeout是60s,
	超过这个 IDLE 时长，线程实例将被终止及移出池。
	注意，放入 CachedThreadPool 的线程不必担心其结束，
	超过 TIMEOUT 不活动，其会自动被终止。																 */
	static int count = 0;
	public static void main(String[] args)
			throws InterruptedException, ExecutionException {
		ExecutorService service=Executors.newCachedThreadPool();
		ThreadFactory hThreadFactory = new ThreadFactory(){
			@Override
			public Thread newThread(Runnable runnable) {
				return new Thread(runnable,
						"newCachedThreadPool"+(count++));
			}
		};
		service = Executors.newCachedThreadPool();

		System.out.println(service);
		List<Future> futureList = new ArrayList<>();
		for(int i = 0; i < 5; i++)
			futureList.add(service.submit(new Callable<String>(){
				@Override
				public String call() throws Exception {
					return Threader.getName()+" call_last";
				}
			}));
		System.out.println(service);

		for(int i = 0; i < futureList.size(); i++)
			Log.i(futureList.get(i).get());
		System.out.println(service);
		try {//todo 默认线程空闲 60 秒，自动销毁
			TimeUnit.SECONDS.sleep(65);
		} catch (InterruptedException e) {}
		Log.i(service);//todo 池中已没有线程
	}

}
