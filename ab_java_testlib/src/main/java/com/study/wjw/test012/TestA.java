package com.study.wjw.test012;

import java.util.Objects;

/**
 equals 和 hashCode
 */
public class TestA {

    class UserBean {

        public String name;
        public int age;

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof UserBean)) {
                return false;
            }
            UserBean user = (UserBean)o;
            boolean mark = user.name.equals(name) &&
                    user.age == age;
            return mark;
        }

        /*@Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof UserBean)) {
                return false;
            }
            UserBean user = (UserBean)o;
            boolean mark = user.name.equals(name) &&
                    user.age == age;
            return mark;
        }*/

        @Override
        public int hashCode() {
            super.hashCode();
            int result = 17;
            result = 31 * result + name.hashCode();
            result = 31 * result + age;
            return result;
        }

        public int hashCode_b() {
            return Objects.hash(name,age);
        }
    }

    /*

    默认情况（没有覆盖equals方法）下equals方法都是调用Object类的equals方法，
    而Object的equals方法主要用于判断对象的内存地址引用是不是同一个地址（是不是同一个对象）。
    一些基类有重写，如String
    Object类提供的默认实现确实保证每个对象的hash码不同（在对象的内存地址基础上经过特定算法返回一个hash码）

    hashCode() 的作用是获取“哈希码”，也称为“散列码”；它实际上是返回一个int整数。这个哈希码的作用是确定该对象在“哈希表”中的“索引位置”
    hashCode() 在散列表中才有用，在其它情况下没用。在散列表中hashCode() 的作用是获取对象的散列码，
    在对对象进行散列的时候作为key输入，进而确定该对象在散列表中的位置。

    —这里所说的“不会创建类对应的散列表”是说：我们不会在HashSet, Hashtable, HashMap等等这些本质是散列表的数据结构中，用到该类。
    1. 第一种 不会创建“类对应的散列表”
    在这种情况下，类的“hashCode() 和 equals() ”没有半毛钱关系的！equals() 用来比较该类的两个对象是否相等。
    而hashCode() 则根本没有任何作用，所以，不用理会hashCode()。

    2. 第二种 会创建“类对应的散列表”
    “在标准环境下”
    也就是说对于两个对象，
    如果调用equals方法得到的结果为true，则两个对象的hashcode值必定相等；
    如果equals方法得到的结果为false，则两个对象的hashcode值不一定不同；
    如果两个对象的hashcode值不等，则equals方法得到的结果必定为false；
    如果两个对象的hashcode值相等，则equals方法得到的结果未知。
    补充说一句：“两个不同的键值对，哈希值相等”，这就是哈希冲突。

     */
    public static void test_main() { }


    /*
     问：两个对象值相同 (tmp1.equals(tmp2) == true) 但却可有不同的 HashCode 值，这句话有问题吗？
     答：这个问题是有陷阱的，
     有问题，这句话是不对的。两个对象 tmp1 和 tmp2 满足 tmp1.equals(tmp2) == true
     时它们的 HashCode 应当相同，因为 Java 对于 eqauls 方法和 hashCode 方法的规定是如果两个
     对象 equals 方法相等则它们的 hashCode 值一定要相同，如果两个对象的 hashCode 相同则它们
     的 equals 方法并不一定相同；实际中我们也可以不按照要求的原则去做，但是如果违背了上述原则就
     会发现在使用容器时相同的对象可以出现在 Set 集合中，同时增加新元素的效率会大大下降（
     对于使用哈希存储的系统，如果哈希码频繁的冲突将会造成存取性能急剧下降）。
    */
    public static void test_01() { }

    /***

     问：可以直接根据 hashCode() 方法产生的值判断两个对象是否相等吗？
     答：不能。因为 hashCode()方法的由来是根据这个对象内存储的数据及对象的一些特征来做散列并返回
     一个有符号的 32 位哈希值，所以 hashCode() 方法返回的是一个散列值，而对于一个散列来说不同
     的内容也是可能会出现相同的散列值，所以即使两个对象的 hashCode() 返回值一样也并不能代表两个
     对象是相等的，要判断两个对象是否相等还是需要使用 equals() 方法。不过要注意：两个对象的 hashCode()
     返回值相等不能判断这两个对象是相等的，但是两个对象的 hashCode() 返回值不相等则可以判断
     这两个对象一定不相等（原因如上题指导原则）。

     问：说说 hashCode() 的返回值和 == 的关系？
     答：若 == 返回 true 则两边对象的 hashCode() 返回值必须相等，若 == 返回 false 则两边
     对象的 hashCode() 返回值有可能相等，也有可能不等；因为在 Java 中对象默认的 equals 方法实
     现就是 == 比较，而 Java 对于 eqauls 方法和 hashCode 方法的规定是如果两个对象 equals 方法
     相等则它们的 hashCode 值一定要相同，如果两个对象的 hashCode 相同则它们的 equals 方法
     并不一定相同，所以可得出上面结论。


     问：Java 中 hashCode() 的作用是什么？
     答：hashCode() 的作用是为了提高在散列结构存储中查找的效率，在线性表中没有作用；
     只有每个对象的 hash 码尽可能不同才能保证散列的存取性能，事实上 Object 类提供的默认实现确实保证
     每个对象的 hash 码不同（在对象的内存地址基础上经过特定算法返回一个 hash 码）。在 Java 有些集
     合类（HashSet）中要想保证元素不重复可以在每增加一个元素就通过对象的 equals 方法比较一次，那么当元
     素很多时后添加到集合中的元素比较的次数就非常多了，也就是说如果集合中现在已经有 3000 个元素则
     第 3001 个元素加入集合时就要调用 3000 次 equals 方法，这显然会大大降低效率，于是 Java 采用了哈希
     表的原理，这样当集合要添加新的元素时会先调用这个元素的 hashCode 方法就一下子能定位到它应该放置的物
     理位置上（实际可能并不是），如果这个位置上没有元素则它就可以直接存储在这个位置上而不用再进行任何比较了，
     如果这个位置上已经有元素了则就调用它的 equals 方法与新元素进行比较，相同的话就不存，不相同就散列其它的
     地址，这样一来实际调用 equals 方法的次数就大大降低了，几乎只需要一两次，而 hashCode 的值对于
     每个对象实例来说是一个固定值。



     问：Java 中为什么重写 equals(str) 方法时尽量要重写 hashCode() 方法？
     答：这是一个检测实际项目踩坑经验的题目，因为我们经常会犯的一个很常见而又低级的错误根
     源在于重写 equals 方法时没有重写 hashCode 方法。自定义类重写 equals 方法是用来进行
     等值比较，重写 compareTo 方法是用来进行不同对象大小比较，而重写 hashCode 方法是为了将
     数据存入 HashSet、HashMap、Hashtable 等基于哈西表的集合类时进行高效比较。
     当 equals 方法被重写时通常有必要重写 hashCode 方法来维护 hashCode 方法的常规协定，
     该协定声明相等对象必须具有相等的哈希码，如果不这样做的话就会违反 hashCode 方法的常规约定，
     从而导致该类无法结合所有基于散列的集合一起正常运作，这样的集合包括 HashMap、HashSet、Hashtable 等。
     hashCode 方法的常规约定如下：
     程序执行期间只要对象 equals 方法比较操作所用到的信息没有被修改，则对这同一个对象无论调用多次 hashCode 方法都必须返回同一个整数。
     如果两个对象根据 equals 方法比较是相等的则调用这两个对象中任意一个对象的 hashCode 方法都必须产生同样的整数结果。
     如果两个对象根据 equals 方法比较是不相等的，则调用这两个对象中任意一个对象的 hashCode 方法不一定要产生相同的整数
     结果（尽量保证不相等的对象产生截然不同的整数结果是可以提高散列表性能的）。

     */
    public static void test_02() { }

    /*

    概念而已，没啥营养
    普通代码块（局部代码快）是在方法名后面用 {} 括起来的代码段，不能够单独存在，
    必须要紧跟在方法名后面且必须使用方法名调用它，作用是限定变量的生命周期和提高效率。

    构造代码块是在类中方法外用 {} 括起来的代码，作用是把所有构造方法中相同的内容抽取出来，
    将来在调用构造方法的时候会去自动调用构造代码块，构造代码快优先于构造方法。

    静态代码块是在类中方法外用 {} 括起来且添加了 static 前缀修饰的代码，作用是随着类的加载而加载且只加载一次。

    同步代码块是方法中使用 synchronized 关键字修饰并使用 {} 括起来的代码片段，表示同一
    时间只能有一个线程进入到该代码块中，作用是一种多线程并发保护机制。
     */
    public static void test_03() { }

    //========================================================






}