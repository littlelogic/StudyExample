package com.study.RxAndroid;
//com.study.RxAndroid.AsyncTaskActiv
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.badlogic.utils.ALog;
import com.example.wujiawen.a_Main.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncTaskActiv__think extends Activity {

    private ProgressBar progressBar;
    private ImageView imageView;
    private TextView textView;
    private Button button;
    private final String url = "https://cdn.rouding.com/imagesrc-s/jpg/201412-20-1132D7E610F508FC-th.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    ///============================================================================

    private void deal_execute(){
/**      execute(Params...);异步任务的启动入口,须在<UI线程中调用>
         此方法时会让任务以<单线程>队列方式或<线程池>队列的方式运行，
         具体取决于<版本不同而不同>
         一个AsyncTask只能执行一个任务,一个任务实例只能执行一次                                                       */
        new MyTask().execute(url);
    }
/**  android.os.AsyncTask<Params参数, Progress进度, Result结果>
     泛型指定非必须,在特定情况下不需要用到的可以传<Void>类型代替。
     除execute方法外,doInBackground、onPreExecute、onProgressUpdate,
                                    onPostExecute方法不能手动调用                                           */
    private class MyTask extends AsyncTask<String,String,Bitmap>{
        //todo 首先开始执行
        @Override protected void onPreExecute() {
            ALog.i(ALog.Tag2,"AsyncTaskActiv-MyTask-onPreExecute->");
            textView.setText("异步开始");
            progressBar.setVisibility(View.VISIBLE);
        }

/**     doInBackground(Params...)<必须覆写>,执行在AsyncTask创建的线程中
        在onPreExecute方法后执行,用于执行费事操作，类似Thread中的run方法。
        接收输入参数（即<execute>中传入的参数），并在异步任务完成后返回结果                                      */
        @Override protected Bitmap doInBackground(String... strings){
            publishProgress("正在下载图片","57%");
            return getBitmapFromUrl(strings[0]);
        }

/**     在UI线程中执行，接收<publishProgress>传来的参数，更新UI
        publishProgress必须在<doInBackground>中执行                                                          */
        @Override protected void onProgressUpdate(String... values){
            ALog.i(ALog.Tag2,"AsyncTaskActiv-MyTask-onProgressUpdate->");
            textView.setText(values[0]);
        }

        //todo 在UI线程中执行，处理doInBackground返回的结果
        @Override protected void onPostExecute(Bitmap bitmap){
            ALog.i(ALog.Tag2,"AsyncTaskActiv-MyTask-onPostExecute->");
            imageView.setImageBitmap(bitmap);
            textView.setText("下载结束");
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    ///============================================================================

    public Bitmap getBitmapFromUrl(String urlString) {
        return null;
    }


}