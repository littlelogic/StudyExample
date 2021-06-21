package com.study.breakPointDown;

public class DownLoadTest {

    /*

    其中设置HTTP访问代理在公网上不需要。可以看出来，整个类中分为三大部分：下载功能方法downloadPart();
    设置断点的私有方法setBreakPoint(long[] startPos, long[] endPos, File tmpfile)；
    以及实现下载功能的内部类，通过读取断点来设置向服务器请求的数据区间class DownLoadThread,
    其中重写了线程的run()方法。

     */
    public static void main(String[] args) {

//        String filepath = "http://down.sandai.net/thunder9/Thunder9.1.40.898.exe";
        //http://down.sandai.net/thunder9/Thunder9.1.40.898.exe
        //http://download.firefox.com.cn/releases-sha2/stub/official/zh-CN/Firefox-latest.exe
        //http://p0.ifengimg.com/pmop/2017/0830/4AB0880C38002DC837856D274469E3D09BDF43B9_size28_w500_h314.jpeg
        String filepath = "http://img.article.pchome.net/00/22/70/23/pic_lib/wm/03.jpg";


        int threadNum = 4;


        MultiTheradDownLoad load = new MultiTheradDownLoad(filepath ,threadNum);
        load.downloadPart();
    }

}
