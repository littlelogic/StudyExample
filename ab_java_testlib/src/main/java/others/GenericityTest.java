package others;

import com.study.wjw.z_utils.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenericityTest {

    public static void main(String[] args) {
        System.out.println("------>");
        Log.i("");
    }



    /*
    泛型有三种使用方式，分别为：泛型类、泛型接口、泛型方法
     */


    /*
    泛型的类型参数只能是类类型，不能是简单类型。
    不能对确切的泛型类型使用instanceof操作。如下面的操作是非法的，编译时会出错。
    if(ex_num instanceof Generic<Number>){ }
     */
    public class Generic<T>{
        //key这个成员变量的类型为T,T的类型由外部指定
        private T key;

        public Generic(T key) { //泛型构造方法形参key的类型也为T，T的类型由外部指定
            this.key = key;
        }

        public T getKey(){ //泛型方法getKey的返回值类型为T，T的类型由外部指定
            return key;
        }
    }

    public void test_1 (){
        Generic generic = new Generic("111111");
        Generic generic1 = new Generic(4444);
        Generic generic2 = new Generic(55.55);
        Generic generic3 = new Generic(false);

        Log.d("泛型测试","key is " + generic.getKey());
        Log.d("泛型测试","key is " + generic1.getKey());
        Log.d("泛型测试","key is " + generic2.getKey());
        Log.d("泛型测试","key is " + generic3.getKey());
    }

    //定义一个泛型接口
    public interface Generator<T> {
        public T next();
    }

    /**
     * 未传入泛型实参时，与泛型类的定义相同，在声明类的时候，需将泛型的声明也一起加到类中
     * 即：class FruitGenerator<T> implements Generator<T>{
     * 如果不声明泛型，如：class FruitGenerator implements Generator<T>，编译器会报错："Unknown class"
     */
    class FruitGenerator<T> implements Generator<T>{
        @Override
        public T next() {
            return null;
        }
    }

    /**
     * 传入泛型实参时：
     * 定义一个生产器实现这个接口,虽然我们只创建了一个泛型接口Generator<T>
     * 但是我们可以为T传入无数个实参，形成无数种类型的Generator接口。
     * 在实现类实现泛型接口时，如已将泛型类型传入实参类型，则所有使用泛型的地方都要替换成传入的实参类型
     * 即：Generator<T>，public T next();中的的T都要替换成传入的String类型。
     */
     class FruitGenerator2 implements Generator<String> {

        private String[] fruits = new String[]{"Apple", "Banana", "Pear"};

        @Override
        public String next() {
            Random rand = new Random();
            return fruits[rand.nextInt(3)];
        }
    }


    ///=================

    /**
     * 泛型方法的基本介绍
     * @param tClass 传入的泛型实参
     * @return T 返回值为T类型
     * 说明：
     *     1）public 与 返回值中间<T>非常重要，可以理解为声明此方法为泛型方法。
     *     2）只有声明了<T>的方法才是泛型方法，泛型类中的使用了泛型的成员方法并不是泛型方法。
     *     3）<T>表明该方法将使用泛型类型T，此时才可以在方法中使用泛型类型T。
     *     4）与泛型类的定义一样，此处T可以随便写为任意标识，常见的如T、E、K、V等形式的参数常用于表示泛型。
     */
    public <T> T genericMethod(Class<T> tClass)throws InstantiationException ,
            IllegalAccessException{
        T instance = tClass.newInstance();
        return instance;
    }

    public class GenericTest {
        //这个类是个泛型类，在上面已经介绍过
        public class Generic<T>{
            private T key;

            public Generic(T key) {
                this.key = key;
            }

            //我想说的其实是这个，虽然在方法中使用了泛型，但是这并不是一个泛型方法。
            //这只是类中一个普通的成员方法，只不过他的返回值是在声明泛型类已经声明过的泛型。
            //所以在这个方法中才可以继续使用 T 这个泛型。
            public T getKey(){
                return key;
            }

            /**
             * 这个方法显然是有问题的，在编译器会给我们提示这样的错误信息"cannot reslove symbol E"
             * 因为在类的声明中并未声明泛型E，所以在使用E做形参和返回值类型时，编译器会无法识别。
             public E setKey(E key){
             this.key = keu
             }
             */
        }

        /**
         * 这才是一个真正的泛型方法。
         * 首先在public与返回值之间的<T>必不可少，这表明这是一个泛型方法，并且声明了一个泛型T
         * 这个T可以出现在这个泛型方法的任意位置.
         * 泛型的数量也可以为任意多个
         *    如：public <T,K> K showKeyName(Generic<T> container){
         *        ...
         *        }
         */
        public <T> T showKeyName(Generic<T> container){
            System.out.println("container key :" + container.getKey());
            //当然这个例子举的不太合适，只是为了说明泛型方法的特性。
            T test = container.getKey();
            return test;
        }

        //这也不是一个泛型方法，这就是一个普通的方法，只是使用了Generic<Number>这个泛型类做形参而已。
        public void showKeyValue1(Generic<Number> obj){
            Log.d("泛型测试","key value is " + obj.getKey());
        }

        //这也不是一个泛型方法，这也是一个普通的方法，只不过使用了泛型通配符?
        //同时这也印证了泛型通配符章节所描述的，?是一种类型实参，可以看做为Number等所有类的父类
        public void showKeyValue2(Generic<?> obj){
            Log.d("泛型测试","key value is " + obj.getKey());
        }

        /**
         * 这个方法是有问题的，编译器会为我们提示错误信息："UnKnown class 'E' "
         * 虽然我们声明了<T>,也表明了这是一个可以处理泛型的类型的泛型方法。
         * 但是只声明了泛型类型T，并未声明泛型类型E，因此编译器并不知道该如何处理E这个类型。
         public <T> T showKeyName(Generic<E> container){
         ...
         }
         */

        /**
         * 这个方法也是有问题的，编译器会为我们提示错误信息："UnKnown class 'T' "
         * 对于编译器来说T这个类型并未项目中声明过，因此编译也不知道该如何编译这个类。
         * 所以这也不是一个正确的泛型方法声明。
         public void showkey(T genericObj){

         }
         */

    }

    public class GenericFruit {
        class Fruit{
            @Override
            public String toString() {
                return "fruit";
            }
        }

        class Apple extends Fruit{
            @Override
            public String toString() {
                return "apple";
            }
        }

        class Person{
            @Override
            public String toString() {
                return "Person";
            }
        }

        class GenerateTest<T>{
            public void show_1(T t){
                System.out.println(t.toString());
            }

            //在泛型类中声明了一个泛型方法，使用泛型E，这种泛型E可以为任意类型。可以类型与T相同，也可以不同。
            //由于泛型方法在声明的时候会声明泛型<E>，因此即使在泛型类中并未声明泛型，编译器也能够正确识别泛型方法中识别的泛型。
            public <E> void show_3(E t){
                System.out.println(t.toString());
            }

            //在泛型类中声明了一个泛型方法，使用泛型T，注意这个T是一种全新的类型，可以与泛型类中声明的T不是同一种类型。
            public <T> void show_2(T t){
                System.out.println(t.toString());
            }
        }

        public void mainee(String[] args) {
            Apple apple = new Apple();
            Person person = new Person();

            GenerateTest<Fruit> generateTest = new GenerateTest<Fruit>();
            //apple是Fruit的子类，所以这里可以
            generateTest.show_1(apple);
            //编译器会报错，因为泛型类型实参指定的是Fruit，而传入的实参类是Person
            //generateTest.show_1(person);

            //使用这两个方法都可以成功
            generateTest.show_2(apple);
            generateTest.show_2(person);

            //使用这两个方法也都可以成功
            generateTest.show_3(apple);
            generateTest.show_3(person);
        }
    }

    /*
    <T>：泛型标识符，用于泛型定义（类、接口、方法等）时，可以想象成形参。
    <?>：通配符，用于泛型实例化时，可以想象成实参。
     */
    public void showKeyValue1(Generic<? extends Number> obj){
        Log.d("泛型测试","key value is " + obj.getKey());
    }

    public void showKeyValue2(Generic<? super Integer> obj){
        Log.d("泛型测试","key value is " + obj.getKey());
    }

    public <M> void showKeyValue3(M m){

    }



    public static <T> T show(T t){
        return null;
    }

    public static Object show2(Object t){
        return null;
    }

    public static void show5(){
        List<? super Integer>[] ls21 = new ArrayList[10];
        List<String>[] ls = new ArrayList[10];
        List<Object>[] ls2 = new ArrayList[10];
        List<?>[] ls3 = new ArrayList<?>[10];
        //List<Object>[] ls4 = new ArrayList<Object>[10];
        ls[0].get(0);
    }



}
