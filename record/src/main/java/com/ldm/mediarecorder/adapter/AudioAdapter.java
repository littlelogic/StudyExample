package com.ldm.mediarecorder.adapter;

import android.content.Context;
import com.ldm.mediarecorder.base.CommonAdapter;
import com.ldm.mediarecorder.base.ViewHolder;
import com.ldm.mediarecorder.model.FileBean;
import com.share.badlogic.R;

import java.util.List;

/**
 * description： 作者：ldm 时间：20172017/2/9 15:45 邮箱：1786911211@qq.com
 */
public class AudioAdapter extends CommonAdapter<FileBean> {
    public AudioAdapter(Context mContext, List<FileBean> mDatas, int itemResId) {
        super(mContext, mDatas, itemResId);
    }

    @Override
    public void convert(ViewHolder holder, FileBean fileBean) {
        holder.setTextView(R.id.item_tv, "录音文件：" + fileBean.getFile().getAbsolutePath() + "\n录音时长：" + fileBean.getFileLength() + "s");
    }
}
