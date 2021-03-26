package com.study.diyView;

/// com.study.diyView.TestActivity

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsoluteLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.badlogic.utils.MyAbsoluteLayout;
import com.badlogic.utils.MyImageView;
import com.badlogic.utils.Tools;
import com.example.wujiawen.a_Main.R;

import static android.view.View.LAYER_TYPE_SOFTWARE;

public class TestActivity extends /*AppCompat*/Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        MyAbsoluteLayout rootView = new MyAbsoluteLayout(this);
//        rootView.setLayerType(LAYER_TYPE_SOFTWARE, null);
        setContentView(rootView);
        rootView.setBackgroundColor(Color.argb(255,255,255,255));

        MyAbsoluteLayout contentView = new MyAbsoluteLayout(this);
        AbsoluteLayout.LayoutParams Params_contentView = new AbsoluteLayout.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, Tools.dip2px(this,400),
                Tools.dip2px(this,20), Tools.dip2px(this,100));
        contentView.setLayoutParams(Params_contentView);
        rootView.addView(contentView);
//        contentView.setBackgroundColor(Color.argb(66,0,255,0));


        MyImageView imageView = new MyImageView(this);
        AbsoluteLayout.LayoutParams Params_imageView = new AbsoluteLayout.LayoutParams(
                Tools.dip2px(this,220), Tools.dip2px(this,220),
                Tools.dip2px(this,20), Tools.dip2px(this,20));
        imageView.setLayoutParams(Params_imageView);
        contentView.addView(imageView);
//        imageView.setBackgroundColor(Color.argb(66,255,0,0));
//        imageView.setBackgroundResource(R.drawable.icon);
//        imageView.setImageResource(R.drawable.icon);
        imageView.setPadding(0,100,0,0);

        imageView.setTranslationX(Tools.dip2px(this,50));
        imageView.setScaleX(1.3f);
//        imageView.setRotation(45);
        imageView.setRotationX(45f);
        imageView.setScrollX(Tools.dip2px(this,100));
//        imageView.setScrollY(Tools.dip2px(this,100));
        imageView.setOnClickListener(v -> {

            android.util.Log.i("22","210324p-TestActivity-onCreate-2-imageView.setOnClickListener-01"
                    + "-getWidth->"+imageView.getWidth()
                    + "-getHeight->"+imageView.getHeight()
                    + "-getLeft->"+imageView.getLeft()
                    + "-getRight->"+imageView.getRight()
                    + "-getMeasuredWidth->"+imageView.getMeasuredWidth()
                    + "-getMeasuredHeight->"+imageView.getMeasuredHeight()
            );
            rootView.invalidate();
//            contentView.invalidate();
//            imageView.invalidate();
        });
        imageView.invalidate();


    }





}
