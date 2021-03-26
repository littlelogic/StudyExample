package com.ldm.mediarecorder.base;

/**
 * @author ldm
 * @description 常量类
 * @time 2017/2/9 9:57
 */
public class Constant {
    public static final String PLAY_POSITION = "position";
    //录音成功
    public static final int RECORD_SUCCESS = 100;
    //录音失败
    public static final int RECORD_FAIL = 101;
    //录音时间太短
    public static final int RECORD_TOO_SHORT = 102;
    //安卓6.0以上手机权限处理
    public static final int PERMISSIONS_REQUEST_FOR_AUDIO = 1;
    //播放完成
    public static final int PLAY_COMPLETION = 103;
    //播放错误
    public static final int PLAY_ERROR = 104;
}
