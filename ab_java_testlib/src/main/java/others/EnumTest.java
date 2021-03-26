package others;

import com.study.wjw.z_utils.Log;

public class EnumTest {



    public interface Behaviour {
        void print();
    }
/**  1.枚举类也是类,可以有自己的成员变量,方法，静态方法、静态变量等
     <能实现接口>，不能继承其他类（因为已经继承了java.lang.Enum）
     2.枚举类与普通类的不同是，它的构造器私有，决定了被继承时的特殊性。
     若其它的外部类继承它，由于在构造类A的对象时，需要调用父类的构造方法，
     由于枚举类的构造器私有，无法调用，导致<枚举类不可以被外部类继承>
     但是有没有办法去继承它？答案是有，需要用到内部类了,<试了没用>
     (内部类能访问外部类的任何成员，当然能访问已被私有的构造器了)。                                           */
    public enum MediaStyle implements Behaviour{
        normal(0){
            public MediaStyle getNext(){
                return tail;
            }
            public void print(){}
        },
        tail(1){
            public void print(){}
        };

        public MediaStyle getNext(){
            return null;
        }

        public final int value;
        MediaStyle(int value) {
            this.value = value;
        }
        public static MediaStyle get(int value){
            if (value == normal.value) {
                return normal;
            } else if (value == tail.value) {
                return tail;
            } else {
                return normal;
            }
        }

        public static final long tail_time = 3000;
        private int location = 0;
        void setLocation(int location_){
            location=location_;
        }
        public int getLocation(){
            return location;
        }

        /*cannot inherit(继承) from enum*/
        /* class Inner extends MediaStyle */
    }
    public static void main(String[] args) {
        MediaStyle tail_1 = MediaStyle.tail;
        tail_1.setLocation(1);
        MediaStyle tail_2 = MediaStyle.tail;
        tail_2.setLocation(2);
        //todo 下面两个输出都是 true  MediaStyle.tail实例是唯一的
        Log.i(tail_1.getLocation()==tail_2.getLocation());
        Log.i(tail_1 == tail_2);
    }
}
