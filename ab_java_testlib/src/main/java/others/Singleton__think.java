package others;

public class Singleton__think {
    private Singleton__think(){}
    private static Singleton__think instance;
    //懒汉模式,非线程安全
    public static Singleton__think getInstance(){
        if(instance==null)
            instance=new Singleton__think();
        return instance;                            }
    //懒汉模式,线程安全,同步开销大
    public static synchronized Singleton__think getInstanceB(){
        if(instance==null)
            instance=new Singleton__think();
        return instance;                                          }
    //饿汉模式,线程安全，没有懒加载
    private static Singleton__think instanceC=new Singleton__think();
    public static Singleton__think getInstanceC(){
        return instanceC;                            }
/** <双重校验锁法>,lazyloading,<理论上线程安全,实际有问题>
    涉及到java的底层机制，内存模型允许所谓的“<无序写入>”,
    双重检查锁定的问题是：并不能保证它会在单处理器或多处理器计算机上顺利运行。                                       */
    private volatile static Singleton__think instanceE;
    public static Singleton__think getInstanceE(){
        if(instance==null)
            synchronized (Singleton__think.class){
                if(instance==null)
                    instance=new Singleton__think();
            }
        return instance;                              }

    //静态内部类,懒加载,线程安全 todo 推荐
    protected static class SingletonHolder{
        private static Singleton__think instance=new Singleton__think();}
    public static Singleton__think getInstanceD(){
        return SingletonHolder.instance;
    }
/** 枚举中构造方法为<私有>,同时每个枚举实例都是<static final>类型,只能实例化一次
    在调用构造方法时，我们的单例被实例化。也就是说，
    因为enum中的实例被保证只会被<实例化一次>,所以我们的INSTANCE也被保证实例化一次
    懒加载,线程安全，自由序列化(<Enum 实现了Serializable>)                                                   */
    private enum SomeThing {//todo 枚举构造法,推荐
        //-INSTANCE_b,//todo 不可加，会两次实例化
        INSTANCE;
        private Singleton__think instance;
        private SomeThing() {//todo 执行一次
            instance = new Singleton__think();}
        public Singleton__think getInstance() {
            return instance;                      }
    }
    public static Singleton__think getInstanceF(){
        return SomeThing.INSTANCE.getInstance();     }
}
