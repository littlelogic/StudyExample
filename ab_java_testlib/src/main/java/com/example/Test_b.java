package com.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Test_b {

    public static void main(String[] args) throws ParseException {
//        test_a();
        test_b();
    }

    public static void test_a()throws ParseException{
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date startDate = dateFormatter.parse("2010/11/28 01:06:00");
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //System.out.println("execute task!"+ this.scheduledExecutionTime());
                System.out.println("execute task!-->"+ System.currentTimeMillis());
            }
        },startDate, 1 * 1000);
    }

    static int num = 0 ;

    public static void test_b()throws ParseException{
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date startDate = dateFormatter.parse("2010/11/28 01:06:00");
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
        ///timer.scheduleAtFixedRate(new TimerTask(){
            public void run() {
                System.out.println("execute Thread().getName-01->"+ Thread.currentThread().getId()
                        +"-time->"+System.currentTimeMillis());
                try {
                    //num++;
                    if (num == 4) {
                        Thread.sleep(4000);
                    } else {
                        Thread.sleep(500);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //System.out.println("execute task!"+ this.scheduledExecutionTime());
                System.out.println("execute Thread().getName-99->"+ Thread.currentThread().getId()
                        +"-time->"+System.currentTimeMillis());
            }
        //},new Date(System.currentTimeMillis()), 1 * 1000);
        //},new Date(System.currentTimeMillis() - 2000), 1 * 1000);
    //},startDate, 1 * 1000);
    },0, 1 * 1000);



    }

}
