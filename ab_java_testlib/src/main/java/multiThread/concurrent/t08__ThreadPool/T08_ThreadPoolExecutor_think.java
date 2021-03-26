package multiThread.concurrent.t08__ThreadPool;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/*
 线程池
 固定容量线程池
 */
public class T08_ThreadPoolExecutor_think {

	public static void study() {
		ExecutorService service1 =
				Executors.newFixedThreadPool(5);
		service1 = new ThreadPoolExecutor(5, 5, 0L,
				TimeUnit.MILLISECONDS,new LinkedBlockingQueue());

		ExecutorService service2 =
				Executors.newSingleThreadExecutor();
		service2 = new ThreadPoolExecutor(1, 1, 0L,
				TimeUnit.MILLISECONDS, new LinkedBlockingQueue());

		ExecutorService service3 = Executors.newCachedThreadPool();
		service3 = new ThreadPoolExecutor(0, 2147483647, 60L,
				TimeUnit.SECONDS, new SynchronousQueue());

		ScheduledExecutorService service4 =
				Executors.newScheduledThreadPool(31);
		/*public static ScheduledExecutorService
		 newScheduledThreadPool(int corePoolSize) {
			return new ScheduledThreadPoolExecutor(corePoolSize);
		}
		//ScheduledThreadPoolExecutor():
		public ScheduledThreadPoolExecutor(int corePoolSize) {
			super(corePoolSize, Integer.MAX_VALUE,
					DEFAULT_KEEPALIVE_MILLIS, MILLISECONDS,
					new DelayedWorkQueue());
		}*/

		ExecutorService service =
			new ThreadPoolExecutor(
					5,//核心线程数量
					5,//最大线程数量
					0L, TimeUnit.MILLISECONDS,//超时时间
					new LinkedBlockingQueue<Runnable>(),//阻塞队列
					Executors.defaultThreadFactory(),//线程构造接口
					new RejectedExecutionHandler(){//异常处理接口
						@Override
						public void rejectedExecution(Runnable runnable,
								ThreadPoolExecutor threadPoolExecutor) {

						}
					});
	}

	/*
	int corePoolSize => 该线程池中核心线程数最大值
	核心线程：
	线程池新建线程的时候，如果当前线程总数小于corePoolSize，则新建的是核心线程，如果超过corePoolSize，则新建的是非核心线程
	核心线程默认情况下会一直存活在线程池中，即使这个核心线程啥也不干(闲置状态)。
	如果指定ThreadPoolExecutor的allowCoreThreadTimeOut这个属性为true，那么核心线程如果不干活(闲置状态)的话，超过一定时间(时长下面参数决定)，就会被销毁掉
	很好理解吧，正常情况下你不干活我也养你，因为我总有用到你的时候，但有时候特殊情况(比如我自己都养不起了)，那你不干活我就要把你干掉了

	int maximumPoolSize
	该线程池中线程总数最大值
	线程总数 = 核心线程数 + 非核心线程数。核心线程在上面解释过了，这里说下非核心线程：
	不是核心线程的线程

	long keepAliveTime
	该线程池中非核心线程闲置超时时长
	一个非核心线程，如果不干活(闲置状态)的时长超过这个参数所设定的时长，就会被销毁掉
	如果设置allowCoreThreadTimeOut = true，则会作用于核心线程

	TimeUnit unit
	keepAliveTime的单位，TimeUnit是一个枚举类型，其包括：
	NANOSECONDS ： 1微毫秒 = 1微秒 / 1000
	MICROSECONDS ： 1微秒 = 1毫秒 / 1000
	MILLISECONDS ： 1毫秒 = 1秒 /1000
	SECONDS ： 秒
	MINUTES ： 分
	HOURS ： 小时
	DAYS ： 天

	BlockingQueue workQueue
	该线程池中的任务队列：维护着等待执行的Runnable对象
	当所有的核心线程都在干活时，新添加的任务会被添加到这个队列中等待处理，如果队列满了，则新建非核心线程执行任务
	常用的workQueue类型：

	SynchronousQueue：
	这个队列接收到任务的时候，会直接提交给线程处理，而不保留它，如果所有线程都在工作怎么办？那就新建一个线程来处理这个任务！所以为了保证不出现<线程数达到了maximumPoolSize而不能新建线程>的错误，使用这个类型队列的时候，maximumPoolSize一般指定成Integer.MAX_VALUE，即无限大

	LinkedBlockingQueue：
	这个队列接收到任务的时候，如果当前线程数小于核心线程数，则新建线程(核心线程)处理任务；如果当前线程数等于核心线程数，则进入队列等待。由于这个队列没有最大值限制，即所有超过核心线程数的任务都将被添加到队列中，这也就导致了maximumPoolSize的设定失效，因为总线程数永远不会超过corePoolSize

	ArrayBlockingQueue：
	可以限定队列的长度，接收到任务的时候，如果没有达到corePoolSize的值，则新建线程(核心线程)执行任务，如果达到了，则入队等候，如果队列已满，则新建线程(非核心线程)执行任务，又如果总线程数到了maximumPoolSize，并且队列也满了，则发生错误

	DelayQueue：
	队列内元素必须实现Delayed接口，这就意味着你传进去的任务必须先实现Delayed接口。这个队列接收到任务时，首先先入队，只有达到了指定的延时时间，才会执行任务

	 */
	public static void main(String[] args) {
		DelayQueue ll;
		///DelayedWorkQueue hDelayedWorkQueue;
		//模拟fixedThreadPool， 核心线程5个，最大容量5个，线程的生命周期无限。
		ExecutorService service = 
				new ThreadPoolExecutor(
						5,//核心线程数量
						5,//最大线程数量
						0L, TimeUnit.MILLISECONDS,//超时时间
						new LinkedBlockingQueue<Runnable>(),//阻塞队列
						Executors.defaultThreadFactory(),//线程构造接口
						new RejectedExecutionHandler(){//异常处理接口
							@Override
							public void rejectedExecution(Runnable runnable,
									ThreadPoolExecutor threadPoolExecutor) {

							}
				});



		
		for(int i = 0; i < 6; i++){
			service.execute(new Runnable() {
				@Override
				public void run() {
					try {
						TimeUnit.MILLISECONDS.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName() + " - test executor");
				}
			});
		}
		
		System.out.println(service);
		
		service.shutdown();
		System.out.println(service.isTerminated());
		System.out.println(service.isShutdown());
		System.out.println(service);
		
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		///service.shutdown();
		System.out.println(service.isTerminated());
		System.out.println(service.isShutdown());
		System.out.println(service);
		
	}

}
