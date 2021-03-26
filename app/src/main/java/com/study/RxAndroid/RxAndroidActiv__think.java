package com.study.RxAndroid;
//com.study.RxAndroid.RxAndroidActiv
import android.app.Activity;
//import android.content.Context;
//import android.content.Context;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.badlogic.utils.ALog;
import com.badlogic.utils.ThreadName;
import com.badlogic.utils.Threader;
import com.badlogic.utils.Tools;
import com.example.wujiawen.a_Main.R;
import com.example.wujiawen.ui.manage.BaseActivity;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class RxAndroidActiv__think extends BaseActivity {

    int dp_5;
    String Tag = "RxAndroid12";
    String TAG = Tag;

    Context ll;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        test_5_b();
    }

    // 模拟网络请求图片
    private Drawable getDrawableFromNet(String url){
        return null;
    }

    ///=====================================================









    private void test_5_b(){


//todo  ？并发执行多个过程，然后合并，zip不是，他是有顺序的
/**     Observable.subscribe(Observer)
        被观察者 通过onNext函数传递消息给 观察者
        onNext onComplete onError

        <事件产生> create() just(未创建子线程) from(未创建子线程)等
        <事件加工> map() flapMap() scan() filter()等
        <事件消费> subscribe()

        subscribeOn() 指定<Observable>、<doOnSubscribe>的执行线程
        observeOn() 指定<Observer>,<事件加工>,<事件消费> 的线程
        doOnSubscribe默认执行在Observable创建的线程,与onStart()类似
        均在代码调用订阅后，<先>开始回调,<且越在后面运行越早>
        接受后面subscribeOn的<最近>的指定线程,
        map<承接>上面指定的线程中执行
        Observable –> map变换 –> Observable                                                                          */
        Observable.create(new ObservableOnSubscribe<Integer>() {
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                ALog.i("create:" + Threader.get());
                emitter.onNext(1);//通过onNext,被观察者通知观察者
                emitter.onComplete();     }})
        .subscribeOn(Schedulers.newThread())
        .doOnSubscribe(new Consumer<Disposable>() {
            @Override public void accept(@NonNull Disposable disposable) throws Exception {
                ALog.i("doOnSubscribe-2:"+Threader.get()); }})
        .map(new Function<Integer, Integer>() {
            @Override public Integer apply(@NonNull Integer integer) throws Exception {
                ALog.i("map-1:"+Threader.get());
                return integer;                     }})
        .observeOn(Schedulers.newThread())
        .map(new Function<Integer, Integer>() {
            @Override public Integer apply(@NonNull Integer integer) throws Exception {
                ALog.i("map-2:"+Threader.get());
                return integer;                  }})
        .subscribeOn(Schedulers.io())
        .doOnSubscribe(new Consumer<Disposable>() {
            @Override public void accept(@NonNull Disposable disposable) throws Exception {
                ALog.i("doOnSubscribe-1:"+Threader.get()); }})
        .subscribeOn(Schedulers.newThread())
        .subscribe(new Consumer<Integer>() {
            @Override public void accept(@NonNull Integer integer) throws Exception {
                ALog.i("subscribe:"+Threader.get()); }
        });//todo 下面是执行的结果
/*      doOnSubscribe-1:RxNewThreadScheduler-1
        doOnSubscribe-2:RxCachedThreadScheduler-1
        create:RxNewThreadScheduler-2
        map-1:RxNewThreadScheduler-2
        map-2:RxNewThreadScheduler-3
        subscribe:RxNewThreadScheduler-3

                                                                  */
    }




    /*

    https://www.jianshu.com/p/7eb5ccf5ab1e
    
    创建被观察者对象Observable
    创建观察者Observer
    连接观察者和被观察者

    被观察者通过onNext函数给观察者通知结果
    被贯彻者onComplete函数通知观察者执行结束
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

    除了被观察者能断开关联，观察者也能主动断开连接，调用onSubscribe函数中传入的对象Disposable的dispose()函数即可完成断开连接，
    同样关联断开后，被观察者依然会继续发送数据

    我们需要先改变Observable发送事件的线程, 让它去子线程中发送事件, 然后再改变Observer的线程, 让它去主线程接收事件.
    通过RxAndroid内置的线程调度器可以很轻松的做到这一点

    */
    private void test_1(final int mark) {
/**     创建<被观察者>对象Observable
        创建<观察者>Observer
        <连接>观察者和被观察者                                                                              */
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
    }

    private void test_2a() {
        Observable<String> observable=Observable.just("hello1","hello2");
        // 将会依次调用：,just(T...): 将传入的参数依次发送出来。
        // onNext("hello1");
        // onNext("hello2");
        // onCompleted();
        Observer<String> oser = new Observer<String>() {
            Disposable mDisposable;
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mDisposable = d;
            }
            @Override
            public void onNext(@NonNull String s) {}
            @Override
            public void onError(@NonNull Throwable e) { }
            @Override
            public void onComplete() {
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
        observable.subscribe(onNextConsumer, onErrorConsumer, onCompleteAction);
        observable.subscribe(onNextConsumer, onErrorConsumer);
        observable.subscribe(onNextConsumer);
    }


    ///=====================================================


    /*
    todo 线程调度
    subscribeOn() 指定的是Observable发送事件的线程, observeOn() 指定的是Observer接收事件的线程.
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




    /*
    对于线程还需要注意

    create() , just() , from()   等              --- 事件产生
    map() , flapMap() , scan() , filter()  等    --  事件加工
    subscribe()                                          --  事件消费

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




}
