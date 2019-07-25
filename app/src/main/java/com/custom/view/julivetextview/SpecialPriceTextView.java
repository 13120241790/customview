package com.custom.view.julivetextview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class SpecialPriceTextView extends AppCompatTextView {

    private Paint mLinePaint;

    public SpecialPriceTextView(Context context) {
        this(context, null);
    }

    public SpecialPriceTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpecialPriceTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mLinePaint = new Paint();
        mLinePaint.setColor(getCurrentTextColor());
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStrokeWidth(2.0f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0, getHeight() * 1.0f / 2, getWidth(), getHeight() * 1.0f / 2, mLinePaint);
    }
}
