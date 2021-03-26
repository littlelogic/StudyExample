package multiThread.concurrent.t02__synchronized_exercise;

import com.study.wjw.z_utils.Log;

import java.util.concurrent.atomic.AtomicInteger;

public class T_04_Atomic三个线程依次打印ABC_d implements Runnable {

    private String name;
    private Object prev;
    private Object self;
    private Object next;
    private int self_id;
    private int next_id;
    AtomicInteger target;

    private T_04_Atomic三个线程依次打印ABC_d(AtomicInteger target, String name, Object prev, Object self, Object next
            , int self_id, int next_id) {
        this.target = target;
        this.name = name;
        this.prev = prev;
        this.self = self;
        this.next = next;
        this.self_id = self_id;
        this.next_id = next_id;
    }

    public void run(){
        int count = 10;
        while (count > 0) {
            try {
                int num_id = target.getAndIncrement();
                num_id = num_id % 3;
                if (num_id == 0) {
                    Log.i("A");
                } else if (num_id == 1) {
                    Log.i("B");
                } else if (num_id == 2) {
                    Log.i("C");
                }
                count--;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        AtomicInteger target = new AtomicInteger(1);//1-a,2-b,3-c
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();
        T_04_Atomic三个线程依次打印ABC_d pa = new T_04_Atomic三个线程依次打印ABC_d(target,"A", c, a,b,1,2);
        T_04_Atomic三个线程依次打印ABC_d pb = new T_04_Atomic三个线程依次打印ABC_d(target,"B", a, b,c,2,3);
        T_04_Atomic三个线程依次打印ABC_d pc = new T_04_Atomic三个线程依次打印ABC_d(target,"C", b, c,a,3,1);
        /*pa.current = a;
        pb.current = a;
        pc.current = a;*/

        new Thread(pa).start();
        ///Thread.sleep(10);
        new Thread(pb).start();
        ///Thread.sleep(10);
        new Thread(pc).start();
        ///Thread.sleep(10);
    }
}