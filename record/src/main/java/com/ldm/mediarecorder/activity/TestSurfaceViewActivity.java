package com.ldm.mediarecorder.activity;//

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;

import com.share.badlogic.R;


/**
 *

 https://hw-hanwei-126-com.iteye.com/blog/2004861
 http://www.mamicode.com/info-detail-1831474.html

 */
public class TestSurfaceViewActivity extends Activity {

	private MView sfv;
	/**
	 * X轴缩小的比例
	 */
	public int rateX = 352;

	/**
	 * Y轴缩小的比例
	 */
	public float rateY = 0;

	/**
	 * Y轴基线
	 */
	public int baseLine = 0;
	/**
	 * 为了节约绘画时间，每三个像素画一个数据
	 */
	int divider = 2;
	Paint mPaint;// 画笔
	private MediaRecorder mediarecord;
	private Timer sfvtimer;
	private ArrayList<Integer> x = new ArrayList<Integer>();// 保存音量数据
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_surface_view);
		initView();
		// 初始化画笔
		mPaint = new Paint();
		mPaint.setColor(getResources().getColor(R.color.maincolor));// 画笔为主色调
		mPaint.setStrokeWidth(4);// 设置画笔粗细
	}

	// 初始化视图
	private void initView() {
		sfv = (MView) findViewById(R.id.record_surfaceView);
		sfv.mTestSurfaceViewActivity = this;
		/*sfv.setZOrderOnTop(true);
		sfv.getHolder().setFormat(PixelFormat.TRANSLUCENT);*/
	}

	public void Record(View view) {
		baseLine = sfv.getHeight() / 2;
		if (mediarecord == null) {

			File file = new File("mnt/sdcard/a123" + ".m4a");
			if (file.exists()) {
				file.delete();
			}

			mediarecord = new MediaRecorder();
			mediarecord.setAudioSource(MediaRecorder.AudioSource.MIC);
			mediarecord.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
			mediarecord.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
			
			mediarecord.setOutputFile("mnt/sdcard/a123" + ".m4a");

			try {
				mediarecord.prepare();
			} catch (IOException e) {
				Log.e("iii", "prepare() failed");
			}
			mediarecord.start();
			
			sfvtimer = new Timer();
			sfvtimer.schedule(new TimerTask() {
				@Override
				public void run() {
					Message message = new Message();
					message.what = 1;
					mHandler.sendMessage(message);
				}
			}, 0, 200);
			// 录音
		} else {
			if (mediarecord != null) {
				mediarecord.stop();
				mediarecord.reset();
				mediarecord.release();
				mediarecord = null;
				sfvtimer.cancel();
				sfvtimer = null;
			}
		}
	}
	
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			sfv.invalidate();
		}
	};
	
	/**
	 * 绘制指定区域
	 * 
	 * @param buf
	 *            缓冲区
	 * @param baseLine
	 *            Y轴基线
	 */
	void SimpleDraw(ArrayList<Integer> buf, int baseLine,Canvas canvas) {
		if (rateY == 0) {
			rateY = 200f / sfv.getHeight();
			baseLine = sfv.getHeight() / 2;
		}
		Log.i("wjw","TestSurfaceViewActivity-SimpleDraw-rateY->"+rateY);
		canvas.drawColor(getResources().getColor(R.color.acoustic_wave_bg));// 清除背景
		int start = sfv.getWidth() - buf.size() * divider;
		float py = baseLine;
		if (buf.size() > 0)
			py += buf.get(0) / rateY;
		float y;
		canvas.drawLine(0, baseLine, start - divider, baseLine, mPaint);
		for (int i = 0; i < buf.size(); i++) {
			y = buf.get(i) / rateY + baseLine;// 调节缩小比例，调节基准线
			canvas.drawLine(start + (i - 1) * divider, py, start + i * divider,
					y, mPaint);
			py = y;
		}
	}
	
	/**
	 * 更新话筒状态
	 *
	 */
	private int BASE = 1;

	//  com.ldm.mediarecorder.activity.TestSurfaceViewActivity$MView
	public static class MView extends View{
		public MView(Context context) {
			super(context);
		}

		public MView(Context context, @Nullable AttributeSet attrs) {
			super(context, attrs);
		}

		TestSurfaceViewActivity mTestSurfaceViewActivity;

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			if (mTestSurfaceViewActivity != null) {
				mTestSurfaceViewActivity.updateMicStatus(canvas);
			}
		}
	}

	private void updateMicStatus(Canvas canvas) {
		if (mediarecord != null) {
			double ratio = (double) mediarecord.getMaxAmplitude() / BASE;
			int db = 0;// 分贝
			if (ratio > 1)
				db = (int) (20 * Math.log10(ratio)) - 50;
			if (db < 0)
				db = 0;
			x.add(-db);
			if (x.size() > sfv.getWidth() / divider) {
				x.remove(0);
			}
			try {
				SimpleDraw(x, baseLine,canvas);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
