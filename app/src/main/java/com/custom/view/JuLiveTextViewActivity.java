package com.custom.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.custom.view.julivetextview.rectstateview.RectStateView;


public class JuLiveTextViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ju_live_text_view);
        RectStateView rectStateView = findViewById(R.id.rsv);
        rectStateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSelected = v.isSelected();
                if (isSelected) {
                    //Do something ...
                } else {
                    //Do something ...
                }
                v.setSelected(!isSelected);
            }
        });

    }
}
