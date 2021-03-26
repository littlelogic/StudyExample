package com.study.wjw.test010;

import com.study.wjw.z_utils.Log;

/**
 *
 *

 http://www.cnblogs.com/xiaoxi/p/6036701.html

 */
public class TestA {

    public static void main(String[] args) {
        test_01();

        TestA ma_test_a = new TestA();
        ma_test_a.test8();
    }

    /**
     *

（1）单独使用""引号创建的字符串都是常量,编译期就已经确定存储到String Pool中；
（2）使用new String("")创建的对象会存储到heap中,是运行期新创建的；
new创建字符串时首先查看池中是否有相同值的字符串，如果有，则拷贝一份到堆中，然后返回堆中的地址；
如果池中没有，则在堆中创建一份，然后返回堆中的地址（注意，此时不需要从堆中复制到池中，否则，
将使得堆中的字符串永远是池中的子集，导致浪费池的空间）！
（3）使用只包含常量的字符串连接符如"aa" + "aa"创建的也是常量,编译期就能确定,已经确定存储到String Pool中；
（4）使用包含变量的字符串连接符如"aa" + s1创建的对象是运行期才创建的,存储在heap中；
4.使用String不一定创建对象
在执行到双引号包含字符串的语句时，如String a = "123"，JVM会先到常量池里查找，如果有的话返回常量池里的这个实例的引用，
否则的话创建一个新实例并置入常量池里。所以，当我们在使用诸如String str = "abc"；的格式定义对象时，总是想当然地认为，
创建了String类的对象str。担心陷阱！对象可能并没有被创建！而可能只是指向一个先前已经创建的对象。
只有通过new()方法才能保证每次都创建一个新的对象。
5.使用new String，一定创建对象
对于 3 和 4 中 str1 的 substring 方法实现里面有个 index == 0 的判断，当 index 等于 0 就直接返回当前对象，
否则新 new 一个 sub 的对象返回，而 == 又是地址比较，所以结果如注释。

     */
    public static void test_01() {
        String s1 = "helloworld";
        String s2 = new String("helloworld");
        Log.i(s1==s2);//1 false 可以看出用new的方式是生成不同的对象
        final String s3="hello"+"world";
        Log.i(s3==s1); //2 true 可以看出s0跟s1是指向同一个对象

        String s5="hello" + new String("world");
        Log.i( s1==s5 ); //3 false

        String s6="helloworld1";
        String s7 = "helloworld" + 1;
        Log.i((s6 == s7)); //4 result = true
        String s8 = s1 + 1;
        Log.i((s6 == s8)); //5 result = false
        String s9 = s3 + 1;
        Log.i((s6 == s9)); //6 result = true，final s3 为常量
        final String s10 = new String("helloworld");
        String s11 = s10 + 1;
        Log.i((s6 == s11)); //7 result = false

        s11.intern();
        Log.i((s11 == s7)); //8 result = false
        s11 = s11.intern();
        Log.i((s11 == s7)); //9 result = true

        System .out.println( "helloworld" == s1.substring( 0 ));//3,true
        System .out.println( "elloworld" == s1.substring(  1 ));//4,false
    }

    /**

可变与不可变：String是不可变字符串对象，StringBuilder和StringBuffer是可变字符串对象（其内部的字符数组长度可变）。
（2）是否多线程安全：String中的对象是不可变的，也就可以理解为常量，显然线程安全。StringBuffer 与 StringBuilder
中的方法和功能完全是等价的，只是StringBuffer 中的方法大都采用了synchronized 关键字进行修饰，因此是线程安全的，
而 StringBuilder 没有这个修饰，可以被认为是非线程安全的。

很显然，new只调用了一次，也就是说只创建了一个对象。而这道题目让人混淆的地方就是这里，这段代码在运行期间确实只创建了一个对象，
即在堆上创建了"abc"对象。而为什么大家都在说是2个对象呢，这里面要澄清一个概念，该段代码执行过程和类的加载过程是有区别的。
在类加载的过程中，确实在运行时常量池中创建了一个"abc"对象，而在代码执行过程中确实只创建了一个String对象。
因此，这个问题如果换成 String str = new String("abc")涉及到几个String对象？合理的解释是2个。
个人觉得在面试的时候如果遇到这个问题，可以向面试官询问清楚”是这段代码执行过程中创建了多少个对象还是涉及到多少个对象“再根据具体的来进行回答。

     *
     */
    public static void test_02() {

        /**
         * A, String是不可变字符串对象，StringBuilder和StringBuffer是可变字符串对象（其内部的字符数组长度可变）。
         *
         * B, String中的对象是不可变的，也就可以理解为常量，显然线程安全。
         * C，StringBuffer 与 StringBuilder 中的方法和功能基本上是等价的，
         * D，StringBuffer，StringBuilder是非线程安全的，

         E，下面语句一定会输出 true
         test_a,test_b为float类型  错的
         if ( test_a > test_b || test_a <= test_b) {
            Log.i(true);
         }

         F, 下面语句创建了2个对象
         String str = new String("abc");

         */

    }

