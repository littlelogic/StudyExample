package com.study.activState;//com.media.editor.mainedit.Fragment_MainEdit

import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.badlogic.utils.ALog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EventBusFragment extends Fragment {

    Context mContext;

//    public EventBusFragment(int dd){
//
//    }


    public EventBusFragment(){
        ALog.i(" 190827eventbus-EventBusFragment-构造->");
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        ALog.i(" 190827eventbus-EventBusFragment-onCreate->");
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
//            EventBus.builder().build().
        }
    }
    @Override
    public void onDestroy() {
        ALog.i(" 190827eventbus-EventBusFragment-onDestroy->");
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING,sticky = true)
    public void EventStickyC(EventbusEvents.EventStickyC event) {
        if (event != null && event.cntentStr != null) {
            Toast.makeText(event.mContext,"EventStickyC :"+event.cntentStr,Toast.LENGTH_SHORT).show();
            ALog.i(" 190827eventbus-EventBusFragment-EventStickyC->"+"EventStickyC :"+event.cntentStr);
            ///EventBus.getDefault().removeStickyEvent(event);

        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING,sticky = false)
    public void EventStickyCD(EventbusEvents.EventStickyC event) {
        ALog.i(" 190827eventbus-EventBusFragment-EventStickyCD-(sticky = false，注册时不会主动获取)->");
        if (event != null && event.cntentStr != null) {
            Toast.makeText(event.mContext,"EventStickyCc :"+event.cntentStr,Toast.LENGTH_SHORT).show();
        }
    }






















}
