package com.custom.view.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.custom.view.R;

public class CustomProgressBar extends View {

    private int firstColor;
    private int secondColor;
    private int circleWidth;
    private int speed;
    private Paint mPaint;
    private Paint textPaint;

    /**
     * 当前进度
     */
    private int mProgress;

    /**
     * 是否应该开始下一个
     */
    private boolean isNext = false;

    public CustomProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomProgressBar, defStyleAttr, 0);
        firstColor = a.getColor(R.styleable.CustomProgressBar_firstColor, Color.TRANSPARENT);
        secondColor = a.getColor(R.styleable.CustomProgressBar_secondColor, Color.YELLOW);
        circleWidth = (int) a.getDimension(R.styleable.CustomProgressBar_circleWidth, 30);
        speed = a.getInt(R.styleable.CustomProgressBar_speed, 20);
        a.recycle();

        mPaint = new Paint();
        textPaint = new Paint();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    mProgress++;
                    if (mProgress == 100) {
                        mProgress = 0;
                    }

//                    if (!isNext) {
//                        isNext = true;
//                    } else {
//                        isNext = false;
//                    }

                    postInvalidate();

                    try {
                        Thread.sleep(speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        int centre = getWidth() / 2;//获取圆形 x 坐标
        int radius = centre - circleWidth / 2;// 半径
        mPaint.setStrokeWidth(circleWidth); // 设置圆环的宽度
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStyle(Paint.Style.STROKE); // 设置空心
        RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius); // 用于定义的圆弧的形状和大小的界限
//        if (!isNext) {// 第一颜色的圈完整，第二颜色跑
        mPaint.setColor(firstColor); // 设置圆环的颜色 目前为透明色
        canvas.drawCircle(centre, centre, radius, mPaint); // 画出圆环
        mPaint.setColor(secondColor); // 设置圆环的颜色

        //画百分比文字
        textPaint.setTextSize(50);
        textPaint.setColor(Color.BLACK);
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        Rect textBound = new Rect();
        String mPercentText = mProgress + "%";
        textPaint.getTextBounds(mPercentText, 0, mPercentText.length(), textBound);
        canvas.drawText(mPercentText, getWidth() / 2 - textBound.width() / 2, getHeight() / 2 + textBound.height() / 2, textPaint);


//        canvas.drawText(mProgress + "%", centre - circleWidth, centre - circleWidth, textPaint);

        //画进度
        canvas.drawArc(oval, -90, (float) (mProgress * (3.6)), false, mPaint); // 根据进度画圆弧
//        } else {
//            mPaint.setColor(secondColor); // 设置圆环的颜色
//            canvas.drawCircle(centre, centre, radius, mPaint); // 画出圆环
//            mPaint.setColor(firstColor); // 设置圆环的颜色
//            canvas.drawArc(oval, -90, mProgress, false, mPaint); // 根据进度画圆弧
//        }

    }
}
