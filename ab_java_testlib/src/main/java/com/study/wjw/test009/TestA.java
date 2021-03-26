package com.study.wjw.test009;

import com.study.wjw.z_utils.Log;

/**
 * Java 包装类型装箱拆箱基础
 *
 Java 包装类型装箱拆箱基础
 自身范围缓存
 boolean
 char[0,127]ASCII的范围
 byte[-128,127]
 范围为256的缓存[-128,127]
 short
 int (可以通过参数配置上限)
 long
 不被缓存
 float
 doubles

 byte：8位，最大存储数据量是255，存放的数据范围是-128~127之间。
 short：16位，最大数据存储量是65536，数据范围是-32768~32767之间。
 int：32位，最大数据存储容量是2的32次方减1，数据范围是负的2的31次方到正的2的31次方减1。
 long：64位，最大数据存储容量是2的64次方减1，数据范围为负的2的63次方到正的2的63次方减1。
 boolean：只有true和false两个取值。
 char：16位，存储Unicode码，用单引号赋值。
 float：32位，数据范围在3.4e-45~1.4e38，直接赋值时必须在数字后加上f或F。
 double：64位，数据范围在4.9e-324~1.8e308，赋值时可以加d或D也可以不加。

 核心考察点就是上道题的答案，即 java 1.5 开始的自动装箱拆箱机制其实是编译器自动完成的替换，
 装箱阶段自动替换为了 valueOf 方法，拆箱阶段自动替换为了 xxxValue 方法。
 对于 Integer 类型的 valueOf 方法参数如果是 -128~127 之间的值会直接返回内部缓存池中已经存在对象的引用，
 参数是其他范围值则返回新建对象；而 Double 类型与 Integer 类型一样会调用到 Double 的 valueOf 方法，
 但是 Double 的区别在于不管传入的参数值是多少都会 new 一个对象来表达该数值（因为在指定范围内浮点型数据个数是不确定的，
 整型等个数是确定的，所以可以 Cache）。 注意：Integer、Short、Byte、Character、Long 的 valueOf 方法实现类似，
 而 Double 和 Float 比较特殊，每次返回新包装对象。
 对于两边都是包装类型的比较 == 比较的是引用，equals 比较的是值，
 对于两边有一边是表达式（包含算数运算）则 == 比较的是数值（自动触发拆箱过程），
 对于包装类型 equals 方法不会进行类型转换,是不同类型的包装类直接返回false。

 用Short的equals()方法与short进行比较的时候，short类型会被判断为是Short类型的实例，然后两个对象都会被转化为基本类型用==进行比较，所以结果为true。
 用==比较Short和short的时候，Short类型对象被拆箱（转为short基本类型），所以结果为true。
 用Short的equals()方法与int进行比较的时候，由于类型判断那里就已经为false了，直接返回false。
 用Short的equals()方法与Integer进行比较的时候，与用Short的equals()方法与int进行比较的时候同样的原因，返回结果为false。
 用==比较Short和int的时候，Short首先是进行了拆箱（转为short基本类型），然后是自动提升类型（转为int），之后才进行比较，所以结果为true。

 */
public class TestA {


    void ll(){
        com.study.wjw.z_utils.Log.i("");
    }

    void test_b(){
        Long l1=128L;
        Long l2=128L;
        Log.i(l1==l2);//1 false
        Log.i(l1==128L);//2 true

        ///Long l3=new Long(127L);
        Long l3=127L;
        Long l4=127L;
        Log.i("3:",l3==l4);//3 true
        Log.i(4,l3==127L);//4 true

        Double D1 = 100.0;
        Double D2 = 100.0;
        Log.i("5:"+(D1==D2));//5 false

        //在Boolean中定义了2个静态成员属性
        Boolean B1 = false;
        Boolean B2 = false;
        Boolean B3 = true;
        Boolean B4 = true;
        Log.i(B1==B2);//6 true
        Log.i(B3==B4);//7 true

        Log.i("---我是分界线------");
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Long g = 3L;
        Long h = 2L;
        Log.i(c==(a+b));//8 true 比较值
        Log.i(c.equals(a+b));//9 true 比较值
        Log.i(g==(a+b));//10 true //比较值
        Log.i(g.equals(a+b));//11 false //比较引用(对于包装器类型，equals方法并不会进行类型转换)
        Log.i(g.equals(a+h));//12 true比较引用

        Character C1 = 122;
        char C2 = 122;
        Character C3 = 'a';//ascii为97
        Character C4 = 65;//A的ascii
        Log.i(C1==C2);//13 true
        Log.i(C3!=97);//14 false
        Log.i(C1=='z');//15 true
        Log.i("16：",C4=='A');//16 true
    }

    public static void main(String[] args) {
        Integer mInteger;
        Long mLong;
        Float mFloat;
        Double mDouble;
        Boolean mBoolean;
        Byte mByte;
        Short mShort;
        ///----
        Character mCharacter;

        TestA a_test_a =  new TestA();
        a_test_a.test_b();

        equalsrr(33);


    }

    public static boolean equalsrr(Object var1) {
        if (var1 instanceof Long) {
            return true;
        } else {
            return false;
        }
    }
}