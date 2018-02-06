package cn.molong.www.netease.splash;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import cn.molong.www.netease.R;

/**
 * Created by 胡锦龙_Squall on 2018/1/25.
 */

public class TimeView extends View {
    //属性

    //文字画笔
    TextPaint mTextPaint;
    Paint circleP;
    Paint outerP;

    String content = "跳过";
    //文字的间距
    int pading = 10;
    //内圆的直径
    int inner;
    //外圆的直径
    int all;
    //外圈的角度
    int dgree;

    RectF outerRect;


    public TimeView(Context context) {
        super(context);
    }

    public TimeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //获取到xml定义的属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TimeView);
        int innerColor = array.getColor(R.styleable.TimeView_innerColor, Color.BLUE);
        int outerColor = array.getColor(R.styleable.TimeView_ringColor, Color.GREEN);

        mTextPaint = new TextPaint();

        //抗锯齿
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(20);
        mTextPaint.setColor(Color.WHITE);

        //内圈画笔
        circleP = new Paint();
        circleP.setFlags(Paint.ANTI_ALIAS_FLAG);
        circleP.setColor(innerColor);

        //外圈
        outerP = new Paint();
        outerP.setFlags(Paint.ANTI_ALIAS_FLAG);
        outerP.setColor(outerColor);
        outerP.setStyle(Paint.Style.STROKE);
        outerP.setStrokeWidth(pading);

        //文字的宽度
        float text_Width = mTextPaint.measureText(content);
        //内圆的直径
        this.inner = (int) text_Width+2*pading;
        //外圆的直径
        all = this.inner +2*pading;

        outerRect = new RectF(pading/2,pading/2,all-pading/2,all-pading/2);
        //回收
        array.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(all,all);
    }

    /**
     * 画图
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {

//        canvas.drawColor(Color.RED);

        canvas.drawCircle(all/2,all/2,inner/2,circleP);

        //旋转-90度
        canvas.save();
        canvas.rotate(-90,all/2,all/2);
        canvas.drawArc(outerRect,0f,dgree,false,outerP);
        canvas.restore();

        //画文字
        float y = canvas.getHeight()/2;
        float de = mTextPaint.descent();
        float a = mTextPaint.ascent();
        canvas.drawText(content,2*pading,y-(de+a)/2,mTextPaint);
    }

    /**
     * 设置进度
     * @param total
     * @param now
     */
    public void setProgess(int total, int now) {
        int space = 360/total;
        dgree = space*now;
        //刷新UI
        invalidate();

    }
}
