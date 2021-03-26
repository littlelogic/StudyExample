package multiThread.concurrent.t06__Concurren;

import com.study.wjw.z_utils.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Deque;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Properties;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.WeakHashMap;

public class T00_集合 {

	class Person {
		int age;
		String name;
		public Person(int age, String name){
			this.age = age;
			this.name = name;
		}
	}

	enum Season {
		SPRING,SUMMER,FALL,WINTER
	}

	public static void main(String[] args) {

		// todo 迭代器的用法
		// todo HashSet LinkedHashSet TreeSet ArrayList Vector Stack LinkedList PriorityQueue ArrayDeque
		Collection mCollection;
			Set mSet;// todo HashSet LinkedHashSet TreeSet
				HashSet mHashSet;
					LinkedHashSet mLinkedHashSet;
				SortedSet mSortedSet;
					TreeSet mTreeSet;
				EnumSet mEnumSet;
			List mList;// todo all
				ArrayList mArrayList;
				Vector mVector;
					Stack mStack;
				LinkedList mLinkedList;
			Queue mQueue;// todo PriorityQueue ArrayDeque
				PriorityQueue mPriorityQueue;
				Deque mDeque;
					ArrayDeque mArrayDeque;
					LinkedList hLinkedList;
		Map mMap;
		// todo HashMap LinkedHashMap Hashtable Properties TreeMap WeakHashMap IdentityHashMap EnumMap
			HashMap mHashMap;
				LinkedHashMap mLinkedHashMap;
			Hashtable mHashtable;
				Properties mProperties;
			SortedMap mSortedMap;
				TreeMap mTreeMap;
			WeakHashMap mWeakHashMap;
			IdentityHashMap mIdentityHashMap;
			EnumMap mEnumMap;
			//--------------

		T00_集合 mT_00_集合 = new T00_集合();
		/*
		Set和Map的关系十分密切，java源码就是先实现了HashMap、TreeMap等集合，
		然后通过包装一个所有的value都为null的Map集合实现了Set集合类
		1) HashSet的性能总是比TreeSet好(特别是最常用的添加、查询元素等操作)，因为TreeSet需要额外的红黑树算法来维护集合元素的次序。
		只有当需要一个保持排序的Set时，才应该使用TreeSet，否则都应该使用HashSet
		2) 对于普通的插入、删除操作，LinkedHashSet比HashSet要略慢一点，这是由维护链表所带来的开销造成的。不过，因为有了链表的存在，遍历LinkedHashSet会更快
		3) EnumSet是所有Set实现类中性能最好的，但它只能保存同一个枚举类的枚举值作为集合元素
		4) HashSet、TreeSet、EnumSet都是"线程不安全"的，通常可以通过Collections工具类的synchronizedSortedSet方法来"包装"该Set集合。
		SortedSet s = Collections.synchronizedSortedSet(new TreeSet(...));
		 */
		if(false) mT_00_集合.study_HashSet();
		if(false) mT_00_集合.study_LinkedHashSet();
		if(false) mT_00_集合.study_TreeSet();
		if(false) mT_00_集合.study_EnumSet();
		//------
		/*
		List集合代表一个元素有序、可重复的集合，集合中每个元素都有其对应的顺序索引。List集合允许加入重复元素，因为它可以通过索引来访问指定位置的集合元素。List集合默认按元素
　　 		的添加顺序设置元素的索引
		 */
		if(false) mT_00_集合.study_ArrayList();
		if(false) mT_00_集合.study_Stack();
		if(false) mT_00_集合.study_LinkedList();
		//------
		/*
		Queue队列
		boolean add(E var1);
		将指定的元素插入此队列（如果立即可行且不会违反容量限制），成功true，如果当前没有可用的空间，则抛出 IllegalStateException。
		boolean offer(E var1);
		将指定的元素插入此队列（如果立即可行且不会违反容量限制），当使用有容量限制的队列时，此方法通常要优于 add(E)，后者可能无法插入元素，而只是抛出一个异常。
		E remove();
		获取并移除此队列的头。此方法与 poll 唯一的不同在于：此队列为空时将抛出一个异常。
		E poll();
		获取并移除此队列的头，如果此队列为空，则返回 null
		E element();
		获取，但是不移除此队列的头。此方法与 peek 唯一的不同在于：此队列为空时将抛出一个异常。
		E peek();
		获取但不移除此队列的头；如果此队列为空，则返回 null

		 */
		if(false) mT_00_集合.study_PriorityQueue();
		if(false) mT_00_集合.study_ArrayDequeQueue();


		if(false) mT_00_集合.study_HashMap();
		if(false) mT_00_集合.study_LinkedHashMap();
		if(false) mT_00_集合.study_Properties();
		if(false) mT_00_集合.study_TreeMap();
		if(false) mT_00_集合.study_WeakHashMap();
		if(false) mT_00_集合.study_IdentityHashMap();
		if(true) mT_00_集合.study_EnumMap();


	}
	/*
	 Collection集合的操作，集合只能存对象，添加，查找，删除，遍历（增删改查）
	 int size();
     boolean isEmpty();
     boolean contains(Object var1);
     Iterator<E> iterator();
	 Object[] toArray();
	 boolean add(E var1);
	 boolean remove(Object var1);

	 1. java提供的List就是一个"线性表接口"，ArrayList(基于数组的线性表)、LinkedList(基于链的线性表)是线性表的两种典型实现
	 2. Queue代表了队列，Deque代表了双端队列(既可以作为队列使用、也可以作为栈使用)
	 3. 因为数组以一块连续内存来保存所有的数组元素，所以数组在随机访问时性能最好。所以的内部以数组作为底层实现的集合在随机访问时性能最好。
	 4. 内部以链表作为底层实现的集合在执行插入、删除操作时有很好的性能
	 5. 进行迭代操作时，以链表作为底层实现的集合比以数组作为底层实现的集合性能好

	 */

