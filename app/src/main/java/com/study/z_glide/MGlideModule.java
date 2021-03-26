package com.study.z_glide;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.module.AppGlideModule;
import java.io.InputStream;

//@GlideModule
public class MGlideModule /*extends AppGlideModule*/ {

    public static final int DISK_CACHE_SIZE = 500 * 1024 * 1024;

    /*
    可以看到，在MyGlideModule类当中，我们重写了applyOptions()和registerComponents()方法，
    这两个方法分别就是用来更改Glide和配置以及替换Glide组件的。我们待会儿只需要在这两个方法中加入具体的逻辑，
    就能实现更改Glide配置或者替换Glide组件的功能了。
     */
//    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, DISK_CACHE_SIZE));
        ///builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
    }

//    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {

    }



}
