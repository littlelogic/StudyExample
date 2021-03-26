package others;

//import com.study.wjw.z_utils.Log;

import java.io.ObjectStreamException;
import java.io.Serializable;

public class SingletonDemo implements Serializable {

    private Object readResolve()  throws ObjectStreamException {
        return instance;
    }

    private SingletonDemo(){}
    private static SingletonDemo instance;
    //懒汉模式,非线程安全
    public static SingletonDemo getInstance(){
        if(instance==null){
            instance=new SingletonDemo();
        }
        return instance;
    }

    //懒汉模式,线程安全
    public static synchronized SingletonDemo getInstanceB(){
        if(instance==null){
            instance=new SingletonDemo();
        }
        return instance;
    }

    //饿汉模式,线程安全，没有懒加载
    private static SingletonDemo instanceC = new SingletonDemo();
    public static SingletonDemo getInstanceC(){
        return instanceC;
    }

    //静态内部类,懒加载,线程安全
    private static class SingletonHolder{
        private static SingletonDemo instance=new SingletonDemo();


    }
    public static SingletonDemo getInstanceD(){
        return SingletonHolder.instance;
    }

    /*
    在枚举中我们明确了构造方法限制为私有，在我们访问枚举实例时会执行构造方法，
    同时每个枚举实例都是static final类型的，也就表明只能被实例化一次。在调用构造方法时，我们的单例被实例化。
    也就是说，因为enum中的实例被保证只会被实例化一次，所以我们的INSTANCE也被保证实例化一次。
     (1)自由序列化。Enum 实现了Serializable
*/
     enum SomeThing {
//        INSTANCE22,//不可加，会两次实例化
        INSTANCE;
        private SingletonDemo instance;
        SomeThing() {
            instance = new SingletonDemo();
//            Log
//            Log.i("SingletonDemo-SomeThing-constuctor->");
        }
        public SingletonDemo getInstance() {
            return instance;
        }
    }
    public static SingletonDemo getInstanceF(){
        return SomeThing.INSTANCE.getInstance();
    }

    //双重校验锁法,lazyloading,理论上线程安全,实际有问题
    //涉及到java的底层机制，内存模型允许所谓的“无序写入”,
    //双重检查锁定的问题是：并不能保证它会在单处理器或多处理器计算机上顺利运行。
    private volatile static SingletonDemo instanceE;
    public static SingletonDemo getInstanceE(){
        if(instance==null){
            synchronized (SingletonDemo.class){
                if(instance==null){
                    instance=new SingletonDemo();
                }
            }
        }
        return instance;
    }




}
