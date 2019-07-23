package com.custom.view.julivetextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

import com.custom.view.R;

public class JuLiveTextView extends AppCompatTextView {

    private static final String TAG = JuLiveTextView.class.getSimpleName();

    private int mode;

    private int filletColor;

    private float filletRadius;

    private int circularColor;

    private float circularLineSize;

    private float circularRadius;
    /**
     * 文字画笔
     */
    private Paint mPaint;

    private Paint filletPaint;

    private Paint circularPaint;

    private Rect mBound;

    private RectF filletRect;

    private RectF circularRect;



    /**
     * <declare-styleable name="JuLiveTextView">
     * <attr name="Mode">
     * <enum name="Fillet" value="0" /> <!--圆角模式-->
     * <enum name="Circular" value="1" /><!--圆形模式-->
     * </attr>
     * <attr name="filletColor" format="color" /> <!--圆角模式下背景颜色-->
     * <attr name="filletRadius" format="dimension" /> <!--圆角模式下圆角半径-->
     * <attr name="circularColor" format="color" /> <!--圆形模式下边框线颜色-->
     * <attr name="circularLineSize" format="dimension" /> <!--圆形模式下边框线粗细-->
     * </declare-styleable>
     */


    public JuLiveTextView(Context context) {
        this(context, null);
    }

    public JuLiveTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JuLiveTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.JuLiveTextView);
        mode = typedArray.getInt(R.styleable.JuLiveTextView_Mode, ShapeMode.NORMAL.getMode());
        if (mode == ShapeMode.FILLET.getMode()) {
            filletColor = typedArray.getColor(R.styleable.JuLiveTextView_filletColor, 0);
            filletRadius = typedArray.getDimension(R.styleable.JuLiveTextView_filletRadius, 0);

            filletPaint = new Paint();
            filletPaint.setColor(filletColor);
            filletPaint.setStyle(Paint.Style.FILL);
            filletPaint.setAntiAlias(true);

            filletRect = new RectF();


        } else if (mode == ShapeMode.CIRCULAR.getMode()) {
            circularColor = typedArray.getColor(R.styleable.JuLiveTextView_circularColor, 0);
            circularLineSize = typedArray.getDimension(R.styleable.JuLiveTextView_circularLineSize, 0);
            circularRadius = typedArray.getDimension(R.styleable.JuLiveTextView_filletRadius, 0);

            circularPaint = new Paint();
            circularPaint.setStyle(Paint.Style.STROKE);
            circularPaint.setColor(circularColor);
            circularPaint.setStrokeWidth(circularLineSize);
            circularPaint.setAntiAlias(true);

            circularRect = new RectF();
        } else {
            throw new IllegalArgumentException("ShapeMode must be specified as FILLET or CIRCULAR, otherwise use TextView");
        }
        mPaint = new Paint();
        mPaint.setTextSize(getTextSize());
        mPaint.setAntiAlias(true);
        mPaint.setColor(getTextColors().getDefaultColor());
        mBound = new Rect();
        mPaint.getTextBounds(getText().toString(), 0, getText().length(), mBound);
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG, "with = " + getWidth());
        Log.e(TAG, "MeasuredHeight = " + getMeasuredHeight());
        Log.e(TAG, "height = " + getHeight());
        Log.e(TAG, "MeasuredHeight = " + getMeasuredHeight());

        Log.e(TAG, "getLeft = " + getLeft());
        Log.e(TAG, "getRight = " + getRight());
        Log.e(TAG, "getTop = " + getTop());
        Log.e(TAG, "getBottom = " + getBottom());

        Log.e(TAG, "getPaddingLeft = " + getPaddingLeft());
        Log.e(TAG, "getPaddingRight = " + getPaddingRight());
        Log.e(TAG, "getPaddingTop = " + getPaddingTop());
        Log.e(TAG, "getPaddingBottom = " + getPaddingBottom());

//        setClipBounds();

//        getBackground().setBounds();
        if (mode == ShapeMode.FILLET.getMode()) {
            filletRect.left = 0;
            filletRect.right = getWidth();
            filletRect.top = 0;
            filletRect.bottom = getHeight();
            canvas.drawRoundRect(filletRect, filletRadius, filletRadius, filletPaint);
            canvas.drawText(getText().toString(), getWidth() / 2 - mBound.width() * 1.0f / 2, getHeight() / 2 + mBound.height() * 1.0f / 2, mPaint);
        } else {
            circularRect.left = 0;
            circularRect.right = getWidth();
            circularRect.top = 0;
            circularRect.bottom = getHeight();
            canvas.drawRoundRect(circularRect, circularRadius, circularRadius, circularPaint);
        }
    }

    public enum ShapeMode {
        /**
         * 普通模式
         */
        NORMAL(0),
        /**
         * 圆角模式
         */
        FILLET(1),
        /**
         * 圆形模式
         */
        CIRCULAR(2);

        private int mode;

        ShapeMode(int mode) {
            this.mode = mode;
        }

        public int getMode() {
            return mode;
        }

        public static ShapeMode setValue(int mode) {
            for (ShapeMode s : ShapeMode.values()) {
                if (mode == s.getMode()) {
                    return s;
                }
            }
            return NORMAL;
        }
    }



    private int dpToPx(Context context, int dp) {
        return dp * (context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    private int pxToDp(Context context, int px) {
        return px / (context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    private float spToPx(Context context, float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    private int pxToSp(Context context, float px) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (px / fontScale + 0.5f);
    }
}
