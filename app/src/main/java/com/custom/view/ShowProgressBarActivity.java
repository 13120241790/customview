package com.custom.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.custom.view.progressbar.HorizontalProgressBarWithNumber;

import java.lang.ref.WeakReference;

public class ShowProgressBarActivity extends AppCompatActivity {

    private static HorizontalProgressBarWithNumber mProgressBar;


    private static final int MSG_PROGRESS_UPDATE = 0x110;

    private static MyHandler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_progress);
        mProgressBar = (HorizontalProgressBarWithNumber) findViewById(R.id.id_progressbar01);
        mHandler = new MyHandler<>(this);
        mHandler.sendEmptyMessage(MSG_PROGRESS_UPDATE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
    }


    static class MyHandler<T> extends Handler {

        // WeakReference to the outer class's instance.
        private WeakReference<T> mOuter;

        MyHandler(T t) {
            mOuter = new WeakReference<>(t);
        }


        @Override
        public void handleMessage(Message msg) {
            T t = mOuter.get();
            if (t instanceof Activity) {
                // Do something with outer as your wish.
                int progress = mProgressBar.getProgress();
                mProgressBar.setProgress(++progress);
                if (progress >= 100) {
                    mHandler.removeMessages(MSG_PROGRESS_UPDATE);

                }
                mHandler.sendEmptyMessageDelayed(MSG_PROGRESS_UPDATE, 100);
            }
        }
    }
}
