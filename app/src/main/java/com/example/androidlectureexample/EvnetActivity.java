package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class EvnetActivity extends AppCompatActivity {
    ImageView iv_01;
    Button btn_imagechange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evnet);

        iv_01 = findViewById(R.id.iv_01);
        btn_imagechange = findViewById(R.id.btn_imagechange);
        btn_imagechange.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                iv_01.setImageResource(R.drawable.android_image2);
            }
        });
    }
}
