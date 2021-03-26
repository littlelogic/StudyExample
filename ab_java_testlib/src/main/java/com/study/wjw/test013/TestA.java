package com.study.wjw.test013;

import com.study.wjw.z_utils.Log;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *


 */
public class TestA {


    /*
    Throwable 是所有异常的父类，它有两个直接子类 Error 和 Exception，
    Error 表示系统错误，通常不能预期和恢复（譬如 JVM 崩溃、内存不足等）；
    Exception 又被继续划分为被检查的异常和运行时的异常；
    被检查的异常在程序中能预期且要尝试修复,如我们必须捕获 FileNotFoundException 异常并为用户提供有用信息和合适日志来进行调试，
    Exception 是所有被检查的异常的父类）；
    运行时异常又称为不受检查异常，譬如我们检索数组元素之前必须确认数组的长度，
    否则就可能会抛出 ArrayIndexOutOfBoundException 运行时异常，
    RuntimeException 是所有运行时异常的父类。

     */
    public static void main(String[] args) {

        TestA mA_test_a = new TestA();
        Throwable mThrowable;
        Error mError;
        Exception mException;
        RuntimeException mRuntimeException;
        ArrayIndexOutOfBoundsException mArrayIndexOutOfBoundsException;

        try {
            mA_test_a.test_01();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mA_test_a.test_02();
        int[] num = {1};
        Log.i(mA_test_a.test_04(num));
        Log.i(num[0]);
        Log.i("test_05->"+mA_test_a.test_05());
        mA_test_a.test_03();//异常可以不用显示处理，但是也会爆发
        Log.i("--last-->");
    }

    void test_01() throws FileNotFoundException {
        int test = 01;
        if (test == 02) {
            throw new FileNotFoundException();
        }
    }

    void test_02() throws ArrayIndexOutOfBoundsException {

    }

    void test_03() {
        throw new ArrayIndexOutOfBoundsException();
    }


    int test_04(int[] num){
        num[0] = 0;
        try{
            return num[0];
        }finally {
            num[0] = 2;
        }
    }

    /*
    避免在 finally 中使用 return 语句或者抛出异常，如果调用的其他代码可能抛出异常则应该捕获异常并进行处理，
    因为 finally 中 return 不仅会覆盖 try 和 catch 内的返回值且还会掩盖 try 和 catch 内的异常，
    就像异常没有发生一样（特别注意，当 try-finally 中没有 return 时该方法运行会继续抛出异常）。

    尽量不要在 catch 块中压制异常（即什么也不处理直接 return），
    因为这样以后无论抛出什么异常都会被忽略，以至没有留下任何问题线索，
    如果在这一层不知道如何处理异常最好将异常重新抛出由上层决定如何处理异常。
     */
    int test_05_1(){
        int ret = 0;
        try{
            int a = 5/0;
            return ret;
        }finally {
            return 2;
        }
    }

    int test_05(){
        int ret = 0;
        try{
            return ret;
        }finally {
            /*try {
                int a = 5/0;
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            return 2;
        }
    }

    void test_06(){
        int ret = 0;
        try{
            int a = 5/0;
        }finally {
            throw new RuntimeException("hello");
        }
    }

    class Base {
        public void funa() throws IOException {
            throw  new IOException("Base IOException");
        }
        public void funb() throws IOException {
            throw  new IOException("Base IOException");
        }
        public void func() throws IOException {
            throw  new IOException("Base IOException");
        }
    }

    class Sub extends Base {
        /*public void func() throws Exception {
            throw  new Exception("Sub IOException");
        }*/
        /*@Override
        public void func() throws AccessDeniedException {
            throw  new AccessDeniedException("Sub IOException");
        }*/

        /*
        在 java 中重写方法抛出的异常不能是原方法抛出异常的父类，
        这里 func 方法在父类中抛出了 IOException，所有在子类中的
        func 方法只能抛出 IOExcepition 或是其子类，但不能是其父类。
         */
        @Override
        public void funa() {
            //super.funa();
        }

        @Override
        public void funb() throws IOException,FileNotFoundException {
        }

        //@Override
        public void func() /*throws Exception*/ {
            try {
                int test;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }














}