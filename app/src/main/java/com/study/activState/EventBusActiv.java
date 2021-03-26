package com.study.activState;
//com.study.activState.EventBusActiv
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.badlogic.utils.ALog;
import com.example.wujiawen.a_Main.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EventBusActiv extends Activity {

    public EventBusActiv(){
        EventBus.builder().build();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        this.setContentView(R.layout.activ_eventbus);
        this.findViewById(R.id.a_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new EventbusEvents.EventString("ThreadMode.MAIN"));
            }
        });
        this.findViewById(R.id.a_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new EventbusEvents.EventB("ThreadMode.POSTING"));
            }
        });
        this.findViewById(R.id.a_6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(new EventbusEvents.EventStickyC("postStickyA",EventBusActiv.this));
                //相同类型的粘滞事件覆盖之前的
                EventBus.getDefault().postSticky(new EventbusEvents.EventStickyC("postStickyB",EventBusActiv.this));
                EventBusFragment hEventBusFragment =  new EventBusFragment();
                EventBusActiv.this.getFragmentManager().beginTransaction().add(hEventBusFragment,"hEventBusFragment");
            }
        });
        this.findViewById(R.id.a_7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBusFragment hEventBusFragment =  new EventBusFragment();
                EventBusActiv.this.getFragmentManager().beginTransaction().add(hEventBusFragment,"hEventBusFragment");

            }
        });
        this.findViewById(R.id.a_8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().removeStickyEvent(EventbusEvents.EventStickyC.class);
                ///EventBus.getDefault().removeAllStickyEvents();
                EventBusFragment hEventBusFragment =  new EventBusFragment();
                EventBusActiv.this.getFragmentManager().beginTransaction().add(hEventBusFragment,"hEventBusFragment");

            }
        });
        this.findViewById(R.id.a_9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(new EventbusEvents.EventStickyC("postStickyC",EventBusActiv.this));
            }
        });
        this.findViewById(R.id.a_10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* new Thread(){
                    @Override
                    public void run() {
                        super.run();

                    }
                }.start();*/

                EventBus.getDefault().postSticky(new EventbusEvents.EventC("EventC__01"));
            }
        });





    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventA1(EventbusEvents.EventString event) {
        if (event != null && event.cntentStr != null) {
            Toast.makeText(this,"onEventAA :"+event.cntentStr,Toast.LENGTH_SHORT).show();
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventA2(EventbusEvents.EventString event) {
        ALog.i("EventBusActiv-onEventA2-01->");
        if (event != null && event.cntentStr != null) {
            Toast.makeText(this,"onEventA2 :"+event.cntentStr,Toast.LENGTH_SHORT).show();
        }
        EventBus.getDefault().cancelEventDelivery(event);
    }
    @Subscribe(threadMode = ThreadMode.MAIN,priority = 1)
    public void onEventA3(EventbusEvents.EventString event) {
        ALog.i("EventBusActiv-onEventA3-01->");
        if (event != null && event.cntentStr != null) {
            Toast.makeText(this,"onEventA3 :"+event.cntentStr,Toast.LENGTH_SHORT).show();
        }
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEventA4(EventbusEvents.EventString event) {
        ALog.i("EventBusActiv-onEventA4-01->");
        if (event != null && event.cntentStr != null) {
            Toast.makeText(this,"onEventA4 :"+event.cntentStr,Toast.LENGTH_SHORT).show();
        }
    }
    @Subscribe(threadMode = ThreadMode.POSTING,sticky = true)
    public void onEventA5(EventbusEvents.EventString event) {
        ALog.i("EventBusActiv-onEventA5-01->");
        if (event != null && event.cntentStr != null) {
            Toast.makeText(this,"onEventA5 sticky :"+event.cntentStr,Toast.LENGTH_SHORT).show();
        }
    }



    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEventB1(EventbusEvents.EventB event) {
        ALog.i("EventBusActiv-onEventB1-01->");
        if (event != null && event.cntentStr != null) {
            Toast.makeText(this,"onEventB1 :"+event.cntentStr,Toast.LENGTH_SHORT).show();
        }
        //todo 事件取消仅限于ThreadMode.PostThread下才可以使用
        EventBus.getDefault().cancelEventDelivery(event);
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEventB2(EventbusEvents.EventB event) {
        ALog.i("EventBusActiv-onEventB2-01->");
        if (event != null && event.cntentStr != null) {
            Toast.makeText(this,"onEventB2 :"+event.cntentStr,Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventC(EventbusEvents.EventC event) {
        EventBus.getDefault().postSticky(new EventbusEvents.EventD("EventD__01"));
        if (event != null && event.cntentStr != null) {
            ALog.i("EventBusActiv-onEventC-MAIN-01-event.cntentStr->"+event.cntentStr);
            Toast.makeText(this,"onEventC :"+event.cntentStr,Toast.LENGTH_SHORT).show();
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public void onEventD(EventbusEvents.EventD event) {
        if (event != null && event.cntentStr != null) {
            ALog.i("EventBusActiv-onEventD-MAIN-01-event.cntentStr->"+event.cntentStr);
            Toast.makeText(this,"onEventD :"+event.cntentStr,Toast.LENGTH_SHORT).show();
        }
    }








    @Subscribe(threadMode = ThreadMode.POSTING,sticky = false)
    public void EventStickyCc(EventbusEvents.EventStickyC event) {
        ALog.i("EventBusActiv-EventStickyCc-01-(sticky = false)->");
        if (event != null && event.cntentStr != null) {
            Toast.makeText(event.mContext,"EventStickyCc :"+event.cntentStr,Toast.LENGTH_SHORT).show();
        }
    }





}
