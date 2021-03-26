package com.study.RxAndroid;
//com.study.RxAndroid.AsyncTaskActiv
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.badlogic.utils.ALog;
import com.badlogic.utils.ThreadName;
import com.badlogic.utils.Tools;
import com.example.wujiawen.a_Main.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class AsyncTaskActiv extends Activity {

    private ProgressBar progressBar;
    private ImageView imageView;
    private TextView textView;
    private Button button;
    private final String url = "https://cdn.rouding.com/imagesrc-s/jpg/201412-20-1132D7E610F508FC-th.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activ_asynctask);

        progressBar = (ProgressBar) findViewById(R.id.demo_progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        imageView = (ImageView) findViewById(R.id.demo_image);
        textView = (TextView) findViewById(R.id.demo_showProgress);
        button = (Button) findViewById(R.id.demo_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setImageBitmap(null);
                deal_execute();
            }
        });
    }

    ///============================================================================

    private void deal_execute(){
        /**
         * todo
         * execute(Params...);
         * 此方法是异步任务的启动入口，该方法应该在UI线程中调用，
         * 调用此方法时会让任务以单线程队列方式或线程池队列的方式运行，
         * 具体取决于版本不同而不同，Parames对应定义子类时指定的Params，
         * 比如若指定为String类型，则MyTask.execute("afds","ffdf","www","ff");。
         * 一个AsyncTask只能执行一个任务
         */
        new MyTask().execute(url);
    }

    /**
     * todo
     * android.os.AsyncTask<Params, Progress, Result>
     * Params代表：启动任务执行的输入参数
     * Progress代表：后台任务执行的进度
     * Result代表：后台计算结果的类型
     * 泛型指定不是必须的，在特定情况下不需要用到的可以传Void类型代替。
     *
     * 1.异步任务的子类的实例应在UI线程中创建
     * 2.除execute方法外，doInBackground、onPreExecute、onProgressUpdate。onPostExecute方法不能手动调用，
     *   而publishProgress方法只能在doInBackground方法中调用
     * 3.doInBackground方法是在与UI线程不同的线程中执行的，因而不能再该方法中操作UI
     * 4.一个任务实例只能执行一次
     */
    private class MyTask extends AsyncTask<String,String,Bitmap>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ALog.i(ALog.Tag2,"AsyncTaskActiv-MyTask-onPreExecute->");
            textView.setText("异步开始");
            progressBar.setVisibility(View.VISIBLE);
        }

        /**
         * todo
         * doInBackground(Params...);
         * AsyncTask子类必须覆写方法，该方法在AsyncTask创建的线程中执行，
         * 该方法在onPreExecute方法后立即执行，用于执行费事操作，类似Thread中的run方法。
         * 此方法接收输入参数（即execute中传入的参数），并在异步任务完成后返回结果，
         * 执行期间可以调用void publishProgress(Progress... values)方法,
         * 实时将进度信息传递给void onProgressUpdate(Progress...values)方法,
         * （若调用了publishProgress方法则MyTask方法中也应覆写onProgressUpdate方法）；
         */
        @Override
        protected Bitmap doInBackground(String... strings) {
            ALog.i(ALog.Tag2,"AsyncTaskActiv-MyTask-doInBackground->");
            publishProgress("正在下载图片");
            return getBitmapFromUrl(strings[0]);
        }

        /**
         * todo 该方法在UI线程中执行，接收publishProgress方法传来的参数，更新UI。
         */
        @Override
        protected void onProgressUpdate(String... values) {
            ALog.i(ALog.Tag2,"AsyncTaskActiv-MyTask-onProgressUpdate->");
            super.onProgressUpdate(values);
            textView.setText(values[0]);
        }

        /**
         * todo 该方法在UI线程中执行，接收doInBackground方法返回的参数，处理异步所得结果。
         */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ALog.i(ALog.Tag2,"AsyncTaskActiv-MyTask-onPostExecute->");
            imageView.setImageBitmap(bitmap);
            textView.setText("下载结束");
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    ///============================================================================

    public Bitmap getBitmapFromUrl(String urlString) {
        Bitmap bitmap;
        InputStream is = null;

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(is);
            connection.disconnect();
            //Log.i("image", "getBitmapFromUrl: true");
            return bitmap;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}