package com.custom.view.julivetextview.rectstateview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.custom.view.R;

public class RectStateView extends LinearLayout {
    /**
     * 圆角背景控件
     */
    private RectSubView mRectSubView;
    /**
     * 文字左侧 ImageView
     */
    private ImageView mLeftImageView;
    /**
     * 居中文本
     */
    private TextView mCenterTextView;

    /**
     * 图片的选中状态和非选中状态
     */
    private Drawable mDrawableLeftUnchecked;
    private Drawable mDrawableLeftChecked;

    /**
     * 文字内容的选中和非选中状态
     */
    private String mTextUnchecked;
    private String mTextChecked;

    /**
     * 文字颜色的选中和非选中状态
     */
    private int mTextColorUncheck;
    private int mTextColorCheck;

    /**
     * 矩形背景颜色的选中和非选中状态
     */
    private int mRectColorUncheck;
    private int mRectColorCheck;

    public RectStateView(Context context) {
        this(context, null);
    }

    public RectStateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RectStateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(getContext(), R.layout.rect_state_view, this);
        initView();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RectStateView);
        initAttrs(typedArray, context);
        typedArray.recycle();
    }

    private void initView() {
        mRectSubView = findViewById(R.id.rect_background);
        mLeftImageView = findViewById(R.id.left_icon);
        mCenterTextView = findViewById(R.id.center_text);
    }

    /**
     * 初始化自定义属性 和 默认状态
     */
    private void initAttrs(TypedArray typedArray, Context context) {
        boolean isSelected = typedArray.getBoolean(R.styleable.RectStateView_isSelected, false);

        //图片大小和状态
        mDrawableLeftChecked = typedArray.getDrawable(R.styleable.RectStateView_leftIconChecked);
        mDrawableLeftUnchecked = typedArray.getDrawable(R.styleable.RectStateView_leftIconUnchecked);
        float imageSize = typedArray.getDimension(R.styleable.RectStateView_leftIconSize, 0);
        if ((mDrawableLeftChecked != null || mDrawableLeftUnchecked != null) && imageSize != 0) {
            mLeftImageView.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams params = mLeftImageView.getLayoutParams();
            params.width = (int) imageSize;
            params.height = (int) imageSize;
            mLeftImageView.setLayoutParams(params);
            mLeftImageView.setImageDrawable(mDrawableLeftUnchecked);
        }
        //文字颜色、大小、内容
        mTextColorUncheck = typedArray.getColor(R.styleable.RectStateView_textColorUnchecked, 0);
        mTextColorCheck = typedArray.getColor(R.styleable.RectStateView_textColorChecked, 0);

        mTextUnchecked = typedArray.getString(R.styleable.RectStateView_textUnchecked);
        mTextChecked = typedArray.getString(R.styleable.RectStateView_textChecked);

        float textSize = typedArray.getDimension(R.styleable.RectStateView_textSize, 14);
        mCenterTextView.setTextSize(px2sp(context, textSize));

        //矩形背景的颜色 圆角大小
        mRectColorUncheck = typedArray.getColor(R.styleable.RectStateView_rectColorUnchecked, 0); //TODO 默认未选中状态颜色
        mRectColorCheck = typedArray.getColor(R.styleable.RectStateView_rectColorChecked, 0);
        float rectRadius = typedArray.getDimension(R.styleable.RectStateView_rectBackgroundRadius, 0);

        mRectSubView.setRectRadius(rectRadius);
        changeStatus(isSelected);
    }

    @Override
    public void setSelected(boolean selected) {
        Log.e(RectStateView.class.getSimpleName(), String.valueOf(selected));
        if (mTextColorCheck == 0 && mRectColorCheck == 0) { //如果选中状态的背景颜色 和 文本颜色没有指定 认为不需要选中状态
            return;
        }
        changeStatus(selected);
        super.setSelected(selected);
    }

    /**
     * 变更 View 状态
     *
     * @param selected 变更 View 状态
     */
    public void changeStatus(boolean selected) {
        mRectSubView.setRectColor(selected ? mRectColorCheck : mRectColorUncheck);
        mCenterTextView.setText(selected ? mTextChecked : mTextUnchecked);
        mCenterTextView.setTextColor(selected ? mTextColorCheck : mTextColorUncheck);
        if (mDrawableLeftChecked != null) {
            mLeftImageView.setImageDrawable(selected ? mDrawableLeftChecked : mDrawableLeftUnchecked);
        }
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    private int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

}
