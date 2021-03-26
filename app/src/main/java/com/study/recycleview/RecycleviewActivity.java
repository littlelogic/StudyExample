package com.study.recycleview;
// com.study.recycleview.RecycleviewActivity
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Adapter;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import com.badlogic.utils.ALog;
import com.badlogic.utils.Tools;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.wujiawen.a_Main.R;

import java.util.ArrayList;
import java.util.List;

//import androidx.recyclerview.widget.RecyclerView;

public class RecycleviewActivity extends Activity {



    RecyclerView mRecyclerView;
    List<String> list = new ArrayList<>();

    MAdapter mAdapter ;
    LinearLayoutManager layoutManager;
    private int dp_1,dp_2,dp_4,dp_5,dp_12,dp_20,dp_24,dp_28,dp_360;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dp_1 =  Tools.dip2px(this,1);
        dp_2 = Tools.dip2px(this,2);
        dp_4 = Tools.dip2px(this,4);
        dp_5 = Tools.dip2px(this,5);
        dp_12 = Tools.dip2px(this,12);
        dp_20 = Tools.dip2px(this,20);
        dp_24 = Tools.dip2px(this,24);
        dp_28 = Tools.dip2px(this,28);
        dp_360 = Tools.dip2px(this,360);

        for (int i = 0 ; i < 50; i++ ) {
            list.add(i+"");
        }
        this.setContentView(R.layout.activ_recycleview);
        TextView test_a_tv = findViewById(R.id.test_a);
        test_a_tv.setText("SetChanged");
        test_a_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ALog.d("200723a-VideoDetailsActiv-test_a-OnClickListener->");
                mAdapter.notifyDataSetChanged();
            }
        });
        TextView test_b_tv = findViewById(R.id.test_b);
        test_b_tv.setText("ItemChanged");
        test_b_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ALog.d("200723a-VideoDetailsActiv-test_b-OnClickListener->");
                ///mAdapter.notifyItemChanged(0,"1223");
                mAdapter.notifyItemChanged(0);
            }
        });

        mRecyclerView = this.findViewById(R.id.rvBar);
        mAdapter = new MAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        layoutManager = new LinearLayoutManager(mRecyclerView.getContext() );
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setViewCacheExtension(mAdapter.mViewCacheExtension);
        mAdapter.notifyDataSetChanged();
        //-----------
        /*mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool(){});
        mRecyclerView.setItemAnimator(null);
        mRecyclerView.setEdgeEffectFactory(null);*/


    }


    class NormalViewHolder extends RecyclerView.ViewHolder{

        int position;
        //---
        View parentView;
        int type;
        TextView title;

        ///----------

        public NormalViewHolder(View parentView,int type_) {
            super(parentView);
            type = type_;
            this.parentView = parentView;
            title = parentView.findViewById(R.id.title);
        }
    }

    class MViewHolder extends RecyclerView.ViewHolder{

        int position;
        //---
        View parentView;
        int type;
        TextView title;

        ///----------

        //--head信息
        TextView title_head;
        TextView watch_num_head;
        TextView save_head;
        TextView share_head;
        TextView download_head;
        TextView name_head;
        TextView subscriber_num_head;
        ImageView headImage_head;
        TextView subscriber_button_head;
        MImageView expand_head;
        Switch autoPlaySwitch;
        //----

        public MViewHolder(View parentView,int type_) {
            super(parentView);
            type = type_;
            this.parentView = parentView;
            if (type_ == MAdapter.HEAD_VIEW) {
                initListHeadData();
            }else {
                title = parentView.findViewById(R.id.title);

            }
            ///------
        }

        private void initListHeadData(){
            expand_head = parentView.findViewById(R.id.expand);
            autoPlaySwitch = parentView.findViewById(R.id.switch_auto_play);
            title_head = parentView.findViewById(R.id.title);
            watch_num_head = parentView.findViewById(R.id.watch_num);
            save_head = parentView.findViewById(R.id.save);
            share_head = parentView.findViewById(R.id.share);
            download_head = parentView.findViewById(R.id.download);
            name_head = parentView.findViewById(R.id.name);
            subscriber_num_head = parentView.findViewById(R.id.subscriber_num);
            headImage_head = parentView.findViewById(R.id.head);
            subscriber_button_head = parentView.findViewById(R.id.subscriber_button);
            subscriber_button_head.setAlpha(0.4f);
            Drawable down_svg = ContextCompat.getDrawable(RecycleviewActivity.this, R.drawable.svg_icon_download);
            download_head.setCompoundDrawablePadding(dp_2);
            down_svg.setBounds(0, 0, dp_28, dp_28);
            download_head.setCompoundDrawables(null,down_svg,null,null);
            download_head.setPadding(0,0,0,dp_1);
            download_head.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    curDowning = true;
                    mAdapter.notifyDataSetChanged();
                }
            });
            //---
            parentView.findViewById(R.id.play_next_hint).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            autoPlaySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    ALog.d("200723a-VideoDetailsActiv-onCheckedChanged-isChecked->"+isChecked);
                }
            });
            expand_head.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deal_expand(v);
                }
            });
        }

        boolean title_expand_show = false;

        private void deal_expand(View arrow){
            ALog.d("200723a-VideoDetailsActiv-deal_expand-01->");
            float degrees_start;
            float degrees_end;
            if (title_head.getLayoutParams().height == ActionBar.LayoutParams.WRAP_CONTENT) {
                title_head.getLayoutParams().height = Tools.dip2px(RecycleviewActivity.this,20);
                title_expand_show = false;
                degrees_start = 180;
                degrees_end = 0;
            } else {
                title_head.getLayoutParams().height = ActionBar.LayoutParams.WRAP_CONTENT;
                title_expand_show = true;
                degrees_start = 0;
                degrees_end = 180;
            }

            final float degrees_end_final = degrees_end;
            //-title_head.requestLayout();
//            mAdapter.notifyDataSetChanged();
            mAdapter.notifyItemChanged(0,"2333");


            if (true) {
                return;
            }

            if (arrow.getAnimation() != null) {
                arrow.getAnimation().cancel();
            }
            arrow.clearAnimation();
            RotateAnimation hRotateAnimation = new RotateAnimation(degrees_start,degrees_end_final,
                    Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
            hRotateAnimation.setDuration(4000);
            hRotateAnimation.setFillEnabled(true);
            hRotateAnimation.setFillAfter(true);
            hRotateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) { }
                @Override
                public void onAnimationRepeat(Animation animation) { }
                @Override
                public void onAnimationEnd(Animation animation) {
                    hRotateAnimation.cancel();
                    arrow.setAnimation(null);
                    arrow.setRotation(degrees_end_final);
                }
            });
            arrow.startAnimation(hRotateAnimation);
            ALog.i("200723a-VideoDetailsActiv-deal_expand-arrow->"+arrow.getId() );
        }
    }

    class MAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        public static final int NORMAL_VIEW = 0;
        public static final int HEAD_VIEW = 1;
        public static final int HEAD_Num = 1;

        public int select_index = -1;
        private LayoutInflater mInflater;
        private Context context;
        public MAdapter(Context context_) {
            context = context_;
            mInflater = LayoutInflater.from(context);

        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return HEAD_VIEW;
            }
            return NORMAL_VIEW;
        }

        @Override
        public int getItemCount() {
            return list.size() + 1;
        }

        public void onBindViewHolder_(@NonNull RecyclerView.ViewHolder holder, int position,@NonNull List<Object> payloads) {
            ALog.d("VideoDetailsActiv-onBindViewHolder"
                    + "-payloads->"+payloads
                    + "-payloads.isEmpty()->"+payloads.isEmpty()
            );
            if (payloads.isEmpty()) {
                super.onBindViewHolder(holder,position,payloads);
            } else {
                ////super.onBindViewHolder(holder,position,payloads);
                onBindViewHolder(holder,position);
                ImageView arrow  =  ((MViewHolder)holder).expand_head;
                if (arrow.getAnimation() != null) {
                    arrow.getAnimation().cancel();
                }
                arrow.clearAnimation();
                RotateAnimation hRotateAnimation = new RotateAnimation(0,180,
                        Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
                hRotateAnimation.setDuration(400);
                hRotateAnimation.setFillEnabled(true);
                hRotateAnimation.setFillAfter(true);
                hRotateAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) { }
                    @Override
                    public void onAnimationRepeat(Animation animation) { }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        hRotateAnimation.cancel();
                        arrow.setAnimation(null);
                        arrow.setRotation(180);
                    }
                });
                arrow.startAnimation(hRotateAnimation);
