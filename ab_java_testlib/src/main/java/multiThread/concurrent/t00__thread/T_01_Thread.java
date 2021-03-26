package multiThread.concurrent.t00__thread;

/**




 */
public class T_01_Thread extends Thread{


	public T_01_Thread() {
	}

	public T_01_Thread(Runnable runnable) {
		super(runnable);
	}

	public T_01_Thread(ThreadGroup threadGroup, Runnable runnable) {
		super(threadGroup, runnable);
	}

	public T_01_Thread(String s) {
		super(s);
	}

	public T_01_Thread(ThreadGroup threadGroup, String s) {
		super(threadGroup, s);
	}

	public T_01_Thread(Runnable runnable, String s) {
		super(runnable, s);
	}

	public T_01_Thread(ThreadGroup threadGroup, Runnable runnable, String s) {
		super(threadGroup, runnable, s);
	}

	public T_01_Thread(ThreadGroup threadGroup, Runnable runnable, String s, long l) {
		super(threadGroup, runnable, s, l);
	}

	//==========================================================




/*




// 挂起和唤醒线程
publicvoid resume( );     // 不建议使用
publicvoid suspend( );    // 不建议使用

// 终止线程
publicvoid stop( );       // 不建议使用
publicvoid interrupt( );
// 得到线程状态
publicboolean isAlive( );
publicboolean isInterrupted( );
publicstaticboolean interrupted( );
// join方法
publicvoid join( ) throws InterruptedException;

 */




	public static void main(String[] args) throws Exception{
		final T_01_Thread mThread = new T_01_Thread();
		mThread.suspend();
		mThread.resume();

		mThread.stop();
		Thread.sleep(2000);

		mThread.interrupt();
		Thread.interrupted();
		mThread.isInterrupted();

		mThread.join(100);
		Thread.yield();
		mThread.isAlive(); //当线程处于运行状态时，返回true，返回false时，可能线程处于等待状态，也可能处于停止状态。


		Thread aThread = new Thread(new Runnable() {
			@Override
			public void run() {

			}
		});
		aThread.start();
		///aThread.join();
		new Thread(new Runnable() {
			@Override
			public void run() {

			}
		}).start();

		aThread.join();
		System.out.println(Thread.currentThread().getName()
				+ " main last ");
	}
	
}
