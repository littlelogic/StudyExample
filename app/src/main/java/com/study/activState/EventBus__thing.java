package com.study.activState;
//com.study.activState.EventBusActiv
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.badlogic.utils.ALog;
import com.example.wujiawen.a_Main.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EventBus__thing extends Activity {


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }
    @Override protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEventB1(EventbusEvents.EventB event) { }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEventB2(EventbusEvents.EventB event) {
        //todo 事件取消仅限于ThreadMode.PostThread下才可以使用
        EventBus.getDefault().cancelEventDelivery(event);
    }
    protected void post() {
        EventBus.getDefault().post(new EventbusEvents.EventB("POSTING"));
        EventBus.getDefault().postSticky(new EventbusEvents.EventStickyC());
        //todo 相同类型的粘滞事件覆盖之前的 ??
        EventBus.getDefault().postSticky(new EventbusEvents.EventStickyC());
    }
    @Subscribe(threadMode = ThreadMode.POSTING,sticky = true)
    public void EventStickyC(EventbusEvents.EventStickyC event) {
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().removeStickyEvent(EventbusEvents.EventStickyC.class);
        EventBus.getDefault().removeStickyEvent(event);
    }


}
