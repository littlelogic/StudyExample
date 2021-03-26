package others;

import com.study.wjw.z_utils.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Fruit{
    public Fruit() {}
}
class Apple extends Fruit {
    public Apple() {}
}
class HongFuShi extends Apple {
    public HongFuShi() {}
}

//泛型有三种使用方式，分别为：泛型类、泛型接口、泛型方法
//定义一个泛型接口
interface Generator<T> {
    public T next(); }
class Generic<T> implements Generator<T> {
    public Generic() {}
    public T next() {return null;}

    //key这个成员变量的类型为T,T的类型由外部指定
    private T key;

    public Generic(T key) { //泛型构造方法形参key的类型也为T，T的类型由外部指定
        this.key = key;
    }

    public T getKey(){ //泛型方法getKey的返回值类型为T，T的类型由外部指定
        return key;
    }

    public void show_1(T t){
        Log.i(t.toString());
    }

    //<T>泛型方法，使用泛型T，T是一种全新的类型，可以与泛型类中声明的T不是同一种类型。
    public <T> void show_2(T t){
        Log.i(t.toString());
    }
    public <M> void show_M(M t){
        Log.i(t.toString());
    }
    //静态方法要使用类中定义的泛型，需声明为泛型方法，此时的泛型已经变了
    public static <T> void show_7(T t){
        Log.i(t.toString());
    }

    public <T> T show8(T t){
        return t;
    }
    public Object show9(Object t){
        return t;
    }

    public void show11(){
        //generic不用泛型类型，只会调用方法 show9
        Generic<Integer> generic = new Generic<Integer>();
        Integer hInteger = new Integer(5);
        hInteger = generic.show8(hInteger);
        hInteger = (Integer)generic.show9(hInteger);

        List<? super Apple> ls = new ArrayList</*Fruit*/>();
        ls.add(new HongFuShi()) ;
        ls.add(new Apple()) ;

        Fruit  hFruit = new Apple();
//        ls.add(hFruit) ;
//        ls.add(new Fruit()) ;


        List<? extends Apple> ls2 = new ArrayList</*Fruit*/>();
        ls.add(new HongFuShi()) ;
        ls.add(new Apple()) ;
//        ls.add(new Fruit()) ;


//        List<? super Apple>[] lsArray1 = new ArrayList<Apple>[10];
//        lsArray1[0].add(new HongFuShi()) ;
//        lsArray1[0].add(new Apple()) ;
//        lsArray1[0].add(new Fruit()) ;
//        lsArray1[0].add(new Object()) ;

//        List<? extends Apple>[] ls2 = new ArrayList[10];
//        ls1[0].add(new HongFuShi()) ;
//        ls1[0].add(new Apple()) ;
//        ls1[0].add(new Fruit()) ;


        Log.i("show11-99-");

    }

//    boolean add(? extends String e) {
//
//    }
}


public class Genericity__think {


    /*
    泛型有三种使用方式，分别为：泛型类、泛型接口、泛型方法
     */
    public static void main(String[] args) {
        System.out.println("------>");
        Log.i("");

        Generic<String>  mGeneric = new Generic<String>();
        mGeneric.show11();
    }






}
