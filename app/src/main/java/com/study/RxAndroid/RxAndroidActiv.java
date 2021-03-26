package com.study.RxAndroid;
//com.study.RxAndroid.RxAndroidActiv
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.badlogic.utils.ThreadName;
import com.badlogic.utils.Threader;
import com.badlogic.utils.Tools;
import com.example.wujiawen.a_Main.R;
import com.example.wujiawen.ui.manage.BaseActivity;
import com.study.activState.EventBusFragment;
import com.study.activState.EventbusEvents;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.functions.Functions;
import io.reactivex.schedulers.Schedulers;

import static io.reactivex.schedulers.Schedulers.start;

public class RxAndroidActiv extends BaseActivity {


    int dp_5;
    String Tag = "RxAndroid12";
    String TAG = Tag;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.show_id = 2019 1116 1453l;
        RelativeLayout mRelativeLayout = new RelativeLayout(this);
        mRelativeLayout.setBackgroundColor(Color.WHITE);
        this.setContentView(mRelativeLayout);

        ScrollView mScrollView = new ScrollView(this);
        mRelativeLayout.addView(mScrollView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout mLinearLayout = new LinearLayout(this);
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
        mLinearLayout.setBackgroundColor(Color.WHITE);
        mScrollView.addView(mLinearLayout, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        ///-------------
        dp_5 = Tools.dip2px(this,5);

        addTestView(mLinearLayout, "test_1 多个onComplete不影响", new Runnable() {
            @Override
            public void run() {
                test_1(1);
            }
        });
        addTestView(mLinearLayout, "test_1 onComplete和onError互斥", new Runnable() {
            @Override
            public void run() {
                test_1(2);
            }
        });

        addTestView(mLinearLayout, "test_1 发多个onError，Observer收到第二个是错误", new Runnable() {
            @Override
            public void run() {
                test_1(3);
            }
        });
        addTestView(mLinearLayout, "test_2a just构造Observable", new Runnable() {
            @Override
            public void run() {
                test_2a();
            }
        });
        addTestView(mLinearLayout, "test_2b observable.subscribe(onNextConsumer)的拓展，不完全调用", new Runnable() {
            @Override
            public void run() {
                test_2b();
            }
        });
        addTestView(mLinearLayout, "test_3 fromArray(T[])构造Observable", new Runnable() {
            @Override
            public void run() {
                test_3();
            }
        });
        addTestView(mLinearLayout, "test_3b filter 条件过滤", new Runnable() {
            @Override
            public void run() {
                test_3b();
            }
        });
        addTestView(mLinearLayout, "test_3c take 最多保留的事件数", new Runnable() {
            @Override
            public void run() {
                test_3c();
            }
        });
        addTestView(mLinearLayout, "test_4 create线程调度,subscribeOn,observeOn", new Runnable() {
            @Override
            public void run() {
                test_4();
            }
        });
        addTestView(mLinearLayout, "test_5"," just线程调度,subscribeOn,observeOn" );
        addTestView(mLinearLayout, "test_5_b"," doOnSubscribe,subscribeOn,observeOn的多次调用" );
        addTestView(mLinearLayout, "test_6  Observable–>'map变换'–>Observable", new Runnable() {
            @Override
            public void run() {
                test_6();
            }
        });
        addTestView(mLinearLayout, "test_7  create和map,map功能包含doOnNext", new Runnable() {
            @Override
            public void run() {
                test_7();
            }
        });
        addTestView(mLinearLayout, "test_8 zip", new Runnable() {
            @Override
            public void run() {
                test_8();
            }
        });
        addTestView(mLinearLayout, "test_9 retrolambda表达式", new Runnable() {
            @Override
            public void run() {
                test_9();
            }
        });
        addTestView(mLinearLayout, "test_10 多次改变observeOn", new Runnable() {
            @Override
            public void run() {
                test_10();
            }
        });
        addTestView(mLinearLayout, "test_21 defer 和 just联用", new Runnable() {
            @Override
            public void run() {
                test_21();
            }
        });






//        addTestView(mLinearLayout, "test_2 发多个onError，Observer收到第二个是错误", new Runnable() {
//            @Override
//            public void run() {
//                test_test();
//            }
//        });


    }

    public void addTestView(LinearLayout mLinearLayout,String text,Runnable hRunnable){
        TextView hTextView_1 = new TextView(this);
        LinearLayout.LayoutParams ParamsTop_1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mLinearLayout.addView(hTextView_1);
        hTextView_1.setPadding(0,dp_5,0,dp_5);
        ParamsTop_1.topMargin = Tools.dip2px(this,10);
        hTextView_1.setLayoutParams(ParamsTop_1);
        hTextView_1.setGravity(Gravity.CENTER);
        hTextView_1.setTextColor(Color.BLACK);
        hTextView_1.setBackground(Tools.getCornerDrawable(Color.LTGRAY,0));
        hTextView_1.setText(text);
        hTextView_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hRunnable.run();
            }
        });

    }

    public void addTestView(LinearLayout mLinearLayout,String methodName,String text){
        TextView hTextView_1 = new TextView(this);
        LinearLayout.LayoutParams ParamsTop_1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mLinearLayout.addView(hTextView_1);
        hTextView_1.setPadding(0,dp_5,0,dp_5);
        ParamsTop_1.topMargin = Tools.dip2px(this,10);
        hTextView_1.setLayoutParams(ParamsTop_1);
        hTextView_1.setGravity(Gravity.CENTER);
        hTextView_1.setTextColor(Color.BLACK);
        hTextView_1.setBackground(Tools.getCornerDrawable(Color.LTGRAY,0));
        hTextView_1.setText(methodName + " " +text);
        hTextView_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Method hMethod = RxAndroidActiv.class.getDeclaredMethod(methodName);
                    hMethod.setAccessible(true);
                    hMethod.invoke(RxAndroidActiv.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    ///=====================================================

    /*

    https://www.jianshu.com/p/7eb5ccf5ab1e
    
    创建被观察者对象Observable
    创建观察者Observer
    连接观察者和被观察者subscribe

    被观察者通过onNext函数给观察者通知结果
    被观察者onComplete函数通知观察者执行结束
    连接观察者和被观察者我们使用subscribe函数

    通过打印的log我们可以看到观察者函数调用情况,调用subscribe函数去绑定观察者和被观察者时候，
    观察者的onSubscribe函数会被回调表示建立关联。
    接着每当被观察者调用onNext给观察者发送数据时候，观察者的onNext 会收到回调，并且得到所发送的数据。
    当被观察者调用onComplete函数时候,代表着完成，观察者的onComplete回调会被触发，
    并且断开了两者的关联，这时被观察者再发送数据，观察者也不会收到。

    关于onComplete和onError唯一并且互斥这一点, 是需要自行在代码中进行控制,
    如果你的代码逻辑中违背了这个规则, 并不一定会导致程序崩溃.
    比如发送多个onComplete是可以正常运行的, 依然是收到第一个onComplete就不再接收了,
    但若是发送多个onError, 则收到第二个onError事件会导致程序会崩溃.当我们写多个onComplete时，不会报错。

    除了被观察者能断开关联，观察者也能主动断开连接，
    调用onSubscribe函数中传入的对象Disposable的dispose()函数即可完成断开连接，
    同样关联断开后，被观察者依然会继续发送数据

    我们需要先改变Observable发送事件的线程, 让它去子线程中发送事件,
    然后再改变Observer的线程, 让它去主线程接收事件.
    通过RxAndroid内置的线程调度器可以很轻松的做到这一点

    */
    private void test_1(final int mark) {
        Log.i(Tag,"191025a-RxAndroidActiv-test_1-01");
        /*Observable.create(new ObservableOnSubscribe(){
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {

            }
        });*/

        Observable<String> oble = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                if (e.isDisposed()) {
                    return;
                }
                if (mark == 1) {
                    e.onNext("RxAndroidActiv-test_1-Observable-onNext1");
                    Log.i(Tag,"RxAndroidActiv-test_1-Observer-onComplete");
                    e.onComplete();
                    e.onNext("RxAndroidActiv-test_1-Observable-onNext2");
                    Log.i(Tag,"RxAndroidActiv-test_1-Observer-onComplete");
                    e.onComplete();
                } else if (mark == 2) {
                    e.onNext("RxAndroidActiv-test_1-Observable-onNext1");
                    Log.i(Tag,"RxAndroidActiv-test_1-Observer-onComplete");
                    e.onComplete();
                    e.onError(new NullPointerException());
                    e.onNext("RxAndroidActiv-test_1-Observable-onNext2");
                } else if (mark == 3) {
                    e.onNext("191025a-RxAndroidActiv-test_1-Observable-onNext1");
                    Log.i(Tag,"191025a-RxAndroidActiv-test_1-Observer-onError");
                    e.onError(new NullPointerException());
                    e.onNext("RxAndroidActiv-test_1-Observable-onNext2");
                    Log.i(Tag,"191025a-RxAndroidActiv-test_1-Observer-onError");
                    e.onError(new NullPointerException());
                }
            }
        });

        Observer<String> oser = new Observer<String>() {

            Disposable mDisposable;
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mDisposable = d;
                Log.i(Tag,"191025a-RxAndroidActiv-test_1-Observer-onSubscribe");
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.i(Tag,"191025a-RxAndroidActiv-test_1-Observer-onNext->"+s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(Tag,"191025a-RxAndroidActiv-test_1-Observer-onError");
                mDisposable.dispose();
            }

            @Override
            public void onComplete() {
                Log.i(Tag,"191025a-RxAndroidActiv-test_1-Observer-onComplete");
                mDisposable.dispose();
            }
        };

        oble.subscribe(oser);
        Log.i(Tag,"191025a-RxAndroidActiv-test_1-99");

    }

    ///=====================================================

    /*
    just(T...): 将传入的参数依次发送出来。
     */
    private void test_2a() {
        Observable<String> observable = Observable.just("hello1","hello2");
        // 将会依次调用：
        // onNext("hello1");
        // onNext("hello2");
        // onCompleted();
        Observer<String> oser = new Observer<String>() {

            Disposable mDisposable;
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mDisposable = d;
                Log.i(Tag,"191025a-RxAndroidActiv-test_1-Observer-onSubscribe");
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.i(Tag,"191025a-RxAndroidActiv-test_1-Observer-onNext->"+s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(Tag,"191025a-RxAndroidActiv-test_1-Observer-onError");
                mDisposable.dispose();
            }

            @Override
            public void onComplete() {
                Log.i(Tag,"191025a-RxAndroidActiv-test_1-Observer-onComplete");
                mDisposable.dispose();
            }
        };
        observable.subscribe(oser);
    }

    private void test_2a_1() {
        String first_str_array[] = new String[]{"hello1_a","hello1_b","hello1_c","hello1_d"};
        String second_str_array[] = new String[]{"hello1_a","hello1_b","hello1_c","hello1_d"};
        Observable<String[]> observable = Observable.just(first_str_array,second_str_array);
        ///--Observable<String> observable = Observable.just("hello1","hello2");
        // 将会依次调用：
        // onNext("hello1");
        // onNext("hello2");
        // onCompleted();
        Observer<String[]> oser = new Observer<String[]>() {

            Disposable mDisposable;
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mDisposable = d;
                Log.i(Tag,"191025a-RxAndroidActiv-test_1-Observer-onSubscribe");
            }

            @Override
            public void onNext(@NonNull String[] s) {
                Log.i(Tag,"191025a-RxAndroidActiv-test_1-Observer-onNext->"+s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(Tag,"191025a-RxAndroidActiv-test_1-Observer-onError");
                mDisposable.dispose();
            }

            @Override
            public void onComplete() {
                Log.i(Tag,"191025a-RxAndroidActiv-test_1-Observer-onComplete");
                mDisposable.dispose();
            }
        };
        observable.subscribe(oser);
    }

    private void test_2b() {
        Observable<String> observable = Observable.just("hello");
        Consumer<String> onNextConsumer = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.i(Tag,"191025a-RxAndroidActiv-test_3-accept-s->"+s);
            }
        };
        Consumer<Throwable> onErrorConsumer = new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(Tag,"191025a-RxAndroidActiv-test_3-accept->");
            }
        };
        Action onCompleteAction = new Action() {
            @Override
            public void run() throws Exception {
                Log.i(Tag,"191025a-RxAndroidActiv-test_3-onCompleteAction->");
            }
        };
        Consumer<Disposable> onSubscribeConsumer = new Consumer<Disposable>() {
            @Override
            public void accept(Disposable s) throws Exception {
                Log.i(Tag,"191025a-RxAndroidActiv-test_3-onSubscribeConsumer->");
            }
        };

        observable.subscribe(onNextConsumer, onErrorConsumer, onCompleteAction,onSubscribeConsumer);

        Log.w(Tag,"191025a-----------------------------------------------");
        observable.subscribe(onNextConsumer, onErrorConsumer, onCompleteAction);

        Log.w(Tag,"191025a-----------------------------------------------");
        observable.subscribe(onNextConsumer, onErrorConsumer);

        Log.w(Tag,"191025a-----------------------------------------------");
        observable.subscribe(onNextConsumer);
    }

    ///=====================================================

    /*
    todo  fromArray(T[]) / fromArray(Iterable < ? extends T>):
          将传入的数组或 Iterable 拆分成具体对象后，依次发送出来。
    */
    private void test_3() {
        String[] words = {"Hello1", "Hello1", "Hello3"};
        Observable observable = Observable.fromArray(words);
        // 将会依次调用：
        // onNext("Hello1");
        // onNext("Hello1");
        // onCompleted();
        Observer<String> oser = new Observer<String>() {

            Disposable mDisposable;
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mDisposable = d;
                Log.i(Tag,"191025a-RxAndroidActiv-test_4-Observer-onSubscribe");
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.i(Tag,"191025a-RxAndroidActiv-test_4-Observer-onNext->"+s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(Tag,"191025a-RxAndroidActiv-test_4-Observer-onError");
                mDisposable.dispose();
            }

            @Override
            public void onComplete() {
                Log.i(Tag,"191025a-RxAndroidActiv-test_4-Observer-onComplete");
                mDisposable.dispose();
            }
        };
        observable.subscribe(oser);
    }

    /**
     *
     * todo filter 条件过滤，去除不符合某些条件的事件。举个栗子:
     */
    private void test_3b() {
        Observable.fromArray(new Integer[]{1, 2, 3, 4, 5})
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        // 偶数返回true，则表示剔除奇数，留下偶数
                        return integer % 2 == 0;
                    }
                }).
                subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(Tag,"191025a-RxAndroidActiv-test_3b-Observer-accept-integer->"+integer);
                    }
                });
    }

    /*
    todo take 最多保留的事件数。只影响 onNext
     */
    private void test_3c() {
        Observable.fromArray(new Integer[]{1, 2, 3, 4, 5,6})
                .take(3)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(Tag,"191025a-RxAndroidActiv-test_3c-onSubscribe->");
                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.i(Tag,"191025a-RxAndroidActiv-test_3c-onNext-value->"+value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(Tag,"191025a-RxAndroidActiv-test_3c-onError->");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(Tag,"191025a-RxAndroidActiv-test_3c-onComplete->");
                    }
                });
    }

    ///=====================================================

    /*
    todo 线程调度

    subscribeOn() 指定的是Observable发送事件的线程,指定Observable自身在哪个调度器上执行
    observeOn() 指定的是Observer接收事件的线程.指定一个观察者在哪个调度器上观察这个Observable
    多次指定Observable的线程只有第一次指定的有效, 也就是说多次调用subscribeOn() 只有第一次的有效, 其余的会被忽略.
    多次指定Observer的线程是可以的, 也就是说每调用一次observeOn() , Observer的线程就会切换一次


    Schedulers.io(): I/O 操作（读写文件、数据库、网络请求等），与newThread()差不多，区别在于io() 的内部实现是是用一个无数量上限的线程池，可以重用空闲的线程，因此多数情况下 io() 效率比 newThread() 更高。值得注意的是，在 io() 下，不要进行大量的计算，以免产生不必要的线程；
    Schedulers.newThread(): 开启新线程操作；
    Schedulers.immediate(): 默认指定的线程，也就是当前线程；
    Schedulers.computation():计算所使用的调度器。这个计算指的是 CPU 密集型计算，即不会被 I/O等操作限制性能的操作，例如图形的计算。这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。值得注意的是，不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU；
    AndroidSchedulers.mainThread(): Rxndroid 扩展的 Android 主线程；

     */
    private void test_4() {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.i(Tag,"191025a-RxAndroidActiv-test_4-subscribe-01"
                        + "-ThreadName.get()->"+ ThreadName.get()
                );
                emitter.onNext(1);
            }
        });

        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(Tag,"191025a-RxAndroidActiv-test_4-accept-01"
                        + "-ThreadName.get()->"+ ThreadName.get()
                        + "-integer->"+ integer
                );
            }
        };

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }

    private void test_5() {
        Log.i(Tag,"191025a-RxAndroidActiv-test_5-01");
        Observable.just(getDrawableFromNet("http://www.baidu.com/icon.png"))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Drawable>() {
                    @Override
                    public void accept(Drawable drawable) throws Exception {
                        Log.i(Tag,"191025a-RxAndroidActiv-test_5-Consumer-accept"
                                + "-ThreadName.get()->"+ ThreadName.get()
                        );
                        ///((ImageView)findViewById(R.id.imageview)).setImageDrawable(drawable);
                    }
                });

    }

    // 默认情况下， doOnSubscribe() 执行在 subscribe() 发生的线程；
    // 而如果在 doOnSubscribe() 之后有 subscribeOn() 的话，
    // 它将执行在离它最近的 subscribeOn() 所指定的线程。
    // doOnSubscribe()与onStart()类似，均在代码调用时就会回调，
    // 但doOnSubscribe()可以通过subscribeOn()操作符改变运行的线程且越在后面运行越早；
    // map会在上面指定的线程中执行
    private void test_5_b(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> observableEmitter) throws Exception {
                Log.i(TAG, "create:" + Thread.currentThread().getName());
                observableEmitter.onNext(1);
                observableEmitter.onComplete();
            }
        })
        .subscribeOn(Schedulers.newThread())
        .map(new Function<Integer, Integer>() {
            @Override
            public Integer apply(@NonNull Integer integer) throws Exception {
                Log.i(TAG, "map:" + Thread.currentThread().getName());
                return integer;
            }
        })
        .observeOn(AndroidSchedulers.mainThread())
        .map(new Function<Integer, Integer>() {
            @Override
            public Integer apply(@NonNull Integer integer) throws Exception {
                Log.i(TAG, "map-1:"+Thread.currentThread().getName());
                return integer;
            }
        })
        .observeOn(Schedulers.newThread())
        .map(new Function<Integer, Integer>() {
            @Override
            public Integer apply(@NonNull Integer integer) throws Exception {
                Log.i(TAG, "map-2:"+Thread.currentThread().getName());
                return integer;
            }
        })
        .doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(@NonNull Disposable disposable) throws Exception {
                Log.i(TAG, "2 doOnSubscribe:" + Thread.currentThread().getName());
            }
        })
        .subscribeOn(Schedulers.io())
        .doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(@NonNull Disposable disposable) throws Exception {
                Log.i(TAG, "1 doOnSubscribe:" + Thread.currentThread().getName());
            }
        })
        .subscribeOn(Schedulers.newThread())
        .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                Log.i(TAG, "subscribe:" + Thread.currentThread().getName());
            }
        });
    }




    // 模拟网络请求图片
    private Drawable getDrawableFromNet(String url){
        Log.i(Tag,"191025a-RxAndroidActiv-getDrawableFromNet-01"
                + "-ThreadName.get()->"+ ThreadName.get()
        );
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return getResources().getDrawable(R.drawable.icon);
    }

    /*
    对于线程还需要注意

    create() , just() , from()   等              --- 事件产生
    map() , flapMap() , scan() , filter()  等    --  事件加工
    subscribe()                                  --  事件消费

    事件产生：默认运行在当前线程，可以由 subscribeOn()  自定义线程
    事件加工：默认跟事件产生的线程保持一致, 可由 observeOn() 自定义线程
    事件消费：默认运行在当前线程，可以有observeOn() 自定义

    三种方式创建的Observable指定IO操作使用区别：
    Observable.create().....subscribeOn(Schedulers.io())  创建子线程
    Observable.just().....subscribeOn(Schedulers.io())未创建子线程
    Observable.from().....subscribeOn(Schedulers.io())未创建子线程

    Map是RxAndroid中最简单的一个变换操作符了,
    它的作用就是对Observable发送的每一个事件应用一个函数,
    使得每一个事件都按照指定的函数去变化。
    Observable –> map变换 –> Observable
    url -> drawable -> bitmap
     */

    private void test_6() {
        Observable.just(getDrawableFromNet("00"))
                .map(new Function<Drawable, Bitmap>() {
                    @Override
                    public Bitmap apply(@NonNull Drawable drawable) throws Exception {
                        Log.i(Tag,"191025a-RxAndroidActiv-test_6-map-01"
                                + "-ThreadName.get()->"+ ThreadName.get()
                        );
                        BitmapDrawable bt = (BitmapDrawable)drawable;
                        return bt.getBitmap();
                    }
                })

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) throws Exception {
                        Log.i(Tag,"191025a-RxAndroidActiv-test_6-subscribe-01"
                                + "-ThreadName.get()->"+ ThreadName.get()
                        );
                    }
                });
    }


    /**
     * todo create和map 一起使用
     */
    private void test_7() {
        Observable.create(new ObservableOnSubscribe<Drawable>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Drawable> e) throws Exception {
                Log.i(Tag,"191025a-RxAndroidActiv-test_7-create-subscribe-01"
                        + "-ThreadName.get()->"+ ThreadName.get()
                );
                Drawable drawable = getResources().getDrawable(R.drawable.icon);
                Log.i(Tag,"191025a-RxAndroidActiv-test_7-create-subscribe-onNext");
                e.onNext(drawable);
                Log.i(Tag,"191025a-RxAndroidActiv-test_7-create-subscribe-onComplete");
                e.onComplete();
                Log.i(Tag,"191025a-RxAndroidActiv-test_7-create-subscribe-99");
            }})
            .map(new Function<Drawable, Bitmap>() {
                @Override
                public Bitmap apply(@NonNull Drawable drawable) throws Exception {
                    Log.i(Tag,"191025a-RxAndroidActiv-test_7-map-01"
                            + "-ThreadName.get()->"+ ThreadName.get()
                    );
                    BitmapDrawable bt = (BitmapDrawable)drawable;
                    return bt.getBitmap();
                }
            })
            .doOnNext(new Consumer<Bitmap>() {
                @Override
                public void accept(Bitmap mBitmap) throws Exception {
                    Log.i(Tag,"191025a-RxAndroidActiv-test_7-doOnNext-01-mBitmap->"+mBitmap);
                }
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<Bitmap>() {
                @Override
                public void accept(Bitmap bitmap) throws Exception {
                    Log.i(Tag,"191025a-RxAndroidActiv-test_7-subscribe-01"
                            + "-ThreadName.get()->"+ ThreadName.get()
                    );
                }
            });
    }

    /*
    todo ZIP
    Zip通过一个函数将多个Observable发送的事件结合到一起，然后发送这些组合到一起的事件.
    它按照严格的顺序应用这个函数。它只发射与发射数据项最少的那个Observable一样多的数据。
     */
    private void test_8() {
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.i(Tag,"191025a-RxAndroidActiv-test_8-observable1-subscribe-emitter 1 -Threader.getName()->"+Threader.getName());
                emitter.onNext(1);
                Log.i(Tag,"191025a-RxAndroidActiv-test_8-observable1-subscribe-emitter 2");
                emitter.onNext(2);
                Log.i(Tag,"191025a-RxAndroidActiv-test_8-observable1-subscribe-emitter 3");
                emitter.onNext(3);
                Log.i(Tag,"191025a-RxAndroidActiv-test_8-observable1-subscribe-emitter 4");
                emitter.onNext(4);
                Log.i(Tag,"191025a-RxAndroidActiv-test_8-observable1-subscribe-emitter complete1");
                emitter.onComplete();
            }
        });
        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.i(Tag,"191025a-RxAndroidActiv-test_8-observable2-subscribe-emitter A -Threader.getName()->"+Threader.getName());
                emitter.onNext("A");
                Log.i(Tag,"191025a-RxAndroidActiv-test_8-observable2-subscribe-emitter B");
                emitter.onNext("B");
                Log.i(Tag,"191025a-RxAndroidActiv-test_8-observable2-subscribe-emitter C");
                emitter.onNext("C");
                Log.i(Tag,"191025a-RxAndroidActiv-test_8-observable2-subscribe-emitter complete2");
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.newThread());
        Observable.zip(observable2, observable1, new BiFunction<String, Integer, String>() {
            @Override
            public String apply( String s,Integer integer) throws Exception {
        /*Observable.zip(observable1,observable2,  new BiFunction< Integer,String, String>() {
            @Override
            public String apply( Integer integer,String s) throws Exception {*/
                Log.i(Tag,"191025a-RxAndroidActiv-test_8-zip-apply-01");
                return integer + s;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(Tag,"191025a-RxAndroidActiv-test_8-onSubscribe");
            }

            @Override
            public void onNext(String value) {
                Log.i(Tag,"191025a-RxAndroidActiv-test_8-onNext-value->"+value);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(Tag,"191025a-RxAndroidActiv-test_8-onNext-onError->");
            }

            @Override
            public void onComplete() {
                Log.i(Tag,"191025a-RxAndroidActiv-test_8-onNext-onComplete->");
            }
        });
    }

    ///=====================================================

    private void test_9() {
        new Thread(){
            @Override
            public void run() {
                Log.i(Tag,"191025a-RxAndroidActiv-test_9-run-01");
            }
        }.start();

//        new Thread(()-> Log.i(Tag,"191025a-RxAndroidActiv-test_9-run-retrolambda-01")).start();
    }

    ///=====================================================

    private void test_10(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
                @Override
                public void subscribe(ObservableEmitter<Integer> observableEmitter) throws Exception {
                    Log.i(TAG, "create:" + Thread.currentThread().getName());
                    observableEmitter.onNext(1);
                    observableEmitter.onComplete();
                }
            })
            .subscribeOn(Schedulers.newThread())
            .map(new Function<Integer, Integer>() {
                @Override
                public Integer apply(@NonNull Integer integer) throws Exception {
                    Log.i(TAG, "map-1:"+Thread.currentThread().getName());
                    return integer;
                }
            })
            .observeOn(Schedulers.newThread())
            .map(new Function<Integer, Integer>() {
                @Override
                public Integer apply(@NonNull Integer integer) throws Exception {
                    Log.i(TAG, "map-2:"+Thread.currentThread().getName());
                    return integer;
                }
            })
            .observeOn(Schedulers.io())
            .map(new Function<Integer, Integer>() {
                @Override
                public Integer apply(@NonNull Integer integer) throws Exception {
                    Log.i(TAG, "map-3:"+Thread.currentThread().getName());
                    return integer;
                }
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<Integer>() {
                @Override
                public void accept(@NonNull Integer integer) throws Exception {
                    Log.i(TAG, "subscribe:"+Thread.currentThread().getName());
                }
            });
    }

    ///=====================================================

    public Observable<Integer> getRxJavaCreateExampleData() {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) {
                emitter.onNext(1);

                emitter.onNext(2);

                emitter.onNext(3);
                emitter.onComplete();
                emitter.onNext(4);

            }
        });
    }

    private void test_21() {
        Log.i(Tag,"191025a-RxAndroidActiv-test_21-01");
        Observable hObservable = Observable.defer(new Callable<ObservableSource<String>>(){

            @Override
            public ObservableSource<String> call() throws Exception {
                Log.i(Tag,"191025a-RxAndroidActiv-test_21-defer-call-"
                        + "-ThreadName.get()->"+ ThreadName.get()
                );
                //--return null;
                return Observable.just("ObservableSource-Observable.just");
                /*return Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) {
                        Log.i(Tag,"191025a-RxAndroidActiv-test_21-Observable.create-"
                                + "-ThreadName.get()->"+ ThreadName.get()
                        );
                        emitter.onNext("ObservableSource-Observable.create-next-1");
                        emitter.onNext("ObservableSource-Observable.create-next-2");
                        emitter.onComplete();
                        emitter.onNext("ObservableSource-Observable.create-next-3");
                    }
                });*/
            }
        });
        Log.i(Tag,"191025a-RxAndroidActiv-test_21-02");
        hObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Log.i(Tag,"191025a-RxAndroidActiv-test_21-Consumer-accept"
                                + "-ThreadName.get()->"+ ThreadName.get()
                                + "-ThreadName.get()-o->"+ o
                        );
                    }
                },new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(Tag,"191025a-RxAndroidActiv-test_21-Consumer-accept"
                                + "-ThreadName.get()->"+ ThreadName.get()
                        );
                    }
                },new Action(){
                    @Override
                    public void run() throws Exception {
                        Log.i(Tag,"191025a-RxAndroidActiv-test_21-Action-run"
                                + "-ThreadName.get()->"+ ThreadName.get()
                        );
                    }
                });
        Log.i(Tag,"191025a-RxAndroidActiv-test_21-99");





    }

    ///=====================================================

    /*todo
        doOnNext
        doOnSubscribe
        doOnTerminate
     */

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
