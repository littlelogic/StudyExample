/**
 * 生产者消费者
 * wait&notify
 * wait/notify都是和while配合应用的。可以避免多线程并发判断逻辑失效问题。
 */
package multiThread.concurrent.t04__ReentrantLock_exercise;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class T_ps_synchronized<E> {

	private final LinkedList<E> list = new LinkedList<>();
	private final int MAX = 10;
	private int count = 0;
	
	public synchronized int getCount(){
		return count;
	}
	
	public synchronized void put(E e){
		while(list.size() == MAX){
			try {
				this.wait();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		
		list.add(e);
		count++;
		this.notifyAll();
	}
	
	public synchronized E get(){
		E e = null;
		while(list.size() == 0){
			try{
				this.wait();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		e = list.removeFirst();
		count--;
		this.notifyAll();
		return e;
	}
	
	public static void main(String[] args) {
		final T_ps_synchronized<String> c = new T_ps_synchronized<>();
		for(int i = 0; i < 10; i++){
			new Thread(new Runnable() {
				@Override
				public void run() {
					for(int j = 0; j < 5; j++){
						System.out.println(c.get());
					}
				}
			}, "consumer"+i).start();
		}
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0; i < 2; i++){
			new Thread(new Runnable() {
				@Override
				public void run() {
					for(int j = 0; j < 25; j++){
						c.put("container value " + j); 
					}
				}
			}, "producer"+i).start();
		}
	}
	
}
