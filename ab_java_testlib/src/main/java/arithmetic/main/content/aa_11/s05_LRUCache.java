package arithmetic.main.content.aa_11;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class s05_LRUCache {

    //todo 基于androdi sdk的LRU
    class LRUCache2 extends LinkedHashMap<Integer,Integer>{
        private int capacity;
        public LRUCache2(int capacity) {
            super(capacity //capacity-初始容量
                    ,0.75F/*加载因子*/
               ,true/* true基于访问顺序,false基于插入顺序 */);
            boolean accessOrder = true; //
            this.capacity = capacity;
        }
        public int get(int key) {
            return super.getOrDefault(key,-1);
        }
        public void put(int key, int value) {
            super.put(key,value);
        }
        @Override
        public boolean removeEldestEntry
                (Map.Entry<Integer, Integer> eldest){
            return size() > capacity;
        }
    }

    static class Node {
        public int key, val;
        public Node next, prev;
        public Node(int k, int v) {
            this.key = k;
            this.val = v;
        }
    }
    static class DoubleList {
        private Node head, tail;
        private int size;
        public void addFirst(Node node) {
            if (head == null) {
                head = tail = node;
            } else {
                Node n = head;
                n.prev = node;
                node.next = n;
                head = node;
            }
            size++;
        }
        public void remove(Node node) {
            if (head ==node&&tail== node){
                head = null;
                tail = null;
            } else if (tail == node) {
                node.prev.next = null;
                tail = node.prev;
            } else if (head == node) {
                node.next.prev = null;
                head = node.next;
            } else {
                node.prev.next = node.next;
                node.next.prev = node.prev;
            }
            size--;
        }
        public Node removeLast() {
            Node node = tail;
            remove(tail);
            return node;
        }
        public int size() {
            return size;
        }
    }

    static class LRUCache {
        private HashMap<Integer, Node> map;
        private DoubleList cache;
        private int cap; //缓存容量
        public LRUCache(int capacity) {
            this.cap = capacity;
            map = new HashMap<>();
            cache = new DoubleList();
        }
        public int get(int key) {
            if(!map.containsKey(key)) return -1;
            int val = map.get(key).val;
            put(key, val);
            return val;
        }
        public void put(int key, int value) {
            Node x = new Node(key, value);
            if (map.containsKey(key)){//可改进
                cache.remove(map.get(key));
                cache.addFirst(x);
                map.put(key,x);
            } else {
                if (cap == cache.size()) {
                    Node last=cache.removeLast();
                    map.remove(last.key);
                }
                cache.addFirst(x);
                map.put(key,x);
            }
        }
    }

    /**

     todo 使用HashMap+双向链表，使get和put的时间复杂度达到O(1)。
     todo 读缓存时从HashMap中查找key，更新缓存时同时更新HashMap和双向链表，双向链表始终按照访问顺序排列。

     测试程序，访问顺序为[[1,1],[2,2],[1],[3,3],[2],[4,4],[1],[3],[4]]，其中成对的数调用put，单个数调用get。
     get的结果为[1],[-1],[-1],[3],[4]，-1表示缓存未命中，其它数字表示命中。

     */

    public static void main(String[] args) {

        LRUCache cache = new LRUCache(2);
        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(cache.get(1));
        cache.put(3, 3);
        System.out.println(cache.get(2));
        cache.put(4, 4);
        System.out.println(cache.get(1));
        System.out.println(cache.get(3));
        System.out.println(cache.get(4));

    }

}
