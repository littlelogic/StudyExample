package com.study.ThreadPool;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolTest3 {
	
	private static Executor executors = Executors.newCachedThreadPool();//缓存线程池
	private static Executor executor2 = Executors.newFixedThreadPool(5);//固定线程个数的线程池
	private static Executor executor3 = Executors.newScheduledThreadPool(5);//计划任务线程池
	private static Executor executor4 = Executors.newSingleThreadExecutor();//单个线程的线程池
	
	public static void main(String[] args) {
		
//		BlockingQueue<E>//单端队列
//		BlockingDeque<E>双端队列
		
		LinkedBlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<Runnable>(12);//100是该容器的最大上限
		//创建线程工厂
		ThreadFactory threadFactory = new ThreadFactory() {
//			int i = 0;
			//线程安全的int的包装类
			AtomicInteger atomicInteger = new AtomicInteger(0);
			
			@Override
			public Thread  newThread(Runnable r) {
				//创建一个线程，然后把r赋值给该线程
				Thread thread = new Thread(r);
				thread.setName("MyThread="+atomicInteger.getAndIncrement());
				return thread;
			}
		};
		ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 12, 1, TimeUnit.SECONDS, blockingQueue, threadFactory);
		///--poolExecutor.getPoolSize()
		for(int i = 0; i < (5 + 12 + 3); i++){
			final int index = i;
			if (i > 5) {
				poolExecutor.execute(new Runnable() {
					@Override
					public void run() {
						try {
							Thread.sleep(50);
						} catch (Exception e) {
							e.printStackTrace();
						}
						System.out.println("--" +index+ "--ThreadName="+Thread.currentThread().getName()+"过来了Over");
					}
				});
			} else {
				poolExecutor.execute(new Runnable() {
					@Override
					public void run() {
						System.out.println("--" +index+ "--ThreadName="+Thread.currentThread().getName()+"过来了");
						try {
							Thread.sleep(1000 * 30);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}

			

		}
	}
	

	
}
