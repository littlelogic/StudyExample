package multiThread.concurrent.t06__Concurren;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


public class T03_ConcurrentLinkedQueue_think {


	/*



	ConcurrentLinkedQueue，并发容器
	队列 - 链表实现的，无界限
 	它采用先进先出的规则对节点进行排序
 	size()是要遍历一遍集合的，效率慢，
 	所以尽量要避免用size而改用isEmpty()    								*/
	public static void main(String[] args){
		Queue<String> queue = new
				ConcurrentLinkedQueue<>();
		for(int i = 0; i < 10; i++){
			queue.offer("value" + i);
		}
		
		System.out.println(queue);
		System.out.println(queue.size());
		
		// peek() -> 查看queue中的首数据
		System.out.println(queue.peek());
		System.out.println(queue.size());
		
		// poll() -> 获取queue中的首数据
		System.out.println(queue.poll());
		System.out.println(queue.size());
	}

}
