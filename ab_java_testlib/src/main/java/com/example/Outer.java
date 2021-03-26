package com.example;

public class Outer {
    class Inner {
        //--static int t1 = 100; //编译错误
        static final int t233 = 100+122289*9; //编译成功
        static final int t2 = t233 +100+122289*9; //编译成功
        //--static final int t3 = new Integer(100); //编译错误
    }

    public static void main_2(String[] args) {
        //--System.out.println(Outer.Inner.t1);
        System.out.println(Outer.Inner.t2);
        //--System.out.println(Outer.Inner.t3);

    }

    public static String foo(){
        System.out.println("foo called.");
        return "return called.";
    }

    static int test_a = 90;


    public static void main(String[] args) {
        String a = "hello2";
        final String b = "hello";
        String c = b + 2;
        String d = "hello";
        String e = "hello" + "2";
        String f = "hello";

        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
        System.out.println(e);
        System.out.println((a == "hello2"));
        System.out.println((a == c));
        System.out.println((a == e));
        System.out.println((f == d));
        System.out.println((a.equals(c) ));
        System.out.println((a.equals(e)));
        //----
        Outer obj = null;
        System.out.println(obj.foo());
        System.out.println(obj.test_a);

        String hel2="hel";
        StringBuilder helloBuilder = new StringBuilder("hel");
        System.out.println(helloBuilder.equals(hel2));
        System.out.println(hel2.equals(helloBuilder.toString()));
        System.out.println(helloBuilder.toString().equals(hel2));
    }


}