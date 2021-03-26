package com.qihoo.qmev3.deferred.docs;

import android.widget.TextView;

import com.badlogic.utils.ALog;
import com.qihoo.qmev3.deferred.Deferred;
import com.qihoo.qmev3.deferred.read.Schedule;
import com.qihoo.qmev3.deferred.read.Task;

public class Test {

    public void test(){
        Deferred.create(Schedule.QME_TASK, new Task<Object>() {
            @Override
            public void run(Object data) {
                /*
                editor_context.getInstance().cm().insureAllEnginesStopped(TAG);
                */
                Deferred.self().resolve(null);
            }
        }).done(Schedule.UI, new Task<Object>() {
            @Override
            public void run(Object data) {
                ALog.i(ALog.Tag2, "Fragment_SplitScreen-doNext-done-Schedule.UI-01->");
                try {
                    TextView uiTest = new TextView(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ALog.i(ALog.Tag2, "Fragment_SplitScreen-doNext-done-Schedule.UI-99->");
            }
        });


        Deferred.create(Schedule.QME_TASK, new Task<Void>() {
            // 引擎停止，在新线程死等
            @Override
            public void run(Void data) {
                /*
                editor_context.getInstance().cm().insureAllEnginesStopped(TAG);
                */
                Deferred.self().resolve(null);
            }
        }).done(Schedule.UI, new Task<Void>() {
            @Override
            public void run(Void data) {
                ALog.i(ALog.Tag2, "Fragment_SplitScreen-doNext-done-Schedule.UI-01->");
                try {
                    TextView uiTest = new TextView(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ALog.i(ALog.Tag2, "Fragment_SplitScreen-doNext-done-Schedule.UI-99->");
            }
        }).root().submit(null);
    }
}
