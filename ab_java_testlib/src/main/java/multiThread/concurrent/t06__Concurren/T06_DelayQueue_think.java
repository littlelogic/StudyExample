package multiThread.concurrent.t06__Concurren;

import com.study.wjw.z_utils.Log;
import com.study.wjw.z_utils.Threader;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;



public class T06_DelayQueue_think {


	/*



	DelayQueue，并发，无界容器，支持延时获取元素的阻塞队列，
	内部采用优先队列PriorityQueue存储元素，同时元素必须实现Delayed；
	在创建元素时可以指定多久才可以从队列中获取当前元素，
	只有在延迟期满时才能从队列中提取元素
	延时出栈获取的关键机制 available.awaitNanos(delay);
	delay可能是前面元素的延时时间，也可能是自己的限制获取时间											 								*/
	public static void main(String[] args)throws Exception {
		long time = System.currentTimeMillis();
		BlockingQueue<MyTask> queue = new DelayQueue<>();
		T06_DelayQueue_think self=new T06_DelayQueue_think();
		//会根据时间进行排序，由小到大
		queue.put(self.new MyTask(time + 1000,"a"));
		queue.put(self.new MyTask(time + 5500,"b"));
		queue.put(self.new MyTask(time + 1500,"c"));
		Log.i(queue);
		Log.i(time);
//		Thread.sleep(6000);

		for(int i = 0; i < 3; i++)
			Log.i(queue.take());
		Log.i("take-89");//
		Log.i(queue.take());
		Log.i("take-last");//todo 不会执行到
	}
	class MyTask implements Delayed {
		private long compareValue;
		private String name;
		public MyTask(long value,String name_){
		    this.compareValue=value;
			this.name = name_;
		}

		/*		比较大小,自动实现升序,建议和getDelay方法配合完成														*/
		@Override
		public int compareTo(Delayed o) {
			return -((int)(this.getDelay(TimeUnit.MILLISECONDS)
					- o.getDelay(TimeUnit.MILLISECONDS)));
		}
/*		获取计划时长的方法,返回一个小于等于0的值时,将发生到期															*/
		@Override //todo 必须实现
		public long getDelay(TimeUnit unit) {
			long differ=compareValue-System.currentTimeMillis();
			return unit.convert(differ, TimeUnit.MILLISECONDS);
		}
		public String toString(){
			return "ThreadName:"+Threader.getName()
					+" name: " + name;
		}
	}
}
