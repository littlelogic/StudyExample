package com.study.wjw.test005555;

/**


 */
public class a_test_a {

    public static void main(String[] args) {
        test_a ();
    }

    /**
     *
     问：java 是否存在使得语句 i > j || i <= j 结果为 false 的 i、j 值？
     答：存在，java 的数值 NaN 代表 not a number，无法用于比较，
     例如使 i =  Double.NaN; j = i; 最后 i == j 的结果依旧为 false，
     这是一道非常变态的题，巨坑，谁特么会这么用。
     */
    public static void test_a () {
        float test_a = Float.NaN;
        float test_b = test_a;
        ///if ( test_a > test_b || true) {
        if ( test_a > test_b || test_a <= test_b) {
            System.out.println("TestA-main-true-");
        } else {
            System.out.println("TestA-main-false-");
        }
    }

    public static void test_b () {
        short s1 = 1;
        //s1 = s1 + 1;
        short s2 = 1;
        s2 += 1;
    }

}