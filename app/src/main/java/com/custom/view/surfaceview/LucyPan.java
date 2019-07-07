package com.custom.view.surfaceview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.custom.view.R;

public class LucyPan extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder holder;
    private Canvas canvas;
    private Thread thread;
    private boolean isRunning;


    private String[] strs = new String[]{"单反相机", "IPAD", "恭喜发财", "IPHONE", "服装一套", "恭喜发财"};
    private int[] imgs = new int[]{R.mipmap.danfan, R.mipmap.ipad, R.mipmap.f015, R.mipmap.iphone, R.mipmap.meizi, R.mipmap.f040};
    private Bitmap[] bitmaps;
    //背景的 bitmap
    private Bitmap bgBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bg2);
    private int[] color = new int[]{0xFFFFC300, 0xfff17e01, 0xFFFFC300, 0xfff17e01, 0xFFFFC300, 0xfff17e01};
    private int itemsCount = 6;


    // 整个盘块范围
    private RectF range;
    //绘制盘块的画笔
    private Paint arcPaint;
    //绘制文本的画笔
    private Paint textPaint;
    //写死的默认大小
    private float textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, getResources().getDisplayMetrics());


    //滚动速度
    private double speed = 0;
    //绘制角度 保证线程间可见性
    private volatile float startAngle = 0;
    //判断是否点击了停止按钮
    private boolean isShouldEnd;
    //转盘的中心位置
    private int center;
    //因为是原形直接取 padding 最小值
    private int padding;
    //半径
    private int radius;


    public LucyPan(Context context) {
        this(context, null);
    }

    public LucyPan(Context context, AttributeSet attrs) {
        super(context, attrs);
        holder = getHolder();
        holder.addCallback(this);//本类实现 surfaceCreated surfaceChanged surfaceDestroyed 三个接口
        //可获得焦点
        setFocusable(true);
        setFocusableInTouchMode(true);
        //获得常量
        setKeepScreenOn(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //盘的宽度
        int width = Math.min(getMeasuredWidth(), getMeasuredHeight());
        padding = getPaddingLeft();
        // 直径
        radius = width - padding * 2;
        //中心点
        center = width / 2;
        setMeasuredDimension(width, width);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //初始化绘制盘块的画笔
        arcPaint = new Paint();
        arcPaint.setAntiAlias(true);
        arcPaint.setDither(true);

        //初始化绘制盘块的画笔
        textPaint = new Paint();
        textPaint.setColor(0XFFFFFFFF);
        textPaint.setTextSize(textSize);

        //初始化整个盘块的范围
        range = new RectF(padding, padding, padding + radius, padding + radius);

        //初始化图片
        bitmaps = new Bitmap[itemsCount];

        for (int i = 0; i < bitmaps.length; i++) {
            bitmaps[i] = BitmapFactory.decodeResource(getResources(), imgs[i]);
        }


        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
    }

    @Override
    public void run() {
        //不断进行绘制
        while (isRunning) {
            long start = System.currentTimeMillis();
            draw();
            long end = System.currentTimeMillis();
            if (end - start < 50) { //每次绘制不足 50 毫秒通过睡眠方式补足
                try {
                    Thread.sleep(50 - (end - start));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void draw() {
        try {
            canvas = holder.lockCanvas();
            if (canvas != null) {
                drawBg();//绘制背景
                //绘制盘块
                float temAngle = startAngle; //起始角度
                float sweepAngle = 360 / itemsCount;
                //每次循环绘制一个盘块 包含盘块的 文字 和 图片
                for (int i = 0; i < itemsCount; i++) {
                    //绘制盘块
                    arcPaint.setColor(color[i]);
                    canvas.drawArc(range, temAngle, sweepAngle, true, arcPaint);

                    //绘制文本
                    drawText(temAngle, sweepAngle, strs[i]);
                    //绘制盘块上的图片
                    drawIcon(temAngle, bitmaps[i]);

                    temAngle += sweepAngle;

                }
                startAngle += speed;

                //如果点击了停止按钮
                if (isShouldEnd) {
                    speed -= 1; //让转盘速度缓慢停下
                }
                if (speed <= 0) {
                    speed = 0;
                    isShouldEnd = false;
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null) { //如果绘制过程中发生异常释放资源
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    /**
     * 绘制每个盘块的图片
     *
     * @param temAngle
     * @param bitmap
     */
    private void drawIcon(float temAngle, Bitmap bitmap) {
        int imgWidth = radius / 8; //约束下图片的宽为直径 1/8

        //度数 * 每度所占的弧长大小
        float angle = (float) ((temAngle + 360 / itemsCount / 2) * Math.PI / 180);

        //通过三角函数算出图片中心点的位置
        int x = (int) (center + radius / 2 / 2 * Math.cos(angle));
        int y = (int) (center + radius / 2 / 2 * Math.sin(angle));

        //确定图片的位置
        Rect rect = new Rect(x - imgWidth / 2, y - imgWidth / 2, x + imgWidth / 2, y + imgWidth / 2);
        canvas.drawBitmap(bitmap, null, rect, null);
    }

    /**
     * 绘制每个盘块的文本
     *
     * @param temAngle
     * @param sweepAngle
     * @param str
     */
    private void drawText(float temAngle, float sweepAngle, String str) {
        //弧形
        Path path = new Path();
        path.addArc(range, temAngle, sweepAngle);

        //调整文字的位置使其居中 设置 偏移量
        float textWidth = textPaint.measureText(str);
        int hOffset = (int) (radius * Math.PI / itemsCount / 2 - textWidth / 2);


        canvas.drawTextOnPath(str, path, hOffset, radius / 12, textPaint);


    }

    /**
     * 绘制背景
     */
    private void drawBg() {
        canvas.drawColor(0XFFFFFFFF);
        canvas.drawBitmap(bgBitmap, null, new RectF(padding / 2, padding / 2, getMeasuredWidth() - padding / 2, getMeasuredHeight() - padding / 2), null);
    }

    /**
     * 点击启动旋转
     */
    public void lucyStart() {
        speed = 50;
        isShouldEnd = false;

    }

    /**
     * 点击停止旋转
     */
    public void lucyStop() {
        isShouldEnd = true;
    }

    /**
     * 转盘是否还在转
     *
     * @return
     */
    public boolean isStart() {
        return speed != 0;
    }


    public boolean isShouldEnd() {
        return isShouldEnd;
    }
}
