package com.study.z_glide;
//com.study.z_glide.GlideActiv
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.badlogic.utils.ALog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.wujiawen.a_Main.MainActivity;
import com.example.wujiawen.a_Main.R;
//import com.study.z_glide.proress_down.GlideApp;
//import com.study.z_glide.proress_down.GlideApp;
import com.study.z_glide.proress_down.GlideApp;
import com.study.z_glide.proress_down.OkHttpUrlLoader;
import com.study.z_glide.proress_down.ProgressInterceptor;
import com.study.z_glide.proress_down.ProgressListener;

import java.io.File;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.concurrent.ExecutionException;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import okhttp3.OkHttpClient;

public class GlideActiv extends FragmentActivity implements OnClickListener {

	Context context = null;
	ImageView imageView = null;
	String url = "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1566218641&di=a9caeb3f352516e9ddf0c4f0c628a90b&src=http://attach.bbs.miui.com/forum/201312/06/211410sxjtbyaj9abo5qzh.jpg";
	String imgUrl = url;
	String TAG = "wjw02";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.activity_glide_a);
		imageView = this.findViewById(R.id.imageview);
		if (false) test_Request();
		test_progress();


		new Thread(() -> {
			//todo 需要放在子线程,同步的
			Log.d(TAG, "GlideActiv-onCreate-test-git-01"
			);
//			String path = "file:///android_asset/cut_to_b/transition_-1.png";
			String path = "file:///android_asset/ohucs.gif";
			try {
				Bitmap myBitmap = Glide.with(this)
						.asBitmap()
						.load(path)
						.submit(500, 500)
						.get();
				Log.d(TAG, "GlideActiv-onCreate-test-git-03"
						+ "-myBitmap.getWidth()->" + myBitmap.getWidth()
						+ "-myBitmap.getHeight()->" + myBitmap.getHeight()
				);
			} catch (Exception e) {
				e.printStackTrace();
				Log.d(TAG, "GlideActiv-onCreate-test-git-01-e->"+e.getMessage());
			}
		}).start();

	}

	void load_path_type(){
		@SuppressLint("WrongViewCast")
		ImageView imageView = findViewById(R.id.btnB);
		String url = "https://www.niwoxuexi.com/statics/images/nougat_bg.png";
		Glide.with(context)
				.load(url)
				.into(imageView);

		// 加载SD卡根目录的test.jpg 图片
		String path = "file://"+ Environment.getExternalStorageDirectory().getPath()+"/test.jpg";
		Glide.with(context)
				.load(path)
				.into(imageView);

		// 加载SD卡根目录的test.jpg 图片  ，通过Flie文件读取
		File file = new File(Environment.getExternalStorageDirectory(), "test.jpg");
		Glide.with(context)
				.load(file)
				.into(imageView);

		// 加载资源文件 drawable 下的图片 image_test.png
		Glide.with(this)
				.load(R.drawable.test_bg_bitmap)
				.into(imageView);

		// 加载资源文件 assets 下的图片 image_test.png
		path =  "file:///android_asset/image_test.png";
		Glide.with(this)
				.load(path)
				.into(imageView);

//		4，加载raw资源图片
//		方法：load("android.resource://包名/raw/raw_1") 或  load("android.resource://包名/raw/"+R.raw.raw_1)


		/*
		todo
			5，当然load 并不局限在上面几种类型，还可以加载下面的参数，我就不举例说明了，有兴趣的大家自行百度学习
			参数	说明
			.load(String string)	string可以为一个文件路径、uri或者url
			.load(Uri uri)	uri类型
			.load(File file)	文件
			.load(Integer resourceId)	资源Id,R.drawable.xxx或者R.mipmap.xxx
			.load(byte[] model)	byte[]类型
			.load(T model)	自定义类型
		 */
	}

    //==============================================

	void test_with(){
		/// Glide的with 会绑定到 参数的生命周期上面，主要是通过 将一个没有页面的fragment和相关的参数activity或fragemnt绑定、
		/*Activity传的参数的是Activity的FragmentManager，Fragment传的参数的是
		ChildFragmentManager，这两者不是一个东西。*/
		Activity hActivity = null;
		Glide.with(hActivity);

		Context hContext = null;
		Glide.with(hContext);
		Glide.with(hActivity.getApplication());

		FragmentActivity hFragmentActivity = null;
		Glide.with(hFragmentActivity);

		android.app.Fragment fragment_a = null;
		Glide.with(fragment_a);

		android.app.Fragment fragment_b = null;
		Glide.with(fragment_b);

		Glide.get(null);
		/*
		    ViewConstant activityRoot = activity.findViewById(android.R.id.content);

		 for (android.app.Fragment fragment : fragmentManager.getFragments()) {
			if (fragment.getView() != null) {
			  result.put(fragment.getView(), fragment);
			  findAllFragmentsWithViews(fragment.getChildFragmentManager(), result);
			}
      }
		 */
		View hView = null;//与view所在的activity生命周期绑定
		Glide.with(hView);
		/*
		Begin a load with Glide that will be tied to the lifecycle of the {@link Fragment},
   * {@link android.app.Fragment}, or {@link Activity} that contains the ViewConstant.
		 */



		///使用 Glide 加载图片非常简单，类似这样：
		Glide.with(this)
				.load(new MyGlideUrl(url))//防止因为url的token变化，导致glide的缓存的key变化，而不断重新下载
				.into(imageView);

		/*
		一般认为，应该及时取消不必要的加载请求，但这并不是必须的操作。
		因为 Glide 会在页面生命周期 / 网络变化时，自动取消加载或重新加载。
		 */
		Glide.with(this).clear(imageView);

	}

	void test_with_think(){

		/**



		 Glide的with的参数可以使 <Context> <Application> <FragmentActivity> <Activity>
		 <android.app.Fragment> <android.support.v4.app.Fragment> <ViewConstant>

		 RequestManagerRetriever：<单例>可以理解为专门用来生产、管理和回收RequestManager的类。
		 RequestManager：<唯一的with参数与之对应，view的所在生命周期体>
		 可以理解为专门用来管理Glide的一系列加载图片的请求的类。
		 RequestManager 会绑定到参数的生命周期上面，
		 主要是通过将一个没有页面的fragment和相关的参数activity或fragemnt绑定、
		 没有布局文件Fragment实际上是为了保存，当Activity重启时，保存大量数据准备的
		 Activity传的参数的是Activity的<FragmentManager>，
		 Fragment传的参数的是<ChildFragmentManager>，这两者不一样

		 ViewConstant activityRoot = activity.findViewById(android.R.id.content);
		 fragmentManager.getFragments()
		 fragment.getView()
		 fm.beginTransaction().add(current, FRAGMENT_TAG).commitAllowingStateLoss();

		 Glide.with((ViewConstant)null);//与view所在的frgment 或 activity生命周期绑定

		 Dalvik和ART都没有使用compacting garbage collector垃圾回收模式，这种模式中GC会遍历堆，
		 同时把活跃对象移到相邻内存区域，让更大的内存块可以用在后续的分配中。因为安卓没有这种模式，
		 就可能会出现被分配的对象分散在各处，对象之间只有很小的内存可用。
		 如果应用试图分配一个大于邻近的闲置内存块空间的对象，就会导致OOM崩溃，
		 即使即使总的空余内存空间大于要分配的对象的大小。

		 getDirty(int width,int height,Bitmap.Config config)和get(int,int,Bitmap.Config)
		 方法一样，只是返回的任何非空Bitmap对象都可能包含未擦除的像素数据或随机数据。

		 Glide缓存机制大致分为三层：
		 Lru算法缓存<LruResourceCache>、弱引用缓存<activeResources>、磁盘缓存<DiskLruCache>
		 读取的顺序是：Lru算法缓存、弱引用缓存、磁盘缓存
		 写入的顺序是：弱引用缓存、Lru算法缓存、磁盘缓存
		 正在使用中的图片放在<弱引用缓存>，不在使用的图片放在<Lru算法缓存>
		 使用引用计数<acquired>,判断图片是否正在使用,<图片复用>时辅助逻辑

		 <BitmapPool>,当LruCache触发Evicted时会把从LruCache中<淘汰>下来的Bitmap回收												 */
	}
