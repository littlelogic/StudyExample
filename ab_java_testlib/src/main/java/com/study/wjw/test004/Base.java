package com.study.wjw.test004;

public abstract class Base {

    Base() {
        System.out.println("--Base--instantiation---");
        preProcess();
    }

    ///abstract void preProcess();

    static void preProcess(){

    }
}


