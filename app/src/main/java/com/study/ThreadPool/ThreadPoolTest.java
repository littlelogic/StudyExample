package com.study.ThreadPool;

import java.util.concurrent.Semaphore;

public class ThreadPoolTest {
	//信号量
	private static Semaphore semaphore = new Semaphore(3);//允许个数，相当于放了3把锁
	
	public static void main(String[] args) {
		
		for(int i=0;i<100;i++){
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						method();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
			}).start();
		}
		
	}
	
	
	//同时最多只允许3个线程过来
	public static void method() throws InterruptedException{
		semaphore.acquire();//获取一把锁
		System.out.println("ThreadName="+Thread.currentThread().getName()+"过来了");
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("ThreadName="+Thread.currentThread().getName()+"出去了");
		semaphore.release();//释放一把锁
	}
	
	
}
