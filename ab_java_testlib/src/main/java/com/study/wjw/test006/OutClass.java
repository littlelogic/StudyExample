package com.study.wjw.test006;


/**

 final类型变量的赋值要在构造函数结束之前，
 static final类型变量的赋值要在所有静态代码块结束之前，
 使用最终变量的默认值只允许通过方法,

 *
 */
class OutClass {

    static final String var_a;
    static final int var_b;
    final int var_c;
    int var_d;

    static {
        //no/String here = var_a;
        print_s();
        var_b = 5;
        var_a = "2";
        //yes/String here_b = var_a;
    }

    OutClass(){
        //no/int here_ = var_c;
        //yes/int here_d = var_d;
        print();
        var_c = 3;
        int here = var_c;
    }

    void print(){
        System.out.println(var_c);
        int here;
        ///System.out.println(here);
    }

    static void print_s(){
        System.out.println(var_a);
        System.out.println(var_b);
    }

    public static void main(String[] args) {
        new OutClass();
    }

/*



 */

}
