package com.custom.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.custom.view.constraintlayout.ConstraintLayoutActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void constraintLayout(View view) {
        startActivity(new Intent(this, ConstraintLayoutActivity.class));
    }

    public void showProgressBar(View view) {
        startActivity(new Intent(this, ShowProgressBarActivity.class));
    }
}
