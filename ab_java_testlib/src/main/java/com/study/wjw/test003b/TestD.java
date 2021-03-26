package com.study.wjw.test003b;
import java.lang.reflect.Method;

abstract class Base {

    Base() {
        System.out.println("Base-instantiation");
        preAbstract();
    }

    abstract void preAbstract();
}

class Derived extends Base {

    Derived() {
        System.out.println("Derived-instantiation");
    }

    void preAbstract() {
        ///super.preAbstract();
        System.out.println("Derived-preAbstract");
    }

    void eat() {
        System.out.println("Derived-eat");
    }
}

class DerivedChild extends Derived {

    DerivedChild() {
        System.out.println("DerivedChild-instantiation");
    }

    void preAbstract() {
        ///super.preAbstract();
        System.out.println("DerivedChild-preAbstract");
    }

    void eat() {
        super.preAbstract();
        System.out.println("DerivedChild-eat");
    }

}


/**

 覆盖的意思：就是将父类的覆盖掉了，子类对象调用该方法，只能是重写的覆盖方法，父类对象可以调用原来的equals

 方法重载Overloading  ， 是在一个类中，有多个方法，这些方法的名字相同，但是具有不同的参数列表，和返回值
 重载的时候，方法名要一样，但是参数类型和参数个数不一样，返回值类型可以相同，也可以不同， 不能以返回值类型判断方法是否重载。

 2. 方法重写Overriding,覆盖 ， 是存在于父类与子类之间
 （1）若子类中的方法与父类中的某一方法具有相同的方法名、返回类型和参数表，则新方法覆盖父类中的方法，如需调 用父类方法用super关键字
 （2）子类的重写方法的权限修饰符不能小于父类的，要大于等于父类

 java反射无法实现用一个子类实例调用父类方法又,不调用子类被重写的方法
 后半句是不严谨的，可以调用子类的一个方法，这个方法里面调用super.父类方法


 */
public class TestD {

    public static void main(String[] args) {

        Derived d = new DerivedChild();
        d.eat();
        try {
            Method mMethod = d.getClass().getSuperclass().getDeclaredMethod("preAbstract");
            mMethod.invoke(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*
System.out.println("Base-instantiation");
System.out.println("DerivedChild-preAbstract");
System.out.println("Derived-instantiation");
System.out.println("DerivedChild-instantiation");
System.out.println("DerivedChild-eat");
System.out.println("DerivedChild-preAbstract");


 */
}