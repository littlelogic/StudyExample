package com.study.z_glide;

/// com.study.z_glide.GifImageView

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.AssetManager.AssetInputStream;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.media.ExifInterface;
import android.os.Build;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import com.badlogic.utils.Tools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 继承了ImageView原生的所有功能，还加入了播放GIF动画的功能。
 *
 */
public class GifImageView extends ImageView {

    /**
     * 播放GIF动画的关键类
     */
    private Movie mMovie;

    /**
     * 记录动画开始的时间
     */
    private long mMovieStart;

    /**
     * GIF图片的宽度
     */
    private int mImageWidth;

    /**
     * GIF图片的高度
     */
    private int mImageHeight;

    /**
     * PowerImageView构造函数。
     *
     * @param context
     */
    public GifImageView(Context context) {
        super(context);
        init(context);
    }

    /**
     * PowerImageView构造函数。
     *
     * @param context
     */
    public GifImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void init(Context context) {
        try {
            Log.d("TAG", "GifImageView-init-01"
            );
            String path = "ohucs.gif";
            AssetManager am = context.getAssets();
            InputStream inStream = null;
            try {
                inStream = am.open(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ///-----

            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inStream, null, options);
            Log.d("TAG", "GifImageView-init-图片信息"
                    + "-options.outWidth->" + options.outWidth
                    + "-options.outHeight->" + options.outHeight
                    + "-图片格式options.outMimeType->" + options.outMimeType
            );
            inStream.close();
            inStream = null;
            try {
                inStream = am.open(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                ExifInterface exifInterface = new ExifInterface(inStream);
                int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_NORMAL);
                Log.d("TAG", "GifImageView-init-图片信息"
                        + "-orientation->" + orientation
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            inStream.close();
            inStream = null;

            try {
                inStream = am.open(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mMovie = Movie.decodeStream(inStream);
            mImageWidth = mMovie.width();
            mImageHeight = mMovie.height();
            if (mMovie != null && false) {

                // todo 拿到某帧
                //  如果返回值不等于null，就说明这是一个GIF图片
                Bitmap bitmap = BitmapFactory.decodeStream(inStream);
                Bitmap firstFrame = Bitmap.createBitmap(mMovie.width(), mMovie.height(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(firstFrame);
//                int relTime = (int) ((now - mMovieStart) % gifMovie.duration());
                int relTime = (int) ((mMovie.duration() - 1) % mMovie.duration());
                mMovie.setTime(relTime);
                mMovie.draw(canvas, 0, 0);
                canvas.save();
            }
            Log.d("TAG", "GifImageView-init-99"
            );

            this.invalidate();

        } catch (Exception e) {
            Log.d("TAG", "GifImageView-init-01-e->"+e.toString()
            );
            e.printStackTrace();
        }

    }


    @Override
    protected void onDraw(Canvas canvas) {
//        Log.d("TAG", "GifImageView-onDraw-01"
//        );
        if (mMovie == null) {
            // mMovie等于null，说明是张普通的图片，则直接调用父类的onDraw()方法
            super.onDraw(canvas);
        } else {
            // mMovie不等于null，说明是张GIF图片
            // 调用playMovie()方法播放GIF动画
            if(!playMovie(canvas)){
                invalidate();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mMovie != null) {
            // 如果是GIF图片则重写设定PowerImageView的大小
            setMeasuredDimension(mImageWidth, mImageHeight);
        }
    }

    /**
     * 开始播放GIF动画，播放完成返回true，未完成返回false。
     *
     * @param canvas
     * @return 播放完成返回true，未完成返回false。
     */
    private boolean playMovie(Canvas canvas) {

        long now = SystemClock.uptimeMillis();

        if (mMovieStart == 0) {
            mMovieStart = now;
        }

        int duration = mMovie.duration();

        if (duration == 0) {
            duration = 1000;
        }

        int relTime = (int) ((now - mMovieStart) % duration);

        mMovie.setTime(relTime);
        mMovie.draw(canvas, 0, 0);

        /*if ((now - mMovieStart) >= duration) {
            //播放完成，显示最后一帧
            mMovie.setTime(duration);
            mMovie.draw(canvas, 0, 0);
//            return true;
            return false;
        }*/
//        Log.d("TAG", "GifImageView-playMovie-false"
//        );
//        return true;
        return false;
    }

    /**
     * 获取到src指定图片资源所对应的id。
     *
     * @param context
     * @param attrs
     * @return 返回布局文件中指定图片资源所对应的id，没有指定任何图片资源就返回0。
     */
    private int getResourceId(Context context, AttributeSet attrs) {

        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            if (attrs.getAttributeName(i).equals("src")) {
                return attrs.getAttributeResourceValue(i, 0);
            }
        }
        return 0;
    }

}