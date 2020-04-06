package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ServiceLifecycleActivity extends AppCompatActivity {
    String TAG = "ServiceLifecycleActivity ";
    Button btnStart;
    Button btnStop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_lifecycle);

        btnStart=findViewById(R.id.btnStart);
        btnStart.setOnClickListener(mClick);
        btnStop=findViewById(R.id.btnStop);
        btnStop.setOnClickListener(mClick);
    }

    View.OnClickListener mClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnStart:
                    Log.v(TAG,"onClick_btnStart"+btnStart);
                    Intent intent = new Intent(getApplicationContext(),SubServiceLifecycle.class);
                    intent.putExtra("MSG", "나나나나나나나나");
                    startService(intent);
                    //만약 Service객체가 없으면 생성 수행
                    //onCreate() -> onStarcCommend()호출
                    //만약 Service객체가 존재하고 있으면 onStartCommand()호출
                    break;
                case R.id.btnStop:
                    Log.v(TAG,"mClick"+btnStop);
                    stopService(new Intent(getApplicationContext(),SubServiceLifecycle.class));
                    break;
            }
        }
    };
}
