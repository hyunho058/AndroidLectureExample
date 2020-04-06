package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SendMassageActivity extends AppCompatActivity {
    TextView tv_01;
    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_massage);

        tv_01 = findViewById(R.id.tv_01);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        tv_01.setText(intent.getStringExtra("sendMsg"));
        Log.v("SendMassageActivity","getStringExtra================"+intent.getExtras().get("sendMsg"));
    }
}
