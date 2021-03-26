package com.qihoo.qmev3.deferred.out;

import android.graphics.Rect;
import android.os.Looper;
import android.util.Log;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.Properties;

/**
 * - 高阶api接口， 应该在开发之初实现这些接口，当编辑UI使用Tool下发touch坐标时，
 * 在tool中计算新的参数，调用以下api，驱动插件更新。
 * - 同时，这些接口可以作为高阶sdk接口
 * @attention 目前重构阶段逐步实现它，并展示相互协作
 */
@SuppressWarnings("unused")
public class ApiBase {
  private static final String TAG = ApiBase.class.getSimpleName();

  public ApiBase(engine engine) {
    engine_ = engine;
  }

  public engine engine() {
    return engine_;
  }

  // 显式的指定 SuppressWarnings("unused")
  // 暂时不用的参数，未来可能会用，用于app接口
  public static void maybe_unused(Object o) {
    if (o != null) {}
  }

  // 永远不用的参数，用于派生规范接口或者第三方sdk的接口
  public static void always_unused(Object o) {
    if (o != null) {}
  }

  public static void note(String msg) {
    ApiBase.always_unused(msg);
  }

  public static void verify_on_ui() throws RuntimeException {
    _check_on_ui(true);
  }

  public static void check_on_ui() throws RuntimeException {
    if (BuildConfig.DEBUG) {
      _check_on_ui(true);
    } else {
      Log.e(TAG, "*** Please call this method on ui thread! ***");
    }
  }

  public static void verify_not_on_ui() throws RuntimeException {
    _check_on_ui(false);
  }

  public static void check_not_on_ui() throws RuntimeException {
    if (BuildConfig.DEBUG) {
      _check_on_ui(false);
    } else {
      Log.e(TAG, "*** Please call this method on worker thread! ***");
    }
  }

  private static void _check_on_ui(boolean on) throws RuntimeException {
    if (on) {
      if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
        throw new RuntimeException("Please call this method on ui thread!");
      }
    }
    else {
      if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
        throw new RuntimeException("Please call this method on worker thread!");
      }
    }
  }

  public static boolean on_ui() throws RuntimeException {
    boolean b = (Looper.getMainLooper().getThread() != Thread.currentThread());
    return b;
  }

  private static void _check_on_qme_task(boolean on) throws RuntimeException {
    boolean b = (Thread.currentThread().getId() == QETaskController.getInstance().threadId());
    if (on && !b) {
      throw new RuntimeException("Please call this method on qme thread!");
    }
    else if (!on && b) {
      throw new RuntimeException("Don't call this method on qme thread!");
    }
  }

  // 检查方法运行在QETaskController
  public static void check_on_qme_task() throws RuntimeException {
    _check_on_qme_task(true);
  }


  /**
   * @group 数据归一化
   * @{
   */
  public static double doubleValue(double num) {
    return round(num, 2);
  }

  public static double floor(double value) {
    return floor(value, 2);
  }

  public static double round(double value) {
    return round(value, 2);
  }

  public static double floor(double value, int places) {
    if (places < 0) throw new IllegalArgumentException();

    BigDecimal bd = new BigDecimal(value);
    bd = bd.setScale(places, RoundingMode.FLOOR);
    return bd.doubleValue();
  }

  public static double round(double value, int places) {
    if (places < 0) throw new IllegalArgumentException();

    BigDecimal bd = new BigDecimal(value);
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
  }

  public static String formatRect(int w, int h, Rect rect, boolean center) {
    float half_cx = 0;
    float half_cy = 0;
    if (center) {
      half_cx = (float) w / 2;
      half_cy = (float) h / 2;
    }

    float fx = (rect.left - half_cx) / (float) w;
    float fy = (rect.top - half_cy) / (float) w;
    float fw = (float) rect.width() / (float) w;
    float fh = (float) rect.height() / (float) h;
    String s = (doubleValue(fx * 100)) + "%/" + (doubleValue(fy * 100)) + "%:" + (doubleValue(fw * 100)) + "%x" + (doubleValue(fh * 100)) + "%";
    return s;
  }

  public static int ms_to_frame(int ms, double fps) {
    return (int) Math.floor((double) ms * fps / 1000f);
  }

  public static int frame_to_ms(int frame, double fps) {
    return (int) Math.floor((double) frame * 1000f / fps);
  }

  public static int frame_to_frame(int frame, double source_fps, double target_fps) {
    int f = (int) Math.round(frame * target_fps / source_fps);
    return f;
  }

  public static double speed_to_fps(double speed, double base_fps) {
    return base_fps / speed;
  }

  /**
   * @}
   */
  public static double volume_to_level(float volume) {
    // @sa shotcut /src/qml/filters/audio_gain/ui.qml
    double max = 24.0f;
    max = 0.0f;  // android下爆音，不支持放大
    double min = -25.0f;
    double db = min + volume / 100.0f * (max - min + 1);
    db = Math.max(min, Math.min(db, max));
    return db;
  }

  public static double volume_to_gain(float volume) {
    double level = volume_to_level(volume);
    double v = Math.pow(10, level / 20);
    v = doubleValue(v);
    return v;
  }

  /**
   * @}
   */

  public static String toString(boolean b) {
    return b ? "1" : "0";
  }

  public static String toString(Boolean b) {
    return b ? "1" : "0";
  }

  public static Boolean toBoolean(String v) {
    boolean returnValue = false;
    if ("1".equalsIgnoreCase(v) || "yes".equalsIgnoreCase(v) ||
      "true".equalsIgnoreCase(v) || "on".equalsIgnoreCase(v)) {
      returnValue = true;
    }
    return returnValue;
  }

  public static boolean isSameProperties(Properties a, Properties b) {
    return isSameProperties(a, b, null);
  }

  public static boolean isSameProperties(Properties a, Properties b, String[] keys) {
    if (keys == null || keys.length == 0) {
      return a.entrySet().equals(b.entrySet());
    }

    boolean same = true;
    for (int i = 0; i < keys.length && same; ++i) {
      String v1 = a.getProperty(keys[i], "");
      String v2 = b.getProperty(keys[i], "");
      same = v1.equals(v2);
    }

    return same;
  }

  public static boolean sameProperties(Properties a, Properties b) {
    return isSameProperties(a, b);
  }

  public static void copyProperties(Properties dest, Properties src, String[] keys) {
    Iterator it = src.keySet().iterator();
    while (it.hasNext()) {
      boolean found = true;
      if (keys != null && keys.length > 0) {
        found = false;
        for (int i = 0; i < keys.length; ++i) {
          if (it.next().equals(keys[i])) {
            found = true;
            break;
          }
        }
      }
      if (found) {
        dest.put(it.next(), dest.get(it.next()));
      }
    }
  }

  private engine engine_;
}
