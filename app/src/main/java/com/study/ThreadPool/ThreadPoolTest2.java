package com.study.ThreadPool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolTest2 {
	
	private static Executor executors = Executors.newCachedThreadPool();//缓存线程池
	private static Executor executor2 = Executors.newFixedThreadPool(5);//固定线程个数的线程池
	private static Executor executor3 = Executors.newScheduledThreadPool(5);//计划任务线程池
	private static Executor executor4 = Executors.newSingleThreadExecutor();//单个线程的线程池
	
	public static void main(String[] args) {
		
//		BlockingQueue<E>//单端队列
//		BlockingDeque<E>双端队列
		
		LinkedBlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<Runnable>(100);//100是该容器的最大上限
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
		
		
//		ArrayList<E> 
		
//		LinkedList<E>
		
		/**
		 * 参数1：核心池个数
		 * 参数2：最大线程池上限个数
		 * 参数3：任务执行完之后，要裁员的延时
		 * 参数4：时间单位
		 * 参数5：用于存储任务的工作队列
		 * 参数6：线程工厂，用于创建线程
		 */
		ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1, 10, 1, TimeUnit.SECONDS, blockingQueue, threadFactory);
		
		
		
		/*
		 * 线程不是越多越好，Google工程给了一个推荐值   ：  线程的个数=CPU核心数+1 = 5;
		 */
		
		
		for(int i=0;i<111;i++){
			
			poolExecutor.execute(new Runnable() {
				
				@Override
				public void run() {
					try {
						method();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
			
			/*executor2.execute(new Runnable() {
				
				@Override
				public void run() {
					try {
						method();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});*/
		}
	}
	
	
	//同时最多只允许5个线程过来
	public static void method() throws InterruptedException{
		System.out.println("ThreadName="+Thread.currentThread().getName()+"过来了");
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("ThreadName="+Thread.currentThread().getName()+"出去了");
	}
	
	
}