	/*
	HashSet
        HashSet是Set接口的典型实现，HashSet使用HASH算法来存储集合中的元素，因此具有良好的存取和查找性能。当向HashSet集合中存入一个元素时，HashSet会调用该对象的
　　　　 hashCode()方法来得到该对象的hashCode值，然后根据该HashCode值决定该对象在HashSet中的存储位置。
		值得注意的是，HashSet集合判断两个元素相等的标准是两个对象通过equals()方法比较相等，并且两个对象的hashCode()方法的返回值相等
	 */
	public void study_HashSet(){
		Set<String> hashSet = new HashSet();
		hashSet.add("aaa");
		hashSet.add("bbb");
		hashSet.add("ccc");
		hashSet.add("ddd");
		//集合不能有重复元素
		boolean mark = hashSet.add("aaa");
		Log.i(mark);
		Log.i(hashSet.toString());
		//遍历集合：
		Log.i("---转数组-----------");
		//转数组
		Object[] objectArray = hashSet.toArray();
		String[] nullStringArray = new String[]{};
		String[] nullStringArray_b = new String[0];
		String[] stringArray = hashSet.toArray(new String[0]);
		for (Object hObject : objectArray) {
			Log.i(hObject);
		}
		Log.i("------");
		for (String hString : stringArray) {
			Log.i(hString);
		}
		Log.i("---增强for-----------");
		for (String hString : hashSet) {
			Log.i(hString);
			//下面代码会引发ConcurrentModificationException异常
			//hashSet.remove(hString);
		}
		//迭代器
		Log.i("---迭代器-----------");
		/*
		1) boolean hasNext(): 是否还有下一个未遍历过的元素
		2) Object next(): 返回集合里的下一个元素
		3) void remove(): 删除集合里上一次next方法返回的元素
		 */
		Iterator<String> hIterator = hashSet.iterator();
		while(hIterator.hasNext()){
			String content = hIterator.next();
			if (content.equals("bbb")) {
				hIterator.remove();
			}
			Log.i(hIterator.next());
		}
	}

	/*
	 LinkedHashSet集合也是根据元素的hashCode值来决定元素的存储位置，但和HashSet不同的是，
	 它同时使用链表维护元素的次序，这样使得元素看起来是以插入的顺序保存的。
　　 当遍历LinkedHashSet集合里的元素时，LinkedHashSet将会按元素的添加顺序来访问集合里的元素。
	 LinkedHashSet需要维护元素的插入顺序，因此性能略低于HashSet的性能，
	 但在迭代访问Set里的全部元素时(遍历)将有很好的性能(链表很适合进行遍历)
	 */
	public void study_LinkedHashSet(){
		LinkedHashSet<String> hLinkedHashSet = new LinkedHashSet();
		hLinkedHashSet.add("aaa");
		hLinkedHashSet.add("bbb");
		hLinkedHashSet.add("ccc");
		hLinkedHashSet.add("ddd");
		Log.i(hLinkedHashSet.toString());
	}

