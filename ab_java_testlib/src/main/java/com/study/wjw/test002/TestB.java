package com.study.wjw.test002;


import com.study.wjw.z_utils.Log;

abstract class Base {

    public String whenAmISet = "set when instantiation";
    public static String instance_variable = "instance_variable";

    Base() {
        System.out.println("Base-instantiation");
        preProcess();
    }

    void preProcess(){

    }

    private static void staticMethod(){ }

    public static void staticMethodB(){ }
}

class Derived extends Base {

    public static String whenAmISet = "set when instantiation";
    public String instance_variable = "Derived-instance_variable";

    Derived() {
        System.out.println("Child-instantiation");
    }

    @Override
    void preProcess() {
        System.out.println("Child-preProcess");
        whenAmISet = "set in preProcess method";
    }

    void staticMethod(){ }

    ///void staticMethodB(){ }
}


/**
 * Created by wujiawen on 2018/3/5.
 */
public class TestB {

    public static void main(String[] args) {
        Derived d = new Derived();
        System.out.println(d.whenAmISet);
        ////----
        Log.i(Derived.whenAmISet);
        Log.i(d.instance_variable);

    }
    /*


     */
}