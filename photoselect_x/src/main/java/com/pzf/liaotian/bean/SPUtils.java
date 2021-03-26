package com.pzf.liaotian.bean;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SPUtils {

    private SharedPreferences mSharedPreferences;

    private static class SingletonHolder {
        static final SPUtils INSTANCE = new SPUtils();
    }

    public static SPUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void destroy() {
        mSharedPreferences = null;
    }

    protected String getSharedPreferencesName() {
        return "com.qihoo.yunzuanbao_";
    }

    protected final SharedPreferences getSharedPreference(Context context) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(getSharedPreferencesName(), Context.MODE_PRIVATE);
        }
        return mSharedPreferences;
    }

    /**
     * 获取SharedPreference 值
     *
     * @param context
     * @param key
     * @return
     */
    public final String getValue(Context context, String key, String defaultValue) {
        return getSharedPreference(context).getString(key, defaultValue);
    }

    public final Boolean getBooleanValue(Context context, String key, boolean defaultValue) {
        return getSharedPreference(context).getBoolean(key, defaultValue);
    }

    public final void putBooleanValue(Context context, String key, boolean bl) {
        SharedPreferences.Editor edit = getSharedPreference(context).edit();
        edit.putBoolean(key, bl);
        edit.commit();
    }

    public final int getIntValue(Context context, String key, int defaultValue) {
        return getSharedPreference(context).getInt(key, defaultValue);
    }

    public final long getLongValue(Context context, String key, long defaultValue) {
        return getSharedPreference(context).getLong(key, defaultValue);
    }

    public final boolean putLongValue(Context context, String key, long value) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    public final Boolean hasValue(Context context, String key) {
        return getSharedPreference(context).contains(key);
    }

    /**
     * 设置SharedPreference 值
     *
     * @param context
     * @param key
     * @param value
     */
    public final boolean putValue(Context context, String key, String value) {
        value = value == null ? "" : value;
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(key, value);
        boolean result = editor.commit();
        if (!result) {
            return false;
        }
        return true;
    }

    /**
     * 存储键值对信息到本地
     *
     * @param context
     * @param key
     * @param list
     * @return
     */
    public final boolean putMapValue(Context context, String key, ArrayList<Map<String, Integer>> list) {
        JSONArray mJsonArray = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Integer> itemMap = list.get(i);
            Iterator<Map.Entry<String, Integer>> iterator = itemMap.entrySet().iterator();
            JSONObject object = new JSONObject();
            while (iterator.hasNext()) {
                Map.Entry<String, Integer> next = iterator.next();
                try {
                    object.put(next.getKey(), next.getValue());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            mJsonArray.put(object);
        }
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(key, mJsonArray.toString());
        boolean result = editor.commit();
        if (!result) {
            return false;
        }
        return true;
    }

    public final ArrayList<Map<String, Integer>> getMapValue(Context context, String key) {
        ArrayList<Map<String, Integer>> data = new ArrayList<>();
        String result = getSharedPreference(context).getString(key, "");
        try {
            JSONArray array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++) {
                JSONObject itemObject = array.getJSONObject(i);
                Map<String, Integer> itemMap = new HashMap<>();
                JSONArray names = itemObject.names();
                if (names != null) {
                    for (int j = 0; j < names.length(); j++) {
                        String name = names.getString(j);
                        int value = itemObject.getInt(name);
                        itemMap.put(name, value);
                    }
                }
                data.add(itemMap);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 设置SharedPreference 值
     *
     * @param context
     * @param key
     * @param value
     */
    public final boolean putIntValue(Context context, String key, int value) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putInt(key, value);
        boolean result = editor.commit();
        if (!result) {
            return false;
        }
        return true;
    }

    public final boolean removeKey(Context context, String key) {
        SharedPreferences.Editor edit = getSharedPreference(context).edit();
//        edit.putString(key,"");
        edit.remove(key);
        return edit.commit();
    }

    private String NOTICE_LAST_TIME_KEY = "notice_last_time_key";
    public final String getNoticeLastTime(Context context) {
        return getValue(context, NOTICE_LAST_TIME_KEY, "");
    }

    public void setNoticeLastTime(Context context, String lastTime) {
        putValue(context, NOTICE_LAST_TIME_KEY, lastTime);
    }
}
