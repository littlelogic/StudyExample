package com.study.wjw.test011;

import com.study.wjw.z_utils.Log;

/**
 *


 */
public class TestA {

    public static void main(String[] args) {
        test_01();

        new TestA().test_02();
        new TestA().test_03();
    }

    /*
    首先 count++ 是一个有返回值的表达式，返回值是 count 自加前的值，
    Java 对自加处理的流程是先把 count 的值（不是引用）拷贝到一个临时变量区，
    然后对 count 变量加1，接着返回临时变量区的值。
    i++,返回使用之前的值，++i返回使用之后的值
     */
    public static void test_01() {
        int count = 0 , num = 0;
        for (int i = 0; i < 100; i++) {
            count = ++count;
            num = num++;//
        }
        Log.i("++count:"+count+",  num++:"+num);
    }

    //======================

    class A {
        public String run(D obj) {
            return "AD";
        }
        public String run(A obj) {
            return "AA";
        }
    }

    class B extends A{
        public String run(B obj) {
            return "BB";
        }
        public String run(A obj) {
            return "BA";
        }
    }

    class C extends B{}
    class D extends B{}

    /*

      成员变量：编译看左，运行看左（因为无法重写）；
      成员方法：编译看左，运行看右（因为普通成员方法可以重写，变量不可以）；
      静态方法：编译看左，运行看左（因为属于类）；
     */
    public void test_02() {

        A aa = new A();
        A ab = new B();
        B b = new B();
        C c = new C();
        D d = new D();
        /*Log.i(aa.run(b));
        Log.i(aa.run(c));
        Log.i(aa.run(d));*/

        /*if (ab instanceof B) {
            Log.i(((B)ab).run(b));
        }*/

        Log.i("-test_02-start---");
        Log.i(ab.run(b));
        Log.i(ab.run(c));
        Log.i(((B)ab).run(b));
        Log.i(ab.run(d));

        Log.i(b.run(b));
        Log.i(b.run(c));
        Log.i(b.run(d));
        Log.i("-test_02-end---");
/*
BB
BA
BA
AD

BB
BB
AD--

 */
    }

    //======================

    class Base {
        Base(){

        }
        public void print(String ... array){
            Log.i("Base-print");
        }
        private void print2(String[] array){ }
    }

    class Sub extends  Base{
        Sub(){
            this.print2("");
            super.print("");
        }
        //@Override
        public void print(String[] array){
            Log.i("Sub-print");
        }

        //no/@Override
        public void print2(String ... array){}

        public void print2(String array){
            Log.i("Sub-print-b");
        }
    }

    public void test_03() {
        Base mBase = new Sub();
        mBase.print("11");
        Sub mSub = new Sub();
        //mSub.print("11");

    }
/*
Sub-print-b
Base-print
Sub-print
Sub-print-b
Base-print


 */



}