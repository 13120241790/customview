package com.custom.view.julivetextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;

import com.custom.view.R;

public class CircularTextView extends AppCompatTextView {

    private float mCircularRadius;

    private RectF circularRect;

    private Paint mPaint;

    public CircularTextView(Context context) {
        this(context, null);
    }

    public CircularTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircularTextView);
        mCircularRadius = typedArray.getDimension(R.styleable.CircularTextView_circularRadius, 0);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(getCurrentTextColor());
        mPaint.setStrokeWidth(2);
        mPaint.setAntiAlias(true);
        circularRect = new RectF();
        setGravity(Gravity.CENTER);
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        circularRect.left = 0;
        circularRect.right = getWidth();
        circularRect.top = 0;
        circularRect.bottom = getHeight();
        canvas.drawRoundRect(circularRect, mCircularRadius, mCircularRadius, mPaint);
    }
}
