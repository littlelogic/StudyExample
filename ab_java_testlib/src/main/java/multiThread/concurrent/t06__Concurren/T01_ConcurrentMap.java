
package multiThread.concurrent.t06__Concurren;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;

/*
  并发容器 - ConcurrentMap

  谈谈你理解的 HashMap，讲讲其中的 get put 过程。
	1.8 做了什么优化？
	是线程安全的嘛？
	不安全会导致哪些问题？
	todo         如何解决？有没有线程安全的并发容器？
	ConcurrentHashMap 是如何实现的？ 1.7、1.8 实现有何不同？为什么这么做？
 */
public class T01_ConcurrentMap {

	HashMap ll;
	static Map<String, String> map = null;
	public static void main(String[] args) {
		HashMap hHashMap =new HashMap();
		hHashMap.put("测试","使map变成有序Map");
		Map map1 = Collections.synchronizedMap(hHashMap);

		if (true) map = new Hashtable<>();
		if (false) map = new ConcurrentHashMap<>();
		if (false) map = new ConcurrentSkipListMap<>();
		final Random r = new Random();
		Thread[] array = new Thread[100];
		final CountDownLatch latch = new CountDownLatch(array.length);

		Stack ddd = null;


		ConcurrentHashMap  hConcurrentHashMap = new ConcurrentHashMap();
		hConcurrentHashMap.put( "",""   );
		
		long begin = System.currentTimeMillis();
		for(int i = 0; i < array.length; i++){
			array[i] = new Thread(new Runnable() {
				@Override
				public void run() {
					for(int j = 0; j < 10000; j++){
						map.put("key"+r.nextInt(100000), "value"+r.nextInt(100000));
					}
					latch.countDown();
				}
			});
		}
		for(Thread t : array){
			t.start();
		}
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println("执行时间为 ： " + (end-begin) + "毫秒！");
		////----
		ConcurrentSkipListMap hConcurrentSkipListMap = null;
//		hConcurrentSkipListMap = new
//		hConcurrentSkipListMap.pu




	}



}