	/*
	SortedSet此接口主要用于排序操作，即实现此接口的子类都属于排序的子类
 	TreeSet是SortedSet接口的实现类，TreeSet可以确保集合元素处于排序状态
 	与HashSet集合采用hash算法来决定元素的存储位置不同，TreeSet采用红黑树的数据结构来存储集合元素。TreeSet支持两种排序方式: 自然排序、定制排序
	 */
	public void study_TreeSet(){
		/*
		1. 自然排序:
		TreeSet会调用集合元素的compareTo(Object obj)方法来比较元素之间的大小关系，然后将集合元素按升序排序，即自然排序。如果试图把一个对象添加到TreeSet时，则该对象的类必须实现Comparable接口，否则程序会抛出异常。
		当把一个对象加入TreeSet集合中时，TreeSet会调用该对象的compareTo(Object obj)方法与容器中的其他对象比较大小，然后根据红黑树结构找到它的存储位置。如果两个对象通过compareTo(Object obj)方法比较相等，新对象将无法添加到TreeSet集合中(牢记Set是不允许重复的概念)。
		注意: 当需要把一个对象放入TreeSet中，重写该对象对应类的equals()方法时，应该保证该方法与compareTo(Object obj)方法有一致的结果，即如果两个对象通过equals()方法比较返回true时，这两个对象通过compareTo(Object obj)方法比较结果应该也为0(即相等)
		看到这里，我们应该明白：
		1) 对与Set来说，它定义了equals()为唯一性判断的标准，而对于到了具体的实现，HashSet、TreeSet来说，它们又会有自己特有的唯一性判断标准，只有同时满足了才能判定为唯一性
		2) 我们在操作这些集合类的时候，对和唯一性判断有关的函数重写要重点关注
		 */
		TreeSet<String> hTreeSet = new TreeSet();
		hTreeSet.add("aaa");
		hTreeSet.add("ccc");
		hTreeSet.add("bbb");
		hTreeSet.add("ddd");
		hTreeSet.add("11");
		hTreeSet.add("22");
		Log.i(hTreeSet.toString());
		Log.i("集合里的第一个元素: "+hTreeSet.first());
		Log.i("集合里的最后个元素: "+hTreeSet.last());
		//返回小于4的子集，不包含4
		Log.i("返回小于aaa的子集，不包含aaa: "+hTreeSet.headSet("aaa"));
		//返回大于5的子集，如果Set中包含5，子集中还包含5
		Log.i("返回大于aaa的子集，如果Set中包含aaa，子集中还包含aaa: "+hTreeSet.tailSet("aaa"));
		//返回大于等于-3，小于4的子集。
		Log.i("返回大于等于22，小于bbb的子集： "+hTreeSet.subSet("22" , "bbb"));

		/*
		2. 定制排序
		TreeSet的自然排序是根据集合元素的大小，TreeSet将它们以升序排序。如果我们需要实现定制排序，则可以通过Comparator接口的帮助(类似PHP中的array_map回调处理函数的思想)。该接口里包含一个int compare(T o1， T o2)方法，该方法用于比较大小
		 1) equals、compareTo决定的是怎么比的问题，即用什么field进行大小比较
		 2) 自然排序、定制排序、Comparator决定的是谁大的问题，即按什么顺序(升序、降序)进行排序
		 */

		TreeSet<Person> bTreeSet = new TreeSet(new Comparator(){
			//todo 如果不实现此方法，那么所添加的元素类必定要是实现Comparable接口
			public int compare(Object o1, Object o2){
				Person m1 = (Person)o1;
				Person m2 = (Person)o2;
				return m1.age > m2.age ? 1
						: m1.age < m2.age ? -1 : 0;
			}
		});
		bTreeSet.add(new Person(34,"34"));
		bTreeSet.add(new Person(12,"12"));
		bTreeSet.add(new Person(32,"32"));
		for (Person hPerson : bTreeSet) {
			Log.i(hPerson.name);
		}
	}


