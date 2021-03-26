package com.example;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Test_e {



    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10 ;i++) {
            list.add(""+(i+1));
        }

        int index = 0;
        int num = 0;
        for (;;) {
            String content = list.get(index);

            if (content.equals("5")) {
                list.remove(content);
            } else {
                index++;
            }
            System.out.println("--->"+content);
            num++;
            if (num >= 10) {
                break;
            }
        }
        /*for (String content : list) {
            if (content.equals("5")) {
                list.remove(content);
            }
            System.out.println("--->"+content);
        }*/





    }




}
