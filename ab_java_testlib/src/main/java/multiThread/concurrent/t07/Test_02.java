package multiThread.concurrent.t07;

import com.study.wjw.z_utils.Log;
import com.study.wjw.z_utils.Threader;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


/*
 并发容器 - Queue
 */
public class Test_02 {
	
	static Queue<String> list = new ConcurrentLinkedQueue<>();
	
	static{
		for(int i = 0; i < 10000; i++){
			list.add("String " + i);
		}
	}
	
	public static void main(String[] args) {
		for(int i = 0; i < 10; i++){
			new Thread(new Runnable() {
				@Override
				public void run() {
					while(true){
						String str = list.poll();
						if(str == null){
							break;
						}
						Log.i(Threader.getName() + " - " + str);
					}
				}
			}, "Thread" + i).start();
		}
	}

}