	/*
	EnumSet是一个专门为枚举类设计的集合类，EnumSet中所有元素都必须是指定枚举类型的枚举值，该枚举类型在创建EnumSet时显式、或隐式地指定。EnumSet的集合元素也是有序的，
　　　　 它们以枚举值在Enum类内的定义顺序来决定集合元素的顺序
	 */
	public void study_EnumSet(){
		//创建一个EnumSet集合，集合元素就是Season枚举类的全部枚举值
		EnumSet es1 = EnumSet.allOf(Season.class);
		//输出[SPRING,SUMMER,FALL,WINTER]
		System.out.println(es1);

		//创建一个EnumSet空集合，指定其集合元素是Season类的枚举值。
		EnumSet es2 = EnumSet.noneOf(Season.class);
		//输出[]
		System.out.println(es2);
		//手动添加两个元素
		es2.add(Season.WINTER);
		es2.add(Season.SPRING);
		//输出[SPRING,WINTER]
		System.out.println(es2);

		//以指定枚举值创建EnumSet集合
		EnumSet es3 = EnumSet.of(Season.SUMMER , Season.WINTER);
		//输出[SUMMER,WINTER]
		System.out.println(es3);

		EnumSet es4 = EnumSet.range(Season.SUMMER , Season.WINTER);
		//输出[SUMMER,FALL,WINTER]
		System.out.println(es4);

		//新创建的EnumSet集合的元素和es4集合的元素有相同类型，
		//es5的集合元素 + es4集合元素 = Season枚举类的全部枚举值
		EnumSet es5 = EnumSet.complementOf(es4);
		//输出[SPRING]
		System.out.println(es5);

		//---------------
		Set<Season> mSet = new HashSet();
		mSet.add(Season.SPRING);
		mSet.add(Season.SPRING);
	}

	//=========================================

	/*
	ArrayList是基于数组实现的List类，它封装了一个动态的增长的、允许再分配的Object[]数组。
	如果一开始就知道ArrayList集合需要保存多少元素，则可以在创建它们时就指定initialCapacity大小，这样可以减少重新分配的次数，提供性能，ArrayList还提供了如下方法来重新分配Object[]数组
	1) ensureCapacity(int minCapacity): 将ArrayList集合的Object[]数组长度增加minCapacity
	2) trimToSize(): 调整ArrayList集合的Object[]数组长度为当前元素的个数。程序可以通过此方法来减少ArrayList集合对象占用的内存空间

	Vector非常类似ArrayList，但是Vector是同步的。由Vector创建的Iterator，虽然和ArrayList创建的 Iterator是同一接口，但是，因为Vector是同步的，
	当一个Iterator被创建而且正在被使用，另一个线程改变了Vector的状态（例如，添加或删除了一些元素），
	这时调用Iterator的方法时将抛出ConcurrentModificationException，因此必须捕获该异常。
	Vector 是同步的。这个类中的一些方法保证了Vector中的对象是线程安全的。而ArrayList则是异步的，
	因此ArrayList中的对象并不是线程安全的。因为同步的要求会影响执行的效率，所以如果你不需要线程安全的集合那么使用ArrayList是一个很好的选择，这样可以避免由于同步带来的不必要的性能开销。

	*/
	public void study_ArrayList(){
		List books = new ArrayList();
		//向books集合中添加三个元素
		books.add(new String("轻量级Java EE企业应用实战"));
		books.add(new String("疯狂Java讲义"));
		books.add(new String("疯狂Android讲义"));
		System.out.println(books);

		//将新字符串对象插入在第二个位置
		books.add(1 , new String("疯狂Ajax讲义"));
		for (int i = 0 ; i < books.size() ; i++ ){
			System.out.println(books.get(i));
		}

		Log.i("---迭代器-----------");
		int num = 0;
		ListIterator lit = books.listIterator();
		while (lit.hasNext()){
			System.out.println(lit.next());
			//不一向迭代的次数
			lit.add("-ListIterator讲解-num->"+num++);

		}
		System.out.println("=======下面开始反向迭代=======");
		while(lit.hasPrevious()){
			System.out.println(lit.previous());
		}
		System.out.println("=======分隔符=======");

		//删除第三个元素
		books.remove(2);
		System.out.println(books);

		//判断指定元素在List集合中位置：输出1，表明位于第二位
		Log.i(books.indexOf(new String("疯狂Ajax讲义")));
		//将第二个元素替换成新的字符串对象
		books.set(1, new String("LittleHann"));
		System.out.println(books);

		//将books集合的第二个元素（包括）
		//到第三个元素（不包括）截取成子集合
		System.out.println(books.subList(1 , 2));
	}

