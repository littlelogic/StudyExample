package com.example;

public class Z extends X{
    Y y;
    static {
        System.out.println("Z static block executed!");
    }

    {
        System.out.println("Z non static block executed!");
        y = new Y();
        y.show();
    }

    Z() {
        ///super(1);
        System.out.println("Z construction executed!");
    }

    public static void main(String[] args) {
        System.out.println(new Z().xVar);
    }
}