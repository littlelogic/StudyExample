package multiThread.concurrent.t08__ThreadPool;
import com.study.wjw.z_utils.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/*
 线程池
 固定容量线程池
 */
public class T08_ThreadPoolExecutor {
	private static final int COUNT_BITS = Integer.SIZE - 3;     // 29
	private static final int CAPACITY = (1 << COUNT_BITS) - 1;  // COUNT_BITS == 29

	public static void main(String[] args) {

		Thread jj = new Thread(new Runnable() {
			@Override
			public void run() {

			}
		}){

		};

		String s = Integer.toBinaryString(CAPACITY);
		Log.i(CAPACITY+"的二进制->"+s);

		// 模拟fixedThreadPool， 核心线程5个，最大容量5个，线程的生命周期无限。
		ExecutorService service = 
				new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS, 
						new LinkedBlockingQueue<Runnable>());

		for(int i = 0; i < 6; i++){
			service.execute(new Runnable() {
				@Override
				public void run() {
					try {
						TimeUnit.MILLISECONDS.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName() + " - test executor");
				}
			});
		}
		
		System.out.println(service);
		
		service.shutdown();
		System.out.println(service.isTerminated());
		System.out.println(service.isShutdown());
		System.out.println(service);
		
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		///service.shutdown();
		System.out.println(service.isTerminated());
		System.out.println(service.isShutdown());
		System.out.println(service);
		
	}

}
