package multiThread.concurrent.t01__synchronized;

/**
 * synchronized关键字
 * 同步方法 - 原子性
 * 加锁的目的： 就是为了保证操作的原子性
 */
public class T_03_保证操作的原子性 implements Runnable {//Runnable的run方法可以加synchronized，

	private int count = 0;
	
	@Override
	public /*synchronized*/ void run() {
		System.out.println(Thread.currentThread().getName() 
				+ " count = " + count++);
	}
	
	public static void main(String[] args) {
		T_03_保证操作的原子性 t = new T_03_保证操作的原子性();
		for(int i = 0; i < 5; i++){
			new Thread(t, "Thread - " + i).start();
		}
	}
	
}
