package multiThread.concurrent.t06__Concurren;

import com.study.wjw.z_utils.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

public class T02_CopyOnWriteList_think {

	/*

CopyOnWriteArrayList原理：
在写的时候不对原集合进行修改，而是重新复制一份，修改完之后，再移动指针

在add()在添加集合的时候加上了锁，保证了同步，避免了多线程写的时候会Copy出N个副本出来。
(想想，你在遍历一个10个元素的集合，每遍历一次有1人调用add方法，你说当你遍历10次，
这add方法是不是得被调用10次呢？是不是得copy出10分新集合呢？万一这个集合非常大呢？)
那么？你还要问？CopyOnWriteArrayList是怎么解决线程安全问题的？答案就是----写时复制，加锁
还要问？那么有没有这么一种情况，当一个线程刚好调用完add()方法，也就是刚好执行到上面1处的代码，
也就是刚好将引用指向心数组，而此时有线程正在遍历呢？会不会报错呢？
（答案是不会的，因为你正在遍历的集合是旧的，这就有点难受啦，哈哈~）

缺点：
1、耗内存（集合复制）
2、实时性不高

优点：
1、数据一致性完整，为什么？因为加锁了，并发数据不会乱
2、解决了像ArrayList、Vector这种集合多线程遍历迭代问题，记住，Vector虽然线程安全，只不过是加了synchronized关键字，迭代问题完全没有解决！

5、CopyOnWriteArrayList使用场景
1、读多写少（白名单，黑名单，商品类目的访问和更新场景），为什么？因为写的时候会复制新集合
2、集合不大，为什么？因为写的时候会复制新集合，时性要求不高，为什么，因为有可能会读取到旧的集合数据





	CopyOnWriteList,并发,在写的时候不对原集合进行修改，
	而是重新复制一份，修改完之后，再移动指针
	正在遍历的集合可能是最新的集合
	解决了像ArrayList、Vector这种集合多线程遍历迭代问题，
	Vector虽然线程安全，只不过是加了synchronized关键字，  								 */

	//todo 若直接用于多线程环境，会有 越界 报错等
	static List<String> list = new ArrayList<>();
	public static void main(String[] args) {
		Thread[] array = new Thread[100];
		final Random r = new Random();
		final CountDownLatch latch =
				new CountDownLatch(array.length);
		//todo 可运行，有同步问题
		list = new ArrayList<>(array.length*1000);
		//todo 下面两种可运行，线程安全
		list = new Vector<>();
		list = new CopyOnWriteArrayList<>();

		long begin = System.currentTimeMillis();
		for(int i = 0; i < array.length; i++)
			array[i] = new Thread(new Runnable() {
				@Override
				public void run() {
					for(int j = 0; j < 1000; j++)
						list.add(
							"V"+r.nextInt(1000));
					latch.countDown();
				}
			});
		for(Thread t : array)
			t.start();
		try {
			latch.await();
		} catch (InterruptedException e) { }
		long end = System.currentTimeMillis();
		Log.i("执行时间 ： " +(end-begin)+"毫秒！");
		Log.i("List.size() : " + list.size());
	}

	/*







	 */

}
