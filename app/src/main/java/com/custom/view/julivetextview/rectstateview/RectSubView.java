package com.custom.view.julivetextview.rectstateview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * RectStateView 的子 View
 */
public class RectSubView extends View {

    private Paint mPaint;
    private RectF mRect;
    private float mRectRadius;

    public RectSubView(Context context) {
        this(context, null);
    }

    public RectSubView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RectSubView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mRect = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mRect.left = 0;
        mRect.right = getWidth();
        mRect.top = 0;
        mRect.bottom = getHeight();
        canvas.drawRoundRect(mRect, mRectRadius, mRectRadius, mPaint);
    }

    /**
     * 设置背景圆角角度
     *
     * @param rectRadius 角度
     */
    public void setRectRadius(float rectRadius) {
        mRectRadius = rectRadius;
    }

    /**
     * 设置背景颜色
     *
     * @param color 颜色
     */
    public void setRectColor(int color) {
        mPaint.setColor(color);
    }
}
