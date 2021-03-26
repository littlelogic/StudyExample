package multiThread.concurrent.t01__synchronized;


public class T_05_DieLock extends Thread {

    static class MyLock {
        // 创建两把锁对象
        public static final Object objA = new Object();
        public static final Object objB = new Object();
    }

    private boolean flag;

    public T_05_DieLock(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        if (flag) {
            synchronized (MyLock.objA) {
                System.out.println("if objA");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (MyLock.objB) {
                    System.out.println("if objB");
                }
            }
        } else {
            synchronized (MyLock.objB) {
                System.out.println("else objB");
                synchronized (MyLock.objA) {
                    System.out.println("else objA");
                }
            }
        }
    }


    public static void main(String[] args) {
        T_05_DieLock dl1 = new T_05_DieLock(true);
        T_05_DieLock dl2 = new T_05_DieLock(false);

        dl1.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dl2.start();
    }


}


