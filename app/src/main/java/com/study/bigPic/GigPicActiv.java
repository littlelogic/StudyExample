package com.study.bigPic;
//com.study.bigPic.GigPicActiv

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.badlogic.utils.ALog;
import com.badlogic.utils.Tools;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.wujiawen.a_Main.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**

todo   PinchImageView-master  XPhotoview-master HVScrollView-master
<BitmapRegionDecoder> todo 大图加载方案
OpenGLRenderer:
 Bitmap too large to be uploaded into a texture (480x4180, max=4096x4096)
< 4096x4096 > -- 一个标准，各个手机限制的不一样

*/

public class GigPicActiv extends Activity implements OnClickListener {

	Context context = null;
	ImageView imageView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.activity_big_pic);
		imageView = this.findViewById(R.id.iv_big);
		findViewById(R.id.tv_a).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				test_a();
			}
		});
		findViewById(R.id.tv_b).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				test_b();
			}
		});
		findViewById(R.id.tv_c).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				test_c();
			}
		});
		findViewById(R.id.tv_d).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				test_d();
			}
		});

		findViewById(R.id.tv_e).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				test_e();
			}
		});



	}

    //==============================================
	String url_a = "file///android_assets/WechatIMG1.jpeg";
	String url_b = "WX20190821-214306@2x.png";
	private void test_a(){
		ALog.i(ALog.Tag2,"GigPicActiv-test_a-01->");
		String url = "http://bmob-cdn-15177.b0.upaiyun.com/2018/08/23/8fa7f1c2404bafbd808bde10ff072ceb.jpg";
		url = url_a;
		Glide.with(this)
				.asBitmap().load(url)
				.into(new SimpleTarget<Bitmap>() {
					@Override
					public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
						setBitmapToImg(resource,imageView);
					}
				});
	}

	Rect mRect = new Rect();
	private void setBitmapToImg(Bitmap resource,ImageView imageView) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			resource.compress(Bitmap.CompressFormat.PNG, 100, baos);

			InputStream isBm = new ByteArrayInputStream(baos.toByteArray());

			//BitmapRegionDecoder newInstance(InputStream is, boolean isShareable)
			//用于创建BitmapRegionDecoder，isBm表示输入流，只有jpeg和png图片才支持这种方式，
			// isShareable如果为true，那BitmapRegionDecoder会对输入流保持一个表面的引用，
			// 如果为false，那么它将会创建一个输入流的复制，并且一直使用它。即使为true，程序也有可能会创建一个输入流的深度复制。
			// 如果图片是逐步解码的，那么为true会降低图片的解码速度。如果路径下的图片不是支持的格式，那就会抛出异常
			BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(isBm, true);

			final int imgWidth = decoder.getWidth();
			final int imgHeight = decoder.getHeight();

			BitmapFactory.Options opts = new BitmapFactory.Options();

			//计算图片要被切分成几个整块，
			// 如果sum=0 说明图片的长度不足3000px，不进行切分 直接添加
			// 如果sum>0 先添加整图，再添加多余的部分，否则多余的部分不足3000时底部会有空白
			int sum = imgHeight/3000;

			int redundant = imgHeight%3000;

			List<Bitmap> bitmapList = new ArrayList<>();

			//说明图片的长度 < 3000
			if (sum == 0){
				//直接加载
				bitmapList.add(resource);
			}else {
				//说明需要切分图片
				for (int i = 0; i < sum; i++) {
					//需要注意：mRect.set(left, top, right, bottom)的第四个参数，
					//也就是图片的高不能大于这里的4096
					mRect.set(0, i*3000, imgWidth, (i+1) * 3000);
					Bitmap bm = decoder.decodeRegion(mRect, opts);
					bitmapList.add(bm);
				}

				//将多余的不足3000的部分作为尾部拼接
				if (redundant > 0){
					mRect.set(0, sum*3000, imgWidth, imgHeight);
					Bitmap bm = decoder.decodeRegion(mRect, opts);
					bitmapList.add(bm);
				}

			}

			Bitmap bigbitmap = Bitmap.createBitmap(imgWidth, imgHeight, Bitmap.Config.ARGB_8888);
			Canvas bigcanvas = new Canvas(bigbitmap);

			Paint paint = new Paint();
			int iHeight = 0;

			//将之前的bitmap取出来拼接成一个bitmap
			for (int i = 0; i < bitmapList.size(); i++) {
				Bitmap bmp = bitmapList.get(i);
				bigcanvas.drawBitmap(bmp, 0, iHeight, paint);
				iHeight += bmp.getHeight();

				bmp.recycle();
				bmp = null;
			}

			imageView.setImageBitmap(bigbitmap);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void test_b(){
		ALog.i(ALog.Tag2,"GigPicActiv-test_b-01->");
		Bitmap hBitmap = Tools.getAssetsBitmap(this,"WechatIMG1.jpeg");
		imageView.setImageBitmap(hBitmap);

	}

	private void test_c(){
		ALog.i(ALog.Tag2,"GigPicActiv-test_c-01->");
		Bitmap hBitmap = Tools.getAssetsBitmap(this,url_b);
		imageView.setImageBitmap(hBitmap);

	}

	private void test_d(){
		ALog.i(ALog.Tag2,"GigPicActiv-test_d-01->");
		AssetManager am = context.getAssets();
		InputStream inputStream = null;
		try {
			inputStream = am.open("WechatIMG1.jpeg");
			BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(inputStream, true);
			int width_need = decoder.getWidth();
			if (width_need > 2000) {
				width_need = 2000;
			}
			int height_need = decoder.getHeight();
			if (height_need > 2000) {
				height_need = 2000;
			}

			BitmapFactory.Options opts = new BitmapFactory.Options();
			//需要注意：mRect.set(left, top, right, bottom)的第四个参数，
			//也就是图片的高不能大于这里的4096
			mRect.set(0, 0, width_need, height_need);
			Bitmap bm = decoder.decodeRegion(mRect, opts);
			imageView.setImageBitmap(bm);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void test_e(){
		ALog.i(ALog.Tag2,"GigPicActiv-test_e-01->");
//		Bitmap bigbitmap = Bitmap.createBitmap(1080, 1080 * 16, Bitmap.Config.ARGB_8888);
		Bitmap bigbitmap = Bitmap.createBitmap(1080, 4096 + 100, Bitmap.Config.ARGB_8888);
		Canvas bigcanvas = new Canvas(bigbitmap);
		bigcanvas.drawColor(Color.YELLOW);
		imageView.setImageBitmap(bigbitmap);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	public void onClick(View v) {
		
	}

	//===============================

	@Override
	protected void onStop() {
		super.onStop();
		ALog.d(ALog.Tag2, "190506state-onStop");
	}

	//===============================

	@Override
	protected void onPause() {
		super.onPause();
	}



}