	/*
	注意Stack的后进先出的特点
	boolean	empty()  测试堆栈是否为空。
	E peek() 查看堆栈顶部的对象，但不从堆栈中移除它。
	E pop() 移除堆栈顶部的对象，并作为此函数的值返回该对象。
	E push(E item) 把项压入堆栈顶部。
	int search(Object o) 返回对象在堆栈中的位置，以 1 为基数
	 */
	public void study_Stack(){
		Stack v = new Stack();
		//依次将三个元素push入"栈"
		v.push("疯狂Java讲义");
		v.push("轻量级Java EE企业应用实战");
		v.push("疯狂Android讲义");
		//输出：[疯狂Java讲义, 轻量级Java EE企业应用实战 , 疯狂Android讲义]
		System.out.println(v);
		//访问第一个元素，但并不将其pop出"栈"，输出：疯狂Android讲义
		System.out.println(v.peek());
		//依然输出：[疯狂Java讲义, 轻量级Java EE企业应用实战 , 疯狂Android讲义]
		System.out.println(v);
		//pop出第一个元素，输出：疯狂Android讲义
		System.out.println(v.pop());
		//输出：[疯狂Java讲义, 轻量级Java EE企业应用实战]
		System.out.println(v);
	}

	/*
	LinkedList实现了List接口，允许null元素。此外LinkedList提供额外的get，remove，insert方法在 LinkedList的首部或尾部。这些操作使LinkedList可被用作堆栈（stack），队列（queue）或双向队列（deque）。
　　注意LinkedList没有同步方法。如果多个线程同时访问一个List，则必须自己实现访问同步。一种解决方法是在创建List时构造一个同步的List：
　　　　List list = Collections.synchronizedList(new LinkedList(...));
	从代码中我们可以看到，LinkedList同时表现出了双端队列、栈的用法。功能非常强大
	implements List<E>, Deque<E>。实现List接口，能对它进行队列操作，即可以根据索引来随机访问集合中的元素。同时它还实现Deque接口，即能将LinkedList当作双端队列
　　　　 使用。自然也可以被当作"栈来使用"
	 */
	public void study_LinkedList(){
		LinkedList books = new LinkedList();
		//将字符串元素加入队列的尾部(双端队列)
		books.offer("疯狂Java讲义");

		//将一个字符串元素加入栈的顶部(双端队列)
		books.push("轻量级Java EE企业应用实战");

		//将字符串元素添加到队列的头(相当于栈的顶部)
		books.offerFirst("疯狂Android讲义");

		for (int i = 0; i < books.size() ; i++ ){
			System.out.println(books.get(i));
		}

		//访问、并不删除栈顶的元素
		System.out.println(books.peekFirst());
		//访问、并不删除队列的最后一个元素
		System.out.println(books.peekLast());
		//将栈顶的元素弹出"栈"
		System.out.println(books.pop());
		//下面输出将看到队列中第一个元素被删除
		System.out.println(books);
		//访问、并删除队列的最后一个元素
		System.out.println(books.pollLast());
		//下面输出将看到队列中只剩下中间一个元素：
		//轻量级Java EE企业应用实战
		System.out.println(books);
	}

	/*
	优先队列
	不允许插入null元素，它还需要对队列元素进行排序，PriorityQueue的元素有两种排序方式
	1) 自然排序:
	采用自然顺序的PriorityQueue集合中的元素对象都必须实现了Comparable接口，而且应该是同一个类的多个实例，否则可能导致ClassCastException异常
	2) 定制排序
	创建PriorityQueue队列时，传入一个Comparator对象，该对象负责对队列中的所有元素进行排序
	关于自然排序、定制排序的原理和之前说的TreeSet类似
	 */
	public void study_PriorityQueue(){ Queue  来了;
		PriorityQueue pq = new PriorityQueue();
		//下面代码依次向pq中加入四个元素
		pq.offer(6);
		pq.offer(-3);
		pq.offer(9);
		pq.offer(0);

		//输出pq队列，并不是按元素的加入顺序排列，
		//而是按元素的大小顺序排列，输出[-3, 0, 9, 6]
		System.out.println(pq);
		//访问队列第一个元素，其实就是队列中最小的元素：-3
		System.out.println(pq.poll());
	}

