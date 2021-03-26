package com.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

public class Test_c {

    public static void main(String[] args) throws ParseException {
        test_a();
    }

    public static void test_a()throws ParseException{

        Map<Integer,Integer> mMap = new TreeMap<>();
        mMap.put(1,1);

        int value = mMap.get(2);

        mMap.put(3,value);



    }


}
