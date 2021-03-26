package com.example;

public class X {


    static {
//        System.out.println(xVar);
        xVar = "ffff";
    }
    static String xVar = "X Value";

    Y b = new Y();
    static {
        System.out.println("X static block executed!");
        System.out.println(xVar);
        xVar = "static value";
    }



    X() {
        System.out.println("X construction executed!");
        System.out.println(xVar);
        xVar = "x value changed!";
//        xVarFF = "x value changed!";
        System.out.println("xVarFF--->"+xVarFF);
    }

    String xVarFF = "xVarFF";

    //X(int i) {
    //    System.out.println("X parameters construction executed!");
    //}
    /**
     *  由于 Java 类属性的初始化顺序为（静态变量、静态初始化块）>（变量、初始化块）> 构造器，所以 JVM 要求所有的静态属性必须在类对象创建之前完成初始化
     *

     X static block executed!
     X Value
     Z static block executed!
     Z non static block executed!
     Y construction executed!
     Y value
     y value changed!
     X construction executed!
     static value
     Z construction executed!
     x value changed!


     *
     *
     *
     */
}