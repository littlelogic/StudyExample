package com.study.wjw.test014;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*


 */
public class Test_a {

    int here = 0;

    /*

      静态内部类、成员内部类、方法内部类（局部内部类）、匿名内部类。

      静态内部类只能直接访问外部类的静态成员或者方法（实质是持有外部类名）
      只能定义在外部类中最外层
      静态方法中定义的内部类是静态内部类（这时不能在类前面加 static 关键字），静态方法中的静态内部类与普通方法中的内部类使用类似

      Java 1.8 之前为什么方法内部类和匿名内部类访问局部变量和形参时必须加 final，
      类 class 前面是不能有 public、protected、private、static 修饰符的
      匿名内部类无法通过构造方法初始化，所以我们只能通过构造代码块进行初始化，可以直接调用父类的构造方法
      使用匿名内部类时必须是继承一个类或实现一个接口（二者不可兼得且只能继承一个类或者实现一个接口）,不能是抽象类

      成员内部类可以无条件访问外部类的成员或者方法,其内部自动创建了外部类的引用(局部内部类、匿名内部类也是一样)，
      可用 abstract 修饰符定义为抽象类，也可以用 private 或 public 定义
      除static内部类外，不能直接在内部类中声明 static 成员（static 常量除外）

     */
    public void test_01(){
        new Thread(){
            static final int dd = 5;
            @Override
            public void run() {
                super.run();
                here = 43;
            }
        }.start();
    }



    class OutClass {
        class InnerClass{ }

        class StaticInnerStaticClass{ }
    }

    /*
      因为成员内部类的继承语法格式要求继承引用方式为 Outter.Inner 形式且继承类的构造器中
      必须有指向外部类对象的引用，并通过这个引用调用 super()，其实这个要求就是因为成员内
      部类默认持有外部类的引用，外部类不先实例化则无法实例化自己。
     */
    class ChildInnerClass extends OutClass.InnerClass {

        public ChildInnerClass(OutClass mOutClass){
            mOutClass.super();
        }
        // 把内部类当成一个成员变量进行实例化
        ///OutClass.InnerClass aa = (new OutClass()).new OutClass.InnerClass();
        OutClass.InnerClass aa;

        private void test_b(){
            /// aa = (new OutClass()).new OutClass.InnerClass();
        }
    }

    //=====================================================================


    /*
    答：程序运行返回 6 个 false。
    首先 list1 指向一个 ArrayList 对象实例；list2 指向一个继承自 ArrayList 的匿
    名类内部类对象；list3 也指向一个继承自 ArrayList 的匿名内部类（里面一对括弧为初始化代码块）对象；
    list4 也指向一个继承自 ArrayList 的匿名内部类（里面多对括弧为多个初始化代码块）对象；
    由于这些匿名内部类都出现在同一个类中，所以编译后其实得到的是 Demo$1、Demo$2、Demo$3 的形式，
    所以自然都互补相等了，不信你可以通过 listX.getClass().getName() 进行验证。
     */
    public void test_02() {
        List list1 = new ArrayList();
        List list2 = new ArrayList(){};
        List list3 = new ArrayList(){{}};
        List list4 = new ArrayList(){{}{}};
        System.out.println(list1.getClass() == list2.getClass());
        System.out.println(list1.getClass() == list3.getClass());
        System.out.println(list1.getClass() == list4.getClass());
        System.out.println(list2.getClass() == list3.getClass());
        System.out.println(list2.getClass() == list4.getClass());
        System.out.println(list3.getClass() == list4.getClass());
        System.out.println(list1.getClass().getName());
        System.out.println(list2.getClass());
        System.out.println(list3.getClass());
        System.out.println(list4.getClass());
    }

    //=====================================================================

    public static void main(String[] args) {
        Test_a aTest_a = new Test_a();
        aTest_a.test_02();
    }





}