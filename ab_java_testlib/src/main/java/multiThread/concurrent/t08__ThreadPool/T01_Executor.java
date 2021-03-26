package multiThread.concurrent.t08__ThreadPool;

import com.study.wjw.z_utils.Log;
import com.study.wjw.z_utils.Threader;

import java.util.concurrent.Executor;


/*
 线程池
 Executor - 线程池底层处理机制。
 在使用线程池的时候，底层如何调用线程中的逻辑。
 */
public class T01_Executor implements Executor {
	public static void main(String[] args) {
		new T01_Executor().execute(new Runnable() {
			@Override
			public void run() {
				Log.i(Threader.getName() + " - test executor");
			}
		});
	}


	//在未来某个时间执行给定的命令。该命令可能在新的线程、已入池的线程或者正调用的线程中执行，
	// 这由 Executor 实现决定
	@Override
	public void execute(Runnable command) {
		new Thread(command).start();
	}
}
