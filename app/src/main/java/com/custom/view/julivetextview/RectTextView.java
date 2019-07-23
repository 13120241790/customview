package com.custom.view.julivetextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;

import com.custom.view.R;

public class RectTextView extends AppCompatTextView {

    private Paint mPaint;
    private RectF mRect;
    private float mRectRadius;

    public RectTextView(Context context) {
        this(context, null);
    }

    public RectTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RectTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RectTextView);
        mRectRadius = typedArray.getDimension(R.styleable.RectTextView_rectRadius, 0);
        int rectColor = typedArray.getColor(R.styleable.RectTextView_rectColor, 0);
        mPaint = new Paint();
        mPaint.setColor(rectColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mRect = new RectF();
        setGravity(Gravity.CENTER);
//        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
//        drawable.setBounds(0, 0, (int) getTextSize(), (int) getTextSize());
//        Log.e("padding", "getCompoundPaddingLeft :" + getCompoundPaddingLeft());
//        setCompoundDrawables(drawable, null, null, null);
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mRect.left = 0;
        mRect.right = getWidth();
        mRect.top = 0;
        mRect.bottom = getHeight();
        canvas.drawRoundRect(mRect, mRectRadius, mRectRadius, mPaint);
        super.onDraw(canvas);
    }

}
