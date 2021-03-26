
package multiThread.concurrent.t08__ThreadPool;

import com.study.wjw.z_utils.Log;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;


/*



 Cup
 硬件核心，主板上面插了几个cup，一般的主板cup插槽是1，2，4，8的插槽
 逻辑核心，4核心8核心，可以同时让今个线程一起运行
 Cup 4核心8线程，一个核心在超频的时候“几乎”相当于同时跑了两个线程，实际还是一个线程。
 ThreadGroup线程组

 线程池
 分支合并线程池。
 有递归的思想
 */
public class T07_ForkJoinPool_think {


	/*


	ForkJoinPool 分支合并线程池 是JDK7引入的线程池，核心思想是将
	大的任务拆分成多个小任务（fork），然后在将多个小任务处理
	汇总到一个结果上（join），非常像MapReduce处理原理,有递归的思想
	分而治之，适合的场景？？？													*/
	public static void main(String[] args) throws Exception{
		//计算数组数据的总和
		long time = System.currentTimeMillis();
		long result = 0L;
		for(int i = 0; i < numbers.length; i++)
			result += numbers[i];
		long differ = System.currentTimeMillis()-time;
		Log.i("time: "+differ +" 结果:"+result);

		time = System.currentTimeMillis();
		ForkJoinPool pool = new ForkJoinPool();
		ForkJoinTask task = new AddTask(0, numbers.length);
		Future<Long> future = pool.submit(task);
		result = future.get();
		differ = System.currentTimeMillis() - time;
		Log.i("time: "+differ +" 结果:"+result);
	}
	final static int[] numbers = new int[90000000];
	final static int MAX_SIZE = 50000;
	final static Random r = new Random();
	static{
		for(int i = 0; i < numbers.length; i++)
			numbers[i] = r.nextInt(1000);
	}
	static class AddTask extends RecursiveTask<Long>{
		int begin, end;
		public AddTask(int begin_, int end_){
			begin = begin_;  end = end_;
		}
		@Override
		protected Long compute(){
			if((end - begin) < MAX_SIZE){
				long sum = 0L;
				for(int i = begin; i < end; i++)
					sum += numbers[i];
				return sum;
			}else{
				int middle = begin + (end - begin)/2;
				AddTask task1 = new AddTask(begin, middle);
				AddTask task2 = new AddTask(middle, end);
				// 开启一个新的分支线程执行 任务
				task1.fork();
				task2.fork();
				//合并,获取任务结果,阻塞方法,知道数据返回
				return task1.join() + task2.join();
			}
		}
	}

	/*
	通常情况下我们不需要直接继承ForkJoinTask类，而只需要继承它的子类，Fork/Join框架提供了以下两个子类：
	RecursiveAction：用于没有返回结果的任务。
	RecursiveTask ：用于有返回结果的任务。
	getQueuedSubmissionCount—获取所有待执行的任务数；
　　getRunningThreadCount—获取正在运行的任务数。

	Java 8为ForkJoinPool添加了一个通用线程池，这个线程池用来处理那些没有被显式提交到任何线程池的任务。
	它是ForkJoinPool类型上的一个静态元素，它拥有的默认线程数量等于运行计算机上的处理器数量。
	当调用Arrays类上添加的新方法时，自动并行化就会发生。比如用来排序一个数组的并行快速排序，
	用来对一个数组中的元素进行并行遍历。自动并行化也被运用在Java 8新添加的Stream API中。

	List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
	numbers.parallelStream().forEach(out::println);




	 */


}
