package multiThread.concurrent.t07;

import com.study.wjw.z_utils.Log;
import com.study.wjw.z_utils.Threader;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


/*
 普通容器
 */
public class Test_01 {


	static boolean vector_mark = true;
	static List<String> list = new ArrayList<>();

	static{
		if (vector_mark)
			list = new Vector<>();
		else
			list = new ArrayList<>();
		for(int i = 0; i < 10000; i++){
			list.add("String " + i);
		}
	}
	
	public static void main(String[] args) {
		if (vector_mark)
			test_Vector();
		else
			test_ArrayList();
	}

	static void test_Vector() {
		for(int i = 0; i < 10; i++)
			new Thread(new Runnable() {
				@Override
				public void run() {
					while(list.size() > 0)
						Log.i(Threader.getName() + " - " + list.remove(0));
				}
			}, "Thread" + i).start();
	}

	static void test_ArrayList() {
		for(int i = 0; i < 10; i++)
			new Thread(new Runnable() {
				@Override
				public void run() {
					while(true)
						synchronized (list) {
							if(list.size() <= 0){
								break;
							}
							Log.i(Threader.getName() + " - " + list.remove(0));
						}
				}
			}, "Thread" + i).start();
	}


}
