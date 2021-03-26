package com.example;

public class MyClass {

    public static void main(String[] args){

        System.out.println("--------->>");
        System.out.println("---1------>>");
        System.out.println("---2------>>");
        System.out.println("---3------>>");

        String a = "hello2";
        final String b = "hello";
        String c = b + 2;
        String d = "hello";
        String e = d + 2;
        System.out.println((a == c));
        System.out.println((a == e));

    }



}
