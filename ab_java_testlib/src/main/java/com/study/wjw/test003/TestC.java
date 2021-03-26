package com.study.wjw.test003;

abstract class Base {

    Base() {
        System.out.println("Base-instantiation");
        preProcess();
        preProcess_b();
    }

    abstract void preProcess_b();

    static void preProcess(){ }
}

class Derived extends Base {

    public static String whenAmISet = "set when instantiation";

    Derived() {
        System.out.println("Child-instantiation");
    }

    static void preProcess() {
        System.out.println("Child-preProcess");
        whenAmISet = "set in preProcess method";
    }

    void preProcess_b() {
        System.out.println("Child-preProcess_b");
        whenAmISet = "set in preProcess_b method";
    }

    void eat() {
        System.out.println("Child-eat");
    }
}


/**
 * Created by wujiawen on 2018/3/5.
 */
public class TestC {

    public static void main(String[] args) {
        Derived d = new Derived();
        System.out.println(d.whenAmISet);
    }
    /*

System.out.println("Base-instantiation");
System.out.println("Child-preProcess_b");
System.out.println("Child-instantiation");
set in preProcess_b method


    */
}