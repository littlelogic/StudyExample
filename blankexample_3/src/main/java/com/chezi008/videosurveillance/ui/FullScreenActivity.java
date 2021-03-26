package com.chezi008.videosurveillance.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.view.TextureView;

import com.chezi008.videosurveillance.R;
import com.chezi008.videosurveillance.base.BaseActivity;

public class FullScreenActivity extends BaseActivity {
    private String TAG = getClass().getSimpleName();
    private TextureView tv_texture;
    private SurfaceTexture mSurfaceTexture;
    @Override
    public int getLayoutResId() {
        return R.layout.activity_full_screen;
    }

    @Override
    public void initVariable() {

    }

    @Override
    public void initView() {
        tv_texture = (TextureView)findViewById(R.id.tv_texture);
        tv_texture.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {

            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {

            }
        });
    }

    @Override
    public void initData() {

    }

    public static void start(Context context) {
        Intent starter = new Intent(context, FullScreenActivity.class);
        context.startActivity(starter);
    }
}
