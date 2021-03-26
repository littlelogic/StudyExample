
package multiThread.concurrent.t06__Concurren;

import com.study.wjw.z_utils.Log;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;

/*
  并发容器 - ConcurrentMap

  谈谈你理解的 HashMap，讲讲其中的 get put 过程。
	1.8 做了什么优化？
	是线程安全的嘛？
	不安全会导致哪些问题？
	todo         如何解决？有没有线程安全的并发容器？
	ConcurrentHashMap 是如何实现的？ 1.7、1.8 实现有何不同？为什么这么做


 */
public class T01_ConcurrentMap_think {


	public void study_Concurrent(){


		/*

		JDK7 提供了 7 个阻塞队列

		ArrayBlockingQueue ：一个由数组结构组成的有界阻塞队列。
		LinkedBlockingQueue ：一个由链表结构组成的有界阻塞队列。
		PriorityBlockingQueue ：一个支持优先级排序的无界阻塞队列。---
		DelayQueue：一个使用优先级队列实现的无界阻塞队列。
		SynchronousQueue：一个不存储元素的阻塞队列。
		LinkedTransferQueue：一个由链表结构组成的无界阻塞队列。
		LinkedBlockingDeque：一个由链表结构组成的双向阻塞队列。---
		*/


		/*




			    抛异常	   返特殊值   一直阻塞  超时退出
		插入方法	add(e)	   offer(e)	 put(e)	  offer(e,time,unit)
		移除方法	remove()   poll()	 take()	  poll(time,unit)
		检查方法	element()  peek()	 不可用	  不可用						*/

		Map map = new HashMap();
		map = Collections.synchronizedMap(map);
		map = new Hashtable<>();
		map = new ConcurrentHashMap<>();

		map = new TreeMap();
		map = new ConcurrentSkipListMap<>();

		List list = new ArrayList<>();
		list = new Vector<>();
		list = Collections.synchronizedList(list);
		list = new CopyOnWriteArrayList<>();

		Set set = new HashSet();
		set = Collections.synchronizedSet(set);
		set = new CopyOnWriteArraySet<>();

		Queue queue = new ArrayDeque<>();
		queue = new LinkedList<>();
		//todo XX queue = Collections.synchronizedQueue(queue);
		queue = new ConcurrentLinkedQueue<>();
		//一个不存储元素的阻塞队列
		queue = new SynchronousQueue<>();
		//一个由链表结构组成的无界阻塞队列
		queue = new LinkedTransferQueue<>();
		//一个使用优先级队列实现的无界阻塞队列
		queue = new DelayQueue();
		//一个由链表结构组成的有界阻塞队列
		queue = new LinkedBlockingQueue();
		//一个由数组结构组成的有界阻塞队列
		queue = new ArrayBlockingQueue(10);
		//一个支持优先级排序的无界阻塞队列
		queue = new PriorityBlockingQueue();
		//一个由链表结构组成的双向阻塞队列
		queue = new LinkedBlockingDeque();


		{

			PriorityBlockingQueue kkk = null;
			kkk.put(null);
			kkk.add("");
		}





	}

	void test_a(){
		Hashtable hashtable = new Hashtable<>();
		ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap<>();


	}

	HashMap ll;
	static Map<String, String> map = null;
	public static void main(String[] args) {
		T01_ConcurrentMap_think self = new T01_ConcurrentMap_think();
		self.test_a();



		if (true) {
			return;
		}


		HashMap hHashMap =new HashMap();
		hHashMap.put("测试","使map变成有序Map");
		Map map1 = Collections.synchronizedMap(hHashMap);

		if (true) map = new Hashtable<>();
		if (false) map = new ConcurrentHashMap<>();
		if (false) map = new ConcurrentSkipListMap<>();


		final Random r = new Random();
		Thread[] array = new Thread[100];
		final CountDownLatch latch = new CountDownLatch(array.length);
		
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
	}






}