//	@GlideModule
//	public class MyGlideModel extends AppGlideModule {
//		public MyGlideModel() {
//			super();
//		}
//		@Override public boolean isManifestParsingEnabled() {
//			return super.isManifestParsingEnabled();
//		}
//		@Override public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
//			super.applyOptions(context, builder);
//		}
//		@Override //todo 自定义组件，主要重写这个方法，注册新的组件，替换老的组件，注意格式，注解
//		public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
//			//super.registerComponents(context,glide,registry);
//			OkHttpClient okHttpClient = new OkHttpClient.Builder()
//					.addInterceptor(new ProgressInterceptor()).build();
//			registry.replace(GlideUrl.class, InputStream.class,new OkHttpUrlLoader.Factory(okHttpClient));
//		}
//	}

	//==============================================

	void test_Request(){
		RequestOptions options = new RequestOptions()
				.placeholder(R.drawable.icon)
				.override(200, 100)
				.override(Target.SIZE_ORIGINAL)
				.diskCacheStrategy(DiskCacheStrategy.NONE);
		options.format(DecodeFormat.PREFER_ARGB_8888);
		new RequestOptions().skipMemoryCache(true);

//		DiskCacheStrategy.NONE： 表示不缓存任何内容。
//		DiskCacheStrategy.DATA： 表示只缓存原始图片。
//		DiskCacheStrategy.RESOURCE： 表示只缓存转换过后的图片。
//		DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片。
//		DiskCacheStrategy.AUTOMATIC： 表示让Glide根据图片资源智能地选择使用哪一种缓存策略（默认选项）。
		new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE);


		Glide.with(this)
				.load(url)
				.apply(options)
				.into(imageView);


