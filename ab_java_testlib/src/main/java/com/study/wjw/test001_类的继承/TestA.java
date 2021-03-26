package com.study.wjw.test001_类的继承;

/*abstract*/ class Base {


    public static String whenAmISetStatic = "set when instantiation whenAmISetStatic" ;
    public String whenAmISet = "set when instantiation Base" ;

    Base() {
        System.out.println("Base-instantiation-this->"+this);
        preProcess();
    }

    public void preProcess(){
        System.out.println("Base-preProcess-this->"+this);
        System.out.println("Base-whenAmISet->"+whenAmISet);
    }

    public void compareA(Object mObject){
        System.out.println("Base-compareA-this->"+(this == mObject));
    }
}

class Derived extends Base {

    ///public String whenAmISet = "set when instantiation";
    public static String whenAmISet = "set when instantiation";
    ///public static String whenAmISet = "set when instantiation";
    public static String whenAmISetStatic = "set when instantiation whenAmISetStatic" ;

    Derived() {
        ///System.out.println("Child-first");
        ///super();
        System.out.println("Child-instantiation");
        super.preProcess();
    }

    public /* 1 static*/ void preProcess() {
        System.out.println("Child-preProcess-this->"+this);
        whenAmISet = "set in preProcess method";
    }

    public void compareB(Object mObject){
        System.out.println("Child-compareB-this->"+(this == mObject));
    }
}

/**
 * Created by wujiawen on 2018/3/5.
 *

 实际上，即使子类声明了与父类完全一样的成员变量，也不会覆盖掉父类的成员变量。
 而是在子类实例化时，会同时定义两个成员变量，子类也可以同时访问到这两个成员变量，
 但父类不能访问到子类的成员变量（父类不知道子类的存在）。而具体在方法中使用成员变量时，
 究竟使用的是父类还是子类的成员变量，则由方法所在的类决定；即，方法在父类中定义和执行，
 则使用父类的成员变量，方法在子类中定义（包括覆盖父类方法）和执行，则使用子类的成员变量。

 首先看一下JAVA中方法和变量在继承时的覆盖和隐藏规则
 1.父类的实例变量和静态变量能被子类的同名变量隐藏
 2.父类的静态方法被子类的同名静态方法隐藏
 3.父类的实例方法被子类的同名实例变量覆盖

 还有几点需要注意的是
 1.不能用子类的静态方法隐藏 父类中同样标示（也就是返回值 名字 参数都一样）的实例方法
 2.不能用子类的实例方法覆盖 父类中同样标示的静态方法
 3.这点儿请注意，就是变量只会被隐藏 不会被覆盖 ，无论他是实例变量还是静态变量，而且，
 子类的静态变量可以隐藏 父类的实例变量，子类的实例变量可以隐藏 父类的静态变量

 隐藏 和覆盖 的区别在于，子类对象转换成父类对象后，能够访问父类被隐藏 的变量和方法，而不能访问父类被覆盖 的方法
 如果需要访问父类被隐藏 的实例变量，加上super就好了，比如访问父类的name，写上super.name就好了

 */
public class TestA {

    public static void main(String[] args) {
        Base mBase = new Derived();
        System.out.println("mBase->"+mBase);
        System.out.println(mBase.whenAmISet);
        System.out.println(((Derived)mBase).whenAmISet);//--
        mBase.preProcess();
        mBase.compareA(mBase);
        ((Derived)mBase).compareB(mBase);
        /*
System.out.println("Base-instantiation-this->"+Derived);
System.out.println("Child-preProcess-this->"+Derived);
System.out.println("Child-instantiation");
System.out.println("Base-preProcess-this->"+Derived);
System.out.println("Base-whenAmISet->"+"set when instantiation Base");
System.out.println("---------");
System.out.println("-------d-this-"+Derived);
System.out.println(d.set when instantiation Base);
set in preProcess method
System.out.println("Child-preProcess-this->"+Derived);





         */
    }
}