	/*
	双端队列
	　ArrayDeque是JDK容器中的一个双端队列实现，内部使用数组进行元素存储，不允许存储null值，可以高效的进行元素查找和尾部插入取出，是用作队列、双端队列、栈的绝佳选择，性能比LinkedList还要好
	是一个基于数组的双端队列，和ArrayList类似，它们的底层都采用一个动态的、可重分配的Object[]数组来存储集合元素，当集合元素超出该数组的容量时，系统会在底层重
　　　　　　　新分配一个Object[]数组来存储集合元素

      get、peek、element方法都是获取元素，但是不会将它移除，而
      pop、poll、remove都会将元素移除并返回，
      add和push、offer都是插入元素，它们的不同点在于插入元素的位置以及插入失败后的结果。

      		失败抛出异常						失败返回特殊值	失败抛出异常			失败返回特殊值
	插入		addFirst(e)/push()				offerFirst(e)	addLast(e)/add()	offerLast(e)
	移除		removeFirst()/pop()/remove()	pollFirst()		removeLast()		pollLast()
	获取		getFirst()/element()			peekFirst()		getLast()			peekLast()

	基本上可以分成两类，一类是以add，remove，get开头的方法，这类方法失败后会抛出异常，
	一类是以offer，poll，peek开头的方法，这类方法失败之后会返回特殊值，如null。

		 插入	移除		获取
	队列：offer，poll，  element，peek
	栈：  push， pop，   peek
	列表：add，  remove，get


	 */
	public void study_ArrayDequeQueue(){
		// 初始化容量为4
		ArrayDeque<String> arrayDeque = new ArrayDeque<>(4);
		//添加元素
		arrayDeque.add("A");
		arrayDeque.add("B");
		arrayDeque.add("C");
		arrayDeque.add("D");
		arrayDeque.add("E");
		arrayDeque.add("F");
		arrayDeque.add("G");
		arrayDeque.add("H");
		arrayDeque.add("I");
		System.out.println(arrayDeque);

		//移除元素
		arrayDeque.remove() ;

		// 获取元素
		String a = arrayDeque.getFirst();
		String a1 = arrayDeque.pop();
		String b = arrayDeque.element();
		String b1 = arrayDeque.removeFirst();
		String c = arrayDeque.peek();
		String c1 = arrayDeque.poll();
		String d = arrayDeque.pollFirst();
		String i = arrayDeque.pollLast();
		String e = arrayDeque.peekFirst();
		String h = arrayDeque.peekLast();
		String h1 = arrayDeque.removeLast();
		System.out.printf("a = %s, a1 = %s, b = %s, b1 = %s, c = %s, c1 = %s, d = %s, i = %s, e = %s, h = %s, h1 = %s", a,a1,b,b1,c,c1,d,i,e,h,h1);
		System.out.println();

		// 添加元素
		arrayDeque.push(e);
		arrayDeque.add(h);
		arrayDeque.offer(d);
		arrayDeque.offerFirst(i);
		arrayDeque.offerLast(c);
		arrayDeque.offerLast(h);
		arrayDeque.offerLast(c);
		arrayDeque.offerLast(h);
		arrayDeque.offerLast(i);
		arrayDeque.offerLast(c);
		System.out.println(arrayDeque);

		// 移除第一次出现的C
		arrayDeque.removeFirstOccurrence(c);
		System.out.println(arrayDeque);

		// 移除最后一次出现的C
		arrayDeque.removeLastOccurrence(c);
		System.out.println(arrayDeque);

	}

	//=========================================
	/*
	Map用于保存具有"映射关系"的数据，因此Map集合里保存着两组值，一组值用于保存Map里的key，
	另外一组值用于保存Map里的value。key和value都可以是任何引用类型的数据。Map的key不允
	许重复，即同一个Map对象的任何两个key通过equals方法比较结果总是返回false。
	关于Map，我们要从代码复用的角度去理解，java是先实现了Map，然后通过包装了一个所有value都为null的Map就实现了Set集合
	Map的这些实现类和子接口中key集的存储形式和Set集合完全相同(即key不能重复)
	Map的这些实现类和子接口中value集的存储形式和List非常类似(即value可以重复、根据索引来查找)


	 get
	put 以前与 key 关联的值，如果没有针对 key 的映射关系，则返回 null。（如果该实现支持 null 值，则返回 null 也可能表示此映射以前将 null 与 key 关联）
	remove(Object key)  如果存在一个键的映射关系，则将其从此映射中移除（可选操作）。
	entrySet() 返回此映射中包含的映射关系的 Set 视图。
	Set<K> keySet() 返回此映射中包含的键的 Set 视图。
	Collection<V> values() 返回此映射中包含的值的 Collection 视图

	1) HashMap和Hashtable的效率大致相同，因为它们的实现机制几乎完全一样。但HashMap通常比Hashtable要快一点，因为Hashtable需要额外的线程同步控制
	2) TreeMap通常比HashMap、Hashtable要慢(尤其是在插入、删除key-value对时更慢)，因为TreeMap底层采用红黑树来管理key-value对
	3) 使用TreeMap的一个好处就是： TreeMap中的key-value对总是处于有序状态，无须专门进行排序操作

	*/

