package com.study.wjw.test012;

import com.study.wjw.z_utils.Log;

import java.util.Date;
import java.util.Objects;

public class TestB extends Date{





    class Base { }

    class Child extends Base{ }

    /*
instanceof 和 isInstance
instanceof 是比较运算符。instanceof 后面的类名在编译时就已知且固定了，即 obj instanceof ClassA，
ClassA 必须是已经存在的类名，不然编译都过不了。
isInstance() 是动态比较的方法。isInstance() 的左边可以在运行时决定，即可以这样 objA.getClass().isInstance(objB)，
objA 可以作为某个方法的参数被传进来，这样可以动态的看两个对象是否类型兼容。
基本一致

Log.i(Base.class instanceof Object);Object是个特殊吧？

getClass（）是一个类的实例所具备的方法，而class（）方法是一个类的方法。
另外getClass（）是在运行时才确定的，而class（）方法是在编译时就确定了
     */
    public static void main(String[] args) {
        TestB mTestB = new TestB();
        Base mBase = mTestB.new Base();
        Child mChild = mTestB.new Child();
        Base mChildBase = mTestB.new Child();
        Log.i("--11----------------");
        Log.i("Base.class instanceof Object :",Base.class instanceof Object);
        //no/Log.i(Child.class instanceof Base);
        Log.i("Object.class.isInstance(Child.class):",Object.class.isInstance(Child.class));
        //no/Log.i(Object.isInstance(Child.class));

        Log.i("Object.class.isInstance(Base.class) :",Object.class.isInstance(Base.class));
        Log.i("Base.class.isInstance(Child.class) :",Base.class.isInstance(Child.class));

        Log.i("mChildBase instanceof Child ",mChildBase instanceof Child);
        Log.i("Child.class.isInstance(mChildBase) ",Child.class.isInstance(mChildBase));

        Log.i("Child.class.getSuperclass() == Base.class :",Child.class.getSuperclass() == Base.class);
        Log.i(11,mBase.getClass() == Base.class);
        Log.i(12,Child.class.getSuperclass() == Child.class);

        mChild = null;
        Log.i(13,mChild instanceof Base);
        Log.i(14,mChild instanceof Object);

        mTestB.test();

/*
false
true


 */
    }

    /*

    打印的是 Test 类名。
    由于 getClass() 方法在 Object 类是 final 的，子类不能覆盖该方法，
    所以在 test 方法中调用 getClass().getName() 方法的实质就是在调用从父类继承的 getClass() 方法，
    等效于调用 super.getClass().getName() 方法；然而 getClass() 方法来自 Object 类且其返回对象在运行时的类型，
    所以运行时类型为 Test，如果想得到父类的名称，应该调用 getClass().getSuperClass().getName() 获取。

     */
    public void test(){
        Log.i(super.getClass().getSimpleName());
        Log.i(this.getClass().getSuperclass().getSimpleName());
    }
    //========================================================






}