package multiThread.concurrent.t03__ReentrantLock;

import com.study.wjw.z_utils.Log;
import com.study.wjw.z_utils.Threader;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class T_05_ReentrantLock___think<E> {
    final LinkedList<E> list = new LinkedList<>();
    Lock lock = new ReentrantLock(false);
    Condition producer = lock.newCondition();
    Condition consumer = lock.newCondition();
    public void put(E e){
        lock.lock();
        try {
            while(list.size() == 10){//why while ?
                Log.i(Threader.getName()+"等待");
                producer.await();
            }Log.i(Threader.getName()+"put");
            list.add(e);
            consumer.signalAll();
        } catch (InterruptedException e1) { } finally {
            lock.unlock();
        }
    }
    public E get(){
        E e = null;
        lock.lock();
        try {
            while(list.size() == 0){
                Log.i(Threader.getName()+"等待");
                consumer.await();
            }Log.i(Threader.getName()+"get");
            e = list.removeFirst();
            producer.signalAll();
        } catch (InterruptedException e1) { } finally {
            lock.unlock();
        }
        return e;
    }
    public static void main(String[] args) {
        final T_05_ReentrantLock___think<String> my
                = new T_05_ReentrantLock___think<>();
        for(int i = 0; i < 10; i++)
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int j = 0; j < 5; j++){
                        Log.i(my.get());
                    }
                }
            }, "consumer"+i).start();
        for(int i = 0; i < 2; i++)
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int j = 0; j < 25; j++){
                        my.put("container value "+j);
                    }
                }
            }, "producer"+i).start();
    }
}