//


            }
//            if (payloads == null) {


//            } else {
//
//            }
        }

        View mViewCacheExtension_view;

        RecyclerView.ViewCacheExtension mViewCacheExtension = new RecyclerView.ViewCacheExtension(){
            @Nullable
            @Override
            public View getViewForPositionAndType(@NonNull RecyclerView.Recycler recycler, int position, int type) {
                if (position == 1 && type == NORMAL_VIEW) {
                    if (mViewCacheExtension_view == null) {
                        mViewCacheExtension_view = mInflater.inflate(R.layout.activ_recycleview_item_normal, null, false);
                    }
                    recycler.bindViewToPosition(mViewCacheExtension_view,position);
                    return mViewCacheExtension_view;
                }
                return null;
            }
        };


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
            ALog.d("200729a-VideoDetailsActiv-onBindViewHolder-position->"+position);
            //---------
            if (getItemViewType(position) == HEAD_VIEW) {
                ((MViewHolder)viewHolder).title_head.setText("head_title----head_title----head_title----head_title----head_title----head_title----head_title----head_title----head_title----head_title----head_title----head_title----");
                setListHeadData((MViewHolder)viewHolder);
                ((MViewHolder)viewHolder).position = position;
                ((MViewHolder)viewHolder).parentView.setTag(position);
                return;
            }
            ((NormalViewHolder)viewHolder).title.setText("title-4-> " + list.get(position - 1));
            ((NormalViewHolder)viewHolder).parentView.setTag(position);
            ((NormalViewHolder)viewHolder).position = position;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = null;
            if (viewType == NORMAL_VIEW) {
                ALog.d("200729a-VideoDetailsActiv-onCreateViewHolder-viewType-NORMAL_VIEW->");
                view = mInflater.inflate(R.layout.activ_recycleview_item_normal, viewGroup, false);
                NormalViewHolder vh = new NormalViewHolder(view,viewType);
                return vh;
            } else if (viewType == HEAD_VIEW) {
                ALog.e("200729a-VideoDetailsActiv-onCreateViewHolder-viewType-HEAD_VIEW->");
                view = mInflater.inflate(R.layout.activ_recycleview_item_head, viewGroup, false);
                view.setBackground(null);
                MViewHolder vh = new MViewHolder(view,viewType);
                return vh;
            }
            return null;
        }

    }

    private boolean curDowning = false;

    private void setListHeadData(MViewHolder hMViewHolder){
        ///-------
        ALog.d("200723a-VideoDetailsActiv-setListHeadData"
                + "-setHeadDownAnima_cont->"+setHeadDownAnima_cont
        );
//        if (setHeadDownAnima_cont > 3) {
//            return;
//        }
        Drawable downDr = hMViewHolder.download_head.getCompoundDrawables()[1];
        if (curDowning) {
            if (downDr instanceof AnimatedVectorDrawableCompat) {
                boolean isRunning = ((AnimatedVectorDrawableCompat) downDr).isRunning();
                ALog.i("200723a-VideoDetailsActiv-setListHeadData-downDr-isRunning->"+isRunning);
                if (isRunning) {
                } else {
                    setHeadDownAnima(hMViewHolder.download_head,true);
                }
            } else {
                setHeadDownAnima(hMViewHolder.download_head,true);
            }
        } else {
            if (downDr instanceof AnimatedVectorDrawableCompat) {
                setHeadDownAnima(hMViewHolder.download_head,false);
            }
        }

    }


    private int  setHeadDownAnima_cont = 1;

    private void setHeadDownAnima(TextView download_head, boolean anima_mark){
        setHeadDownAnima_cont++;
        ALog.d("200723a-VideoDetailsActiv-setHeadDownAnima"
                + "-anima_mark->"+anima_mark
                + "-setHeadDownAnima_cont->"+setHeadDownAnima_cont
        );
        if (setHeadDownAnima_cont > 3) {
            return;
        }
        curDowning = anima_mark;
        Drawable hDrawable = download_head.getCompoundDrawables()[1];
        if (hDrawable instanceof AnimatedVectorDrawableCompat) {
            ((AnimatedVectorDrawableCompat)hDrawable).clearAnimationCallbacks();
        }
        Drawable animatedVectorDrawable;
        if (anima_mark) {
            animatedVectorDrawable = AnimatedVectorDrawableCompat.create(RecycleviewActivity.this, R.drawable.svg_icon_download_anima);
            ((AnimatedVectorDrawableCompat)animatedVectorDrawable).registerAnimationCallback(new Animatable2Compat.AnimationCallback(){
                @Override
                public void onAnimationEnd(Drawable drawable) {
                    ALog.i("200723a-VideoDetailsActiv-down_svg-onAnimationEnd-01->");
                    ((AnimatedVectorDrawableCompat)animatedVectorDrawable).start();
                }
            });
        } else {
            animatedVectorDrawable = ContextCompat.getDrawable(RecycleviewActivity.this, R.drawable.svg_icon_download);
        }
        download_head.setCompoundDrawablePadding(dp_2);
        animatedVectorDrawable.setBounds(0, 0, dp_28, dp_28);
        download_head.setCompoundDrawables(null,animatedVectorDrawable,null,null);
        download_head.setPadding(0,0,0,dp_1);
        if (animatedVectorDrawable instanceof AnimatedVectorDrawableCompat) {
            ((AnimatedVectorDrawableCompat)animatedVectorDrawable).start();
        }
    }






}
