package com.custom.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.custom.view.surfaceview.LucyPan;

public class ShowSurfaceViewLucyPanActivity extends AppCompatActivity {

    private LucyPan lucyPan;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface_view);
        lucyPan = findViewById(R.id.id_lucy_pan);
        imageView = findViewById(R.id.id_img_status);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!lucyPan.isStart()) {
                    lucyPan.lucyStart();
                    imageView.setImageResource(R.mipmap.stop);
                } else {
                    if (!lucyPan.isShouldEnd()) {
                        lucyPan.lucyStop();
                        imageView.setImageResource(R.mipmap.start);
                    }
                }
            }
        });
    }

}
