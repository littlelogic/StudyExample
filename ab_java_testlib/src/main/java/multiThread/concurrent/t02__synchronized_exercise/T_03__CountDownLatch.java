package multiThread.concurrent.t02__synchronized_exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/*
 CountDownLatch 门闩

 public CountDownLatch(int count) {  };  //参数count为计数值

 然后下面这3个方法是CountDownLatch类中最重要的方法：
 public void await() throws InterruptedException { };   //调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
 public boolean await(long timeout, TimeUnit unit) throws InterruptedException { };  //和await()类似，只不过等待一定的时间后count值还没变为0的话就会继续执行
 public void countDown() { };  //将count值减1
 public long getCount()

 */
public class T_03__CountDownLatch {
	public static void main(String[] args) {
		final Test_03_Container t = new Test_03_Container();
		final CountDownLatch latch = new CountDownLatch(1);

		new Thread(new Runnable(){
			@Override
			public void run() {
				if(t.size() != 5){
					try {
						latch.await(); // 等待门闩的开放。 不是进入等待队列
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("size = 5");
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i = 0; i < 10; i++){
					System.out.println("add Object to Container " + i);
					t.add(new Object());
					if(t.size() == 5){
						///System.out.println("add Object to Container t.size() " + t.size());
						latch.countDown(); // 门闩-1
					}
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}

class Test_03_Container{
	List<Object> container = new ArrayList<>();
	
	public void add(Object o){
		this.container.add(o);
	}

	// todo 存在原子性隐患
	public int size(){
		return this.container.size();
	}
}