	/*
	HashSet集合不能保证元素的顺序一样，HashMap也不能保证key-value对的顺序。
	并且类似于HashSet判断两个key是否相等的标准也是: 两个key通过equals()方法比较返回true、
　　 同时两个key的hashCode值也必须相等
	 */
	public void study_HashMap() {
		HashMap<String,Integer> hHashMap = new HashMap(16,0.7f);
		for (int i = 1; i < 5; i++) {
			Log.i(hHashMap.put(i+"",i * 100));
		}
		Log.i(hHashMap.put(4+"",444));
		//-----
		Log.i(hHashMap.put("张三",10003));
		Log.i(hHashMap.put("历史",10004));
		Log.i(hHashMap.put("王五",10005));
		Log.i(hHashMap.put("赵柳",10006));
		//-----
		Log.i(hHashMap);
		Log.i("-----分界线-----------------");
		// TODO key不可以重复
		Set<String> hSet = hHashMap.keySet();
		for (String hInteger : hSet) {
			Log.i(hInteger + "： " + hHashMap.get(hInteger));
		}
		Log.i("-----分界线-----------------");
		// TODO value不可以重复
		Collection<Integer> hCollection = hHashMap.values();
		for (Integer hInteger : hCollection)
			Log.i(hInteger);
		Log.i("-----分界线-----------------");
		Set<Map.Entry<String, Integer>> entrySet = hHashMap.entrySet();
		for (Map.Entry<String, Integer> map : entrySet) {
			Log.i(map.getKey() + "： " + map.getValue());
		}
	}

	/*
	LinkedHashMap也使用双向链表来维护key-value对的次序，该链表负责维护Map的迭代顺序，
	与key-value对的插入顺序一致(注意和TreeMap对所有的key-value进行排序进行区分)
	 */
	public void study_LinkedHashMap() {
		LinkedHashMap<String,Integer> hHashMap = new LinkedHashMap(16,0.7f);
		for (int i = 1; i < 5; i++) {
			hHashMap.put(i+"",i * 100);
		}
		//-----
		Log.i(hHashMap.put("张三",10003));
		Log.i(hHashMap.put("历史",10004));
		Log.i(hHashMap.put("王五",10005));
		Log.i(hHashMap.put("赵柳",10006));
		//-----
		Log.i("是加入的顺序"+hHashMap);
		Log.i("-----分界线-----------------");

	}

	public void study_HashTable() {


	}


