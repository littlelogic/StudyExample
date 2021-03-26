package multiThread.concurrent.t08__ThreadPool;

import com.study.wjw.z_utils.Log;
import com.study.wjw.z_utils.ThreadName;
import com.study.wjw.z_utils.Threader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


/**
 * 线程池
 * 容量为1的线程池。 顺序执行。
 */
public class T04_newSingleThreadExecutor_think {
	
	public static void main(String[] args) throws
			InterruptedException,ExecutionException{
		// 容量为1的线程池,顺序执行。用的是和cache池相同的底层池,
		// 0 秒 IDLE（无 IDLE）
		ExecutorService service =
				Executors.newSingleThreadExecutor();
		Log.i("Single: "+service);
		Runnable hRunnable = new Runnable() {
			@Override
			public void run() {
				try {
					TimeUnit.MILLISECONDS.sleep(500);
				} catch (InterruptedException e) { }
				Log.i(ThreadName.get()+"Single_run_end");
			}
		};
		List<Future> futureList = new ArrayList<>();
		for(int i = 0; i < 5; i++)
			futureList.add(service.submit(hRunnable));
		Log.i("Single: "+service);
		for (int i = 0; i < futureList.size(); i++)
			Log.i("阻塞result："+futureList.get(i).get());

/*		有最大线程数量。根据需求增加线程
		用的是和cache池相同的底层池,0秒IDLE（无IDLE）											*/
		service = Executors.newFixedThreadPool(8);
		Log.i("Fixed此时数量为0: "+service);
		futureList.clear();
		for(int i = 0; i < 5; i++)
			futureList.add(service.submit(newCallable()));
		Log.i("Fixed此时数量为5: "+service);
		for (int i = 0; i < futureList.size(); i++)
			Log.i(i+"阻塞："+futureList.get(i).get());
		for(int i = 0; i < 12; i++)
			futureList.add(service.submit(newCallable()));
		Log.i("Fixed此时数量Max为8: "+service);
	}
	static Callable newCallable(){
		return new Callable() {
			@Override
			public String call() {
				try {
					TimeUnit.MILLISECONDS.sleep(500);
				} catch (InterruptedException e) { }
				return Threader.getName() + " call_end";
			}
		};
	}

}
