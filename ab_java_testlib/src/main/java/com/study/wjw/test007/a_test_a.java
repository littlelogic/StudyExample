package com.study.wjw.test007;

import com.study.wjw.z_utils.Log;

/**

 因为浮点数不能完全精确的表示出来，一般都会损失精度。
 和浮点数在电脑上的存储方式有关,用“==”要确认
 这几个记下来，注意此类问题

 */
public class a_test_a {

    /**
     因为浮点数不能完全精确的表示出来，一般都会损失精度。
     和浮点数在电脑上的存储方式有关,用“==”要确认
     这几个记下来，注意此类问题
     */
    void ll(){
        Log.i(0.1*1==.1);
        Log.i(0.1*2==.2);
        Log.i(0.1*3==.3);//false
        Log.i(0.1*4==.4);
        Log.i(0.1*5==.5);
        Log.i(0.1*6==.6);//false
        Log.i(0.1*7==.7);//false
        Log.i(0.1*8==.8);
        Log.i(0.1*9==.9);
    }

    public static void main(String[] args) {
        Log.i("-start->"+(3*0.1 == 0.3));
        System.out.print("0.1*1="+0.1*1+"    ");Log.i(0.1*1==.1);
        System.out.print("0.1*2="+0.1*2+"    ");Log.i(0.1*2==.2);
        System.out.print("0.1*3="+0.1*3+"    ");Log.i(0.1*3==.3);
        System.out.print("0.1*4="+0.1*4+"    ");Log.i(0.1*4==.4);
        System.out.print("0.1*5="+0.1*5+"    ");Log.i(0.1*5==.5);
        System.out.print("0.1*6="+0.1*6+"    ");Log.i(0.1*6==.6);
        System.out.print("0.1*7="+0.1*7+"    ");Log.i(0.1*7==.7);
        System.out.print("0.1*8="+0.1*8+"    ");Log.i(0.1*8==.8);
        System.out.print("0.1*9="+0.1*9+"    ");Log.i(0.1*9==.9);
        //----
        Log.i(0.6==.6);
        //float f = 3.4;
        //new TestA();


    }
}