//		这个方法的意思就是说这里只允许加载静态图片，不需要Glide去帮我们自动进行图片格式的判断了。
//		如果你传入的还是一张GIF图的话，Glide会展示这张GIF图的第一帧，而不会去播放它。
		Glide.with(this)
				.asBitmap().load("http://guolin.tech/test.gif").into(imageView);





		// todo 监听
		SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
			@Override
			public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
				imageView.setImageDrawable(resource);
			}
		};
		Glide.with(this).load("http://guolin.tech/book.png").into(simpleTarget);



		//预加载,Target的下载完成的不设置到image
		//preload()方法有两个方法重载，一个不带参数，表示将会加载图片的原始尺寸，另一个可以通过参数指定加载图片的宽和高。
		Glide.with(this).load("http://guolin.tech/book.png").preload(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
		Glide.with(this).load("http://guolin.tech/book.png").preload(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);



		// 下载图片
		Context context = null;
		new Thread(() -> {
			try {
				FutureTarget<File> target = Glide.with(getApplicationContext())
						.asFile()
						.load(url)
						.submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
				final File imageFile = target.get();//阻塞获取
				runOnUiThread(() -> Toast.makeText(context, imageFile.getPath(), Toast.LENGTH_LONG).show());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		RequestListener listener = new RequestListener<Drawable>() {
			@Override
			public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
				return false;
			}

			@Override
			public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
				return false;
			}
		};
		Glide.with(this).load(url).listener(listener).into(imageView);


		RequestOptions options22 = new RequestOptions().centerCrop();
		RequestOptions options33 = new RequestOptions()
				.transforms(new BlurTransformation(), new GrayscaleTransformation());
		RequestOptions options44 = new RequestOptions().transforms(new GlideRoundTransform(this));
		Glide.with(this)
				.load(url)
				.apply(options22)
				.into(imageView);
	}

	void test_Request_think(){
		RequestOptions options = new RequestOptions()
				.placeholder(R.drawable.icon)
				.override(200, 100)
				.override(Target.SIZE_ORIGINAL)
				.diskCacheStrategy(DiskCacheStrategy.NONE);
		options.format(DecodeFormat.PREFER_ARGB_8888);
		options.skipMemoryCache(true);
//		DiskCacheStrategy.NONE： 表示不缓存任何内容。
//		DiskCacheStrategy.DATA： 表示只缓存原始图片。
//		DiskCacheStrategy.RESOURCE： 表示只缓存转换过后的图片。
//		DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片。
//		DiskCacheStrategy.AUTOMATIC： 表示让Glide根据图片资源智能地选择使用哪一种缓存策略（默认选项）。
		options.diskCacheStrategy(DiskCacheStrategy.NONE);
		RequestOptions options22 = new RequestOptions().centerCrop();
		RequestOptions options33 = new RequestOptions()
				.transforms(new BlurTransformation(), new GrayscaleTransformation());
		Glide.with(this).load(url).apply(options).into(imageView);

//		这个方法的意思就是说这里只允许加载静态图片，不需要Glide去帮我们自动进行图片格式的判断了。
//		如果你传入的还是一张GIF图的话，Glide会展示这张GIF图的第一帧，而不会去播放它。
		Glide.with(this).asBitmap().load("http://guo/test.gif").into(imageView);

		//todo 监听
		SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
			@Override
			public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
				imageView.setImageDrawable(resource);
			}
		};
		Glide.with(this).load(url).into(simpleTarget);

		//todo 预加载,Target的下载完成的不设置到image
		Glide.with(this).load(url).preload(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
		Glide.with(this).load(url).preload(100, 100);


		String imgUrl = "https://avatar.csdn.net/9/7/A/3_zhangphil.jpg";

		ImageView image1 = (ImageView)findViewById(R.id.image1);
		ImageView image2 = (ImageView)findViewById(R.id.image2);
		ImageView image3 = (ImageView)findViewById(R.id.image3);

		Glide.with(this).load(imgUrl).centerCrop().into(image1);//加载原始图片和其他形状的图片形状作为对比。

		Glide.with(this).load(imgUrl).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(image2);//标准圆形图片。

		Glide.with(this).load(imgUrl).apply(RequestOptions.bitmapTransform(new RoundedCorners(50))).into(image3);//四周都是圆角的圆角矩形图片。


		new Thread(() -> {//todo 下载图片
			try {
				FutureTarget<File> target = Glide.with(getApplicationContext())
						.asFile().load(url).submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
				final File imageFile = target.get();//阻塞获取
				runOnUiThread(() -> Toast.makeText(context, imageFile.getPath(), Toast.LENGTH_LONG).show());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		RequestListener listener = new RequestListener<Drawable>() {
			public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
				return false;
			}
			public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
				return false;
			}
		};
		Glide.with(this).load(url).listener(listener).into(imageView);
	}

	//==============================================

	ProgressDialog progressDialog;

	public void test_progress() {
		/*Glide.with(this)
				.load(url)
				.into(imageView);
		if (true) {
			return;
		}*/
//		Glide.get(this).getBitmapPool().

		progressDialog = new ProgressDialog(this);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setCancelable(false);
		progressDialog.setMessage("加载中");
		ProgressInterceptor.addListener(imgUrl, new ProgressListener() {
			@Override
			public void onProgress(int progress) {
				Log.d(TAG, "GlideActiv-test_progress-onProgress: " + progress);
				progressDialog.setProgress(progress);
			}
		});

		SimpleTarget<Drawable> simpleTarge = new SimpleTarget<Drawable>() {
			@Override
			public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
				Log.d(TAG, "GlideActiv-test_progress-onResourceReady-resource->"+resource);
				progressDialog.dismiss();
				ProgressInterceptor.removeListener(imgUrl);
				imageView.setImageDrawable(resource.getCurrent());
				///imageView.setImageBitmap(resource);
			}

			@Override
			public void onStart() {
				super.onStart();
				Log.d(TAG, "GlideActiv-test_progress-onStart");
				progressDialog.show();
			}
		};

		//构建全部通过才会，出现
		GlideApp.with(this)
				.load(imgUrl)
				.diskCacheStrategy(DiskCacheStrategy.NONE)//不使用缓存
				.skipMemoryCache(true)
				.into(simpleTarge);


//		清除所有内存缓存(需要在Ui线程操作)
		Glide.get(this).clearMemory();

//		清除所有磁盘缓存(需要在子线程操作)
//		Glide.get(GlideActiv.this).
//		Glide.get(GlideActiv.this).clearDiskCache();

		BitmapPool mBitmapPool = null;

		//替换 public class LruBitmapPool implements BitmapPool {  组件
//		mBitmapPool.trimMemory();

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
