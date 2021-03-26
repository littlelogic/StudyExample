package others;

import others.SingletonDemo;

public class SingletonDemoHelper {


    public static void main(String[] args) {
        SingletonDemo mSingletonDemo = null;
//        SingletonDemo.SomeThing kk = SingletonDemo.SomeThing.INSTANCE;
        mSingletonDemo = SingletonDemo.getInstanceF();
    }

}
