package com.study.wjw.test005;

/**
 *

 java中的编译期常量包括java八大基本类型和直接声明的string类型。
 什么是直接声明的string类型呢？就是没有通过new关键字声明的字符串（String str = "abc";）。
 编译期常量用（public）static final 修饰,编译期常量必须要在声明时进行初始化
 进行常量折叠需要满足：
 1. 编译时常量（数字字面值，字符串字面值等）。
 2. 编译时常量进行简单运算的结果也是编译时常量，如1 + 2，”a”+ “b”。
 3. 被 final 修饰的基本类型和字符串变量也是编译时常量。


 *
 */
class OutClass {

    static {
        System.out.println("initalized OutClass!");
    }

    public static int inititalize_varible = 1;

    public static final int INITIALIZED_VARIBLE = 2;

    public static final int INITIALIZED_varible ;

    static {
        ///System.out.println(INITIALIZED_varible);
        INITIALIZED_varible = 1;
    }

    class InClass {

        //yes/ public static final String s1 = "0";
        //no/ public static final String s2 = new String("0");
        //no/ public static final int num = Math.abs(2);
    }

    public static final int num = Math.abs(2);
    public static final int num_b = true ? 3 : 2;

}


public class TestInitializeClass {

    public static void main(String[] args) {
        /**
         * 分别执行下面的输出语句的结果如何
         */
        System.out.println(OutClass.INITIALIZED_VARIBLE);
        System.out.println(OutClass.inititalize_varible);
        System.out.println(OutClass.INITIALIZED_varible);

        System.out.println(OutClass.num);
        System.out.println(OutClass.num_b);
    }
/*
 2

 */
}