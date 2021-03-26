package com.example.wujiawen.ExampleTextView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

import com.badlogic.utils.ALog;


/**
 * Created by zhangshuo-b on 2016/7/15.
 */
public class VerticalCentreImageSpan extends ImageSpan {

    public VerticalCentreImageSpan(Drawable drawable) {
        super(drawable);
    }

    public VerticalCentreImageSpan(Context context, Bitmap bitmap){
        super(context, bitmap);
    }

    public int getSize(Paint paint, CharSequence text, int start, int end,
                       Paint.FontMetricsInt fontMetricsInt) {
        ALog.e("wjw01", "--VerticalCentreImageSpan--getSize--fontMetricsInt.top-->>"+paint.getFontMetrics().top);
        ALog.e("wjw01", "--VerticalCentreImageSpan--getSize--fontMetricsInt.ascent-->>"+paint.getFontMetrics().ascent);
        ALog.e("wjw01", "--VerticalCentreImageSpan--getSize--fontMetricsInt.descent-->>"+paint.getFontMetrics().descent);
        ALog.e("wjw01", "--VerticalCentreImageSpan--getSize--fontMetricsInt.bottom-->>"+paint.getFontMetrics().bottom);

        Drawable drawable = getDrawable();
        Rect rect = drawable.getBounds();
        if (fontMetricsInt != null) {
            ALog.e("wjw01", "--VerticalCentreImageSpan--getSize--fontMetricsInt.top-->>"+fontMetricsInt.top);
            ALog.e("wjw01", "--VerticalCentreImageSpan--getSize--fontMetricsInt.ascent-->>"+fontMetricsInt.ascent);
            ALog.e("wjw01", "--VerticalCentreImageSpan--getSize--fontMetricsInt.descent-->>"+fontMetricsInt.descent);
            ALog.e("wjw01", "--VerticalCentreImageSpan--getSize--fontMetricsInt.bottom-->>"+fontMetricsInt.bottom);
            Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
            int fontHeight = fmPaint.bottom - fmPaint.top;
            int drHeight = rect.bottom - rect.top;

            int top = drHeight / 2 - fontHeight / 4;
            int bottom = drHeight / 2 + fontHeight / 4;

            fontMetricsInt.ascent = -bottom;
            fontMetricsInt.top = -bottom;
            fontMetricsInt.bottom = top;
            fontMetricsInt.descent = top;
            ALog.e("wjw01", "--VerticalCentreImageSpan--getSize-3-fontMetricsInt.top-->>"+fontMetricsInt.top);
            ALog.e("wjw01", "--VerticalCentreImageSpan--getSize-3-fontMetricsInt.ascent-->>"+fontMetricsInt.ascent);
            ALog.e("wjw01", "--VerticalCentreImageSpan--getSize-3-fontMetricsInt.descent-->>"+fontMetricsInt.descent);
            ALog.e("wjw01", "--VerticalCentreImageSpan--getSize-3-fontMetricsInt.bottom-->>"+fontMetricsInt.bottom);
            ALog.is("wjw01","VerticalCentreImageSpan--getSize-fontMetricsInt.top:",fontMetricsInt.top,"-fontMetricsInt.ascent:",
                    fontMetricsInt.ascent,"-fontMetricsInt.descent:",fontMetricsInt.descent,"-fontMetricsInt.bottom:",fontMetricsInt.bottom);
            ALog.is("wjw01","VerticalCentreImageSpan--getSize-fontMetricsInt.top:",null,null,null,null);
            ALog.is("wjw01","VerticalCentreImageSpan--getSize-fontMetricsInt.top:");
        }
        return rect.right;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end,
                     float x, int top, int y, int bottom, Paint paint) {

        ALog.e("wjw01", "--VerticalCentreImageSpan--draw-text-->>"+text);
        ALog.e("wjw01", "--VerticalCentreImageSpan--draw-start-->>"+start);
        ALog.e("wjw01", "--VerticalCentreImageSpan--draw-end-->>"+end);
        ALog.e("wjw01", "--VerticalCentreImageSpan--draw-x-->>"+x);
        ALog.e("wjw01", "--VerticalCentreImageSpan--draw-y-->>"+y);
        ALog.e("wjw01", "--VerticalCentreImageSpan--draw-top-->>"+top);
        ALog.e("wjw01", "--VerticalCentreImageSpan--draw-bottom-->>"+bottom);
        ALog.e("wjw01", "--VerticalCentreImageSpan--draw--fontMetricsInt.top-->>"+paint.getFontMetrics().top);
        ALog.e("wjw01", "--VerticalCentreImageSpan--draw--fontMetricsInt.ascent-->>"+paint.getFontMetrics().ascent);
        ALog.e("wjw01", "--VerticalCentreImageSpan--draw--fontMetricsInt.descent-->>"+paint.getFontMetrics().descent);
        ALog.e("wjw01", "--VerticalCentreImageSpan--draw--fontMetricsInt.bottom-->>"+paint.getFontMetrics().bottom);

        Drawable drawable = getDrawable();
        canvas.save();
        int transY = 0;
        transY = ((bottom - top) - drawable.getBounds().bottom) / 2 + top;
        transY = top;//wjw--
        canvas.translate(x, transY);
        drawable.draw(canvas);
        canvas.restore();

        ALog.e("wjw01", "--VerticalCentreImageSpan--draw--transY-->>"+transY);
    }


    /**
     *
     *
     ascent: 字体最上端到基线的距离，为负值。
     descent：字体最下端到基线的距离，为正值。
     中间那条线就是基线，基线到上面那条线的距离就是ascent，基线到下面那条线的距离就是descent。

     1. 基准点是baseline
     2. Ascent是baseline之上至字符最高处的距离
     3. Descent是baseline之下至字符最低处的距离
     4. Leading文档说的很含糊，这个我还不清楚，但有人说是上一行字符的descent到下一行的ascent之间的距离，也有人说不是
     5. Top指的是指的是最高字符到baseline的值，即ascent的最大值
     6. 同上，bottom指的是最下字符到baseline的值，即descent的最大值

             x，要绘制的image的左边框到textview左边框的距离。
             y，要替换的文字的基线坐标，即基线到textview上边框的距离。
             top，替换行的最顶部位置。
             bottom，替换行的最底部位置。注意，textview中两行之间的行间距是属于上一行的，所以这里bottom是指行间隔的底部位置。
             paint，画笔，包含了要绘制字体的度量信息。
             这几个参数含义在代码中找不到说明，写了个demo测出来的。top和bottom参数只是解释下，函数里面用不上。
             然后解释下代码逻辑：
             getDrawable获取要绘制的image，getBounds是获取包裹image的矩形框尺寸；
             y + fm.descent得到字体的descent线坐标；
             y + fm.ascent得到字体的ascent线坐标；
             两者相加除以2就是两条线中线的坐标；
             b.getBounds().bottom是image的高度（试想把image放到原点），除以2即高度一半；
             前面得到的中线坐标减image高度的一半就是image顶部要绘制的目标位置；
             最后把目标坐标传递给canvas.translate函数就可以了，至于这个函数的理解先不管了。
     */
    public void draw___(Canvas canvas, CharSequence text,
                     int start, int end, float x,
                     int top, int y, int bottom, Paint paint) {
        // image to draw
        Drawable b = getDrawable();
        // font metrics of text to be replaced
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
        //图片的Y中心和（fm.descent，fm.ascent）的中心的对上的
        int transY = (y + fm.descent + y + fm.ascent) / 2 - b.getBounds().bottom / 2;

        canvas.save();
        canvas.translate(x, transY);
        b.draw(canvas);
        canvas.restore();
    }







}