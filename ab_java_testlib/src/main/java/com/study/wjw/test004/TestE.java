package com.study.wjw.test004;



//class Child extends Base {
class Child  {

    Child() {
        System.out.println("--Child--instantiation---");
        preProcess();
    }

    static void preProcess() {
        System.out.println("--Child--preProces-whenAmISet-"+whenAmISet);
        whenAmISet = "set in preProcess method";
        System.out.println("--Child--preProces-whenAmISet-"+whenAmISet);
    }

    static{
//        System.out.println(whenAmISet);
        whenAmISet = "set in preProcess method";
//        System.out.println("--Child--preProces-whenAmISet-"+whenAmISet);
    }

    {
        whenAmISetFF = "set in preProcess method 222";
    }


    /*{
        whenAmISetFF = "set in preProcess method";
    }*/

    public static String whenAmISet = "set when instantiation";
    ///public String whenAmISetFF;
    ///public String whenAmISetFF = Print("");
    public String whenAmISetFF = "eeeee";

    {
        System.out.println("--Child--Print-whenAmISetFF-"+whenAmISetFF);
        whenAmISetFF = "set in preProcess method 33";
    }
    {
        whenAmISetFF = "set in preProcess method 44";
    }

    String Print(String text){

        System.out.println("--Child--Print-whenAmISetFF-"+whenAmISetFF);
        return "";
    }
}


/**
 * Created by wujiawen on 2018/3/5.
 * https://blog.csdn.net/darxin/article/details/5293427
 */
public class TestE {

    {
        start = "11";
        //a/System.out.println("-start->"+start);
    }

    String print(){
        System.out.println("-start->"+start);
        return "22";
    }

    String start = print();

    {
        //b/System.out.println("-start->"+start);
    }

    static{
        //c/System.out.println("-start->"+static_str);
        static_str = "11";
        //d/System.out.println("-start->"+static_str);
    }
    static  String static_str;

    public static void main(String[] args) {
        new TestE();
    }


}