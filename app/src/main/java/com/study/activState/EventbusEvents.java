package com.study.activState;

import android.content.Context;

public class EventbusEvents {

    public static class EventString {

        public String  cntentStr;

        public EventString(String cntentStr_) {
            cntentStr = cntentStr_;
        }
    }

    public static class EventB {

        public String  cntentStr;

        public EventB(String cntentStr_) {
            cntentStr = cntentStr_;
        }
    }

    public static class EventC {

        public String  cntentStr;

        public EventC(String cntentStr_) {
            cntentStr = cntentStr_;
        }
    }

    public static class EventD {

        public String  cntentStr;

        public EventD(String cntentStr_) {
            cntentStr = cntentStr_;
        }
    }

    public static class EventStickyC {

        public String  cntentStr;
        public Context  mContext;

        public EventStickyC(String cntentStr_, Context mContext_) {
            cntentStr = cntentStr_;
            mContext = mContext_;
        }
        public EventStickyC() { }
    }

}