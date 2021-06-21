package com.study.z_reference;

// com.study.z_reference.ReferenceActivity
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.badlogic.utils.ALog;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import static java.sql.DriverManager.println;



public class ReferenceActivity extends Activity {



    class Person{

        public Person(){

        }

    }


    private WeakReference<Person> mWeakReference = null;
    private ReferenceQueue queue = new ReferenceQueue();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Person s = new Person();
        mWeakReference = new WeakReference(s, queue);
        s = null;
        System.gc();

        Button button = new Button(this);
        button.setText("poll");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.gc();
                Log.i("wjw","-ReferenceActivity-onCreate-onClick-mWeakReference.get()->" + mWeakReference.get());
                WeakReference reference = (WeakReference) queue.poll();
                if (reference != null) {
                    Log.i("wjw","-ReferenceActivity-onCreate-onClick-reference.get()->" + reference.get());
                    if (mWeakReference == reference) {
                        Log.i("wjw","-ReferenceActivity-onCreate-onClick-哈哈监听到啦！->");
                    }
                }
            }
        });
        setContentView(button);
    }






    /**
     *
     java中的引用类型和他们的对象的可及性
     强引用 StrongReference "=" 直接复制，强可及对象，永远都不会被GC回收。
     软引用 SoftReference，当系统内存不足的时候，被GC回收。在android优先级下降，可能一次gc就回收了。
     弱引用 WeakRefrence ，当系统GC发现这个对象，就被回收。
     虚引用 PhantomReference

     Refrence 和引用队列 ReferenceQueue 联合使用时，如果 Refrence持有的对象被垃圾回收，
     Java 虚拟机就会把这个弱引用加入到与之关联的引用队列中。

     java 监听StrongReference的回收？？？？


     Reference和ReferenceQueue的应用案例
     通过上面的说明，我们已经了解了Reference和ReferenceQueue的大概，下面来看它们配合使用的一个例子；LeakCanary，想必大家都很熟悉了，
     它是开发阶段用来检测内存泄漏的一个库，这其中的原理就是使用WeakReference和ReferenceQueue完成的。这里只描述一下，就不贴代码了。

     在应用的Application类中注册一个ActivityLifecycleCallbacks回调，重写onActivityDestroyed(Activity activity)方法
     在退出Activity的时候，该回调中的destroy方法触发，创建一个WeakReference对象包装这个销毁的activity目标对象，并指定一个ReferenceQueue
     触发GC并监控ReferenceQueue的变化。因为activity对象即将被销毁，因此未来某个时刻应该仅仅存在该activity的弱引用并在GC时得到处理，对应的WeakReference对象被添加至ReferenceQueue中。
     如果一直检测不到该WeakReference对象被添加至队列中，说明肯定存在其他的引用路径，也就代表了可能存在内存泄漏问题
     通过android.os.Debug#dumpHprofData方法dump此时的java heap至一个文件中，在后台通过工具分析该heap profile，找出目标activity的引用路径，发送状态栏通知告知开发者



     */
    public static void main(String[] args) {

        String str = "abc";//在常量池中创建了abc对象

        //在堆内存中创建了String对象
        str = new String("abc");

        //创建一个软引用，引用到str
        SoftReference<String> sfr = new SoftReference<String>(str);

        //创建一个弱引用，引用到str
        WeakReference<String> wrf = new WeakReference<String>(str);

        str = null;//相当于去掉了强引用链
        //清除软引用的引用链
        sfr.clear();

        //System.gc();//清理堆内存

        String srfString = sfr.get();
        String wrfString = wrf.get();

        System.out.println("软引用获取到的对象："+srfString);
        System.out.println("弱引用获取到的对象："+wrfString);

        try {
            main_b(null);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static boolean isRun = true;

    /**
     虚引用PhantomReference， 在<<深入理解Java虚拟机>>一文中，
     它唯一的目的就是为一个对象设置虚引用关联的唯一目的就是能在这个对象被收集器回收时收到一个系统通知。
     那么如何实现呢？它与ReferenceQueue配合使用来实现，下面给出代码示例：

     虚顾名思义就是没有的意思，建立虚引用之后通过get方法返回结果始终为null,通过源代码你会发现,虚引用通向会把引用的对象写进referent,
     只是get方法返回结果为null.先看一下和gc交互的过程在说一下他的作用.
       	 1、不把referent设置为null, 直接把heap中的new String("abc")对象设置为可结束的(finalizable).
         2、与软引用和弱引用不同, 先把PhantomRefrence对象添加到它的ReferenceQueue中.然后在释放虚可及的对象.
            你会发现在收集heap中的new String("abc")对象之前,你就可以做一些其他的事情.通过以下代码可以了解他的作用.
     */
    @SuppressWarnings("static-access")
    public static void main_b(String[] args) throws Exception {
        String abc = new String("abc");
        System.out.println(abc.getClass() + "@" + abc.hashCode());
        final ReferenceQueue<String> referenceQueue = new ReferenceQueue<String>();
        new Thread() {
            public void run() {
                while (isRun) {
                    Object obj = referenceQueue.poll();
                    if (obj != null) {
                        try {
                            Field rereferent = Reference.class.getDeclaredField("referent");
                            ///--Field rereferent = PhantomReference.class.getDeclaredField("referent");
                            rereferent.setAccessible(true);
                            Object result = rereferent.get(obj);//虚引用通向会把引用的对象写进referent
                            System.out.println("gc will collect："
                                    + result.getClass() + "@"
                                    + result.hashCode() + "\t"
                                    + (String) result);

							/*System.out.println("ReferenceTest-main_b"
									+ "-((Reference) obj).get()->"+((Reference) obj).get()
									+ "-obj->"+obj
									//+ "-((Reference) obj).get()->"+((Reference) obj).get().hashCode()
							);*/
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();
        PhantomReference<String> abcWeakRef = new PhantomReference<String>(abc,referenceQueue);
        abc = null;
        Thread.currentThread().sleep(3000);
        System.gc();
        Thread.currentThread().sleep(3000);
        isRun = false;
    }

}
