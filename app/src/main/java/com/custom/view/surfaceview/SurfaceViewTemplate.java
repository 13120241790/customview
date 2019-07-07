package com.custom.view.surfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 一般标准使用 SurfaceView 的模板  @see LucyPan
 */
public class SurfaceViewTemplate extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder holder;
    private Canvas canvas;
    private Thread thread;
    private boolean isRunning;

    public SurfaceViewTemplate(Context context) {
        this(context, null);
    }

    public SurfaceViewTemplate(Context context, AttributeSet attrs) {
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
    public void surfaceCreated(SurfaceHolder holder) {
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
            draw();
        }
    }

    private void draw() {
        try {
            canvas = holder.lockCanvas();
            if (canvas != null) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null) { //如果绘制过程中发生异常释放资源
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }
}
