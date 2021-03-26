package multiThread.concurrent.t01__synchronized;

import java.util.concurrent.TimeUnit;


/**
 * synchronized关键字
 * 同步方法 - 继承
 * 子类同步方法覆盖父类同步方法。可以指定调用父类的同步方法。
 * 相当于锁的重入。
 */
public class T_07_synchronized与方法覆盖无关 implements Runnable {//Runnable的run方法可以加synchronized，
	
	synchronized void m(){
		System.out.println("Super Class m start");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Super Class m end");
	}

	@Override
	public /*synchronized*/ void run() {

	}
	
	public static void main(String[] args) {
		new Sub_Test_07().m();
	}
	
}

class Sub_Test_07 extends T_07_synchronized与方法覆盖无关 {
	@Override
	/*synchronized*/ void m(){
		System.out.println("Sub Class m start");
		super.m();
		System.out.println("Sub Class m end");
	}
}