	/*
	继承Hashtable
	Properties还可以把key-value对以XML文件的形式保存起来，也可以从XML文件中加载key-value对
	 */
	public void study_Properties() {
		try {
			Properties props = new Properties();
			//向Properties中增加属性
			props.setProperty("username" , "yeeku");
			props.setProperty("password" , "123456");

			//将Properties中的key-value对保存到a.ini文件中
			props.store(new FileOutputStream("a.ini"), "comment line");   //①

			//新建一个Properties对象
			Properties props2 = new Properties();
			//向Properties中增加属性
			props2.setProperty("gender" , "male");

			//将a.ini文件中的key-value对追加到props2中
			props2.load(new FileInputStream("a.ini") );    //②
			System.out.println(props2);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	class RNum implements Comparable {
		int count;
		public RNum(int count){
			this.count = count;
		}
		public String toString(){
			return "RNum[count:" + count + "]";
		}
		//根据count来判断两个对象是否相等。
		public boolean equals(Object obj){
			if (this == obj)
				return true;
			if (obj != null&& obj.getClass() == RNum.class){
				RNum r = (RNum)obj;
				return r.count == this.count;
			}
			return false;
		}
		//根据count属性值来判断两个对象的大小。
		public int compareTo(Object obj){
			RNum r = (RNum)obj;
			return count > r.count ? 1 :
					count < r.count ? -1 : 0;
		}
	}

	/*
	 TreeMap就是一个红黑树数据结构，每个key-value对即作为红黑树的一个节点。TreeMap存储key-value对(节点)时，需要根据key对节点进行排序。TreeMap可以保证所有的
　　　　 key-value对处于有序状态。同样，TreeMap也有两种排序方式: 自然排序、定制排序
	TreeMap中判断两个key相等的标准是:
	1) 两个key通过compareTo()方法返回0
	2) equals()放回true
	 */
	public void study_TreeMap() {
		TreeMap tm = new TreeMap();
		tm.put(new RNum(3) , "轻量级Java EE企业应用实战");
		tm.put(new RNum(-5) , "疯狂Java讲义");
		tm.put(new RNum(9) , "疯狂Android讲义");

		System.out.println(tm);

		//返回该TreeMap的第一个Entry对象
		System.out.println(tm.firstEntry());

		//返回该TreeMap的最后一个key值
		System.out.println(tm.lastKey());

		//返回该TreeMap的比new RNum(2)大的最小key值。
		System.out.println(tm.higherKey(new RNum(2)));

		//返回该TreeMap的比new RNum(2)小的最大的key-value对。
		System.out.println(tm.lowerEntry(new RNum(2)));

		//返回该TreeMap的子TreeMap
		System.out.println(tm.subMap(new RNum(-1) , new RNum(4)));
	}

	/*
	WeakHashMap与HashMap的用法基本相似。区别在于，HashMap的key保留了对实际对象的"强引用"，这意味着只要该HashMap对象不被销毁，该HashMap所引用的对象就不会被垃圾回收。
　　但WeakHashMap的key只保留了对实际对象的弱引用，这意味着如果WeakHashMap对象的key所引用的对象没有被其他强引用变量所引用，则这些key所引用的对象可能被垃圾回收，当垃
　　圾回收了该key所对应的实际对象之后，WeakHashMap也可能自动删除这些key所对应的key-value对
	如果需要使用WeakHashMap的key来保留对象的弱引用，则不要让key所引用的对象具有任何强引用，否则将失去使用WeakHashMap的意义
	 */
	public void study_WeakHashMap() {
		WeakHashMap whm = new WeakHashMap();
		//将WeakHashMap中添加三个key-value对，
		//三个key都是匿名字符串对象（没有其他引用）
		whm.put(new String("语文") , new String("良好"));
		whm.put(new String("数学") , new String("及格"));
		whm.put(new String("英文") , new String("中等"));

		//将WeakHashMap中添加一个key-value对，
		//该key是一个系统缓存的字符串对象。"java"是一个常量字符串强引用
		whm.put("java" , new String("中等"));
		//输出whm对象，将看到4个key-value对。
		System.out.println(whm);
		//通知系统立即进行垃圾回收
		System.gc();
		System.runFinalization();//(强制)调用已经失去引用的对象的finalize方法
		//通常情况下，将只看到一个key-value对。
		System.out.println(whm);
	}

	/*
	IdentityHashMap的实现机制与HashMap基本相似，在IdentityHashMap中，
	当且仅当两个key严格相等(key1 == key2)时，IdentityHashMap才认为两个key相等
	 */
	public void study_IdentityHashMap() {
		IdentityHashMap ihm = new IdentityHashMap();
		//下面两行代码将会向IdentityHashMap对象中添加两个key-value对
		ihm.put(new String("语文") , 89);
		ihm.put(new String("语文") , 78);

		//下面两行代码只会向IdentityHashMap对象中添加一个key-value对
		ihm.put("java" , 93);
		ihm.put("java" , 98);
		System.out.println(ihm);

	}

	/*
	与创建普通Map有所区别的是，创建EnumMap是必须指定一个枚举类，从而将该EnumMap和指定枚举类关联起来
	 */
	public void study_EnumMap() {
		//创建一个EnumMap对象，该EnumMap的所有key
		//必须是Season枚举类的枚举值
		EnumMap enumMap = new EnumMap(Season.class);
		enumMap.put(Season.SUMMER , "夏日炎炎");
		enumMap.put(Season.SPRING , "春暖花开");
		System.out.println(enumMap);
	}





}
