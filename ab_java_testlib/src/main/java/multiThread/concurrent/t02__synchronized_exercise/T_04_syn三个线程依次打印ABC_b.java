package multiThread.concurrent.t02__synchronized_exercise;

import com.study.wjw.z_utils.Log;

import java.util.concurrent.atomic.AtomicInteger;

public class T_04_syn三个线程依次打印ABC_b implements Runnable {

    private String name;
    private Object prev;
    private Object self;
    private Object next;
    private int self_id;
    private int next_id;
    AtomicInteger target;

    private T_04_syn三个线程依次打印ABC_b(AtomicInteger target,String name, Object prev, Object self, Object next
            ,int self_id,int next_id) {
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
                Log.i("run-"+name+"-self_out");
                synchronized (self) {
                    int current_id = target.get();
                    Log.i("run-"+name+"-self_in-current_id->"+current_id);
                    if (current_id == self_id) {
                        Log.i("run-"+name+"-self_do");
                        ///System.out.print(name);
                        Log.i(name);
                        count--;
                        synchronized (next) {
                            target.set(next_id);
                            next.notify();
                            Log.i("run-"+name+"-self_next-current_id->"+target.get());
                        }
                        self.wait();
                    } else {
                        Log.i("run-"+name+"-self_wait-1-------------");
                        self.wait();
                    }
                    Log.i("run-"+name+"-last->");
                }
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
        T_04_syn三个线程依次打印ABC_b pa = new T_04_syn三个线程依次打印ABC_b(target,"A", c, a,b,1,2);
        T_04_syn三个线程依次打印ABC_b pb = new T_04_syn三个线程依次打印ABC_b(target,"B", a, b,c,2,3);
        T_04_syn三个线程依次打印ABC_b pc = new T_04_syn三个线程依次打印ABC_b(target,"C", b, c,a,3,1);
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