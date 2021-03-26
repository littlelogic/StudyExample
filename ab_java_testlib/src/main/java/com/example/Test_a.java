package com.example;

import test.algorithms.ListNode.ListNodeTestMain;

/**
 * Created by wujiawen on 2018/3/5.
 */
public class Test_a {




    public static void main(String[] args) {
        //------
        /*TestA sn = new TestA();
        sn.test_aa();*/

        //------
        /*Child d = new Child();
        System.out.println(d.whenAmISet);*/
        //---
        Z.main(null);

    }

    final Object mObject = new  Object();

    public void test_aa() {

        System.out.println("-aa--01------");
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("-aa--02------");
                try {

                    Thread.sleep(500);
                    System.out.println("-aa--03------");
                    synchronized (mObject) {
                        mObject.notify();
                    }
                    System.out.println("-aa--04------");
                } catch (Exception e) {
                    System.out.println("-aa--Exception----e--"+e);
                }
            }
        }).start();


        synchronized (mObject){
            System.out.println("-bb--01------");
            try {
                mObject.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("-bb--02------");
        }

    }


}