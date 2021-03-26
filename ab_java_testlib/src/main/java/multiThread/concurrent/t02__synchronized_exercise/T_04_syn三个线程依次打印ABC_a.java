package multiThread.concurrent.t02__synchronized_exercise;

public class T_04_syn三个线程依次打印ABC_a implements Runnable {

    private String name;
    private Object prev;
    private Object self;

    private T_04_syn三个线程依次打印ABC_a(String name, Object prev, Object self) {
        this.name = name;
        this.prev = prev;
        this.self = self;
    }

    @Override
    public void run(){
        int count = 10;
        while (count > 0) {
            synchronized (prev) {
                synchronized (self) {
                    System.out.print(name);
                    count--;
                    try{
                        Thread.sleep(1);
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }

                    self.notify();
                }
                try {
                    prev.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static void main(String[] args) throws Exception {
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();
        T_04_syn三个线程依次打印ABC_a pa = new T_04_syn三个线程依次打印ABC_a("A", c, a);
        T_04_syn三个线程依次打印ABC_a pb = new T_04_syn三个线程依次打印ABC_a("B", a, b);
        T_04_syn三个线程依次打印ABC_a pc = new T_04_syn三个线程依次打印ABC_a("C", b, c);

        new Thread(pa).start();
        Thread.sleep(10);
        new Thread(pb).start();
        Thread.sleep(10);
        new Thread(pc).start();
        Thread.sleep(10);
    }
}