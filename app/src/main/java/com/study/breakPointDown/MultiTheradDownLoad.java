package com.study.breakPointDown;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * Encode:UTF-8
 *
 * Author:GFC
 *
 * 根据输入的url和设定的线程数，来完成断点续传功能。
 *
 * 每个线程支负责某一小段的数据下载；再通过RandomAccessFile完成数据的整合。
 */




public class MultiTheradDownLoad {
    private String urlStr = null;
    private String filename = null;
    private String tmpfilename = null;

    private int threadNum = 0;

    private CountDownLatch latch = null;//设置一个计数器，代码内主要用来完成对缓存文件的删除

    private long fileLength = 0l;
    private long threadLength = 0l;
    private long[] startPos;//保留每个线程下载数据的起始位置。
    private long[] endPos;//保留每个线程下载数据的截止位置。

    private boolean bool = false;

    private URL url = null;

    //有参构造函数，先构造需要的数据
    public MultiTheradDownLoad(String urlStr, int threadNum) {
        this.urlStr = urlStr;
        this.threadNum = threadNum;
        startPos = new long[this.threadNum];
        endPos = new long[this.threadNum];
        latch = new CountDownLatch(this.threadNum);
    }

    /*
     * 组织断点续传功能的方法
     */
    @SuppressLint("NewApi")
    public void downloadPart() {

        File file = null;
        File tmpfile = null;

        //设置HTTP网络访问代理
        System.setProperty("http.proxySet", "true");
        System.setProperty("http.proxyHost", "proxy3.bj.petrochina");
        System.setProperty("http.proxyPort", "8080");


        //从文件链接中获取文件名，此处没考虑文件名为空的情况，此种情况可能需使用UUID来生成一个唯一数来代表文件名。
        filename = urlStr.substring(urlStr.lastIndexOf('/') + 1, urlStr
                .contains("?") ? urlStr.lastIndexOf('?') : urlStr.length());
        tmpfilename = filename + "_tmp";

        try {
            //创建url
            url = new URL(urlStr);

            //打开下载链接，并且得到一个HttpURLConnection的一个对象httpcon
            HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
            httpcon.setRequestMethod("GET");

            //todo 获取请求资源的总长度。
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                fileLength = httpcon.getContentLengthLong();
//            }

            //下载文件和临时文件
            file = new File("a_down"+ File.separator+filename);//相对目录
            tmpfile = new File("a_down"+ File.separator+tmpfilename);

//            System.out.println("file.getParent(): " + file.getParent());
//            String path_new = file.getParent() + File.separator +"a_down"+ File.separator+filename;
//            file = new File(path_new);
//            tmpfile = new File(file.getParent() + File.separator +"a_down"+ File.separator+ tmpfilename);

            //每个线程需下载的资源大小；由于文件大小不确定，为避免数据丢失
            threadLength = fileLength%threadNum == 0 ? fileLength/threadNum : fileLength/threadNum+1;
            //打印下载信息
            System.out.println("fileName: " + filename + " ," + "fileLength= "
                    + fileLength + " the threadLength= " + threadLength);
            System.out.println("file.getAbsolutePath(): " + file.getAbsolutePath());
            System.out.println("tmpfile.getAbsolutePath(): " + tmpfile.getAbsolutePath());

            //各个线程在exec线程池中进行，起始位置--结束位置
            if (file.exists() && file.length() == fileLength) {
                System.out.println("文件已存在!!");
                return;
            } else {
                setBreakPoint(startPos, endPos, tmpfile);
                ExecutorService exec = Executors.newCachedThreadPool();
                for (int i = 0; i < threadNum; i++) {
                    exec.execute(new DownLoadThread(startPos[i], endPos[i],
                            this, i, tmpfile, latch));
                }
                latch.await();//当你的计数器减为0之前，会在此处一直阻塞。
                exec.shutdown();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //下载完成后，判断文件是否完整，并删除临时文件
        if (file.length() == fileLength) {
            if (tmpfile.exists()) {
                System.out.println("删除临时文件!!");
                tmpfile.delete();
            }
        }
    }

    /*
     * 断点设置方法，当有临时文件时，直接在临时文件中读取上次下载中断时的断点位置。没有临时文件，即第一次下载时，重新设置断点。
     *
     * Rantmpfile.seek()跳转到一个位置的目的是为了让各个断点存储的位置尽量分开。
     *
     * 这是实现断点续传的重要基础。
     */
    private void setBreakPoint(long[] startPos, long[] endPos, File tmpfile) {
        RandomAccessFile rantmpfile = null;
        try {
            if (tmpfile.exists()) {
                System.out.println("继续下载!!");
                rantmpfile = new RandomAccessFile(tmpfile, "rw");
                for (int i = 0; i < threadNum; i++) {
                    rantmpfile.seek(8 * i + 8);
                    startPos[i] = rantmpfile.readLong();

                    rantmpfile.seek(8 * (i + 1000) + 16);
                    endPos[i] = rantmpfile.readLong();

                    System.out.println("the Array content in the exit file: ");
                    System.out.println("thre thread" + (i + 1) + " startPos:"
                            + startPos[i] + ", endPos: " + endPos[i]);
                }
            } else {
                System.out.println("the tmpfile is not available!!");
                rantmpfile = new RandomAccessFile(tmpfile, "rw");

                //最后一个线程的截止位置大小为请求资源的大小
                for (int i = 0; i < threadNum; i++) {
                    startPos[i] = threadLength * i;
                    if (i == threadNum - 1) {
                        endPos[i] = fileLength;
                    } else {
                        endPos[i] = threadLength * (i + 1) - 1;
                    }

                    rantmpfile.seek(8 * i + 8);
                    rantmpfile.writeLong(startPos[i]);

                    rantmpfile.seek(8 * (i + 1000) + 16);
                    rantmpfile.writeLong(endPos[i]);

                    System.out.println("the Array content: ");
                    System.out.println("thre thread" + (i + 1) + " startPos:"
                            + startPos[i] + ", endPos: " + endPos[i]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rantmpfile != null) {
                    rantmpfile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * 实现下载功能的内部类，通过读取断点来设置向服务器请求的数据区间。
     */
    class DownLoadThread implements Runnable {

        private long startPos;
        private long endPos;
        private MultiTheradDownLoad task = null;
        private RandomAccessFile downloadfile = null;
        private int id;
        private File tmpfile = null;
        private RandomAccessFile rantmpfile = null;
        private CountDownLatch latch = null;

        public DownLoadThread(long startPos, long endPos,
                              MultiTheradDownLoad task, int id, File tmpfile,
                              CountDownLatch latch) {
            this.startPos = startPos;
            this.endPos = endPos;
            this.task = task;
            this.tmpfile = tmpfile;
            try {
                //
                this.downloadfile = new RandomAccessFile("a_down"+ File.separator+this.task.filename,"rw");
                this.rantmpfile = new RandomAccessFile(this.tmpfile, "rw");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            this.id = id;
            this.latch = latch;
        }

        @Override
        public void run() {

            HttpURLConnection httpcon = null;
            InputStream is = null;
            int length = 0;

            System.out.println("线程" + id + " 开始下载!!");

            while (true) {
                try {
                    httpcon = (HttpURLConnection) task.url.openConnection();
                    httpcon.setRequestMethod("GET");

                    //防止网络阻塞，设置指定的超时时间；单位都是ms。超过指定时间，就会抛出异常
                    httpcon.setReadTimeout(20000);//读取数据的超时设置
                    httpcon.setConnectTimeout(20000);//连接的超时设置

                    if (startPos < endPos) {

                        //向服务器请求指定区间段的数据，这是实现断点续传的根本。
                        httpcon.setRequestProperty("Range", "bytes=" + startPos+ "-" + endPos);

                        System.out.println("线程 " + id+ " 长度:---- "+ (endPos - startPos));

                        downloadfile.seek(startPos);

                        if (httpcon.getResponseCode() != HttpURLConnection.HTTP_OK
                                && httpcon.getResponseCode() != HttpURLConnection.HTTP_PARTIAL) {
                            this.task.bool = true;
                            httpcon.disconnect();
                            downloadfile.close();
                            System.out.println("线程 ---" + id + " 下载完成!!");
                            latch.countDown();//计数器自减
                            break;
                        }

                        is = httpcon.getInputStream();//获取服务器返回的资源流
                        long count = 0l;
                        byte[] buf = new byte[1024];

                        while (!this.task.bool && (length = is.read(buf)) != -1) {
                            count += length;
                            downloadfile.write(buf, 0, length);

                            //不断更新每个线程下载资源的起始位置，并写入临时文件；为断点续传做准备
                            startPos += length;
                            rantmpfile.seek(8 * id + 8);
                            rantmpfile.writeLong(startPos);
                        }
                        System.out.println("线程 " + id
                                + " 总下载大小: " + count);

                        //关闭流
                        is.close();
                        httpcon.disconnect();
                        downloadfile.close();
                        rantmpfile.close();
                    }
                    latch.countDown();//计数器自减
                    System.out.println("线程 " + id + " 下载完成!!");
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}

