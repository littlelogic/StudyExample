package com.example;

public class Y {
    String yVar = "Y value";
    Y() {
        System.out.println("Y construction executed!");
        System.out.println(yVar);
        yVar = "y value changed!";
    }

    void show() {
        System.out.println(yVar);
    }
}
