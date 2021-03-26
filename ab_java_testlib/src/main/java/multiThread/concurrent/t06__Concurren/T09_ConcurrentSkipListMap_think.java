package multiThread.concurrent.t06__Concurren;

import com.study.wjw.z_utils.Log;

import java.util.Comparator;
import java.util.concurrent.ConcurrentSkipListMap;

public class T09_ConcurrentSkipListMap_think {


    /*



    NavigableSet<K>	descendingKeySet()
    返回此映射中所包含键的逆序 NavigableSet 视图。
    ConcurrentNavigableMap<K,V>	descendingMap()
    返回此映射中所包含映射关系的逆序视图。
    NavigableSet<K>	navigableKeySet()
    返回此映射中所包含键的 NavigableSet 视图。

    V putIfAbsent(K key, V value)
    如果指定键已经不再与某个值相关联，则将它与给定值关联。

    boolean	remove(Object key, Object value)
    只有目前将键的条目映射到给定值时，才移除该键的条目。

    V replace(K key, V value)
    只有目前将键的条目映射到某一值时，才替换该键的条目。

    boolean	replace(K key, V oldValue, V newValue)
    只有目前将键的条目映射到给定值时，才替换该键的条目。

    Map.Entry<K,V>	higherEntry(K key)
    返回与严格大于给定键的最小键关联的键-值映射关系；如果不存在这样的键，则返回 null。
    Map.Entry<K,V>	lowerEntry(K key)
    返回与严格小于给定键的最大键关联的键-值映射关系；如果不存在这样的键，则返回 null。

    Set<Map.Entry<K,V>>	entrySet()
    返回此映射中所包含的映射关系的 Set 视图。

    Map.Entry<K,V>	pollFirstEntry()
    移除并返回与此映射中的最小键关联的键-值映射关系；如果该映射为空，则返回 null。

    Map.Entry<K,V>	ceilingEntry(K key)
    返回与大于等于给定键的最小键关联的键-值映射关系；如果不存在这样的条目，则返回 null。
    Map.Entry<K,V>	floorEntry(K key)
    返回与小于等于给定键的最大键关联的键-值映射关系；如果不存在这样的键，则返回 null。

    NavigableSet<K>	keySet()
    返回此映射中所包含键的 NavigableSet 视图。
    Collection<V>	values()
    返回此映射中所包含值的 Collection 视图。

    Map.Entry<K,V>	firstEntry()
    返回与此映射中的最小键关联的键-值映射关系；如果该映射为空，则返回 null。
    Map.Entry<K,V>	lastEntry()
    返回与此映射中的最大键关联的键-值映射关系；如果该映射为空，则返回 null。

    ConcurrentNavigableMap<K,V>	headMap(K toKey, boolean inclusive)
    返回此映射的部分视图，其键小于（或等于，如果 inclusive 为 true）toKey。
    ConcurrentNavigableMap<K,V>	tailMap(K fromKey, boolean inclusive)
    返回此映射的部分视图，其键大于（或等于，如果 inclusive 为 true）fromKey。
    ConcurrentNavigableMap<K,V>	subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive)
    返回此映射的部分视图，其键的范围从 fromKey 到 toKey。

    ConcurrentSkipListMap<K,V>	clone()
    返回此 ConcurrentSkipListMap 实例的浅表副本。
    Comparator<? super K>	comparator()
    返回对此映射中的键进行排序的比较器；如果此映射使用键的自然顺序，则返回 null。

    todo ==========================================================

        ConcurrentSkipListMap(跳表),多线程安全，key是有序的，
        TreeMap的并发模式，"支持更高的并发"
        在非多线程的情况下，应当尽量使用TreeMap。
        此外对于并发性相对较低的并行程序可以使用
        Collections.synchronizedSortedMap将TreeMap进行包装，
        也可以提供较好的效率。																	 */
    public void study_ConcurrentSkipListMap(){
        ConcurrentSkipListMap CSLMap
                = new ConcurrentSkipListMap<>(new Comparator(){
            //todo 如不用此方法，则添加的元素类要是实现Comparable接口，
            //	   添加的第二个匀速开始报错，因为要进行比较
            public int compare(Object o1, Object o2){
                Person m1 = (Person)o1;
                Person m2 = (Person)o2;
                return m1.age > m2.age ? 1 //todo 升序
                        : m1.age < m2.age ? -1 : 0;
            }
        });
        for (int i = 1; i < 6; i++ )
            CSLMap.put(new Person(i,i+""),i+""+i);
        Log.i(CSLMap.toString());
        //返回大于等于给定键的最小键的键-值映射；若不存在，返回null
        Log.i(CSLMap.ceilingEntry(new Person(3.5f,"3")));
        //返回小于等于给定键的最大键的键-值映射；若不存在，则返回null
        Log.i(CSLMap.floorEntry(new Person(3,"3")));
        //移除并返回与此映射中的最小键的键-值映射；若该映射为空，则返回null
        Log.i(CSLMap.pollFirstEntry());
        Log.i(CSLMap.toString());
    }
    class Person {
        float age;
        String name;
        public Person(float age, String name){
            this.age = age;
            this.name = name;
        }
    }


    public static void main(String[] args) {
        T09_ConcurrentSkipListMap_think self =
                new T09_ConcurrentSkipListMap_think();
        self.study_ConcurrentSkipListMap();
    }

}