    /**
     * 采用字面值的方式赋值
     */
    /*public void test1(){
        String str1="aaa";
        String str2="aaa";
        Log.i("===========test1============");
        Log.i(str1==str2);//true 可以看出str1跟str2是指向同一个对象
    }*/

    /**
     * 采用new关键字新建一个字符串对象
     */
    public void test2(){
        String str3=new String("aaa");
        String str4=new String("aaa");
        Log.i("===========test2============");
        Log.i(str3==str4);//false 可以看出用new的方式是生成不同的对象
    }

    /**
     * 编译期确定
     */
    public void test3(){
        String s0="helloworld";
        String s1="helloworld";
        String s2="hello"+"world";
        Log.i("===========test3============");
        Log.i(s0==s1); //true 可以看出s0跟s1是指向同一个对象
        Log.i(s0==s2); //true 可以看出s0跟s2是指向同一个对象
    }

    /**
     * 编译期无法确定
     */
    public void test4(){
        String s0="helloworld";
        String s1=new String("helloworld");
        String s2="hello" + new String("world");
        Log.i("===========test4============");
        Log.i( s0==s1 ); //false
        Log.i( s0==s2 ); //false
        Log.i( s1==s2 ); //false
    }

    /**
     * 继续-编译期无法确定
     */
    public void test5(){
        String str1="abc";
        String str2="def";
        String str3=str1+str2;
        Log.i("===========test5============");
        Log.i(str3=="abcdef"); //false
    }

    /**
     * 编译期优化
     */
    public void test6(){
        String s0 = "a1";
        String s1 = "a" + 1;
        Log.i("===========test6============");
        Log.i((s0 == s1)); //result = true
        String s2 = "atrue";
        String s3= "a" + "true";
        Log.i((s2 == s3)); //result = true
        String s4 = "a3.4";
        String s5 = "a" + 3.4;
        Log.i((s4 == s5)); //result = true
    }

    /**
     * 编译期无法确定
     */
    public void test7(){
        String s0 = "ab";
        String s1 = "b";
        String s2 = "a" + s1;
        Log.i("===========test7============");
        Log.i((s0 == s2)); //result = false
    }


    /**
     * 比较字符串常量的“+”和字符串引用的“+”的区别
     */
    public void test8(){
        String test="javalanguagespecification";
        String str="java";
        String str1="language";
        String str2="specification";
        final String str_final = str + str1 + str2;
        Log.i("===========test8============");
        Log.i(test == "java" + "language" + "specification");
        Log.i(test == str + str1 + str2);
        Log.i(test == str_final);
    }

    /**
     *
     编译期确定
     和例子7中唯一不同的是s1字符串加了final修饰，对于final修饰的变量，(采用类似字面值的方式赋值)
     它在编译时被解析为常量值的一个本地拷贝存储到自己的常量池中或嵌入到它的字节码流中。
     所以此时的"a" + s1和"a" + "b"效果是一样的。故上面程序的结果为true。

     */
    public void test9(){
        String s0 = "ab";
        final String s1 = "b";
        String s2 = "a" + s1;
        Log.i("===========test9============");
        Log.i((s0 == s2)); //result = true
    }

    /**
     * 关于String.intern()

     关于String.intern()
     intern方法使用：一个初始为空的字符串池，它由类String独自维护。当调用 intern方法时，
     如果池已经包含一个等于此String对象的字符串（用equals(oject)方法确定），则返回池中的字符串。
     否则，将此String对象添加到池中，并返回此String对象的引用。
     它遵循以下规则：对于任意两个字符串 s 和 t，当且仅当 s.equals(t) 为 true 时，s.intern() == t.intern() 才为 true。
     String.intern();
     再补充介绍一点：存在于.class文件中的常量池，在运行期间被jvm装载，并且可以扩充。String的intern()方法
     就是扩充常量池的一个方法；当一个String实例str调用intern()方法时，java查找常量池中是否有
     相同unicode的字符串常量，如果有，则返回其引用，如果没有，则在常量池中增加一个unicode等于str的字符串并返回它的引用。

     */
    public void test11(){
        String s0 = "kvill";
        String s1 = new String("kvill");
        String s2 = new String("kvill");
        Log.i("===========test11============");
        Log.i( s0 == s1 ); //false
        Log.i( "**********" );
        s1.intern(); //虽然执行了s1.intern(),但它的返回值没有赋给s1
        s2 = s2.intern(); //把常量池中"kvill"的引用赋给s2
        Log.i( s0 == s1); //flase
        Log.i( s0 == s1.intern() ); //true//说明s1.intern()返回的是常量池中"kvill"的引用
        Log.i( s0 == s2 ); //true
    }




}