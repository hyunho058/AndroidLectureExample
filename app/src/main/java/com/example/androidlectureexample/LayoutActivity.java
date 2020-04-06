package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LayoutActivity extends AppCompatActivity {
    Button btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(mClick);
    }
    View.OnClickListener mClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            finish();
        }
    };
}
