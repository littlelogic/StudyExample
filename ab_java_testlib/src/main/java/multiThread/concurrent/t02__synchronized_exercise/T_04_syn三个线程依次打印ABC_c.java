package multiThread.concurrent.t02__synchronized_exercise;

import com.study.wjw.z_utils.Log;

public class T_04_syn三个线程依次打印ABC_c implements Runnable {

    private String name;
    private Object prev;
    private Object self;
    private Object next;
    Hold mHold;

    private T_04_syn三个线程依次打印ABC_c(String name, Object prev, Object self, Object next,Hold mHold) {
        this.name = name;
        this.prev = prev;
        this.self = self;
        this.next = next;
        this.mHold = mHold;
    }

    @Override
    public void run(){
        int count = 10;
        while (count > 0) {
            try {
                ///Log.i("run-"+name+"-out");
                synchronized (mHold) {
                    ///Log.i("run-"+name+"-in-current->"+mHold.current);
                    if (mHold.current.equals(self)) {
                        count--;
                        ///System.out.print(name);
                        Log.i(name);
                        mHold.current = next.toString();
                        mHold.notifyAll();
                        //Log.i("run-"+name+"-in-a");
                        ///mHold.wait();
                    } else {
                        mHold.notifyAll();
                        ///Log.i("run-"+name+"-in-b");
                        mHold.wait();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) throws Exception {
        Hold mHold = new Hold();
        T_04_syn三个线程依次打印ABC_c pa = new T_04_syn三个线程依次打印ABC_c("A", "c", "a","b",mHold);
        T_04_syn三个线程依次打印ABC_c pb = new T_04_syn三个线程依次打印ABC_c("B", "a", "b","c",mHold);
        T_04_syn三个线程依次打印ABC_c pc = new T_04_syn三个线程依次打印ABC_c("C", "b", "c","a",mHold);

        new Thread(pa).start();
        ///Thread.sleep(10);
        new Thread(pb).start();
        ///Thread.sleep(10);
        new Thread(pc).start();
        ///Thread.sleep(10);
    }
}

class Hold {
    String current = "a";
}