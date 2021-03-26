package multiThread.concurrent.t01__synchronized;

/**
 * synchronized关键字
 * 同步粒度问题
 * 尽量在商业开发中避免同步方法。使用同步代码块。 细粒度解决同步问题。
 * 可以提高效率。
 */
public class T_12_避免重锁 {
	
	synchronized void m1(){
		// 前置逻辑
		System.out.println("同步逻辑");
		// 后置逻辑
	}
	
	void m2(){
		// 前置逻辑
		synchronized (this) {
			System.out.println("同步逻辑");
		}
		// 后置逻辑
	}
	
}